package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.listeners;

import com.bitdubai.fermat_api.layer.all_definition.event.EventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.event.EventType;

/**
 * Created by rodrigo on 2015.07.08..
 */
public class IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEventListener extends GenericEventListener {

    public IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEventListener(final EventMonitor eventMonitor){
        super(EventType.INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER, eventMonitor);
    }

}
