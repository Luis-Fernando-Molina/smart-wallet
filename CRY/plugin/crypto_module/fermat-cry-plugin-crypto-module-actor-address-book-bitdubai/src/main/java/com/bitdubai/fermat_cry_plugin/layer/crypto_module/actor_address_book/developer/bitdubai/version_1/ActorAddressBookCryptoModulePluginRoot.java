package com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBookRegistryException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRegistry;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions.CantInitializeActorAddressBookCryptoModuleException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDeveloperDatabaseFactory;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by loui on 20/02/15.asd
 */

/**
 * This Plug-in has the responsibility to manage the relationship between users and crypto addresses.
 *
 * * * * * *
 */

public class ActorAddressBookCryptoModulePluginRoot implements ActorAddressBookManager, DatabaseManagerForDevelopers, DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, LogManagerForDevelopers, Plugin, Service {

    /**
     * DatabaseManagerForDevelopers interface implementation
     * Returns the list of databases implemented on this plug in.
     */
    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager, SE ALMACENO EN UNA VARIABLE EL RETORNO PARA GESTIONAR DE MEJOR MANERA LA EXCEPCION.
    * */
    /*
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        ActorAddressBookCryptoModuleDeveloperDatabaseFactory dbFactory = new ActorAddressBookCryptoModuleDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }*/
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        ActorAddressBookCryptoModuleDeveloperDatabaseFactory dbFactory = new ActorAddressBookCryptoModuleDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabase> developerDatabasesList = null;
        try {
            developerDatabasesList = dbFactory.getDatabaseList(developerObjectFactory);
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "","CAN NOT RETURN THE LIST");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        return developerDatabasesList;
    }
    /**
     * returns the list of tables for the given database
     *
     * @param developerObjectFactory
     * @param developerDatabase
     * @return
     */
    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager, SE ALMACENO EN UNA VARIABLE EL RETORNO PARA GESTIONAR DE MEJOR MANERA LA EXCEPCION.
    * */
    /*@Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        ActorAddressBookCryptoModuleDeveloperDatabaseFactory dbFactory = new ActorAddressBookCryptoModuleDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }*/
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        ActorAddressBookCryptoModuleDeveloperDatabaseFactory dbFactory = new ActorAddressBookCryptoModuleDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTable> developerDatabaseTableList = null;
        try {
            developerDatabaseTableList = dbFactory.getDatabaseTableList(developerObjectFactory);
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "","CAN NOT RETURN THE LIST");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        return developerDatabaseTableList;
    }

    /**
     * returns the list of records for the passed table
     *
     * @param developerObjectFactory
     * @param developerDatabase
     * @param developerDatabaseTable
     * @return
     */
    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager
    * */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        ActorAddressBookCryptoModuleDeveloperDatabaseFactory dbFactory = new ActorAddressBookCryptoModuleDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = null;
        try {
            dbFactory.initializeDatabase();
            developerDatabaseTableRecordList = dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "","CAN NOT RETURN THE LIST");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        return developerDatabaseTableRecordList;
    }
    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;


    /**
     * ActorAddressBookManager Interface implementation.
     */
    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager, SE CAMBIO LA UBICACION DEL RETORNO
    * */
    @Override
    public ActorAddressBookRegistry getActorAddressBookRegistry() throws CantGetActorAddressBookRegistryException {
        /**
         * I created instance of ActorCryptoAddressBookRegistry
         */
        ActorAddressBookCryptoModuleRegistry actorCryptoAddressBookRegistry = new ActorAddressBookCryptoModuleRegistry();

        actorCryptoAddressBookRegistry.setErrorManager(this.errorManager);
        actorCryptoAddressBookRegistry.setPluginDatabaseSystem(this.pluginDatabaseSystem);
        actorCryptoAddressBookRegistry.setPluginId(this.pluginId);

        try {
            actorCryptoAddressBookRegistry.initialize();
//            return actorCryptoAddressBookRegistry;
        } catch (CantInitializeActorAddressBookCryptoModuleException cantInitializeActorCryptoAddressBookException) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE,FermatException.wrapException(cantInitializeActorCryptoAddressBookException)," ","COULD NOT CREATE INSTANCE");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantInitializeActorCryptoAddressBookException);
            throw new CantGetActorAddressBookRegistryException(CantGetActorAddressBookRegistryException.DEFAULT_MESSAGE, cantInitializeActorCryptoAddressBookException);
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "","CAN NOT RETURN THE LIST");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            //System.out.println("******* Error trying to get database table list for plugin Wallet Contacts");
        }
        return actorCryptoAddressBookRegistry;
    }

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager, SE CAMBIO LA UBICACION DEL RETORNO
    * */
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        try {
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.ActorAddressBookCryptoModulePluginRoot");
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions.CantInitializeActorAddressBookCryptoModuleException");
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDao");
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleRecord");
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleRegistry");
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDatabaseConstants");
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDeveloperDatabaseFactory");
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDatabaseFactory");
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "", "CAN NOT RETURN THE CLASS");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        /**
         * I return the values.
         */
        return returnedClasses;
    }

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager,
    * */
    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel){
        try {
            /**
             * I will check the current values and update the LogLevel in those which is different
             */
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
                /**
                 * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
                 */
                if (ActorAddressBookCryptoModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    ActorAddressBookCryptoModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    ActorAddressBookCryptoModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    ActorAddressBookCryptoModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "","CAN NOT CHECK VALUES");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }


    }


    /**
     * Static method to get the logging level from any class under root.
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className){
        try{
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return ActorAddressBookCryptoModulePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager. SOLO SE GESTIONO EXCEPCIONES GENERICAS POR LA SIMPLICIDAD DEL METODO.
    * */
    /**
     * Service Interface implementation.
     */
    @Override
    public void start() {
        try{
            this.serviceStatus = ServiceStatus.STARTED;
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "","CAN NOT START PLUGIN");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
    }

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager. SOLO SE GESTIONO EXCEPCIONES GENERICAS POR LA SIMPLICIDAD DEL METODO.
    * */
    @Override
    public void pause() {
        try{
            this.serviceStatus = ServiceStatus.PAUSED;
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "","CAN NOT PAUSE PLUGIN");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
    }

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager. SOLO SE GESTIONO EXCEPCIONES GENERICAS POR LA SIMPLICIDAD DEL METODO.
    * */
    @Override
    public void resume() {
        try{
            this.serviceStatus = ServiceStatus.STARTED;
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "","CAN NOT RESUME PLUGIN");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
    }

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager. SOLO SE GESTIONO EXCEPCIONES GENERICAS POR LA SIMPLICIDAD DEL METODO.
    * */
    @Override
    public void stop() {
        try{
            this.serviceStatus = ServiceStatus.STOPPED;
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "","CAN NOT STOP PLUGIN");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
    }

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager. SOLO SE GESTIONO EXCEPCIONES GENERICAS POR LA SIMPLICIDAD DEL METODO.
    * */
    @Override
    public ServiceStatus getStatus() {
        ServiceStatus serviceStatus = null;
        try{
            serviceStatus = this.serviceStatus;
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "","CAN NOT KNOW THE STATUS SERVICE");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        return serviceStatus;
    }

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager. SOLO SE GESTIONO EXCEPCIONES GENERICAS POR LA SIMPLICIDAD DEL METODO.
    * */
    /**
     *DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        try{
            this.errorManager = errorManager;
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "","CAN NOT KNOW ERROR");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
    }

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager. SOLO SE GESTIONO EXCEPCIONES GENERICAS POR LA SIMPLICIDAD DEL METODO.
    * */
    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        try{
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "","CAN NOT KNOW PLUGIN DATABASE");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
    }

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager. SOLO SE GESTIONO EXCEPCIONES GENERICAS POR LA SIMPLICIDAD DEL METODO.
    * */
    /**
     * DealsWithPluginIdentity methods implementation.
     */
    @Override
    public void setId(UUID pluginId) {
        try{
            this.pluginId = pluginId;
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "","CAN NOT KNOW ID");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
    }

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager. SOLO SE GESTIONO EXCEPCIONES GENERICAS POR LA SIMPLICIDAD DEL METODO.
    * */
    @Override
    public void setLogManager(LogManager logManager) {
        try {
            this.logManager = logManager;
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "","CAN NOT KNOW LOG");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
    }
}