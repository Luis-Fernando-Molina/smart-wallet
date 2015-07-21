package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseSelectOperatorType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseTableOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseSelectOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableColumn;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseVariable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantSelectRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by Natalia on 09/02/2015.
 */
/**
 * This class define methods to manage a DatabaseTable object
 * Set filters and orders, and load records to memory.
 *
 * *
 */

public class AndroidDatabaseTable implements  DatabaseTable {

    /**
     * DatabaseTable Member Variables.
     */
    Context context;
    String tableName;
    SQLiteDatabase database;

    private List<DatabaseTableFilter> tableFilter;
    private  List<DatabaseTableRecord> records;
    private  DatabaseTableRecord tableRecord;
    private List<DataBaseTableOrder> tableOrder;
    private String top = "";
    private String offset = "";
    private DatabaseTableFilterGroup tableFilterGroup;

    private List<DatabaseVariable> variablesResult;

    private List<DatabaseSelectOperator> tableSelectOperator;

    // Public constructor declarations.

    /**
     * <p>DatabaseTable implementation constructor
     *
     * @param context Android Context Object
     * @param database name database to use
     * @param tableName name table to use
     */

    public AndroidDatabaseTable (Context context,SQLiteDatabase database, String tableName){
        this.tableName = tableName;
        this.context = context;
        this.database = database;
    }

    /**
     * DatabaseTable interface implementation.
     */

    public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	/**
     * <p>This method return a new empty instance of DatabaseTableColumn object
     *
     * @return DatabaseTableColumn object
     */
    @Override
    public DatabaseTableColumn newColumn(){
        return new AndroidDatabaseTableColumn();
        }

    /**
     * <p>This method return a list of table columns names
     *
     * @return List<String> of columns names
     */

    @Override
    public List<String> getColumns(){
        List<String> columns = new ArrayList<String>();
        Cursor c = this.database.rawQuery("SELECT * FROM "+ tableName, null);
        String[] columnNames = c.getColumnNames();
        c.close();

        for (int i = 0; i < columnNames.length; ++i) {
            columns.add(columnNames[i].toString());
        }

        return columns;
    }

    /**
     * <p>This method return a list of Database Table Record objects
     *
     * @return List<DatabaseTableRecord> List of DatabaseTableRecord objects
     */
    @Override
    public List<DatabaseTableRecord> getRecords()
    {
        return this.records;

    }

    /**
     *
     * @return
     */

    @Override
    public List<DatabaseVariable> getVarialbesResult(){
        return this.variablesResult;
    }


    @Override
    public void setVarialbesResult(List<DatabaseVariable> variables){
        this.variablesResult = variables;
    }

    /**
     *  <p>This method return a new empty instance of DatabaseTableRecord object
     *
     * @return DatabaseTableRecord object
     */
    @Override
    public DatabaseTableRecord getEmptyRecord()
    {
        return new AndroidDatabaseRecord() ;

    }


    @Override
    public DatabaseTableFilter getEmptyTableFilter()
    {
        return new AndroidDatabaseTableFilter() ;

    }

    @Override
    public DatabaseTableFilterGroup getEmptyTableFilterGroup()
    {
        return new AndroidDatabaseTableFilterGroup() ;

    }

    /**
     * <p>This method clean Filter object
     */
    @Override
    public void clearAllFilters()
    {
        this.tableFilter = null;
    }

    /**
     * <p>This method return a list of DatabaseTableFilter objects
     *
     * @return List<DatabaseTableFilter> object
     */
    @Override
    public List<DatabaseTableFilter> getFilters()
    {
       return this.tableFilter;
    }

    /**
     * <p>This method return a DatabaseTableFilterGroup objects
     *
     * @return DatabaseTableFilterGroup object
     */
    @Override
    public DatabaseTableFilterGroup getFilterGroup()
    {
        return this.tableFilterGroup;
    }

    /**
     * <p>This method selects one or more fields in a table .
     * <p>Accepts the use of operators on select as SUM or COUNT.
     * <p>saves each field with its value in an array of variables to be used in other querys.
     *
     * @param record
     * @throws CantSelectRecordException
     */

