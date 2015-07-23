package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.NicheWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantInstallWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.WalletInstallationProcess;

import java.util.UUID;

/**
 * Created by natalia on 21/07/15.
 */
public class WalletManagerInstallationProcess implements WalletInstallationProcess {



    /**
     * WalletInstallationProcess Interface implementation.
     */

    /**
     * This method gives us the progress of the current installation
     *
     * @return an integer that reflects the said progress
     */
    @Override
    public int getInstallationProgress(){
        return 0;
    }

    @Override
    public void startInstallation(String walletName, String walletIconName, String walletIdentifier, UUID skinId, Version skinVersion, UUID languageId, Version languageVersion, UUID walletCatalogueId, Version walletVersion) throws CantInstallWalletException {

    }


}
