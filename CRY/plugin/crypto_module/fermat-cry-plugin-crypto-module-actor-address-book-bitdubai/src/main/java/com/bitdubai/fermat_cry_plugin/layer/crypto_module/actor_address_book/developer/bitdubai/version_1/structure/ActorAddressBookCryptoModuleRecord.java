package com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRecord;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions.CantInitializeActorAddressBookCryptoModuleException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.UUID;

/**
 * Created by Natalia on 16/06/2015
 */

/**
 * This class manages the relationship between actors and crypto addresses by storing them on a Database Table.
 */
public class ActorAddressBookCryptoModuleRecord implements ActorAddressBookRecord {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;
    /**
     * ActorAddressBookRecord Interface member variables.
     */
    private UUID deliveredByActorId ;
    private Actors deliveredByActorType;
    private UUID deliveredToActorId ;
    private Actors deliveredToActorType;
    private CryptoAddress cryptoAddress;

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager.
    * */
    /**
     * Constructor.
     */
    public ActorAddressBookCryptoModuleRecord(UUID deliveredByActorId, Actors deliveredByActorType, UUID deliveredToActorId, Actors deliveredToActorType, CryptoAddress cryptoAddress){
        /**
         * Set actor settings.
         */
        try{
            this.deliveredByActorId = deliveredByActorId;
            this.deliveredByActorType = deliveredByActorType;
            this.deliveredToActorId = deliveredToActorId;
            this.deliveredToActorType = deliveredToActorType;
            this.cryptoAddress = cryptoAddress;
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "","CAN NOT CREATE SET ACTOR");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
    }


    @Override
    public UUID getDeliveredByActorId() {
        return deliveredByActorId;
    }

    @Override
    public Actors getDeliveredByActorType() {
        return deliveredByActorType;
    }

    @Override
    public UUID getDeliveredToActorId() {
        return deliveredToActorId;
    }

    @Override
    public Actors getDeliveredToActorType() {
        return deliveredToActorType;
    }

    @Override
    public CryptoAddress getCryptoAddress(){
        return this.cryptoAddress;
    }
}