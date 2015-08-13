/*
 * @#InformationPublishedComponentDao.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.DescriptorFactoryProjectType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.enums.ComponentPublishedInformationStatus;
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
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure.ImageImpl;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure.ImageImpl;

import java.net.MalformedURLException;
import java.net.URL;
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
public class ScreensShotsComponentsDao {


    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Constructor with parameters
     *
     * @param dataBase
     */
    public ScreensShotsComponentsDao(Database dataBase) {
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
        return getDataBase().getTable(WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_TABLE_NAME);
    }

    /**
     * Method that find an ImageImpl by id in the data base.
     *
     *  @param id Long id.
     *  @return ImageImpl found.
     *  @throws CantReadRecordDataBaseException
     */
    public ImageImpl findById (String id) throws CantReadRecordDataBaseException {

        if (id == null){
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        ImageImpl walletPublishedMiddlewareInformation = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable incomingMessageTable =  getDatabaseTable();
            incomingMessageTable.setStringFilter(WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_FIRST_KEY_COLUMN, id, DatabaseFilterType.EQUAL);
            incomingMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = incomingMessageTable.getRecords();


            /*
             * 3 - Convert into ImageImpl objects
             */
            for (DatabaseTableRecord record : records){

                /*
                 * 3.1 - Create and configure a  ImageImpl
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
     *  @return All ImageImpl.
     *  @throws CantReadRecordDataBaseException
     */
    public List<ImageImpl> findAll () throws CantReadRecordDataBaseException {


        List<ImageImpl> list = null;

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
             * 3 - Create a list of ImageImpl objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into ImageImpl objects
             */
            for (DatabaseTableRecord record : records){

                /*
                 * 4.1 - Create and configure a  ImageImpl
                 */
                ImageImpl walletPublishedMiddlewareInformation = constructFrom(record);

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
     *  @return All ImageImpl.
     *  @throws CantReadRecordDataBaseException
     */
    public List<ImageImpl> findAll (String columnName, String columnValue) throws CantReadRecordDataBaseException {

        if (columnName == null ||
                columnName.isEmpty() ||
                columnValue == null ||
                columnValue.isEmpty()){

            throw new IllegalArgumentException("The filter are required, can not be null or empty");
        }


        List<ImageImpl> list = null;

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
             * 3 - Create a list of ImageImpl objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into ImageImpl objects
             */
            for (DatabaseTableRecord record : records){

                /*
                 * 4.1 - Create and configure a  ImageImpl
                 */
                ImageImpl walletPublishedMiddlewareInformation = constructFrom(record);

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
     * @return List<ImageImpl>
     * @throws CantReadRecordDataBaseException
     */
    public List<ImageImpl> findAll (Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null ||
                filters.isEmpty()){

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<ImageImpl> list = null;
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
             * 4 - Create a list of ImageImpl objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 5 - Convert into ImageImpl objects
             */
            for (DatabaseTableRecord record : records){

                /*
                 * 5.1 - Create and configure a  ImageImpl
                 */
                ImageImpl walletPublishedMiddlewareInformation = constructFrom(record);

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
     *  @param entity ImageImpl to create.
     *  @throws CantInsertRecordDataBaseException
     */
    public void create (ImageImpl entity) throws CantInsertRecordDataBaseException {

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
     *  @param entity ImageImpl to update.
     *  @throws CantUpdateRecordDataBaseException
     */
    public void update(ImageImpl entity) throws CantUpdateRecordDataBaseException {

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
     * Construct a ImageImpl whit the values of the table record pass
     * by parameter
     *
     * @param record with values from the table
     * @return ImageImpl setters the values from table
     */
    private ImageImpl constructFrom(DatabaseTableRecord record){

        ImageImpl imageImpl = new ImageImpl();

        imageImpl.setFileId(UUID.fromString(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_FILE_ID_COLUMN_NAME)));
        imageImpl.setComponentId(UUID.fromString(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_COMPONENT_ID_COLUMN_NAME)));

        //TODO: Deserializar el objeto image xml
        imageImpl.setData(new byte[]{});

        return imageImpl;
    }

    /**
     * Construct a DatabaseTableRecord whit the values of the a imageImpl pass
     * by parameter
     *
     * @param imageImpl the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(ImageImpl imageImpl){

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();

        /*
         * Set the entity values
         */
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_FILE_ID_COLUMN_NAME,      imageImpl.getFileId().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_COMPONENT_ID_COLUMN_NAME, imageImpl.getComponentId().toString());

        /*
         * return the new table record
         */
        return entityRecord;

    }


}
