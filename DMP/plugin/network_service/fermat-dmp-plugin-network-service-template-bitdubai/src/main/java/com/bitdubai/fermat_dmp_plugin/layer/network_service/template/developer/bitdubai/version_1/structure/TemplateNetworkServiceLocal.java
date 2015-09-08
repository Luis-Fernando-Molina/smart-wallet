/*
 * @#TemplateNetworkServiceLocal.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.database.OutgoingMessageDao;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.NewNetworkServiceMessageReceivedEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.Message;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;

import java.util.Observable;
import java.util.Observer;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.TemplateNetworkServiceLocal</code> represent
 * the remote network services locally
 * <p/>
 * This class extend of the <code>java.util.Observer</code> class,  its used on the software design pattern called: The observer pattern,
 * for more info see @link https://en.wikipedia.org/wiki/Observer_pattern
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class TemplateNetworkServiceLocal implements Observer {

    /**
     * Represent the public key of the remote network service
     */
    private String remoteNetworkServicePublicKey;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * Represent the outgoingMessageDao
     */
    private OutgoingMessageDao outgoingMessageDao;

    /**
     * Constructor with parameters
     *
     * @param remoteNetworkServicePublicKey
     * @param errorManager                  instance
     * @param outgoingMessageDao            instance
     */
    public TemplateNetworkServiceLocal(String remoteNetworkServicePublicKey, ErrorManager errorManager, EventManager eventManager, OutgoingMessageDao outgoingMessageDao) {
        this.remoteNetworkServicePublicKey = remoteNetworkServicePublicKey;
        this.errorManager = errorManager;
        this.eventManager = eventManager;
        this.outgoingMessageDao = outgoingMessageDao;
    }


    /**
     * This method prepare the message to send and save on the
     * data base in the table <code>outgoing_messages</code>
     *
     * @param message the message to send
     */
    public void sendMessage(Message message) {

        try {

            /*
             * Cast the message to OutgoingTemplateNetworkServiceMessage
             */
            OutgoingTemplateNetworkServiceMessage outgoingIntraUserNetworkServiceMessage = (OutgoingTemplateNetworkServiceMessage) message;

            /*
             * Configure the correct status
             */
            outgoingIntraUserNetworkServiceMessage.setStatus(MessagesStatus.PENDING_TO_SEND);

            /*
             * Save to the data base table
             */
            outgoingMessageDao.create(outgoingIntraUserNetworkServiceMessage);

        } catch (CantInsertRecordDataBaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not send message. Error reason: " + e.getMessage()));
        }

    }


    /**
     * Notify the client when a incoming message is receive by the incomingTemplateNetworkServiceMessage
     * ant fire a new event
     *
     * @param incomingTemplateNetworkServiceMessage received
     */
    private void onMessageReceived(IncomingTemplateNetworkServiceMessage incomingTemplateNetworkServiceMessage) {

        /**
         * Put the message on a event and fire new event
         */
        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE);
        platformEvent.setSource(EventSource.NETWORK_SERVICE_TEMPLATE_PLUGIN);
        ((NewNetworkServiceMessageReceivedEvent) platformEvent).setData(incomingTemplateNetworkServiceMessage); //VALIDAR CON LUIS ESTE ATTRIBUTO
        eventManager.raiseEvent(platformEvent);

    }

    /**
     * This method is called automatically when TemplateNetworkServiceRemoteAgent (Observable object) update the database
     * when new message is received
     *
     * @param observable the observable object
     * @param data       the data update
     */
    @Override
    public void update(Observable observable, Object data) {

        //Validate and process
        if (data instanceof IncomingTemplateNetworkServiceMessage)
            onMessageReceived((IncomingTemplateNetworkServiceMessage) data);
    }

    /**
     * Return the public key of the remote network service
     *
     * @return
     */
    public String getRemoteNetworkServicePublicKey() {
        return remoteNetworkServicePublicKey;
    }
}
