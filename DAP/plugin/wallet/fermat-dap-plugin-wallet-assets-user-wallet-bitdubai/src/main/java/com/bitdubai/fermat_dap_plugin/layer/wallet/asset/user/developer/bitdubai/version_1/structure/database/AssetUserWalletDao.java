package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransactionRecord;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransactionSummary;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetActorTransactionSummaryException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantStoreMemoException;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.AssetUserWalletBalance;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.AssetUserWalletTransactionWrapper;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.exceptions.CantExecuteAssetUserTransactionException;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.exceptions.CantGetBalanceRecordException;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 05/10/15.
 */
public class AssetUserWalletDao {
    //TODO: Manejo de excepciones
    private Database database;

    public AssetUserWalletDao(Database database) {
        this.database = database;
    }

    private long getCurrentBookBalance() throws CantGetBalanceRecordException{
        return getCurrentBalance(BalanceType.BOOK);
    }

    private long getCurrentBalance(final BalanceType balanceType) throws CantGetBalanceRecordException {
        long balanceAmount = 0;
        if (balanceType == BalanceType.AVAILABLE){
            for (DatabaseTableRecord record : getBalancesRecord())
            {
                balanceAmount += record.getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
            }
        }
        else{
            for (DatabaseTableRecord record : getBalancesRecord())
            {
                balanceAmount += record.getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);
            }
        }
        return balanceAmount;
    }

    private List<DatabaseTableRecord> getBalancesRecord() throws CantGetBalanceRecordException{
        try {
            DatabaseTable balancesTable = getBalancesTable();
            balancesTable.loadToMemory();
            //return balancesTable.getRecords().get(0);
            return balancesTable.getRecords();
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record",exception,"Can't load balance table" , "");
        }
    }

    private long getCurrentBalanceByAsset(BalanceType balanceType, String assetPublicKey)
    {
        try {
            long balanceAmount = 0;
            if (balanceType == BalanceType.AVAILABLE)
                balanceAmount = getBalancesByAssetRecord(assetPublicKey).getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
            else
                balanceAmount = getBalancesByAssetRecord(assetPublicKey).getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);
            return balanceAmount;
        }
        catch (Exception exception){
            return 0;
        }
    }

    private DatabaseTable getBalancesTable(){
        return database.getTable(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_NAME);
    }

    private List<AssetUserWalletList> getCurrentBookBalanceByAssets() throws CantGetBalanceRecordException{
        return getCurrentBalanceByAsset(BalanceType.BOOK);
    }

    private List<AssetUserWalletList> getCurrentBalanceByAsset(final BalanceType balanceType) throws CantGetBalanceRecordException {
        List<AssetUserWalletList> issuerWalletBalances= new ArrayList<>();
        if (balanceType == BalanceType.AVAILABLE){
            for (DatabaseTableRecord record : getBalancesRecord())
            {
                AssetUserWalletList assetIssuerWalletBalance = new AssetUserWalletBalance();
                assetIssuerWalletBalance.setName(record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_NAME_COLUMN_NAME));
                assetIssuerWalletBalance.setDescription(record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_DESCRIPTION_COLUMN_NAME));
                assetIssuerWalletBalance.setAssetPublicKey(record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME));
                assetIssuerWalletBalance.setBookBalance(record.getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME));
                assetIssuerWalletBalance.setAvailableBalance(record.getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME));
                issuerWalletBalances.add(assetIssuerWalletBalance);
            }
        }
        else{
            for (DatabaseTableRecord record : getBalancesRecord())
            {
                AssetUserWalletBalance assetIssuerWalletBalance = new AssetUserWalletBalance();
                assetIssuerWalletBalance.setName(record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_NAME_COLUMN_NAME));
                assetIssuerWalletBalance.setDescription(record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_DESCRIPTION_COLUMN_NAME));
                assetIssuerWalletBalance.setAssetPublicKey(record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME));
                assetIssuerWalletBalance.setAvailableBalance(record.getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME));
                assetIssuerWalletBalance.setBookBalance(record.getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME));
                issuerWalletBalances.add(assetIssuerWalletBalance);
            }

        }
        return issuerWalletBalances;
    }

    /*
     * getBookBalance must get actual Book Balance global of Asset Issuer wallet, select record from balances table
     */
    public long getBookBalance() throws CantCalculateBalanceException {
        try {
            return getCurrentBookBalance();
        } catch (CantGetBalanceRecordException exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");

        }
    }

    /*
     * getBookBalance must get actual Book Balance global of Asset Issuer wallet, select record from balances table
     */
    public List<AssetUserWalletList> getBookBalanceByAssets() throws CantCalculateBalanceException {
        try {
            return getCurrentBookBalanceByAssets();
        } catch (CantGetBalanceRecordException exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");

        }
    }


    /*
    * getBookBalance must get actual Book Balance global of Asset Issuer wallet, select record from balances table
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

    /*
    * getBookBalance must get actual Book Balance global of Asset Issuer wallet, select record from balances table
    */
    public List<AssetUserWalletList> getAvailableBalanceByAsset() throws CantCalculateBalanceException {
        try{
            return getCurrentAvailableBalanceByAssets();
        } catch (CantGetBalanceRecordException exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    /*
    * Add a new debit transaction.
    */
    public void addDebit(final AssetUserWalletTransactionRecord assetUserWalletTransactionRecord, final BalanceType balanceType) throws CantRegisterDebitException {
        try {
            if (isTransactionInTable(assetUserWalletTransactionRecord.getIdTransaction(), TransactionType.CREDIT, balanceType))
                throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, null, null, "The transaction is already in the database");

            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? assetUserWalletTransactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? assetUserWalletTransactionRecord.getAmount() : 0L;
            long availableRunningBalance = calculateAvailableRunningBalanceByAsset(-availableAmount, assetUserWalletTransactionRecord.getDigitalAsset().getPublicKey());
            long bookRunningBalance = calculateBookRunningBalanceByAsset(-bookAmount, assetUserWalletTransactionRecord.getDigitalAsset().getPublicKey());
            executeTransaction(assetUserWalletTransactionRecord, TransactionType.DEBIT, balanceType, availableRunningBalance, bookRunningBalance);
        }catch(CantGetBalanceRecordException | CantLoadTableToMemoryException | CantExecuteAssetUserTransactionException exception){
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception){
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    /*
    * Add a new credit transaction.
    */
    public void addCredit(final AssetUserWalletTransactionRecord assetUserWalletTransactionRecord, final BalanceType balanceType) throws CantRegisterCreditException {
        try {
            if (isTransactionInTable(assetUserWalletTransactionRecord.getIdTransaction(), TransactionType.CREDIT, balanceType))
                throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, null, null, "The transaction is already in the database");

            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? assetUserWalletTransactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? assetUserWalletTransactionRecord.getAmount() : 0L;
            long availableRunningBalance = calculateAvailableRunningBalanceByAsset(availableAmount, assetUserWalletTransactionRecord.getDigitalAsset().getPublicKey());
            long bookRunningBalance = calculateBookRunningBalanceByAsset(bookAmount, assetUserWalletTransactionRecord.getDigitalAsset().getPublicKey());
            executeTransaction(assetUserWalletTransactionRecord, TransactionType.CREDIT, balanceType, availableRunningBalance, bookRunningBalance);
        }catch(CantGetBalanceRecordException | CantLoadTableToMemoryException | CantExecuteAssetUserTransactionException exception){
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception){
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }


    public List<AssetUserWalletTransaction> listsTransactionsByAssets(BalanceType balanceType, TransactionType transactionType, int max, int offset, String assetPublicKey) throws CantGetTransactionsException {
        try {
            DatabaseTable databaseTableAssuerUsetWallet = getBalancesTable();
            databaseTableAssuerUsetWallet.setStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPublicKey, DatabaseFilterType.EQUAL);
            databaseTableAssuerUsetWallet.setStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
            databaseTableAssuerUsetWallet.setFilterTop(String.valueOf(max));
            databaseTableAssuerUsetWallet.setFilterOffSet(String.valueOf(offset));

            databaseTableAssuerUsetWallet.loadToMemory();
            return createTransactionList(databaseTableAssuerUsetWallet.getRecords());
        }
        catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        }
        catch (Exception exception){
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public List<AssetUserWalletTransaction> getTransactionsByActor(String actorPublicKey, BalanceType balanceType, int max, int offset) throws CantGetTransactionsException {
        try {
            DatabaseTable databaseTableAssuerUserWallet = getAssetUserWalletTable();

            databaseTableAssuerUserWallet.setStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ACTOR_FROM_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
            databaseTableAssuerUserWallet.setStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ACTOR_TO_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
            databaseTableAssuerUserWallet.setStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
            databaseTableAssuerUserWallet.setFilterOrder(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            databaseTableAssuerUserWallet.setFilterTop(String.valueOf(max));
            databaseTableAssuerUserWallet.setFilterOffSet(String.valueOf(offset));

            databaseTableAssuerUserWallet.loadToMemory();

            return createTransactionList(databaseTableAssuerUserWallet.getRecords());
        }
        catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        }
        catch (Exception exception){
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public List<AssetUserWalletTransaction> getTransactionsByTransactionType(TransactionType transactionType, int max, int offset) throws CantGetTransactionsException {
        try {
            DatabaseTable databaseTableAssuerUserWallet = getAssetUserWalletTable();

            databaseTableAssuerUserWallet.setStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
            databaseTableAssuerUserWallet.setFilterOrder(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            databaseTableAssuerUserWallet.setFilterTop(String.valueOf(max));
            databaseTableAssuerUserWallet.setFilterOffSet(String.valueOf(offset));

            databaseTableAssuerUserWallet.loadToMemory();

            return createTransactionList(databaseTableAssuerUserWallet.getRecords());
        }
        catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        }
        catch (Exception exception){
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public AssetUserWalletTransactionSummary getActorTransactionSummary(String actorPublicKey, BalanceType balanceType) throws CantGetActorTransactionSummaryException {
        return null;
    }

    public void updateMemoField(UUID transactionID, String memo) throws CantStoreMemoException, CantFindTransactionException {
        try
        {
            DatabaseTable databaseTableAssuerUserWalletBalance = getBalancesTable();
            /**
             *  I will load the information of table into a memory structure, filter for transaction id
             */

            databaseTableAssuerUserWalletBalance.setStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TABLE_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);
            for(DatabaseTableRecord record : databaseTableAssuerUserWalletBalance.getRecords()){
                record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_MEMO_COLUMN_NAME,memo);
                databaseTableAssuerUserWalletBalance.updateRecord(record);
            }
            databaseTableAssuerUserWalletBalance.loadToMemory();
        }catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantFindTransactionException("Transaction Memo Update Error",cantLoadTableToMemory,"Error load Transaction table" + transactionID.toString(), "");

        } catch (CantUpdateRecordException cantUpdateRecord) {
            throw new CantStoreMemoException("Transaction Memo Update Error",cantUpdateRecord,"Error update memo of Transaction " + transactionID.toString(), "");
        } catch(Exception exception){
            throw new CantStoreMemoException(CantStoreMemoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    private boolean isTransactionInTable(final UUID transactionId, final TransactionType transactionType, final BalanceType balanceType) throws CantLoadTableToMemoryException {
        DatabaseTable assetIssuerWalletTable = getAssetUserWalletTable();
        assetIssuerWalletTable.setUUIDFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_VERIFICATION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.setStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.setStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.loadToMemory();
        return !assetIssuerWalletTable.getRecords().isEmpty();
    }

    private List<AssetUserWalletList> getCurrentAvailableBalanceByAssets() throws CantGetBalanceRecordException{
        return getCurrentBalanceByAsset(BalanceType.AVAILABLE);
    }

    // Read record data and create transactions list
    private List<AssetUserWalletTransaction> createTransactionList(final Collection<DatabaseTableRecord> records) {
        List<AssetUserWalletTransaction> transactions = new ArrayList<>();

        for(DatabaseTableRecord record : records)
            transactions.add(constructAsseUserWalletTransactionFromRecord(record));

        return transactions;
    }

    private AssetUserWalletTransaction constructAsseUserWalletTransactionFromRecord(DatabaseTableRecord record) {
        UUID transactionId              = record.getUUIDValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TABLE_ID_COLUMN_NAME);
        String assetPublicKey           = record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME);
        String transactionHash          = record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TRANSACTION_HASH_COLUMN_NAME);
        TransactionType transactionType = TransactionType.getByCode(record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TYPE_COLUMN_NAME));
        CryptoAddress addressFrom       = new CryptoAddress();
        addressFrom.setAddress(record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESS_FROM_COLUMN_NAME));
        CryptoAddress addressTo         = new CryptoAddress();
        addressTo.setAddress(record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESS_TO_COLUMN_NAME));
        String actorFromPublicKey       = record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ACTOR_FROM_COLUMN_NAME);
        String actorToPublicKey         = record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ACTOR_TO_COLUMN_NAME);
        Actors actorFromType            = Actors.getByCode(record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ACTOR_FROM_TYPE_COLUMN_NAME));
        Actors actorToType              = Actors.getByCode(record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ACTOR_TO_TYPE_COLUMN_NAME));
        BalanceType balanceType         = BalanceType.getByCode(record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TYPE_COLUMN_NAME));
        long amount                     = record.getLongValue(  AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_AMOUNT_COLUMN_NAME);
        long runningBookBalance         = record.getLongValue(  AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_RUNNING_BOOK_BALANCE_COLUMN_NAME);
        long runningAvailableBalance    = record.getLongValue(  AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        long timeStamp                  = record.getLongValue(  AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TIME_STAMP_COLUMN_NAME);
        String memo                     = record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_MEMO_COLUMN_NAME);
        return new AssetUserWalletTransactionWrapper(transactionId, transactionHash, assetPublicKey, transactionType, addressFrom, addressTo,
                actorFromPublicKey, actorToPublicKey, actorFromType, actorToType, balanceType, amount, runningBookBalance, runningAvailableBalance, timeStamp, memo) {
        };

    }

    private void executeTransaction(final AssetUserWalletTransactionRecord assetIssuerWalletTransactionRecord, final TransactionType transactionType, final BalanceType balanceType, final long availableRunningBalance, final long bookRunningBalance) throws CantExecuteAssetUserTransactionException {
        //TODO: Falta manejo de excepciones
        try {
            DatabaseTableRecord assetIssuerWalletRecord = constructAssetIssuerWalletRecord(assetIssuerWalletTransactionRecord, transactionType, balanceType, availableRunningBalance, bookRunningBalance);//DatabaseTableRecord balanceRecord = constructBalanceRecord(availableRunningBalance, bookRunningBalance);
            DatabaseTableRecord assetBalanceRecord = constructAssetBalanceRecord(assetIssuerWalletTransactionRecord.getDigitalAsset(), availableRunningBalance, bookRunningBalance);
            DatabaseTransaction transaction = database.newTransaction();
            transaction.addRecordToInsert(getAssetUserWalletTable(), assetIssuerWalletRecord);
            DatabaseTableRecord record = getBalancesByAssetRecord(assetIssuerWalletTransactionRecord.getDigitalAsset().getPublicKey());
            if (record == null) {
                transaction.addRecordToInsert(getBalancesTable(), assetBalanceRecord);
            } else {
                transaction.addRecordToUpdate(getBalancesTable(), assetBalanceRecord);
            }
        }catch (Exception exception){

        }
    }

    private DatabaseTableRecord getBalancesByAssetRecord(String assetPublicKey) throws CantGetBalanceRecordException{
        try {
            DatabaseTable balancesTable = getBalancesTable();
            balancesTable.getNewFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, DatabaseFilterType.EQUAL, assetPublicKey);
            balancesTable.loadToMemory();
            return balancesTable.getRecords().get(0);
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record",exception,"Can't load balance table" , "");
        }
    }

    private DatabaseTableRecord constructAssetBalanceRecord(DigitalAsset digitalAsset, long availableRunningBalance, long bookRunningBalance) {
        try {
            DatabaseTableRecord record = getBalancesByAssetRecord(digitalAsset.getPublicKey()); //Si esto devuelve null, entonces creamos un registro en blanco
            if (record == null)
            {
                record = getBalancesTable().getEmptyRecord();
            }
            record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, digitalAsset.getPublicKey());
            record.setLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
            record.setLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME, bookRunningBalance);
            return record;
        }catch (Exception exception){
            return  null;
        }
    }

    private DatabaseTableRecord constructAssetIssuerWalletRecord(final AssetUserWalletTransactionRecord assetIssuerWalletTransactionRecord, final TransactionType transactionType, final BalanceType balanceType, final long availableRunningBalance, final long bookRunningBalance) {
        return null;
    }

    private long calculateAvailableRunningBalanceByAsset(final long transactionAmount, String assetPublicKey) throws CantGetBalanceRecordException{
        return  getCurrentAvailableBalanceByAsset(assetPublicKey) + transactionAmount;
    }


    private long calculateBookRunningBalanceByAsset(final long transactionAmount, String assetPublicKey) throws CantGetBalanceRecordException{
        return  getCurrentBookBalanceByAsset(assetPublicKey) + transactionAmount;
    }

    private long getCurrentBookBalanceByAsset(String assetPublicKey) throws CantGetBalanceRecordException{
        return getCurrentBalanceByAsset(BalanceType.BOOK, assetPublicKey);
    }

    private DatabaseTable getAssetUserWalletTable(){
        return database.getTable(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TABLE_NAME);
    }

    private long getCurrentAvailableBalance() throws CantGetBalanceRecordException{
        return getCurrentBalance(BalanceType.AVAILABLE);
    }

    private long getCurrentAvailableBalanceByAsset(String assetPublicKey) throws CantGetBalanceRecordException{
        return getCurrentBalanceByAsset(BalanceType.AVAILABLE, assetPublicKey);
    }
}