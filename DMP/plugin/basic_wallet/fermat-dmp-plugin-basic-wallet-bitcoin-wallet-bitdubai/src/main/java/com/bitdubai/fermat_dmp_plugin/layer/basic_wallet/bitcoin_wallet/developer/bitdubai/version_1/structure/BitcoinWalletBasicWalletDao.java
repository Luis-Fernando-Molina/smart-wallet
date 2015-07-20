package com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure;



import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantStoreMemoException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterCreditException;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;


import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.exceptions.CantExecuteBitconTransactionException;
import com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.exceptions.CantGetBalanceRecordException;
import com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.util.BitcoinWalletTransactionWrapper;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


/**
 * Created by eze on 2015.06.23..
 */
public class BitcoinWalletBasicWalletDao {

    /**
     * CryptoAddressBook Interface member variables.
     */
    private Database database;

    /**
     * Constructor.
     */
    public BitcoinWalletBasicWalletDao(Database database){
        /**
         * The only one who can set the pluginId is the Plugin Root.
         */
        this.database = database;
    }

    /*
     * getBookBalance must get actual Book Balance of wallet, select record from balances table
     */
    public long getBookBalance() throws CantCalculateBalanceException {
        try{
            return getCurrentBookBalance();
        } catch (CantGetBalanceRecordException exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    /*
     * getBookBalance must get actual Book Balance of wallet, select record from balances table
     */
    public long getAvailableBalance() throws CantCalculateBalanceException {
        try{
            return getCurrentAvailableBalance();
        } catch (CantGetBalanceRecordException exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public List<BitcoinWalletTransaction> getTransactions(int max, int offset) throws CantGetTransactionsException {
        try {
            DatabaseTable bitcoinwalletTable = filterBitcoinWalletTableMaxOffset(max, offset);
            return createTransactionList(bitcoinwalletTable.getRecords());
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception){
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    /*
     * Add a new debit transaction.
     */
    public void addDebit(final BitcoinWalletTransactionRecord transactionRecord, final BalanceType balanceType) throws CantRegisterDebitException {
        try {
            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? transactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? transactionRecord.getAmount() : 0L;
            long availableRunningBalance = calculateAvailableRunningBalance(-availableAmount);
            long bookRunningBalance = calculateBookRunningBalance(-bookAmount);

            //todo update if the record exists. The record might exists if many send request are executed so add an else to this If

            if (!isTransactionInTable(transactionRecord.getIdTransaction(), TransactionType.DEBIT, balanceType))
                executeTransaction(transactionRecord, TransactionType.DEBIT, balanceType, availableRunningBalance, bookRunningBalance);

        } catch (CantGetBalanceRecordException | CantExecuteBitconTransactionException exception) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, e, null, "Check the cause");
        } catch (Exception exception){
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    /*
     * Add a new Credit transaction.
     */
    public void addCredit(final BitcoinWalletTransactionRecord transactionRecord, final BalanceType balanceType) throws CantRegisterCreditException{
        try{
            if(isTransactionInTable(transactionRecord.getIdTransaction(), TransactionType.CREDIT, balanceType))
                throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, null, null, "The transaction is already in the database");

            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? transactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? transactionRecord.getAmount() : 0L;
            long availableRunningBalance = calculateAvailableRunningBalance(availableAmount);
            long bookRunningBalance = calculateBookRunningBalance(bookAmount);
            executeTransaction(transactionRecord,TransactionType.CREDIT ,balanceType, availableRunningBalance, bookRunningBalance);
        } catch(CantGetBalanceRecordException | CantLoadTableToMemoryException | CantExecuteBitconTransactionException exception){
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception){
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public void updateMemoFiled(UUID transactionID, String memo) throws CantStoreMemoException, CantFindTransactionException {
        try {
            // create the database objects
            DatabaseTable bitcoinwalletTable = getBitcoinWalletTable();
            /**
             *  I will load the information of table into a memory structure, filter for transaction id
             */
            bitcoinwalletTable.setStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);

            bitcoinwalletTable.loadToMemory();

            // Read record data and create transactions list
            for(DatabaseTableRecord record : bitcoinwalletTable.getRecords()){
                record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_MEMO_COLUMN_NAME,memo);
                bitcoinwalletTable.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantFindTransactionException("Transaction Memo Update Error",cantLoadTableToMemory,"Error load Transaction table" + transactionID.toString(), "");

        } catch (CantUpdateRecordException cantUpdateRecord) {
            throw new CantStoreMemoException("Transaction Memo Update Error",cantUpdateRecord,"Error update memo of Transaction " + transactionID.toString(), "");
        } catch(Exception exception){
            throw new CantStoreMemoException(CantStoreMemoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /**
     * We use this method to check if the given transaction is already in the table, we do a query to the table with the specifics of the record
     * and then check if it's not empty
     * @param transactionId
     * @param transactionType
     * @param balanceType
     * @return
     * @throws CantLoadTableToMemoryException
     */
    private boolean isTransactionInTable(final UUID transactionId, final TransactionType transactionType, final BalanceType balanceType) throws CantLoadTableToMemoryException {
        DatabaseTable bitCoinWalletTable = getBitcoinWalletTable();
        bitCoinWalletTable.setUUIDFilter(BitcoinWalletDatabaseConstants.BBITCOIN_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
        bitCoinWalletTable.setStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        bitCoinWalletTable.setStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
        bitCoinWalletTable.loadToMemory();
        return !bitCoinWalletTable.getRecords().isEmpty();
    }

    private DatabaseTable getBitcoinWalletTable(){
        return database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_NAME);
    }

    private long calculateAvailableRunningBalance(final long transactionAmount) throws CantGetBalanceRecordException{
        return  getCurrentAvailableBalance() + transactionAmount;
    }

    private long calculateBookRunningBalance(final long transactionAmount) throws CantGetBalanceRecordException{
        return  getCurrentBookBalance() + transactionAmount;
    }

    private long getCurrentBookBalance() throws CantGetBalanceRecordException{
        return getCurrentBalance(BalanceType.BOOK);
    }

    private long getCurrentAvailableBalance() throws CantGetBalanceRecordException{
        return getCurrentBalance(BalanceType.AVAILABLE);
    }

    private long getCurrentBalance(final BalanceType balanceType) throws CantGetBalanceRecordException {
        if (balanceType == BalanceType.AVAILABLE)
            return getBalancesRecord().getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
        else
            return getBalancesRecord().getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);
    }

    private DatabaseTableRecord getBalancesRecord() throws CantGetBalanceRecordException{
        try {
            database.openDatabase();
            DatabaseTable balancesTable = getBalancesTable();
            balancesTable.loadToMemory();
            return balancesTable.getRecords().get(0);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException | CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record",exception,"Can't load balance table" , "");
        }
    }

    private DatabaseTable getBalancesTable(){
        return database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_NAME);
    }

    private void executeTransaction(final BitcoinWalletTransactionRecord transactionRecord, final TransactionType transactionType, final BalanceType balanceType, final long availableRunningBalance, final long bookRunningBalance) throws CantExecuteBitconTransactionException {
        try{
            DatabaseTableRecord bitcoinWalletRecord = constructBitcoinWalletRecord(transactionRecord, balanceType,transactionType ,availableRunningBalance, bookRunningBalance);
            DatabaseTableRecord balanceRecord = constructBalanceRecord(availableRunningBalance, bookRunningBalance);

            BitcoinWalletBasicWalletDaoTransaction bitcoinWalletBasicWalletDaoTransaction = new BitcoinWalletBasicWalletDaoTransaction(database);

            bitcoinWalletBasicWalletDaoTransaction.executeTransaction(getBitcoinWalletTable(), bitcoinWalletRecord, getBalancesTable(), balanceRecord);
        } catch(CantGetBalanceRecordException | CantLoadTableToMemoryException exception){
            throw new CantExecuteBitconTransactionException(CantExecuteBitconTransactionException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        }
    }

    /**
     * This method constructs a Table Record using the data from the transactionRecord, the balance type and the runningBalances that have already been calculated
     * @param transactionRecord
     * @param balanceType
     * @param availableRunningBalance
     * @param bookRunningBalance
     * @return
     * @throws CantLoadTableToMemoryException
     */
    private DatabaseTableRecord constructBitcoinWalletRecord(final BitcoinWalletTransactionRecord transactionRecord, final BalanceType balanceType, final TransactionType transactionType,final long availableRunningBalance, final long bookRunningBalance) throws CantLoadTableToMemoryException{
        DatabaseTableRecord record = getBitcoinWalletEmptyRecord();
        record.setUUIDValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME, UUID.randomUUID());
        record.setUUIDValue(BitcoinWalletDatabaseConstants.BBITCOIN_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME, transactionRecord.getIdTransaction());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode());
        record.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_AMOUNT_COLUMN_NAME, transactionRecord.getAmount());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_MEMO_COLUMN_NAME, transactionRecord.getMemo());
        record.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, transactionRecord.getTimestamp());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TRANSACTION_HASH_COLUMN_NAME, transactionRecord.getTransactionHash());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_FROM_COLUMN_NAME, transactionRecord.getAddressFrom().getAddress());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_TO_COLUMN_NAME, transactionRecord.getAddressTo().getAddress());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode());
        record.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
        record.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_BOOK_BALANCE_COLUMN_NAME, bookRunningBalance);
        record.setUUIDValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME, transactionRecord.getActorFrom());
        record.setUUIDValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_TO_COLUMN_NAME, transactionRecord.getActorTo());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_FROM_TYPE_COLUMN_NAME, transactionRecord.getActorFromType().getCode());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_TO_TYPE_COLUMN_NAME, transactionRecord.getActorToType().getCode());
        return record;
    }

    private DatabaseTableRecord getBitcoinWalletEmptyRecord() throws CantLoadTableToMemoryException{
        return getBitcoinWalletTable().getEmptyRecord();
    }

    private DatabaseTableRecord constructBalanceRecord(final long availableRunningBalance, final long bookRunningBalance) throws CantGetBalanceRecordException{
        DatabaseTableRecord record = getBalancesRecord();
        record.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
        record.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME, bookRunningBalance);
        return record;
    }

    // create the database objects
    private DatabaseTable filterBitcoinWalletTableMaxOffset(int max, int offset) throws CantLoadTableToMemoryException {
        DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();
        bitcoinWalletTable.setFilterTop(String.valueOf(max));
        bitcoinWalletTable.setFilterOffSet(String.valueOf(offset));
        bitcoinWalletTable.loadToMemory();
        return bitcoinWalletTable;
    }

    // Read record data and create transactions list
    private List<BitcoinWalletTransaction> createTransactionList(final Collection<DatabaseTableRecord> records){

        List<BitcoinWalletTransaction> transactions = new ArrayList<>();

        for(DatabaseTableRecord record : records)
            transactions.add(constructBitcoinWalletTransactionFromRecord(record));

        return transactions;
    }

    private BitcoinWalletTransaction constructBitcoinWalletTransactionFromRecord(final DatabaseTableRecord record){
        UUID transactionId = record.getUUIDValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME);
        String transactionHash= record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME);
        TransactionType transactionType= TransactionType.getByCode(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME));
        CryptoAddress addressFrom = new CryptoAddress();
        addressFrom.setAddress(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_FROM_COLUMN_NAME));
        CryptoAddress addressTo = new CryptoAddress();
        addressTo.setAddress(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_TO_COLUMN_NAME));
        UUID actorFrom = record.getUUIDValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME);
        UUID actorTo = record.getUUIDValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME);
        Actors actorFromType = Actors.getByCode(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_FROM_TYPE_COLUMN_NAME));
        Actors actorToType = Actors.getByCode(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_TO_TYPE_COLUMN_NAME));
        BalanceType balanceType =  BalanceType.getByCode(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME));
        long amount = record.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_AMOUNT_COLUMN_NAME);
        long runningBookBalance = record.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_BOOK_BALANCE_COLUMN_NAME);
        long runningAvailableBalance = record.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        long timeStamp = record.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME);
        String memo = record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_MEMO_COLUMN_NAME);

        return new BitcoinWalletTransactionWrapper(transactionId, transactionHash, transactionType, addressFrom, addressTo,
                actorFrom, actorTo, actorFromType, actorToType, balanceType, amount, runningBookBalance, runningAvailableBalance, timeStamp, memo);
    }
}