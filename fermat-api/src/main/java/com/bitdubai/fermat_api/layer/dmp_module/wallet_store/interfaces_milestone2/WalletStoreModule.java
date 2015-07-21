package com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces_milestone2;

import com.bitdubai.fermat_api.layer.all_definition.enums.NicheWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions_milestone2.CantGetRefinedCatalogException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions_milestone2.CantStartInstallationException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions_milestone2.CantStartLanguageInstallationException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions_milestone2.CantStartSkinInstallationException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions_milestone2.CantStartUninstallLanguageException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions_milestone2.CantStartUninstallSkinException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions_milestone2.CantStartUninstallWalletException;

import java.util.UUID;

/**
 * This interface manage the presentation logic of the wallet store
 */
public interface WalletStoreModule {

    /**
     * This method let us get the wallet catalogue
     *
     * @return the wallet store catalogue
     * @throws CantGetRefinedCatalogException
     */
    public WalletStoreCatalogue getCatalogue() throws CantGetRefinedCatalogException;

    /**
     * This method initialize the installation presses of a skin by giving the control to the
     * wallet manager.
     *
     * @param walletCatalogueId the wallet identifier where to install the skin in.
     * @param languageId the language identifier
     * @throws CantStartLanguageInstallationException
     */
    public void installLanguage(UUID walletCatalogueId, UUID languageId) throws CantStartLanguageInstallationException;

    /**
     * This method initialize the installation presses of a skin by giving the control to the
     * wallet manager.
     *
     * @param walletCatalogueId the wallet identifier where to install the skin in.
     * @param skinId the skin identifier
     * @throws CantStartSkinInstallationException
     */
    public void installSkin(UUID walletCatalogueId, UUID skinId) throws CantStartSkinInstallationException;

    /**
     * This method initialize the installation presses of the wallet by giving the control to the
     * wallet manager.
     *
     * @param walletCategory the category of the wallet to install
     * @param nicheWallet the type of the wallet to install
     * @param skinId the skin to install with the wallet
     * @param languageId the language to install in the wallet
     * @param walletCatalogueId the wallet identifier in the catalogue
     * @param version the version of the wallet to install
     * @throws CantStartInstallationException
     */
    public void installWallet(WalletCategory walletCategory,
                              NicheWallet nicheWallet,
                              UUID skinId,
                              UUID languageId,
                              UUID walletCatalogueId,
                              String version) throws CantStartInstallationException;

    /**
     * This method initialize the uninstall presses of a skin by giving the control to the
     * wallet manager.
     *
     * @param walletCatalogueId the wallet identifier where to uninstall the skin from.
     * @param languageId the language identifier
     * @throws CantStartLanguageInstallationException
     */
    public void uninstallLanguage(UUID walletCatalogueId, UUID languageId) throws CantStartUninstallLanguageException;

    /**
     * This method initialize the uninstall presses of a skin by giving the control to the
     * wallet manager.
     *
     * @param walletCatalogueId the wallet identifier where to uninstall the skin from.
     * @param skinId the skin identifier
     * @throws CantStartSkinInstallationException
     */
    public void uninstallSkin(UUID walletCatalogueId, UUID skinId) throws CantStartUninstallSkinException;

    /**
     * This method initialize the uninstall presses of the wallet by giving the control to the
     * wallet manager.
     *
     * @param walletCatalogueId the wallet id in the catalogue
     * @throws CantStartInstallationException
     */
    public void uninstallWallet(UUID walletCatalogueId) throws CantStartUninstallWalletException;

}
