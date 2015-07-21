package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.NicheWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
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

    /**
     * This method starts the wallet installation process
     *
     * @param walletCategory The category of the wallet to install
     * @param nicheWallet The identification of the wallet
     * @param skinId the identifier of the skin to install
     * @param languageId the indentifier of the language to install
     * @param walletCatalogueId An identifier of the wallet to install
     * @param walletVersion the version of the wallet to install
     * @throws CantInstallWalletException
     */
    @Override
    public void startInstallation(WalletCategory walletCategory,
                                  NicheWallet nicheWallet,
                                  UUID skinId,
                                  String skinVersion,
                                  UUID languageId,
                                  String languageVersion,
                                  UUID walletCatalogueId,
                                  String walletVersion) throws CantInstallWalletException{

    }
}
