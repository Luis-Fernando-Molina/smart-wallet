package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventType;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.events.IncomingCryptoIdentifiedEvent;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantCalculateTransactionConfidenceException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantExecuteQueryException;

import org.bitcoinj.core.Wallet;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by rodrigo on 2015.06.17..
 */
public class CryptoVaultDatabaseActions implements DealsWithEvents, DealsWithErrors{
    /**
     * CryptoVaultDatabaseActions  member variables
     */
    Database database;
    Wallet vault;

    /**
     * DealsWithEvents interface member variables
     */
    EventManager eventManager;

    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;


    /**
     * DealsWithErrors interface implementation
     * @param errorManager
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }


    /**
     * Constructor
     * @param database
     */
    public CryptoVaultDatabaseActions(Database database, ErrorManager errorManager, EventManager eventManager){
        this.database = database;
        this.eventManager = eventManager;
        this.errorManager = errorManager;
    }

    public void setVault(Wallet vault){
        this.vault = vault;
    }


    public void saveIncomingTransaction(String txHash) throws CantExecuteQueryException {
        DatabaseTable cryptoTxTable;
        DatabaseTransaction dbTx = this.database.newTransaction();
        cryptoTxTable = database.getTable(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_NAME);
        DatabaseTableRecord incomingTxRecord =  cryptoTxTable.getEmptyRecord();
        UUID txId = UUID.randomUUID();
        incomingTxRecord.setStringValue(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_ID_COLUMN_NAME, txId.toString());
        incomingTxRecord.setStringValue(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_HASH_COLUMN_NAME, txHash);
        incomingTxRecord.setStringValue(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_PROTOCOL_STS_COLUMN_NAME, ProtocolStatus.TO_BE_NOTIFIED.toString());
        incomingTxRecord.setStringValue(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRANSACTION_STS_COLUMN_NAME, CryptoStatus.IDENTIFIED.toString());

        dbTx.addRecordToInsert(cryptoTxTable, incomingTxRecord);
        try {
            this.database.executeTransaction(dbTx);
        } catch (DatabaseTransactionFailedException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExecuteQueryException();
        }


        /**
         * after I save the transaction in the database and the vault, I'll raise the incoming transaction.
         *
         */
        PlatformEvent event = new IncomingCryptoIdentifiedEvent(EventType.INCOMING_CRYPTO_RECEIVED);
        eventManager.raiseEvent(event);

    }

    /**
     * Validates if the transaction ID passed is new or not. This helps to decide If I need to apply the transactions or not
     * @param txId the ID of the transaction
     * @return
     */
    public boolean isNewFermatTransaction(UUID txId) throws CantExecuteQueryException {
        DatabaseTable fermatTxTable;

        fermatTxTable = database.getTable(CryptoVaultDatabaseConstants.FERMAT_TRANSACTIONS_TABLE_NAME);
        fermatTxTable.setStringFilter(CryptoVaultDatabaseConstants.FERMAT_TRANSACTIONS_TABLE_TRX_ID_COLUMN_NAME, txId.toString(), DatabaseFilterType.EQUAL);
        try {
            fermatTxTable.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            throw new CantExecuteQueryException();
        }
        /**
         * If I couldnt find any record with this transaction id, then it is a new transactions.
         */
        if (fermatTxTable.getRecords().isEmpty())
            return true;
        else
            return false;
    }


    /**
     * I will persist a new crypto transaction generated by our wallet.
     */
    public UUID persistNewTransaction(String txHash) throws CantExecuteQueryException {
        UUID txId = UUID.randomUUID();

        DatabaseTable cryptoTxTable;
        DatabaseTransaction dbTx = this.database.newTransaction();
        cryptoTxTable = database.getTable(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_NAME);
        DatabaseTableRecord incomingTxRecord =  cryptoTxTable.getEmptyRecord();
        incomingTxRecord.setStringValue(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_ID_COLUMN_NAME, txId.toString());
        incomingTxRecord.setStringValue(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_HASH_COLUMN_NAME, txHash);
        /**
         * since the wallet generated this transaction, we dont need to inform it.
         */
        incomingTxRecord.setStringValue(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_PROTOCOL_STS_COLUMN_NAME, ProtocolStatus.NO_ACTION_REQUIRED.toString());


        /**
         * Will get the transaction confidence of the transaction. Preety sure it should be Identified. But just in case...
         */
        TransactionConfidenceCalculator transactionConfidenceCalculator = new TransactionConfidenceCalculator(txId.toString(), database, vault);
        CryptoStatus cryptoStatus;
        try {
            cryptoStatus = transactionConfidenceCalculator.getCryptoStatus();
        } catch (CantCalculateTransactionConfidenceException e) {
            /**
             * If I can't calculate the confidence, I will default it to Identified.
             */
            cryptoStatus = CryptoStatus.IDENTIFIED;
        }

        incomingTxRecord.setStringValue(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRANSACTION_STS_COLUMN_NAME, CryptoStatus.IDENTIFIED.toString());

        dbTx.addRecordToInsert(cryptoTxTable, incomingTxRecord);
        try {
            database.executeTransaction(dbTx);
        } catch (DatabaseTransactionFailedException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExecuteQueryException();
        }

        return txId;
    }

