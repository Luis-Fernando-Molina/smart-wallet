package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IncomingCryptoTransactionsWaitingTransferenceExtraUserEvent;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions.CantSaveEventException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserEventRecorderService;

/**
 * Created by eze on 2015.06.19..
 */
public class IncomingCryptoTransactionsWaitingTransferenceExtraUserEventHandler implements EventHandler {
    /**
     * IncomingCryptoIdentifiedEventHandler member variables
     */
    private final IncomingExtraUserEventRecorderService eventRecorderService;

    public IncomingCryptoTransactionsWaitingTransferenceExtraUserEventHandler(final IncomingExtraUserEventRecorderService eventRecorderService){
        this.eventRecorderService = eventRecorderService;
    }

    /**
     * EventHandler interface implementation
     */
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        if(!eventRecorderService.getStatus().equals(ServiceStatus.STARTED))
            throw new TransactionServiceNotStartedException(TransactionServiceNotStartedException.DEFAULT_MESSAGE, null, null, "Events can't be handled if the service is not started");

        if(platformEvent instanceof IncomingCryptoTransactionsWaitingTransferenceExtraUserEvent)
            eventRecorderService.saveEvent(platformEvent);
        else
            throw  new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE, null, "Event: " + platformEvent.getEventType().toString(), "This should have been IncomingCryptoTransactionsWaitingTransferenceExtraUserEvent");

    }

}