package com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions.CantInitializeActorAddressBookCryptoModuleException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDeveloperDatabaseFactory</code> have
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 29/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class ActorAddressBookCryptoModuleDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;


    Database database;

    /**
     * Constructor
     *
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public ActorAddressBookCryptoModuleDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager.
    * */
    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeActorAddressBookCryptoModuleException
     */
    public void initializeDatabase() throws CantInitializeActorAddressBookCryptoModuleException {

        /**
         * I will try to open the wallets' database..
         */
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE,FermatException.wrapException(cantOpenDatabaseException)," ","COULD NOT OPEN DATABASE");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (DatabaseNotFoundException databaseNotFoundException) {

            ActorAddressBookCryptoModuleDatabaseFactory databaseFactory = new ActorAddressBookCryptoModuleDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

            /**
             * I will create the database where I am going to store the information of this wallet.
             */
            try {

                this.database = databaseFactory.createDatabase(this.pluginId, this.pluginId);

            } catch (CantCreateDatabaseException cantCreateDatabaseException) {

                /**
                 * The database cannot be created. I can not handle this situation.
                 */
                FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE,FermatException.wrapException(cantCreateDatabaseException)," ","COULD NOT CREATE DATABASE");
                this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot create the database.");
            }
        }
    }

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager.
    * */
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        try{
            databases.add(developerObjectFactory.getNewDeveloperDatabase("Actor Address Book", this.pluginId.toString()));
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "","CAN NOT RETURN DATABASE");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        return databases;
    }

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager.
    * */
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();
        try{
            /**
             * ActorAddressBook columns
             */
            List<String> actorAddressBookTableColumns = new ArrayList<String>();
            actorAddressBookTableColumns.add(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ID);
            actorAddressBookTableColumns.add(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_DELIVERED_BY_ACTOR_ID);
            actorAddressBookTableColumns.add(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_DELIVERED_BY_ACTOR_TYPE);
            actorAddressBookTableColumns.add(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_DELIVERED_TO_ACTOR_ID);
            actorAddressBookTableColumns.add(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_DELIVERED_TO_ACTOR_TYPE);
            actorAddressBookTableColumns.add(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS);
            actorAddressBookTableColumns.add(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY);
            actorAddressBookTableColumns.add(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_TIME_STAMP);

            /**
             * actorAdressBook table
             */
            DeveloperDatabaseTable actorAdressBookTable = developerObjectFactory.getNewDeveloperDatabaseTable(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME, actorAddressBookTableColumns);
            tables.add(actorAdressBookTable);
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "","CAN NOT RETURN TABLE");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        return tables;
    }


    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager.
    * */
    /*public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable) {
//         * Will get the records for the given table
        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<DeveloperDatabaseTableRecord>();


         // I load the passed table name from the SQLite database.
        DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());
        try {
            selectedTable.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
//            if there was an error, I will returned an empty list.
            return returnedRecords;
        }

        List<DatabaseTableRecord> records = selectedTable.getRecords();
        for (DatabaseTableRecord row: records){
//              for each row in the table list
            List<String> developerRow = new ArrayList<String>();
            for (DatabaseRecord field : row.getValues()){
//                 * I get each row and save them into a List<String>
                developerRow.add(field.getValue());
            }
//              I create the Developer Database record
            returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));

        }
         //return the list of DeveloperRecords for the passed table.
        return returnedRecords;
    }*/
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable) {
        /**
         * Will get the records for the given table
         */
        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<DeveloperDatabaseTableRecord>();
        /**
         * I load the passed table name from the SQLite database.
         */
        DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());
        try {
            selectedTable.loadToMemory();

            List<DatabaseTableRecord> records = selectedTable.getRecords();
            for (DatabaseTableRecord row: records){
                /**
                 * for each row in the table list
                 */
                List<String> developerRow = new ArrayList<String>();
                for (DatabaseRecord field : row.getValues()){
                    /**
                     * I get each row and save them into a List<String>
                     */
                    developerRow.add(field.getValue());
                }
                /**
                 * I create the Developer Database record
                 */
                returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));

            }
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE,FermatException.wrapException(cantLoadTableToMemory)," ","COULD NOT RETURN RECORDS");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantLoadTableToMemory);
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "","CAN NOT RETURN RECORDS");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        /**
         * return the list of DeveloperRecords for the passed table.
         */
        return returnedRecords;
    }


    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager.
    * */
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
    * SE AGREGO EXCEPCIONES FermatException. CON REPORTES GENERADOS EN ErrorManager.
    * */
    @Override
    public void setPluginId(UUID pluginId) {
        try{
            this.pluginId = pluginId;
        }  catch (Exception exception) {
            FermatException e = new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "","CAN NOT KNOW ID");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
    }
}
