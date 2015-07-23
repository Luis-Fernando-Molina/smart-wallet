package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.WalletInstallationInformation;

import java.util.UUID;

/**
 * Created by natalia on 21/07/15.
 */
public class WalletManagerInstallationInformation implements WalletInstallationInformation {


    private UUID catalogId;
    private UUID skinId;
    private UUID languajeId;


    /**
     * WalletInstallationInformation Interface implementation.
     */

    /**
     * This method returns the identifier of the wallet to install
     *
     * @return the identifier
     */
    @Override
    public UUID getWalletCatalogId(){

        return this.catalogId;
    }

    /**
     * This method tells us the identifier of the selected skin to install
     *
     * @return the identifier
     */
    @Override
    public UUID getSkinId(){
        return this.skinId;
    }

    /**
     * This method gives us the identifier of the language selected to install
     *
     * @return the identifier
     */
    @Override
    public UUID getLanguageId(){
        return this.languajeId;
    }
}
