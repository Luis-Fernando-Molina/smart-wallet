/*
 * @#InformationPublishedComponentDao.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure.ComponentVersionDetailImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.InformationPublishedComponentDao</code> have
 * all methods implementation to access the data base (CRUD)
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ComponentVersionDetailDao {


    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Constructor with parameters
     *
     * @param dataBase
     */
    public ComponentVersionDetailDao(Database dataBase) {
        super();
        this.dataBase = dataBase;
    }

    /**
     * Return the Database
     * @return Database
     */
    Database getDataBase() {
        return dataBase;
    }

    /**
     * Return the DatabaseTable
     * @return DatabaseTable
     */
    DatabaseTable getDatabaseTable() {
        return getDataBase().getTable(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_TABLE_NAME);
    }

    /**
     * Method that find an ComponentVersionDetailImpl by id in the data base.
     *
     *  @param id Long id.
     *  @return ComponentVersionDetailImpl found.
     *  @throws CantReadRecordDataBaseException
     */
    public ComponentVersionDetailImpl findById (String id) throws CantReadRecordDataBaseException {

        if (id == null){
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        ComponentVersionDetailImpl walletPublishedMiddlewareInformation = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable incomingMessageTable =  getDatabaseTable();
            incomingMessageTable.setStringFilter(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_FIRST_KEY_COLUMN, id, DatabaseFilterType.EQUAL);
            incomingMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = incomingMessageTable.getRecords();


            /*
             * 3 - Convert into ComponentVersionDetailImpl objects
             */
            for (DatabaseTableRecord record : records){

                /*
                 * 3.1 - Create and configure a  ComponentVersionDetailImpl
                 */
                walletPublishedMiddlewareInformation = constructFrom(record);
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return walletPublishedMiddlewareInformation;
    };

    /**
     * Method that list the all entities on the data base.
     *
     *  @return All ComponentVersionDetailImpl.
     *  @throws CantReadRecordDataBaseException
     */
    public List<ComponentVersionDetailImpl> findAll () throws CantReadRecordDataBaseException {


        List<ComponentVersionDetailImpl> list = null;

        try {

            /*
             * 1 - load the data base to memory
             */
            DatabaseTable walletPublishedMiddlewareInformationTable =  getDatabaseTable();
            walletPublishedMiddlewareInformationTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = walletPublishedMiddlewareInformationTable.getRecords();

            /*
             * 3 - Create a list of ComponentVersionDetailImpl objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into ComponentVersionDetailImpl objects
             */
            for (DatabaseTableRecord record : records){

                /*
                 * 4.1 - Create and configure a  ComponentVersionDetailImpl
                 */
                ComponentVersionDetailImpl walletPublishedMiddlewareInformation = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(walletPublishedMiddlewareInformation);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
        return list;
    };


    /** Method that list the all entities on the data base. The valid value of
     * the column name are the att of the <code>WalletPublisherMiddlewareDatabaseConstants</code>
     *
     *  @see WalletPublisherMiddlewareDatabaseConstants
     *  @return All ComponentVersionDetailImpl.
     *  @throws CantReadRecordDataBaseException
     */
    public List<ComponentVersionDetailImpl> findAll (String columnName, String columnValue) throws CantReadRecordDataBaseException {

        if (columnName == null ||
                columnName.isEmpty() ||
                columnValue == null ||
                columnValue.isEmpty()){

            throw new IllegalArgumentException("The filter are required, can not be null or empty");
        }


        List<ComponentVersionDetailImpl> list = null;

        try {

            /*
             * 1 - load the data base to memory with filters
             */
            DatabaseTable walletPublishedMiddlewareInformationTable =  getDatabaseTable();
            walletPublishedMiddlewareInformationTable.setStringFilter(columnName, columnValue, DatabaseFilterType.EQUAL);
            walletPublishedMiddlewareInformationTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = walletPublishedMiddlewareInformationTable.getRecords();

            /*
             * 3 - Create a list of ComponentVersionDetailImpl objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into ComponentVersionDetailImpl objects
             */
            for (DatabaseTableRecord record : records){

                /*
                 * 4.1 - Create and configure a  ComponentVersionDetailImpl
                 */
                ComponentVersionDetailImpl walletPublishedMiddlewareInformation = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(walletPublishedMiddlewareInformation);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
        return list;
    };


    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>WalletPublisherMiddlewareDatabaseConstants</code>
     *
     * @param filters
     * @return List<ComponentVersionDetailImpl>
     * @throws CantReadRecordDataBaseException
     */
    public List<ComponentVersionDetailImpl> findAll (Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null ||
                filters.isEmpty()){

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<ComponentVersionDetailImpl> list = null;
        List<DatabaseTableFilter> filtersTable = new ArrayList<>();

        try {

            /*
             * 1- Prepare the filters
             */
            DatabaseTable walletPublishedMiddlewareInformationTable =  getDatabaseTable();

            for (String key: filters.keySet()){

                DatabaseTableFilter newFilter = walletPublishedMiddlewareInformationTable.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(key);
                newFilter.setValue((String) filters.get(key));

                filtersTable.add(newFilter);
            }

            /*
             * 2 - load the data base to memory with filters
             */
            walletPublishedMiddlewareInformationTable.setFilterGroup(filtersTable, null, DatabaseFilterOperator.OR);
            walletPublishedMiddlewareInformationTable.loadToMemory();

            /*
             * 3 - read all records
             */
            List<DatabaseTableRecord> records = walletPublishedMiddlewareInformationTable.getRecords();

            /*
             * 4 - Create a list of ComponentVersionDetailImpl objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 5 - Convert into ComponentVersionDetailImpl objects
             */
            for (DatabaseTableRecord record : records){

                /*
                 * 5.1 - Create and configure a  ComponentVersionDetailImpl
                 */
                ComponentVersionDetailImpl walletPublishedMiddlewareInformation = constructFrom(record);

                /*
                 * 5.2 - Add to the list
                 */
                list.add(walletPublishedMiddlewareInformation);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
        return list;
    };

    /**
     * Method that create a new entity in the data base.
     *
     *  @param entity ComponentVersionDetailImpl to create.
     *  @throws CantInsertRecordDataBaseException
     */
    public void create (ComponentVersionDetailImpl entity) throws CantInsertRecordDataBaseException {

        if (entity == null){
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            /*
             * 1- Create the record to the entity
             */
            DatabaseTableRecord entityRecord = constructFrom(entity);

            /*
             * 2.- Create a new transaction and execute
             */
            DatabaseTransaction transaction = getDataBase().newTransaction();
            transaction.addRecordToInsert(getDatabaseTable(), entityRecord);
            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {


            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            CantInsertRecordDataBaseException cantInsertRecordDataBaseException = new CantInsertRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantInsertRecordDataBaseException;

        }

    }

    /**
     * Method that update an entity in the data base.
     *
     *  @param entity ComponentVersionDetailImpl to update.
     *  @throws CantUpdateRecordDataBaseException
     */
    public void update(ComponentVersionDetailImpl entity) throws CantUpdateRecordDataBaseException {

        if (entity == null){
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            /*
             * 1- Create the record to the entity
             */
            DatabaseTableRecord entityRecord = constructFrom(entity);

            /*
             * 2.- Create a new transaction and execute
             */
            DatabaseTransaction transaction = getDataBase().newTransaction();
            transaction.addRecordToUpdate(getDatabaseTable(), entityRecord);
            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;

        }

    }

    /**
     * Method that delete a entity in the data base.
     *
     *  @param id Long id.
     *  @throws CantDeleteRecordDataBaseException
     */
    public void delete (Long id) throws CantDeleteRecordDataBaseException {

        if (id == null){
            throw new IllegalArgumentException("The id is required can not be null");
        }

        try {

            /*
             * Create a new transaction and execute
             */
            DatabaseTransaction transaction = getDataBase().newTransaction();

            //TODO: falta configurar la llamada para borrar la entidad

            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantDeleteRecordDataBaseException cantDeleteRecordDataBaseException = new CantDeleteRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantDeleteRecordDataBaseException;

        }

    }


    /**
     * Construct a ComponentVersionDetailImpl whit the values of the table record pass
     * by parameter
     *
     * @param record with values from the table
     * @return ComponentVersionDetailImpl setters the values from table
     */
    private ComponentVersionDetailImpl constructFrom(DatabaseTableRecord record){

        ComponentVersionDetailImpl componentVersionDetailImpl = new ComponentVersionDetailImpl();

        try {

            componentVersionDetailImpl.setId(UUID.fromString(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_ID_COLUMN_NAME)));
            componentVersionDetailImpl.setScreenSize(ScreenSize.getByCode(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_SCREEN_SIZE_COLUMN_NAME)));
            componentVersionDetailImpl.setVersion(new Version(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_VERSION_COLUMN_NAME)));
            componentVersionDetailImpl.setVersionTimestamp(new Timestamp(record.getLongValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_VERSION_TIMESTAMP_COLUMN_NAME)));
            componentVersionDetailImpl.setInitialWalletVersion(new Version(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_INITIAL_WALLET_VERSION_COLUMN_NAME)));
            componentVersionDetailImpl.setFinalWalletVersion(new Version(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_FINAL_WALLET_VERSION_COLUMN_NAME)));
            componentVersionDetailImpl.setInitialPlatformVersion(new Version(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_INITIAL_PLATFORM_VERSION_COLUMN_NAME)));
            componentVersionDetailImpl.setFinalPlatformVersion(new Version(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_FINAL_PLATFORM_VERSION_COLUMN_NAME)));
            componentVersionDetailImpl.setObservations(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_OBSERVATIONS_COLUMN_NAME));
            componentVersionDetailImpl.setCatalogId(UUID.fromString(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_CATALOG_ID_COLUMN_NAME)));
            componentVersionDetailImpl.setComponentId(UUID.fromString(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_COMPONENT_ID_COLUMN_NAME)));

        } catch (InvalidParameterException e) {

            //this should not happen, but if it happens return null
            return null;
        }


        return componentVersionDetailImpl;
    }

    /**
     * Construct a DatabaseTableRecord whit the values of the a componentVersionDetailImpl pass
     * by parameter
     *
     * @param componentVersionDetailImpl the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(ComponentVersionDetailImpl componentVersionDetailImpl){

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();

        /*
         * Set the entity values
         */
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_ID_COLUMN_NAME,      componentVersionDetailImpl.getId().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_SCREEN_SIZE_COLUMN_NAME, componentVersionDetailImpl.getScreenSize().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_VERSION_COLUMN_NAME, componentVersionDetailImpl.getVersion().toString());
        entityRecord.setLongValue  (WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_VERSION_TIMESTAMP_COLUMN_NAME, componentVersionDetailImpl.getVersionTimestamp().getTime());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_INITIAL_WALLET_VERSION_COLUMN_NAME, componentVersionDetailImpl.getInitialWalletVersion().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_FINAL_WALLET_VERSION_COLUMN_NAME, componentVersionDetailImpl.getFinalWalletVersion().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_INITIAL_PLATFORM_VERSION_COLUMN_NAME, componentVersionDetailImpl.getInitialPlatformVersion().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_FINAL_PLATFORM_VERSION_COLUMN_NAME, componentVersionDetailImpl.getFinalPlatformVersion().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_OBSERVATIONS_COLUMN_NAME, componentVersionDetailImpl.getObservations().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_CATALOG_ID_COLUMN_NAME, componentVersionDetailImpl.getCatalogId().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_COMPONENT_ID_COLUMN_NAME, componentVersionDetailImpl.getComponentId().toString());

        /*
         * return the new table record
         */
        return entityRecord;

    }


}
