/*
 * @#PropagateNodeCatalogAgent.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.agents;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.RecordNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.clients.FermatWebSocketClientNodeChannel;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.ReceiveNodeCatalogTransactionsMsjRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.DaoFactory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.NodesCatalogDao;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.NodesCatalogTransactionsPendingForPropagationDao;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalogTransaction;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalogTransactionsPendingForPropagation;

import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.agents.PropagateNodeCatalogAgent</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PropagateNodeCatalogAgent extends FermatAgent {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(PropagateNodeCatalogAgent.class.getName());

    /**
     * Represent the propagation time
     */
    private final int PROPAGATION_TIME = 10;

    /**
     * Represent the scheduledThreadPool
     */
    private ScheduledExecutorService scheduledThreadPool;

    /**
     * Represent the scheduledFutures
     */
    private List<ScheduledFuture> scheduledFutures;

    /**
     * Represent the nodesCatalogDao
     */
    private NodesCatalogDao nodesCatalogDao;

    /**
     * Represent the nodesCatalogTransactionsPendingForPropagationDao
     */
    private NodesCatalogTransactionsPendingForPropagationDao nodesCatalogTransactionsPendingForPropagationDao;

    /**
     * Constructor
     */
    public PropagateNodeCatalogAgent(){
        this.scheduledThreadPool = Executors.newScheduledThreadPool(1);
        this.scheduledFutures    = new ArrayList<>();
        this.nodesCatalogDao     = ((DaoFactory) NodeContext.get(NodeContextItem.DAO_FACTORY)).getNodesCatalogDao();
        this.nodesCatalogTransactionsPendingForPropagationDao = ((DaoFactory) NodeContext.get(NodeContextItem.DAO_FACTORY)).getNodesCatalogTransactionsPendingForPropagationDao();
    }

    /**
     * Propagation logic implementation
     */
    private void propagateCatalog() throws CantReadRecordDataBaseException, CantUpdateRecordDataBaseException, RecordNotFoundException, InvalidParameterException {

        LOG.info("Executing propagateCatalog()");

        List<NodesCatalog> nodesCatalogsList = nodesCatalogDao.getNodeCatalogueListToShare();
        List<NodesCatalogTransaction> transactionList = getNodesCatalogTransactionsPendingForPropagationBlock();

        if ((nodesCatalogsList != null && !nodesCatalogsList.isEmpty()) &&
                (transactionList != null && !transactionList.isEmpty())){

            for (NodesCatalog remoteNodesCatalog: nodesCatalogsList) {

                try {

                    FermatWebSocketClientNodeChannel fermatWebSocketClientNodeChannel = new FermatWebSocketClientNodeChannel(remoteNodesCatalog);
                    ReceiveNodeCatalogTransactionsMsjRequest receiveNodeCatalogTransactionsMsjRequest = new ReceiveNodeCatalogTransactionsMsjRequest(transactionList);
                    fermatWebSocketClientNodeChannel.sendMessage(receiveNodeCatalogTransactionsMsjRequest.toJson(), PackageType.RECEIVE_NODE_CATALOG_TRANSACTIONS_RESPOND);

                }catch (Exception e){

                    remoteNodesCatalog.setOfflineCounter(remoteNodesCatalog.getOfflineCounter()+1);
                    nodesCatalogDao.update(remoteNodesCatalog);
                }
            }

        }else {

            LOG.info("Nothing to propagate ...");

        }


    }

    /**
     * Internal thread
     */
    private class PropagationTask implements Runnable {

        /**
         * (non-javadoc)
         * @see Runnable#run()
         */
        @Override
        public void run() {
            try {
                propagateCatalog();
            }catch (Exception e){
                LOG.error(e.getMessage());
            }
        }
    }


    /**
     * (non-javadoc)
     * @see FermatAgent#start()
     */
    @Override
    public void start() throws CantStartAgentException {

        LOG.info("Start");
        try {

            scheduledFutures.add(scheduledThreadPool.scheduleAtFixedRate(new PropagationTask(), PROPAGATION_TIME, PROPAGATION_TIME, TimeUnit.MINUTES));
            this.status = AgentStatus.STARTED;

        } catch (Exception exception) {
            throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    /**
     * (non-javadoc)
     * @see FermatAgent#resume()
     */
    public void resume() throws CantStartAgentException {
        LOG.info("Resume");
        try {
            try {

                scheduledFutures.add(scheduledThreadPool.scheduleAtFixedRate(new PropagationTask(), PROPAGATION_TIME,  PROPAGATION_TIME, TimeUnit.MINUTES));
                this.status = AgentStatus.STARTED;

            } catch (Exception exception) {
                throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
            }

        } catch (Exception exception) {

            throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    /**
     * (non-javadoc)
     * @see FermatAgent#pause()
     */
    public void pause() throws CantStopAgentException {
        LOG.info("Pause");
        try {

            for (ScheduledFuture future: scheduledFutures) {
                future.cancel(Boolean.TRUE);
            }

            this.status = AgentStatus.PAUSED;

        } catch (Exception exception) {

            throw new CantStopAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    /**
     * (non-javadoc)
     * @see FermatAgent#stop()
     */
    public void stop() throws CantStopAgentException {
        LOG.info("Stop");
        try {

            scheduledThreadPool.shutdown();
            this.status = AgentStatus.PAUSED;

        } catch (Exception exception) {

            throw new CantStopAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    /**
     * Return a list of Nodes Catalog Transactions Pending For Propagation
     *
     * @return List<NodesCatalogTransaction>
     */
    private List<NodesCatalogTransaction> getNodesCatalogTransactionsPendingForPropagationBlock() throws CantReadRecordDataBaseException {

        List<NodesCatalogTransactionsPendingForPropagation> transactionsPendingForPropagation = nodesCatalogTransactionsPendingForPropagationDao.findAll();
        List<NodesCatalogTransaction> transactionList = new ArrayList<>();

        if (transactionsPendingForPropagation != null && !transactionsPendingForPropagation.isEmpty()){

            for (NodesCatalogTransactionsPendingForPropagation nodesCatalogTransactionsPendingForPropagation : transactionsPendingForPropagation) {
                transactionList.add(nodesCatalogTransactionsPendingForPropagation.getNodesCatalogTransactions());
            }

        }

        return  transactionList;

    }

}
