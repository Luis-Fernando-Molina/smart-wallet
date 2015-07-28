package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetCatalogItemException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletIconException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletsCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantPublishDesignerInCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantPublishLanguageInCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantPublishSkinInCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantPublishTranslatorInCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantPublishWalletInCatalogException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.CantPublishItemInCatalogException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.CatalogItem;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Designer;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.DetailedCatalogItem;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Developer;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Language;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Skin;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.Translator;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.WalletCatalog;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.common.DatabaseOperations;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreNetworkServiceDatabaseDao;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 7/21/15.
 */

public class WalletStoreManager implements DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem {
    /**
     * WalletStoreManager member variables
     */
    UUID pluginId;

    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;

    /**
     * DealsWithLogger interface mmeber variables
     */
    LogManager logManager;

    /**
     * DealsWithPluginDatabaseSystem interface member variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem interface member variables
     */
    PluginFileSystem pluginFileSystem;


    /**
     * Constructor
     * @param errorManager
     * @param logManager
     * @param pluginDatabaseSystem
     * @param pluginFileSystem
     */
    public WalletStoreManager(ErrorManager errorManager, LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    /**
     * DealsWithErrors interface implementation
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithlogger interface implementation
     */
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithPluginFileSystem interface implementation
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    private WalletStoreNetworkServiceDatabaseDao getDatabaseDAO() throws CantExecuteDatabaseOperationException {
        WalletStoreNetworkServiceDatabaseDao dbDAO = new WalletStoreNetworkServiceDatabaseDao(errorManager, logManager, pluginDatabaseSystem, pluginId, WalletStoreNetworkServiceDatabaseConstants.WALLET_STORE_DATABASE);
        return dbDAO;
    }

    private void publishItemInDB (CatalogItem catalogItem, Developer developer, Language language, Translator translator, Skin skin, Designer designer) throws CantPublishItemInCatalogException {
        try {
            getDatabaseDAO().catalogDatabaseOperation(DatabaseOperations.INSERT, catalogItem, developer, language, translator, skin, designer);
        } catch (CantExecuteDatabaseOperationException e) {
            throw new CantPublishItemInCatalogException(CantPublishItemInCatalogException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    private void saveCatalogItemIconFile(CatalogItem catalogItem) throws CantPublishWalletInCatalogException {
        try {
            saveImageIntoFile(catalogItem.getId().toString(), catalogItem.getName(), catalogItem.getIcon());
        } catch (CantPublishItemInCatalogException | CantGetWalletIconException  e) {
            throw new CantPublishWalletInCatalogException(CantPublishWalletInCatalogException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    private void saveImageIntoFile(String directory, String filename, byte[] content) throws CantPublishItemInCatalogException {
        try{
            PluginBinaryFile imageFile = pluginFileSystem.createBinaryFile(pluginId, directory, filename, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            imageFile.setContent(content);
            imageFile.persistToMedia();
        } catch (CantCreateFileException | CantPersistFileException e) {
            throw new CantPublishItemInCatalogException(CantPublishItemInCatalogException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    /**
     * Saves the catalog Item information into database and the icon file into disk.
     * @param catalogItem
     * @throws CantPublishWalletInCatalogException
     */
    public void publishWallet (CatalogItem catalogItem) throws CantPublishWalletInCatalogException {
        try {
            publishItemInDB(catalogItem, null, null, null, null, null);
            saveCatalogItemIconFile(catalogItem);
        } catch (Exception exception) {
            throw new CantPublishWalletInCatalogException(CantPublishWalletInCatalogException.DEFAULT_MESSAGE, exception, null, null);
        }
    }

    /**
     * pubish the skin into DB and files into disk
     * @param skin
     * @throws CantPublishSkinInCatalogException
     */
    public void publishSkin (Skin skin) throws CantPublishSkinInCatalogException{
        try {
            publishItemInDB(null, null, null, null, skin, null);
            saveImageIntoFile(skin.getSkinId().toString(), skin.getSkinName(), skin.getPresentationImage());
            int i = 0;
            for (byte[] images : skin.getPreviewImageList()){
                i++;
                saveImageIntoFile(skin.getSkinId().toString(), skin.getSkinName() + "_" + i, images);
            }
        } catch (Exception exception) {
            throw new CantPublishSkinInCatalogException(CantPublishSkinInCatalogException.DEFAULT_MESSAGE, exception, null, null);
        }
    }

    /**
     * saves the language in DB
     * @param language
     * @throws CantPublishLanguageInCatalogException
     */
    public void publishLanguage(Language language) throws CantPublishLanguageInCatalogException {
        try{
            publishItemInDB(null, null, language, null, null, null);
        } catch (Exception e) {
            throw new CantPublishLanguageInCatalogException(CantPublishLanguageInCatalogException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    /**
     * saves the designer in DB
     * @param designer
     * @throws CantPublishDesignerInCatalogException
     */
    public void publishDesigner(Designer designer) throws CantPublishDesignerInCatalogException {
        try{
            publishItemInDB(null, null, null, null, null, designer);
        } catch (Exception e) {
            throw new CantPublishDesignerInCatalogException (CantPublishDesignerInCatalogException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    /**
     * saves the translator in DB
     * @param translator
     * @throws CantPublishTranslatorInCatalogException
     */
    public void publishTranslator(Translator translator) throws CantPublishTranslatorInCatalogException{
        try{
            publishItemInDB(null, null, null, translator, null, null);
        } catch (Exception e) {
            throw new CantPublishTranslatorInCatalogException (CantPublishTranslatorInCatalogException.DEFAULT_MESSAGE, e, null, null);
        }
    }


    private DetailedCatalogItem getDetailedCatalogItemFromDatabase (UUID walletId) throws CantGetCatalogItemException {
        try {
            return getDatabaseDAO().getDetailedCatalogItem(walletId);
        } catch (Exception e) {
            throw new CantGetCatalogItemException(CantGetCatalogItemException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    private byte[] getSkinContent(String directory, String fileName) throws  CantGetCatalogItemException{
        try {
            PluginBinaryFile skinFile = pluginFileSystem.getBinaryFile(pluginId, directory, fileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            skinFile.loadFromMedia();
            return skinFile.getContent();
        } catch (FileNotFoundException | CantCreateFileException | CantLoadFileException exception) {
          throw new CantGetCatalogItemException(CantGetCatalogItemException.DEFAULT_MESSAGE, exception, null, null);
        }
    }

    /**
     * Get the detailed catalog item from db and icon imagess.
     * @param walletId
     * @return
     * @throws CantGetCatalogItemException
     */
    public DetailedCatalogItem getDetailedCatalogItem (UUID walletId) throws CantGetCatalogItemException {
        try{
            DetailedCatalogItem detailedCatalogItem;
            detailedCatalogItem = getDetailedCatalogItemFromDatabase (walletId);
            Skin defaultSkin = (Skin) detailedCatalogItem.getDefaultSkin();
            defaultSkin.setPresentationImage(getSkinContent(pluginId.toString(), defaultSkin.getSkinName()));
            detailedCatalogItem.setDefaultSkin(defaultSkin);

            return detailedCatalogItem;
        } catch (Exception exception){
            throw new CantGetCatalogItemException(CantGetCatalogItemException.DEFAULT_MESSAGE, exception, null, null);
        }
    }

    /**
     * Gets the catalogItem from the database
     * @param walletId
     * @return
     * @throws CantGetCatalogItemException
     */
    public CatalogItem getCatalogItem(UUID walletId) throws CantGetCatalogItemException{
        CatalogItem catalogItem;
        try {
            catalogItem = getDatabaseDAO().getCatalogItem(walletId);
            catalogItem.setDetailedCatalogItem(getDetailedCatalogItem(walletId));
        } catch (Exception e) {
            throw new CantGetCatalogItemException(CantGetCatalogItemException.DEFAULT_MESSAGE, e, null, null);
        }

        return catalogItem;
    }

    /**
     * gets the entire wallet catalog
     * @return
     * @throws CantGetWalletsCatalogException
     */
    public WalletCatalog getWalletCatalogue() throws CantGetWalletsCatalogException {
        WalletCatalog walletCatalog = new WalletCatalog();
        try {
            List<CatalogItem> catalogItemList =getDatabaseDAO().getCatalogItems();
            walletCatalog.setCatalogItems(catalogItemList);
            walletCatalog.setCatalogSize(catalogItemList.size());
        } catch (Exception e) {
            throw new CantGetWalletsCatalogException(CantGetWalletsCatalogException.DEFAULT_MESSAGE, e, null, null);
        }
        return walletCatalog;
    }

}
