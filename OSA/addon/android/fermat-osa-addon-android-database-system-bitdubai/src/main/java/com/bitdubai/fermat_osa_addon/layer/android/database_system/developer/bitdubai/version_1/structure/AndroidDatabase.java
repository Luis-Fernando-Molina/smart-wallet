package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableColumn;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseVariable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;

/**
 * Created by ciencias on 23.12.14.
 */
/**
 * This class define methods to execute query and transactions on database
 * And method to get a database table definition
 *
 * *
 */

public class AndroidDatabase implements Database, DatabaseFactory {

    /**
     * database Interface member variables.
     */

    private Context context;
    private String databaseName;
    private UUID ownerId;
    private String query;
    private SQLiteDatabase database;
    private DatabaseTransaction databaseTransaction;
    private DatabaseTable databaseTable;

    public AndroidDatabase(){
    }
    // Public constructor declarations.

    /**
     * <p>Plugin implementation constructor
     *
     * @param context Android Context Object
     * @param ownerId PlugIn owner id
     * @param databaseName name database using
     */
    public AndroidDatabase(Context context, UUID ownerId, String databaseName) {
        this.context = context;
        this.ownerId = ownerId;
        this.databaseName = databaseName;
    }

    /**
     * <p>Platform implementation constructor
     *
     * @param context Android Context Object
     * @param databaseName name database using
     */
    public AndroidDatabase(Context context, String databaseName) {
        this.context = context;
        this.databaseName = databaseName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public DatabaseTransaction getDatabaseTransaction() {
        return databaseTransaction;
    }

    public void setDatabaseTransaction(DatabaseTransaction databaseTransaction) {
        this.databaseTransaction = databaseTransaction;
    }

    /**
     * database interface implementation.
     */
    @Override
    public DatabaseFactory getDatabaseFactory(){
        return this;
    }

    /**
     * <p> This method execute a string query command in database
     */
    @Override
    public void executeQuery() {
        database.execSQL(query);
    }


    /**
     * <p>Return a new DatabaseTransaction definition
     *
     * @return DatabaseTransaction object
     */
    @Override
    public DatabaseTransaction newTransaction(){

        return new AndroidDatabaseTransaction();
    }

    /**
     * <p>Return a DatabaseTable definition
     *
     * @param tableName name database table using
     * @return DatabaseTable Object
     */
    @Override
    public DatabaseTable getTable(String tableName){
        try{
            if(!database.isOpen())
                openDatabase();
            databaseTable = new AndroidDatabaseTable(context,database, tableName);
            return databaseTable;
        } catch( CantOpenDatabaseException | DatabaseNotFoundException exception){
            String message = CantCreateTableException.DEFAULT_MESSAGE;
            FermatException cause = exception;
            String context = "";
            String possibleReason = "We couldn't open the Database, you should checkout the cause";
            System.err.println(new CantCreateTableException(message, cause, context, possibleReason).toString());
        }
        return null;
    }

    /**
     * <p>Execute a transaction in database
     *
     * @param transaction DatabaseTransaction object to contain definition of operations to update and insert
     * @throws DatabaseTransactionFailedException
     */
    @Override
    public void executeTransaction(DatabaseTransaction transaction) throws DatabaseTransactionFailedException{

        if(transaction == null){
            String message = DatabaseTransactionFailedException.DEFAULT_MESSAGE;
            FermatException cause = null;
            String context = "Transaction: null";
            String possibleReason = "The passed transaction can't be null";
            throw new DatabaseTransactionFailedException(message, cause, context, possibleReason);
        }

        List<DatabaseVariable> variablesResult = null;

        /**
         * I get tablets and records to insert or update
         * then make sql sentences
         */
        List<DatabaseTable> selectTables = transaction.getTablesToSelect();
        List<DatabaseTable> insertTables = transaction.getTablesToInsert();
        List<DatabaseTable> updateTables = transaction.getTablesToUpdate();
        List<DatabaseTableRecord> updateRecords = transaction.getRecordsToUpdate();
        List<DatabaseTableRecord> selectRecords = transaction.getRecordsToSelect();
        List<DatabaseTableRecord> insertRecords = transaction.getRecordsToInsert();
        try{
            if(!database.isOpen())
                openDatabase();

            database.beginTransaction(); // EXCLUSIVE

            DatabaseVariable variables;
            //select
            if(selectTables != null)
                for (int i = 0; i < selectTables.size(); ++i){

                    selectTables.get(i).selectRecord(selectRecords.get(i));
                    //get list of variables and this values to pass at update and insert
                    //I assume that only a select defined
                    variablesResult =  selectTables.get(i).getVarialbesResult();
                }

            //update
            if(updateTables != null)
                for (int i = 0; i < updateTables.size(); ++i){
                    updateTables.get(i).setVarialbesResult(variablesResult);
                    updateTables.get(i).updateRecord(updateRecords.get(i));
                }

            //insert
            if(insertTables != null)
                for (int i = 0; i < insertTables.size(); ++i){
                    insertTables.get(i).setVarialbesResult(variablesResult);
                    insertTables.get(i).insertRecord(insertRecords.get(i));
                }

            database.setTransactionSuccessful();
            database.endTransaction();
        }catch(Exception exception) {

            /**
             * for error not complete transaction
             */
            String message = DatabaseTransactionFailedException.DEFAULT_MESSAGE;
            FermatException cause = exception instanceof FermatException ? (FermatException) exception : FermatException.wrapException(exception);
            String context = "Dabatase Name: " + databaseName;
            context += DatabaseTransactionFailedException.CONTEXT_CONTENT_SEPARATOR;
            context += transaction.toString();
            String possibleReason = "The most reasonable thing to do here is check the cause as this is a triggered exception that can come from many situations";

            throw new DatabaseTransactionFailedException(message, cause, context, possibleReason);
        }
    }



    /**
     * Private methods implementation.
     */

    /**
     * Open specific database file
     * if used by a plugin, method use plugin id to define directory path name
     *
     * @throws CantOpenDatabaseException
     * @throws DatabaseNotFoundException
     */
    @Override
    public void openDatabase() throws CantOpenDatabaseException, DatabaseNotFoundException {
        if(isOpen())
            return;
        /**
         * First I try to open the database.
         */
        String databasePath ="";
        /**
         * if owner id if null
         * because it comes from platformdatabase
         */
        if(ownerId != null)
            databasePath =  context.getFilesDir().getPath() +  "/databases/" +  ownerId.toString();
        else
            databasePath =  context.getFilesDir().getPath() + "/databases/";

        databasePath += "/" + databaseName.replace("-","") + ".db";

        if(!(new File(databasePath)).exists()){
            String message = DatabaseNotFoundException.DEFAULT_MESSAGE;
            FermatException cause = null;
            String context = "database Constructed Path: " + databasePath;
            String possibleReason = "Check if the constructed path is valid";
            throw new DatabaseNotFoundException(message, cause, context, possibleReason);
        }

        try {
            database = SQLiteDatabase.openDatabase(databasePath,null, SQLiteDatabase.OPEN_READWRITE,null);
        } catch (SQLiteException exception) {

            /**
             * Probably there is no distinctions between a database that it can not be opened and a one that doesn't not exist.
             * We will assume that if it didn't open it was because it didn't exist.
             * * *
             */
            String message = CantOpenDatabaseException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(exception);
            String context = "database Constructed Path: " + databasePath;
            String possibleReason = "Check the cause for this error as we have already checked that the database exists";
            throw new CantOpenDatabaseException(message, cause, context, possibleReason);
        }

    }

    @Override
    public void closeDatabase(){
        if(isOpen())
            database.close();
    }

    public boolean isOpen(){
        if(database == null)
            return false;
        return database.isOpen();
    }

    /**
     *
     te a specific database file
     * if used by a plugin, It use plugin id to define directory path name
     *
     * @throws CantOpenDatabaseException
     * @throws DatabaseNotFoundException
     */
    public void deleteDatabase() throws CantOpenDatabaseException, DatabaseNotFoundException {

        // determine directry path name
        String databasePath ="";

        if(ownerId != null)
            databasePath =  context.getFilesDir().getPath() +  "/databases/" +  ownerId.toString();
        else
            databasePath =  context.getFilesDir().getPath() + "/databases/";

        databasePath += "/" + databaseName.replace("-","") + ".db";


        /**
         * if owner id if null
         * because it comes from platformdatabase
         */
        File databaseFile = new File(databasePath);

        if(SQLiteDatabase.deleteDatabase(databaseFile))
            return;

        /**
         * unexpected error deleting the database
         * * *
         */
        String message = "SOMETHING UNEXPECTED HAS HAPPENED";
        FermatException cause = null;
        String context = "Constructed database Path: " + databasePath;
        String possibleCause = "The most probable reason is that the database path could not be found";
        throw new DatabaseNotFoundException(message, cause, context, possibleCause);

    }


    /**
     * DatabaseFactory interface implementation.
     */


    /**
     * This method creates a new database file
     *
     * @param databaseName name database to create
     * @throws CantCreateDatabaseException
     */
    @Override
    public void createDatabase(String databaseName) throws CantCreateDatabaseException {

        String databasePath ="";
        /**
         * if owner id if null
         * because it comes from platformdatabase
         */
        if(ownerId != null)
            databasePath =  context.getFilesDir().getPath() +   "/databases/" +   ownerId.toString();
        else
            databasePath =  context.getFilesDir().getPath() +  "/databases/" ;

        File storagePath = new File(databasePath);
        if (!storagePath.exists())
            storagePath.mkdirs();

        /**
         * Hash data base name
         */
        File databaseFile = new File(storagePath.getPath() + "/" + databaseName.replace("-","") + ".db");

        if(databaseFile.exists()){
            String message = CantCreateDatabaseException.DEFAULT_MESSAGE;
            FermatException cause = null;
            String context = "database File: " + databaseFile.getPath();
            String possibleReasons = "This happens if the database has already been created";
            throw new CantCreateDatabaseException(message, cause, context, possibleReasons);
        }
        /**
         * This call opens or creates the database, it doesn't throw a determined exception, but we'll try to emulate one in the tests
         */
        try{
            database = SQLiteDatabase.openOrCreateDatabase(databaseFile,null);
        } catch (SQLiteException ex){
            String message = CantCreateDatabaseException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(ex);
            String context = "Storage Path: " + storagePath.getPath();
            context += CantCreateDatabaseException.CONTEXT_CONTENT_SEPARATOR;
            context += "database Name: " + databaseName;
            String possibleReasons = "This can happen if the database File where we wanted to create the database can't be created";
            throw new CantCreateDatabaseException(message, cause, context, possibleReasons);
        }
    }

    /**
     * This method creates a new table into the database based on the definition received.
     * verify plugin owner id
     *
     * @param ownerId Plugin owner id
     * @param table DatabaseTableFactory object containing the definition of the table
     * @throws InvalidOwnerIdException
     * @throws CantCreateTableException
     */
    @Override
    public void createTable(UUID ownerId, DatabaseTableFactory table) throws InvalidOwnerIdException, CantCreateTableException {
        try{
            if(!database.isOpen())
                openDatabase();
        } catch( CantOpenDatabaseException | DatabaseNotFoundException exception){
            String message = CantCreateTableException.DEFAULT_MESSAGE;
            FermatException cause = exception;
            String context = "";
            String possibleReason = "We couldn't open the Database, you should checkout the cause";
            throw new CantCreateTableException(message, cause, context, possibleReason);
        }

        /**
         * I check that the owner id is the same I currently have..
         */
        if (this.ownerId != ownerId) {
            String message = InvalidOwnerIdException.DEFAULT_MESSAGE;
            String context = "database Owner Id: " + ownerId;
            FermatException cause = null;
            context += InvalidOwnerIdException.CONTEXT_CONTENT_SEPARATOR;
            context += "Owner Id in the method invocation: " + ownerId;
            String possibleReason = "The owner Id passed in the Invocation doesn't belong to the Android database Owner, maybe this was a passed object";
            throw new InvalidOwnerIdException(message, cause, context, possibleReason);
        }

        if(table == null){
            String message = CantCreateTableException.DEFAULT_MESSAGE;
            FermatException cause = null;
            String context = "Owner Id : " + ownerId.toString();
            String possibleReason = "DatabaseTableFactory can't be null.";
            throw new CantCreateTableException(message, cause, context, possibleReason);
        }

        /**
         * Get the columns of the table and write the query to create it
         */
        try
        {
            query ="CREATE TABLE IF NOT EXISTS " + table.getTableName() + "(";
            ArrayList<DatabaseTableColumn> tableColumns = table.getColumns();

            for (int i = 0; i < tableColumns.size(); i++) {

                query += tableColumns.get(i).getName() +" " +  tableColumns.get(i).getType().name();
                if(tableColumns.get(i).getType() == DatabaseDataType.STRING)
                    query +="("+ String.valueOf(tableColumns.get(i).getDataTypeSize()) + ")";

                if(i < tableColumns.size()-1)
                    query +=",";
            }

            query += ")";

            executeQuery();

            /**
             * get index column
             */
            if(table.getIndex() != null && !table.getIndex().isEmpty ()) {
                query = " CREATE INDEX IF NOT EXISTS " + table.getIndex() + "_idx ON " + table.getTableName() + " (" + table.getIndex() + ")";
                executeQuery();
            }
        }catch (Exception ex) {
            String message = CantCreateTableException.DEFAULT_MESSAGE;
            FermatException cause = ex instanceof FermatException ? (FermatException) ex : FermatException.wrapException(ex);
            String context = "Owner Id : " + ownerId.toString();
            context += CantCreateTableException.CONTEXT_CONTENT_SEPARATOR;
            context += "DatabaseTableFactory Info: " + table.toString();
            String possibleReason = "Check the cause for the reason we are getting this error.";
            throw new CantCreateTableException(message, cause, context, possibleReason);
        }


    }

    /**
     * This method creates a new table into the database based on the definition received.
     * Create  primary keys and index if defined
     *
     * @param table DatabaseTableFactory object containing the definition of the table
     * @throws CantCreateTableException
     */
    @Override
    public void createTable(DatabaseTableFactory table) throws CantCreateTableException {
        try{
            if(!database.isOpen())
                openDatabase();
            createTable(ownerId, table);
        } catch (InvalidOwnerIdException ex){
            throw new CantCreateTableException(CantCreateTableException.DEFAULT_MESSAGE, ex, "database Owner Id: " + ownerId, "This error is strange and shouldn't ever happen");
        } catch( CantOpenDatabaseException | DatabaseNotFoundException exception){
            String message = CantCreateTableException.DEFAULT_MESSAGE;
            FermatException cause = exception;
            String context = "";
            String possibleReason = "We couldn't open the Database, you should checkout the cause";
            throw new CantCreateTableException(message, cause, context, possibleReason);
        }
    }


    /**
     * This method provides the caller with a Table Structure object.
     * verify plugin owner id
     *
     * @param ownerId Plugin owner id
     * @param tableName table name to use
     * @return DatabaseTableFactory Object
     * @throws InvalidOwnerIdException
     */
    @Override
    public DatabaseTableFactory newTableFactory(UUID ownerId, String tableName) throws InvalidOwnerIdException {

        /**
         * I check that the owner id is the same I currently have..
         */
        if (this.ownerId != ownerId) {
            throw new InvalidOwnerIdException();
        }

        return new AndroidDatabaseTableFactory(tableName);
    }

    /**
     * This method provides the caller with a Table Structure object.
     *
     * @param tableName table name to use
     * @return DatabaseTableFactory object
     * @throws InvalidOwnerIdException
     */
    @Override
    public DatabaseTableFactory newTableFactory(String tableName) {
        return new AndroidDatabaseTableFactory(tableName);
    }

}