    @Override
    public void selectRecord (DatabaseTableRecord record) throws CantSelectRecordException {
        /**
         * First I get the table records with values.
         * and construct de ContentValues array for SqlLite
         */
        try{
            StringBuffer strRecords = new StringBuffer ("");
            StringBuffer strValues  = new StringBuffer ("");

            List<DatabaseRecord> records =  record.getValues();

            //check if declared operators to apply on select or only define some fields

            if(this.tableSelectOperator != null) {


                for (int i = 0; i < tableSelectOperator.size(); ++i) {

                    if (strRecords.length() > 0)
                        strRecords.append(",");


                    switch (tableSelectOperator.get(i).getType()) {
                        case SUM:
                            strRecords.append(" SUM (" + tableSelectOperator.get(i).getColumn() +") AS " + tableSelectOperator.get(i).getAliasColumn() );
                            break;
                        case COUNT:
                            strRecords.append(" COUNT (" + tableSelectOperator.get(i).getColumn() +") AS " + tableSelectOperator.get(i).getAliasColumn());
                            break;


                        default:
                            strRecords.append(" ");
                            break;
                    }


                }
            }
            else
            {
                for (int i = 0; i < records.size(); ++i) {

                    if(strRecords.length() > 0 )
                        strRecords.append (",");
                    strRecords.append(records.get(i).getName ());

                }
            }





            Cursor c = this.database.rawQuery("SELECT " + strRecords + " FROM " + tableName + " " + makeFilter(),null);

            List<String> columns = getColumns();
            int columnsCant =0;

            this.variablesResult =  new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    /**
                     * Get columns name to read values of files
                     *
                     */
                   DatabaseVariable variable = new AndroidVariable();

                    variable.setName("@" + c.getColumnName(columnsCant));
                    variable.setValue(c.getString(columnsCant));

                    this.variablesResult.add(variable);
                    columnsCant++;
                } while (c.moveToNext());
            }
        }
        catch (Exception exception)
        {
            throw new CantSelectRecordException();
        }


        }
    /**
     * <p>This method update a table record in the database
     *
     * @param record DatabaseTableRecord object to update
     * @throws CantUpdateRecordException
     */
    @Override
    public void updateRecord (DatabaseTableRecord record) throws CantUpdateRecordException
    {

        try
        {
             List<DatabaseRecord> records =  record.getValues();
            StringBuffer strRecords = new StringBuffer ("");
           // ContentValues recordUpdateList = new ContentValues();

            /**
             * I update only the fields marked as modified
             *
             */

        for (int i = 0; i < records.size(); ++i) {

            if(records.get(i).getChange())
            {

                   // recordUpdateList.put(records.get(i).getName(), records.get(i).getValue());
                if(strRecords.length() > 0 )
                    strRecords.append (",");

                //I check if the value to change what I have to take a variable,
                // and look that at the result of the select

                if(records.get(i).getUseValueofVariable())
                {
                    for (int j = 0; j < variablesResult.size(); ++j) {

                        if(variablesResult.get(j).getName().equals(records.get(i).getValue()))
                            strRecords.append(records.get(i).getName() + " = '" + variablesResult.get(j).getValue() + "'");
                    }

                }
                else
                {
                    strRecords.append(records.get(i).getName() +" = '" + records.get(i).getValue() + "'");
                }

            }

        }

            this.database.execSQL("UPDATE " + tableName + " SET " + strRecords + " " + makeFilter());

      //  this.database.update(tableName, recordUpdateList, makeFilter().replace("WHERE", ""), null);

       }
        catch (Exception exception)
        {
            throw new CantUpdateRecordException();
        }
    }

    /**
     * <p>This method inserts a new record in the database
     *
     * @param record DatabaseTableRecord to insert
     * @throws CantInsertRecordException
     */
    @Override
    public void insertRecord(DatabaseTableRecord record) throws CantInsertRecordException {

        /**
         * First I get the table records with values.
         * and construct de ContentValues array for SqlLite
         */
        try{
        	StringBuffer strRecords = new StringBuffer ("");
            StringBuffer strValues  = new StringBuffer ("");

             List<DatabaseRecord> records =  record.getValues();



            for (int i = 0; i < records.size(); ++i) {
                //initialValues.put(records.get(i).getName(),records.get(i).getValue());

                if(strRecords.length() > 0 )
                    strRecords.append (",");
                strRecords.append(records.get(i).getName ());

                if(strValues.length() > 0 )
                    strValues.append (",");

                //I check if the value to insert what I have to take a variable,
                // and look that at the result of the select

                if(records.get(i).getUseValueofVariable())
                {
                    for (int j = 0; j < variablesResult.size(); ++j) {

                        if(variablesResult.get(j).getName().equals(records.get(i).getValue()))
                            strValues.append("'" + variablesResult.get(j).getValue() + "'");
                    }
                }
                else
                {
                    strValues.append ("'" + records.get(i).getValue() + "'");
                }

            }

            this.database.execSQL("INSERT INTO " + tableName + "(" + strRecords + ")" + " VALUES (" + strValues + ")");
        }
        catch (Exception exception) {
            throw new CantInsertRecordException();
        }


    }

    /**
     * <p>This method load all table records in a List of DatabaseTableRecord object
     * <p>Then use the method getRecords() to to retrieve.
     *
     * @throws CantLoadTableToMemoryException
     */
    @Override
    public void loadToMemory() throws CantLoadTableToMemoryException {

        this.records = new ArrayList<>();

        String topSentence = "";
        String offsetSentence = "";
        if (!this.top.isEmpty())
            topSentence = " LIMIT " + this.top;

        if (!this.offset.isEmpty())
            offsetSentence = " OFFSET " + this.offset;

        Cursor cursor = null;

        /**
         * Get columns name to read values of files
         *
         */
        try {
            List<String> columns = getColumns();
            cursor = this.database.rawQuery("SELECT  * FROM " + tableName + makeFilter() + makeOrder() + topSentence + offsetSentence, null);
            while (cursor.moveToNext()) {
                DatabaseTableRecord tableRecord = new AndroidDatabaseRecord();
                List<DatabaseRecord> recordValues = new ArrayList<>();

                for(String column : columns){
                    DatabaseRecord recordValue = new AndroidRecord();
                    recordValue.setName(column);
                    recordValue.setValue(cursor.getString(cursor.getColumnIndex(column)));
                    recordValue.setChange(false);
                    recordValue.setUseValueofVariable(false);
                    recordValues.add(recordValue);
                }

                tableRecord.setValues(recordValues);
                this.records.add(tableRecord);
            }
            cursor.close();
        } catch (Exception e) {
            if(cursor != null)
                cursor.close();
            throw new CantLoadTableToMemoryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }

    /**
     * <p>Check if the set will table in tableName variable exists
     *
     * @return boolean
     */
    @Override
    public boolean isTableExists() {

        Cursor cursor = this.database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+ this.tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    /**
     *<p>Sets the filter on a string field
     *
     * @param columName column name to filter
     * @param value value to filter
     * @param type DatabaseFilterType object
     */
    @Override
    public void setStringFilter(String columName, String value,DatabaseFilterType type){

        if(this.tableFilter == null)
            this.tableFilter = new ArrayList<DatabaseTableFilter>();

        DatabaseTableFilter filter = new AndroidDatabaseTableFilter();

        filter.setColumn(columName);
        filter.setValue(value);
        filter.setType(type);

        this.tableFilter.add(filter);
    }

    /**
     *<p>Sets the filter on a UUID field
     *
     * @param columName column name to filter
     * @param value value to filter
     * @param type DatabaseFilterType object
     */
    @Override
    public void setUUIDFilter(String columName, UUID value,DatabaseFilterType type){

        if(this.tableFilter == null)
            this.tableFilter = new ArrayList<DatabaseTableFilter>();

        DatabaseTableFilter filter = new AndroidDatabaseTableFilter();

        filter.setColumn(columName);
        filter.setValue(value.toString());
        filter.setType(type);

        this.tableFilter.add(filter);

    }

    /**
     * <p>Sets the order in which filtering field shown in ascendent or descending
     *
     * @param columnName    Name of the column to sort
     * @param direction  DatabaseFilterOrder object
     */
    @Override
    public void setFilterOrder(String columnName, DatabaseFilterOrder direction){

        if(this.tableOrder == null)
            this.tableOrder = new ArrayList<DataBaseTableOrder>();

        DataBaseTableOrder order = new AndroidDatabaseTableOrder();

        order.setColumName(columnName);
        order.setDirection(direction);


        this.tableOrder.add(order);
    }

    /**
     * <p>Sets the operator to apply on select statement
     *
     * @param columnName Name of the column to apply operator
     * @param operator  DataBaseSelectOperatorType type
     */
    @Override
    public void setSelectOperator(String columnName, DataBaseSelectOperatorType operator, String alias){

        if(this.tableSelectOperator == null)
            this.tableSelectOperator = new ArrayList<DatabaseSelectOperator>();

        DatabaseSelectOperator selectOperator = new AndroidDatabaseSelectOperator();

        selectOperator.setColumn(columnName);
            selectOperator.setType(operator);
        selectOperator.setAliasColumn(alias);


        this.tableSelectOperator.add(selectOperator);
    }


    /**
     *<p>Sets the number of records to be selected in query
     *
     * @param top number of records to select (in string)
     */
    @Override
    public void setFilterTop(String top){
        this.top = top;
    }


    /**
     *<p>Sets the records page
     * @param offset
     */
    public void setFilterOffSet(String offset){
        this.offset = offset;
    }


    /**
     *<p>Sets the filter and subgroup to filter for queries with grouped where
     *
     * @param filters list of DatabaseTableFilter object
     * @param subGroups list of DatabaseTableFilterGroup objects
     * @param operator DatabaseFilterOperator enumerator
     */
    @Override
    public void setFilterGroup(List<DatabaseTableFilter> filters, List<DatabaseTableFilterGroup> subGroups, DatabaseFilterOperator operator){

        DatabaseTableFilterGroup filterGroup = new AndroidDatabaseTableFilterGroup();

        filterGroup.setFilters(filters);
        filterGroup.setSubGroups(subGroups);
        filterGroup.setOperator(operator);

        this.tableFilterGroup = filterGroup;
    }

    /**
     * DatabaseTable interface private void.
     */

    /**
     * Sets the context for access to the device memory
     * @param context Android Context Object
     */

    public void setContext(Object context) {
        this.context = (Context) context;
    }

    private String makeFilter(){

        // I check the definition for the filter object, filter type, filter columns names
        // and build the WHERE statement
        String filter = "";
        StringBuffer strFilter = new StringBuffer();

        if(this.tableFilter != null)
        {
            for (int i = 0; i < tableFilter.size(); ++i) {

                strFilter.append(tableFilter.get(i).getColumn());

                switch (tableFilter.get(i).getType()) {
                    case EQUAL:
                        strFilter.append(" ='" + tableFilter.get(i).getValue() + "'");
                        break;
                    case GRATER_THAN:
                        strFilter.append( " > " + tableFilter.get(i).getValue());
                        break;
                    case LESS_THAN:
                        strFilter.append( " < " + tableFilter.get(i).getValue());
                        break;
                    case LIKE:
                        strFilter.append(" Like '%" + tableFilter.get(i).getValue() + "%'");
                        break;
                    default:
                        strFilter.append(" ");
                        break;
                }

                if(i < tableFilter.size()-1)
                    strFilter.append(" AND ");

            }


            filter = strFilter.toString();
            if(strFilter.length() > 0 ) filter = " WHERE " + filter;

            return filter;
        }
        else
        {
            //if set group filter
            if(this.tableFilterGroup != null)
            {
                return makeGroupFilters(this.tableFilterGroup);
            }
            else
            {
                return filter;
            }
        }


    }

    private String makeOrder(){

        // I check the definition for the oder object, order direction, order columns names
        // and build the ORDER BY statement
        String order= "";
        StringBuffer strOrder = new StringBuffer();

        if(this.tableOrder != null)
        {
            for (int i = 0; i < tableOrder.size(); ++i) {

                switch (tableOrder.get(i).getDirection()) {
                    case DESCENDING:
                        strOrder.append(tableOrder.get(i).getColumName() + " DESC ");
                        break;
                    case ASCENDING:
                        strOrder.append(tableOrder.get(i).getColumName());
                        break;
                    default:
                        strOrder.append(" ");
                        break;

                }
                    if(i < tableOrder.size()-1)
                        strOrder.append(" , ");

            }
        }

        order = strOrder.toString();
        if(strOrder.length() > 0 ) order = " ORDER BY " + order;

        return order;
    }


    private String makeInternalCondition(DatabaseTableFilter filter){

        StringBuffer strFilter = new StringBuffer();

       strFilter.append(filter.getColumn());

        switch (filter.getType()) {
            case EQUAL:
                strFilter.append(" ='" + filter.getValue() + "'");
                break;
            case GRATER_THAN:
                strFilter.append(" > " + filter.getValue());
                break;
            case LESS_THAN:
                strFilter.append(" < " + filter.getValue());
                break;
            case LIKE:
                strFilter.append(" Like '%" + filter.getValue() + "%'");
                break;
            default:
                strFilter.append(" ");
        }
        return strFilter.toString();
    }

    private String makeInternalConditionGroup(List<DatabaseTableFilter> filters, DatabaseFilterOperator operator){

        StringBuffer strFilter = new StringBuffer();

        for (DatabaseTableFilter filter : filters){
            switch (operator) {
                case AND:
                    if(strFilter.length() > 0)
                        strFilter.append(" AND ");

                    strFilter.append(makeInternalCondition(filter));
                    break;
                case OR:
                    if(strFilter.length() > 0)
                        strFilter.append(" OR ");

                    strFilter.append(makeInternalCondition(filter));
                    break;
                default:
                    strFilter.append(" ");
            }

        }
        return strFilter.toString();
    }

    public String makeGroupFilters(DatabaseTableFilterGroup databaseTableFilterGroup){

        StringBuffer strFilter = new StringBuffer();
        String filter = "";

        if(databaseTableFilterGroup != null && (databaseTableFilterGroup.getFilters().size() > 0 || databaseTableFilterGroup.getSubGroups().size() > 0)) {
                strFilter.append("(");
                strFilter.append(makeInternalConditionGroup(databaseTableFilterGroup.getFilters(), databaseTableFilterGroup.getOperator()));

                int ix = 0;
                for(DatabaseTableFilterGroup subGroup : databaseTableFilterGroup.getSubGroups()){
                    if (subGroup.getFilters().size() > 0 || ix > 0){
                        switch (databaseTableFilterGroup.getOperator()) {
                            case AND:
                                strFilter.append(" AND ");
                                break;
                            case OR:
                                strFilter.append(" OR ");
                                break;
                            default:
                                strFilter.append(" ");
                        }
                    }
                    strFilter.append("(");
                    strFilter.append(makeGroupFilters(subGroup));
                    strFilter.append(")");
                    ix++;
                }
                strFilter.append(")");
        }

        filter = strFilter.toString();
        if(strFilter.length() > 0 ) filter = " WHERE " + filter;

        return filter;
    }

    @Override
    public void deleteRecord(DatabaseTableRecord record) throws CantDeleteRecordException {
        try{


            List<DatabaseRecord> records =  record.getValues();

            String queryWhereClause="";

            if(!records.isEmpty()) {
                for (int i = 0; i < records.size(); ++i) {

                    if (queryWhereClause.length() > 0) {
                        queryWhereClause += " and ";
                        queryWhereClause += records.get(i).getName();
                    } else
                        queryWhereClause += records.get(i).getName();
                    queryWhereClause += "=";
                    queryWhereClause += records.get(i).getValue();
                }
            }else{
                queryWhereClause=null;
            }


            if(queryWhereClause!=null){
                this.database.execSQL("DELETE FROM " + tableName + " WHERE " + queryWhereClause);
            }else{
                this.database.execSQL("DELETE FROM " + tableName);
            }

        }catch (Exception exception) {
            throw new CantDeleteRecordException();
        }
    }

    //testear haber si funciona así de abstracto o hay que hacerlo más especifico
    @Override
    public DatabaseTableRecord getRecordFromPk(String pk) throws Exception {

        Cursor c = database.rawQuery(" SELECT * from "+tableName+" WHERE pk="+pk,null);

        List<String> columns = getColumns();
        DatabaseTableRecord tableRecord1 = new AndroidDatabaseRecord();
        if (c.moveToFirst()) {
            /**
             -                * Get columns name to read values of files
             *
             */

            List<DatabaseRecord> recordValues = new ArrayList<>();

            for (int i = 0; i < columns.size(); ++i) {
                DatabaseRecord recordValue = new AndroidRecord();
                recordValue.setName(columns.get(i).toString());
                recordValue.setValue(c.getString(c.getColumnIndex(columns.get(i).toString())));
                recordValue.setChange(false);
                recordValues.add(recordValue);
            }
            tableRecord1.setValues(recordValues);

            if(c.moveToNext()){
                //si pasa esto es porque hay algo mal
                throw new Exception();
            }

        }else{
            return null;
        }
        return tableRecord1;
    }

    @Override
    public String toString(){
        return tableName;
    }


}

