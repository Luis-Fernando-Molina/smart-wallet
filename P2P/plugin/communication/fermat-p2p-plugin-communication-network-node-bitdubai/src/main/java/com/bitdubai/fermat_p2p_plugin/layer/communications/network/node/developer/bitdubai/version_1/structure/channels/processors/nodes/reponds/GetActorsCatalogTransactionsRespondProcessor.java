package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.reponds;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.RecordNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.GetActorCatalogTransactionsMsjRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.GetNodeCatalogTransactionsMsjRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.respond.GetActorCatalogTransactionsMsjRespond;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.respond.GetNodeCatalogTransactionsMsjRespond;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalogTransaction;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalogTransactionsPendingForPropagation;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalogTransaction;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalogTransactionsPendingForPropagation;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.util.List;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.nodes.GetNodeCatalogTransactionsRespondProcessor</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class GetActorsCatalogTransactionsRespondProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(GetActorsCatalogTransactionsRespondProcessor.class));

    /**
     * Constructor with parameter
     *
     * @param channel
     * */
    public GetActorsCatalogTransactionsRespondProcessor(FermatWebSocketChannelEndpoint channel) {
        super(channel, PackageType.GET_ACTOR_CATALOG_TRANSACTIONS_RESPOND);
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        LOG.info("Processing new package received");

        String channelIdentityPrivateKey = getChannel().getChannelIdentity().getPrivateKey();
        String destinationIdentityPublicKey = (String) session.getUserProperties().get(HeadersAttName.CPKI_ATT_HEADER_NAME);

        try {

            GetActorCatalogTransactionsMsjRespond messageContent =  GetActorCatalogTransactionsMsjRespond.parseContent(packageReceived.getContent());

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.OBJECT){


                if (messageContent.getStatus() == GetNodeCatalogTransactionsMsjRespond.STATUS.SUCCESS){

                     /*
                     * Get the block of transactions
                     */
                    List<ActorsCatalogTransaction>  transactionList = messageContent.getActorsCatalogTransaction();

                    for (ActorsCatalogTransaction actorsCatalogTransaction : transactionList) {

                        /*
                         * Process the transaction
                         */
                        processTransaction(actorsCatalogTransaction);
                    }


                    long totalRowInDb = getDaoFactory().getActorsCatalogDao().getAllCount();

                    if (totalRowInDb < messageContent.getCount()){

                        GetActorCatalogTransactionsMsjRequest getActorCatalogTransactionsMsjRequest = new GetActorCatalogTransactionsMsjRequest(transactionList.size(), 250);
                        Package packageRespond = Package.createInstance(getActorCatalogTransactionsMsjRequest.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.GET_ACTOR_CATALOG_TRANSACTIONS_REQUEST, channelIdentityPrivateKey, destinationIdentityPublicKey);

                        /*
                         * Send the respond
                         */
                        session.getAsyncRemote().sendObject(packageRespond);
                    }

                }else {

                    LOG.warn(messageContent.getStatus() + " - " + messageContent.getDetails());
                }

            }

        } catch (Exception exception){

            LOG.error(exception.getMessage());

        }

    }

    /**
     * Process the transaction
     * @param actorsCatalogTransaction
     */
    private int processTransaction(ActorsCatalogTransaction actorsCatalogTransaction) throws CantReadRecordDataBaseException, RecordNotFoundException, CantInsertRecordDataBaseException, CantUpdateRecordDataBaseException, CantDeleteRecordDataBaseException, InvalidParameterException {

        LOG.info("Executing method processTransaction");

        if (getDaoFactory().getActorsCatalogDao().exists(actorsCatalogTransaction.getIdentityPublicKey())){
            return 1;
        }else {

            switch (actorsCatalogTransaction.getTransactionType()){

                case ActorsCatalogTransaction.ADD_TRANSACTION_TYPE :
                    insertActorsCatalog(actorsCatalogTransaction);
                    break;

                case ActorsCatalogTransaction.UPDATE_TRANSACTION_TYPE :
                    updateActorsCatalog(actorsCatalogTransaction);
                    break;

                case ActorsCatalogTransaction.DELETE_TRANSACTION_TYPE :
                    deleteActorsCatalog(actorsCatalogTransaction.getIdentityPublicKey());
                    break;
            }

            insertActorsCatalogTransaction(actorsCatalogTransaction);
            insertActorsCatalogTransaction(actorsCatalogTransaction);

        }

        return 0;
    }

    /**
     * Create a new row into the data base
     *
     * @param actorsCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private void insertActorsCatalog(ActorsCatalogTransaction actorsCatalogTransaction) throws CantInsertRecordDataBaseException {

        LOG.info("Executing method insertActorsCatalog");

        /*
         * Create the ActorsCatalog
         */
        ActorsCatalog actorsCatalog = new ActorsCatalog();
        actorsCatalog.setIdentityPublicKey(actorsCatalogTransaction.getIdentityPublicKey());
        actorsCatalog.setActorType(actorsCatalogTransaction.getActorType());
        actorsCatalog.setAlias(actorsCatalogTransaction.getAlias());
        actorsCatalog.setExtraData(actorsCatalogTransaction.getExtraData());
        actorsCatalog.setHostedTimestamp(actorsCatalogTransaction.getHostedTimestamp());
        actorsCatalog.setLastLatitude(actorsCatalogTransaction.getLastLatitude());
        actorsCatalog.setLastLongitude(actorsCatalogTransaction.getLastLongitude());
        actorsCatalog.setName(actorsCatalogTransaction.getName());
        actorsCatalog.setNodeIdentityPublicKey(actorsCatalogTransaction.getNodeIdentityPublicKey());
        actorsCatalog.setPhoto(actorsCatalogTransaction.getPhoto());

        /*
         * Save into the data base
         */
        getDaoFactory().getActorsCatalogDao().create(actorsCatalog);
    }

    /**
     * Update a row into the data base
     *
     * @param actorsCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private void updateActorsCatalog(ActorsCatalogTransaction actorsCatalogTransaction) throws CantUpdateRecordDataBaseException, RecordNotFoundException, InvalidParameterException, CantReadRecordDataBaseException, CantInsertRecordDataBaseException {

        LOG.info("Executing method updateActorsCatalog");

        if(getDaoFactory().getActorsCatalogDao().exists(actorsCatalogTransaction.getIdentityPublicKey())){

            /*
             * Create the ActorsCatalog
             */
            ActorsCatalog actorsCatalog = new ActorsCatalog();
            actorsCatalog.setIdentityPublicKey(actorsCatalogTransaction.getIdentityPublicKey());
            actorsCatalog.setActorType(actorsCatalogTransaction.getActorType());
            actorsCatalog.setAlias(actorsCatalogTransaction.getAlias());
            actorsCatalog.setExtraData(actorsCatalogTransaction.getExtraData());
            actorsCatalog.setHostedTimestamp(actorsCatalogTransaction.getHostedTimestamp());
            actorsCatalog.setLastLatitude(actorsCatalogTransaction.getLastLatitude());
            actorsCatalog.setLastLongitude(actorsCatalogTransaction.getLastLongitude());
            actorsCatalog.setName(actorsCatalogTransaction.getName());
            actorsCatalog.setNodeIdentityPublicKey(actorsCatalogTransaction.getNodeIdentityPublicKey());
            actorsCatalog.setPhoto(actorsCatalogTransaction.getPhoto());

            /*
             * Save into the data base
             */
            getDaoFactory().getActorsCatalogDao().update(actorsCatalog);

        }else {

            insertActorsCatalog(actorsCatalogTransaction);
        }

    }

    /**
     * Delete a row from the data base
     *
     * @param identityPublicKey
     * @throws CantDeleteRecordDataBaseException
     * @throws RecordNotFoundException
     */
    private void deleteActorsCatalog(String identityPublicKey) throws CantDeleteRecordDataBaseException, RecordNotFoundException {

        LOG.info("Executing method deleteActorsCatalog");

        /*
         * Delete from the data base
         */
        getDaoFactory().getActorsCatalogDao().delete(identityPublicKey);
    }

    /**
     * Create a new row into the data base
     *
     * @param actorsCatalogTransaction
     * @throws CantInsertRecordDataBaseException
     */
    private void insertActorsCatalogTransaction(ActorsCatalogTransaction actorsCatalogTransaction) throws CantInsertRecordDataBaseException {

        LOG.info("Executing method insertActorsCatalogTransaction");

        /*
         * Save into the data base
         */
        getDaoFactory().getActorsCatalogTransactionDao().create(actorsCatalogTransaction);
    }
}