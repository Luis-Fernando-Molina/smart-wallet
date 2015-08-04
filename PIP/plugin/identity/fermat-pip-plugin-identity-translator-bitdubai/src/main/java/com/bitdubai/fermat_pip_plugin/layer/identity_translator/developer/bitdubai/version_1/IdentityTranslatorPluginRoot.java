package com.bitdubai.fermat_pip_plugin.layer.identity_translator.developer.bitdubai.version_1;



import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.CantInitializeExtraUserRegistryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;

import com.bitdubai.fermat_pip_api.layer.pip_identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_pip_api.layer.pip_identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_pip_api.layer.pip_identity.translator.exceptions.CantCreateNewTranslatorException;
import com.bitdubai.fermat_pip_api.layer.pip_identity.translator.exceptions.CantGetUserTranslatorIdentitiesException;
import com.bitdubai.fermat_pip_api.layer.pip_identity.translator.interfaces.Translator;
import com.bitdubai.fermat_pip_api.layer.pip_identity.translator.interfaces.TranslatorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DealsWithDeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_plugin.layer.identity_translator.developer.bitdubai.version_1.developerUtils.IdentityTranslatorDeveloperDataBaseFactory;
import com.bitdubai.fermat_pip_plugin.layer.identity_translator.developer.bitdubai.version_1.database.IdentityTranslatorDatabaseConstants;
import com.bitdubai.fermat_pip_plugin.layer.identity_translator.developer.bitdubai.version_1.estructure.IdentityTranslatorDao;
import com.bitdubai.fermat_pip_plugin.layer.identity_translator.developer.bitdubai.version_1.estructure.IdentityTranslatorTranslator;
import com.bitdubai.fermat_pip_plugin.layer.identity_translator.developer.bitdubai.version_1.exceptions.CantDeliverDatabaseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by natalia on 31/07/2015.
 */

/**
 * Plugin functions:
 * -Translator manages identities type .
 * -Keep track of the different identities and relationship withthe Device Users.
 * -Serves layers above list Translators returning linked to Device User who is logged in.
 *-Create a new Translator , which automatically associated with this log User Device .
 *- You should allow signing messages using the private key of the Translator.
 *
 * * * * * * *
 */

public class IdentityTranslatorPluginRoot implements DatabaseManagerForDevelopers,DealsWithDeviceUser,DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem,DealsWithErrors,DealsWithLogger, LogManagerForDevelopers,Plugin,Service,TranslatorManager {

    /**
     * PlugIn Interface member variables.
     */


    IdentityTranslatorDao identityTranslatorDao;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /**
     * DealsWithDeviceUser Interface member variables.
     */
    DeviceUserManager deviceUserManager;

    /**
     * DealsWithEvents Interface member variables.
     */
    ErrorManager errorManager;

    // DealsWithPluginDatabaseSystem Interface member variables.
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * FileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;


    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();


    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;

    /**
     * DatabaseManagerForDevelopers Interface implementation.
     */

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {


        IdentityTranslatorDeveloperDataBaseFactory dbFactory = new IdentityTranslatorDeveloperDataBaseFactory(this.pluginId.toString(), IdentityTranslatorDatabaseConstants.TRANSLATOR_DB_NAME);
        return dbFactory.getDatabaseList(developerObjectFactory);


    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return IdentityTranslatorDeveloperDataBaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, IdentityTranslatorDatabaseConstants.TRANSLATOR_DB_NAME);
            return IdentityTranslatorDeveloperDataBaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        }catch (CantOpenDatabaseException cantOpenDatabaseException){
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverDatabaseException("I can't open database",cantOpenDatabaseException,"WalletId: " + developerDatabase.getName(),"");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DEVELOPER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists",databaseNotFoundException,"WalletId: " + developerDatabase.getName(),"");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DEVELOPER_IDENTITY,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        // If we are here the database could not be opened, so we return an empry list
        return new ArrayList<>();
    }


    /**
     * DealsWithDeviceUser Interface implementation
     */
    @Override
    public void setDeviceUserManager(DeviceUserManager deviceUserManager) {
        this.deviceUserManager = deviceUserManager;
    }

    /**
     *DealWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager =errorManager;
    }


    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;

    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem  = pluginFileSystem;

    }


    /**
     * DealsWithLogger Interface implementation.
     */

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * LogManagerForDevelopers Interface implementation.
     */

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_pip_plugin.layer.identity_translator.developer.bitdubai.version_1.IdentityTranslatorPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_pip_plugin.layer.identity_translator.developer.bitdubai.version_1.estructure.IdentityTranslatorTranslator");
        returnedClasses.add("com.bitdubai.fermat_pip_plugin.layer.identity_translator.developer.bitdubai.version_1.estructure.IdentityTranslatorDao");
        returnedClasses.add("com.bitdubai.fermat_pip_plugin.layer.identity_translator.developer.bitdubai.version_1.database.IdentityTranslatorDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_pip_plugin.layer.identity_translator.developer.bitdubai.version_1.database.IdentityTranslatorDatabaseConstants");


        /**
   * I return the values.
   */
        return returnedClasses;
    }


    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */

        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (IdentityTranslatorPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                IdentityTranslatorPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                IdentityTranslatorPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                IdentityTranslatorPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }

    @Override
    public void start() throws CantStartPluginException {

        this.serviceStatus = ServiceStatus.STARTED;

        /**
         * I created instance of IdentityTranslatorDao
         */
        this.identityTranslatorDao = new IdentityTranslatorDao(errorManager,pluginDatabaseSystem,pluginId, this.pluginFileSystem);


        try {
            this.identityTranslatorDao.initialize();

        } catch (CantInitializeExtraUserRegistryException cantInitializeExtraUserRegistryException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_EXTRA_USER, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantInitializeExtraUserRegistryException);
            throw new CantStartPluginException(cantInitializeExtraUserRegistryException, Plugins.BITDUBAI_USER_EXTRA_USER);
        }

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void stop() {

    }

    @Override
    public ServiceStatus getStatus() {
        return null;
    }

    /**
     * Translator Manager Interface implementation
     */
    @Override
    public List<Translator> getTranslatorsFromCurrentDeviceUser() throws CantGetUserTranslatorIdentitiesException {

        try {

            return this.identityTranslatorDao.getDevelopersFromCurrentDeviceUser(deviceUserManager.getLoggedInDeviceUser());

        }
        catch (CantGetLoggedInDeviceUserException e) {

            throw new CantGetUserTranslatorIdentitiesException("CAN'T GET TRANSLATORS LIST", e, "Translator Identity", ".");
        }
        catch (CantGetUserDeveloperIdentitiesException e)
        {
            throw new CantGetUserTranslatorIdentitiesException ("CAN'T GET TRANSLATORS LIST", e, "Translator Identity", "");
        }

    }

    @Override
    public Translator createNewTranslator(String alias) throws CantCreateNewTranslatorException {

        // Create the new developer.
        try {

            ECCKeyPair keyPair= new ECCKeyPair();

            identityTranslatorDao.createNewTranslator(alias, keyPair, deviceUserManager.getLoggedInDeviceUser());


            return new IdentityTranslatorTranslator(alias,keyPair.getPublicKey(), keyPair.getPrivateKey());

        } catch (CantGetLoggedInDeviceUserException e) {

           throw new CantCreateNewTranslatorException ("CAN'T CREATE TRANSLATOR IDENTITY", e, "Translator Identity", ".");

        } catch (CantCreateNewDeveloperException e) {

            throw new CantCreateNewTranslatorException ("CAN'T CREATE TRANSLATOR IDENTITY", e, "Translator Identity", "");

        }

    }






}
