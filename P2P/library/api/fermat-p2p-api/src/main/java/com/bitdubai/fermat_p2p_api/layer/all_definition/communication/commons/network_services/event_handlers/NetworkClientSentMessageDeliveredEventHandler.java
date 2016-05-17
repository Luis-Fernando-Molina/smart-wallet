/*
* @#NetworkClientSentMessageDeliveredEventHandler.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientNewMessageDeliveredEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientSentMessageDeliveredEventHandler</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 17/05/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkClientSentMessageDeliveredEventHandler implements FermatEventHandler<NetworkClientNewMessageDeliveredEvent> {

    /**
     * Represent the networkService
     */
    private AbstractNetworkService networkService;

    /**
     * Constructor with parameter
     *
     * @param networkService
     */
    public NetworkClientSentMessageDeliveredEventHandler(AbstractNetworkService networkService) {
            this.networkService = networkService;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler#handleEvent(FermatEvent)
     *
     * @param fermatEvent instance of NetworkClientConnectionLostEvent
     *
     * @throws com.bitdubai.fermat_api.FermatException if something goes wrong.
     */
    @Override
    public void handleEvent(NetworkClientNewMessageDeliveredEvent fermatEvent) throws FermatException {

        if (this.networkService.isStarted() &&
            this.networkService.getProfile().getNetworkServiceType().equals(fermatEvent.getNetworkServiceTypeSource()))
                    networkService.onSentMessage(networkService.getNetworkServiceConnectionManager().getOutgoingMessagesDao().findById(fermatEvent.getId()));

    }


}