    /**
     * Will retrieve all the transactions that are in status pending ProtocolStatus = TO_BE_NOTIFIED
     * @return
     */
    public HashMap<String, String> getPendingTransactionsHeaders() throws CantExecuteQueryException {
        /**
         * I need to obtain all the transactions ids with protocol status SENDING_NOTIFIED y TO_BE_NOTIFIED
         */
        DatabaseTable cryptoTxTable;
        HashMap<String, String> transactionsIds = new HashMap<String, String>();
        cryptoTxTable = database.getTable(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_NAME);

        /**
         * I get the transaction IDs and Hashes for the TO_BE_NOTIFIED
         */
        cryptoTxTable.setStringFilter(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_PROTOCOL_STS_COLUMN_NAME, ProtocolStatus.TO_BE_NOTIFIED.toString(), DatabaseFilterType.EQUAL);
        try {
            cryptoTxTable.loadToMemory();
            for (DatabaseTableRecord record : cryptoTxTable.getRecords()){
                transactionsIds.put(record.getStringValue(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_ID_COLUMN_NAME), record.getStringValue(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_HASH_COLUMN_NAME));
            }
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            throw new CantExecuteQueryException();
        }



        /**
         * I get the transaction IDs and Hashes for the TO_BE_NOTIFIED
         */
        /*
        cryptoTxTable.setStringFilter(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_PROTOCOL_STS_COLUMN_NAME, ProtocolStatus.SENDING_NOTIFIED.toString(), DatabaseFilterType.EQUAL);
        try {
            cryptoTxTable.loadToMemory();
            for (DatabaseTableRecord record : cryptoTxTable.getRecords()){
                transactionsIds.put(record.getStringValue(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_ID_COLUMN_NAME), record.getStringValue(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_HASH_COLUMN_NAME));
            }
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            throw new CantExecuteQueryException();
        }
        */

        return transactionsIds;
    }

    /**
     * will update the transaction to the new state
     * @param txHash
     * @param newState
     */
    public void updateCryptoTransactionStatus(String txHash, CryptoStatus newState) throws CantExecuteQueryException {
        DatabaseTable cryptoTxTable;
        cryptoTxTable = database.getTable(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_NAME);
        DatabaseTableRecord toUpdate;
        cryptoTxTable.setStringFilter(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_HASH_COLUMN_NAME, txHash, DatabaseFilterType.EQUAL);
        try {
            cryptoTxTable.loadToMemory();
            toUpdate = cryptoTxTable.getRecords().get(0); //todo add validation that only one record exists
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            throw new CantExecuteQueryException();
        }


        /**
         * I set the Protocol status to the new value
         */
        DatabaseTransaction dbTrx = this.database.newTransaction();
        toUpdate.setStringValue(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRANSACTION_STS_COLUMN_NAME, newState.toString());
        dbTrx.addRecordToUpdate(cryptoTxTable, toUpdate);
        try {
            database.executeTransaction(dbTrx);
        } catch (DatabaseTransactionFailedException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExecuteQueryException();
        }


    }

    /**
     * Will update the protocol status of the passed transaction.
     * @param txId
     * @param newStatus
     */
    public void updateTransactionProtocolStatus(UUID  txId, ProtocolStatus newStatus) throws CantExecuteQueryException {
        DatabaseTable cryptoTxTable;
        cryptoTxTable = database.getTable(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_NAME);
        cryptoTxTable.setStringFilter(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_ID_COLUMN_NAME, txId.toString(), DatabaseFilterType.EQUAL);
        try {
            cryptoTxTable.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            throw  new CantExecuteQueryException();
        }

        DatabaseTransaction dbTrx = this.database.newTransaction();
        DatabaseTableRecord toUpdate = cryptoTxTable.getRecords().get(0); //todo add validation that only one record exists

        /**
         * I set the Protocol status to the new value
         */
        toUpdate.setStringValue(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_PROTOCOL_STS_COLUMN_NAME, newStatus.toString());
        dbTrx.addRecordToUpdate(cryptoTxTable, toUpdate);
        try {
            database.executeTransaction(dbTrx);
        } catch (DatabaseTransactionFailedException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExecuteQueryException();
        }

    }

