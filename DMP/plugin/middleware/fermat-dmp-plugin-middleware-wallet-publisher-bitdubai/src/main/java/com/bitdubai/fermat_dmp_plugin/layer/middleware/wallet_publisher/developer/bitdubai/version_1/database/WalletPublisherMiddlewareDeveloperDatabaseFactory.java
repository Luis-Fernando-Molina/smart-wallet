/*
 * @#WalletPublisherMiddlewareDeveloperDatabaseFactory.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
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
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantInitializeWalletPublisherMiddlewareDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.WalletPublisherMiddlewareDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class WalletPublisherMiddlewareDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;

    /**
     * Represent the database.
     */
    Database database;

    /**
     * Constructor
     *
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public WalletPublisherMiddlewareDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeWalletPublisherMiddlewareDatabaseException
     */
    public void initializeDatabase() throws CantInitializeWalletPublisherMiddlewareDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeWalletPublisherMiddlewareDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            WalletPublisherMiddlewareDatabaseFactory walletPublisherMiddlewareDatabaseFactory = new WalletPublisherMiddlewareDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = walletPublisherMiddlewareDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeWalletPublisherMiddlewareDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /*
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Wallet Publisher", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /*
         * Table COMPONENT PUBLISHED INFORMATION columns.
         */
        List<String> cOMPONENTPUBLISHEDINFORMATIONColumns = new ArrayList<String>();

        cOMPONENTPUBLISHEDINFORMATIONColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_ID_COLUMN_NAME);
        cOMPONENTPUBLISHEDINFORMATIONColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_DFP_ID_COLUMN_NAME);
        cOMPONENTPUBLISHEDINFORMATIONColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_DFP_NAME_COLUMN_NAME);
        cOMPONENTPUBLISHEDINFORMATIONColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_COMPONENT_TYPE_COLUMN_NAME);
        cOMPONENTPUBLISHEDINFORMATIONColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_STATUS_COLUMN_NAME);
        cOMPONENTPUBLISHEDINFORMATIONColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_STATUS_TIMESTAMP_COLUMN_NAME);
        cOMPONENTPUBLISHEDINFORMATIONColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_PUBLICATION_TIMESTAMP_COLUMN_NAME);
        cOMPONENTPUBLISHEDINFORMATIONColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_PUBLISHER_ID_COLUMN_NAME);

        /*
         * Table COMPONENT PUBLISHED INFORMATION addition.
         */
        DeveloperDatabaseTable cOMPONENTPUBLISHEDINFORMATIONTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_PUBLISHED_INFORMATION_TABLE_NAME, cOMPONENTPUBLISHEDINFORMATIONColumns);
        tables.add(cOMPONENTPUBLISHEDINFORMATIONTable);

        /*
         * Table COMPONENT DETAILS VERSION columns.
         */
        List<String> cOMPONENTDETAILSVERSIONColumns = new ArrayList<String>();

        cOMPONENTDETAILSVERSIONColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_DETAILS_VERSION_ID_COLUMN_NAME);
        cOMPONENTDETAILSVERSIONColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_DETAILS_VERSION_SCREEN_SIZE_COLUMN_NAME);
        cOMPONENTDETAILSVERSIONColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_DETAILS_VERSION_VERSION_COLUMN_NAME);
        cOMPONENTDETAILSVERSIONColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_DETAILS_VERSION_VERSION_TIMESTAMP_COLUMN_NAME);
        cOMPONENTDETAILSVERSIONColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_DETAILS_VERSION_INITIAL_WALLET_VERSION_COLUMN_NAME);
        cOMPONENTDETAILSVERSIONColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_DETAILS_VERSION_FINAL_WALLET_VERSION_COLUMN_NAME);
        cOMPONENTDETAILSVERSIONColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_DETAILS_VERSION_INITIAL_PLATFORM_VERSION_COLUMN_NAME);
        cOMPONENTDETAILSVERSIONColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_DETAILS_VERSION_FINAL_PLATFORM_VERSION_COLUMN_NAME);
        cOMPONENTDETAILSVERSIONColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_DETAILS_VERSION_OBSERVATIONS_COLUMN_NAME);
        cOMPONENTDETAILSVERSIONColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_DETAILS_VERSION_CATALOG_ID_COLUMN_NAME);
        cOMPONENTDETAILSVERSIONColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_DETAILS_VERSION_COMPONENT_ID_COLUMN_NAME);

        /*
         * Table COMPONENT DETAILS VERSION addition.
         */
        DeveloperDatabaseTable cOMPONENTDETAILSVERSIONTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_DETAILS_VERSION_TABLE_NAME, cOMPONENTDETAILSVERSIONColumns);
        tables.add(cOMPONENTDETAILSVERSIONTable);



        return tables;
    }


    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable) {

        /*
         * Will get the records for the given table
         */
        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<DeveloperDatabaseTableRecord>();


        /*
         * I load the passed table name from the SQLite database.
         */
        DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());
        try {
            selectedTable.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /*
             * if there was an error, I will returned an empty list.
             */
            return returnedRecords;
        }

        List<DatabaseTableRecord> records = selectedTable.getRecords();
        List<String> developerRow = new ArrayList<String>();
        for (DatabaseTableRecord row : records) {

            /*
             * for each row in the table list
             */
            for (DatabaseRecord field : row.getValues()) {

                /*
                 * I get each row and save them into a List<String>
                 */
                developerRow.add(field.getValue().toString());
            }

            /*
             * I create the Developer Database record
             */
            returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
        }


        /*
         * return the list of DeveloperRecords for the passed table.
         */
        return returnedRecords;
    }


    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
