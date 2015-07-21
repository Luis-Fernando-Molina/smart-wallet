package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.NicheWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantInstallWalletException;

import java.util.UUID;

/**
 * Created by eze on 2015.07.19..
 */
public interface WalletInstallationProcess {

    /**
     * This method gives us the progress of the current installation
     *
     * @return an integer that reflects the said progress
     */
    public int getInstallationProgress();

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
    public void startInstallation(WalletCategory walletCategory,
                                  NicheWallet nicheWallet,
                                  UUID skinId,
                                  String skinVersion,
                                  UUID languageId,
                                  String languageVersion,
                                  UUID walletCatalogueId,
                                  String walletVersion) throws CantInstallWalletException;
}