    /**
     * returns the current protocol status of this transaction
     * @param txId
     * @return
     */
    public ProtocolStatus getCurrentTransactionProtocolStatus(UUID txId) throws CantExecuteQueryException {
        DatabaseTable cryptoTxTable;
        cryptoTxTable = database.getTable(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_NAME);
        cryptoTxTable.setStringFilter(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_ID_COLUMN_NAME, txId.toString(), DatabaseFilterType.EQUAL);
        try {
            cryptoTxTable.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            throw new CantExecuteQueryException();
        }
        DatabaseTableRecord currentStatus = cryptoTxTable.getRecords().get(0); //todo add validation that only one record exists

        return ProtocolStatus.valueOf(currentStatus.getStringValue(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_PROTOCOL_STS_COLUMN_NAME));
    }

    /**
     * will return true if there are transactions in NO_BE_NOTIFIED status
     * @return
     */
    public boolean isPendingTransactions() throws CantExecuteQueryException {
        DatabaseTable cryptoTxTable;
        cryptoTxTable = database.getTable(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_NAME);
        cryptoTxTable.setStringFilter(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_PROTOCOL_STS_COLUMN_NAME,ProtocolStatus.TO_BE_NOTIFIED.toString() ,DatabaseFilterType.EQUAL);
        try {
            cryptoTxTable.loadToMemory();
            if (cryptoTxTable.getRecords().isEmpty())
                return false;
            else
                return true;
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            throw new CantExecuteQueryException();
        }
    }

    /**
     * I Will check in the vault all transactions that are not included in the database and insert them.
     * @param vault
     */
    public void persistMissingTransactionsFromWallet(Wallet vault) throws CantExecuteQueryException {
        /**
         * get all transactions from the vault
         */
        Set<org.bitcoinj.core.Transaction> transactions;
        transactions = vault.getTransactions(false);
        DatabaseTable txTable = database.getTable(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_NAME);

        for (org.bitcoinj.core.Transaction transaction : transactions){
                txTable.setStringFilter(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_HASH_COLUMN_NAME, transaction.getHashAsString(), DatabaseFilterType.EQUAL);
            try {
                txTable.loadToMemory();
            } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
                throw new CantExecuteQueryException();
            }
            if (txTable.getRecords().isEmpty()){
                    /**
                     * if this transaction hash is not in the database, I will insert it.
                     */
                    this.persistNewTransaction(transaction.getHashAsString());
                }
        }

    }

    /**
     * increase by one or resets to zero the counter of transactions found ready to be consumed
     * @param newOcurrence
     * @return the amount of iterations
     * @throws CantExecuteQueryException
     */
    public int updateTransactionProtocolStatus(boolean newOcurrence) throws CantExecuteQueryException {
        DatabaseTable transactionProtocolStatusTable;
        transactionProtocolStatusTable = database.getTable(CryptoVaultDatabaseConstants.TRANSITION_PROTOCOL_STATUS);

        try {
            transactionProtocolStatusTable.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            throw new CantExecuteQueryException();
        }
        List<DatabaseTableRecord> records = transactionProtocolStatusTable.getRecords();
        if (records.isEmpty()){
            /**
             * there are no records, I will insert the first one that will be always updated
             */
            long timestamp = System.currentTimeMillis() / 1000L;
            DatabaseTableRecord emptyRecord = transactionProtocolStatusTable.getEmptyRecord();
            emptyRecord.setLongValue(CryptoVaultDatabaseConstants.TRANSITION_PROTOCOL_STATUS_TABLE_TIMESTAMP_COLUMN_NAME, timestamp);
            emptyRecord.setIntegerValue(CryptoVaultDatabaseConstants.TRANSITION_PROTOCOL_STATUS_TABLE_ocurrences_COLUMN_NAME, 0);

            /**
             * returns 1
             */
            return 0;
        }

        DatabaseTableRecord record = records.get(0);
        DatabaseTransaction dbTx = database.newTransaction();

        if (newOcurrence){
            /**
             * I need to increase the ocurrences counter by one
             */
            int ocurrence = record.getIntegerValue(CryptoVaultDatabaseConstants.TRANSITION_PROTOCOL_STATUS_TABLE_ocurrences_COLUMN_NAME);
            ocurrence++;
            record.setIntegerValue(CryptoVaultDatabaseConstants.TRANSITION_PROTOCOL_STATUS_TABLE_ocurrences_COLUMN_NAME, ocurrence);
            dbTx.addRecordToUpdate(transactionProtocolStatusTable, record);

            try {
                database.executeTransaction(dbTx);
            } catch (DatabaseTransactionFailedException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantExecuteQueryException();
            }
            return ocurrence;

        }else {
            /**
             * I need to reset the counter to 0
             */
            record.setIntegerValue(CryptoVaultDatabaseConstants.TRANSITION_PROTOCOL_STATUS_TABLE_ocurrences_COLUMN_NAME, 0);
            dbTx.addRecordToUpdate(transactionProtocolStatusTable, record);
            try {
                database.executeTransaction(dbTx);
            } catch (DatabaseTransactionFailedException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantExecuteQueryException();
            }

            return 0;

        }
    }

}
