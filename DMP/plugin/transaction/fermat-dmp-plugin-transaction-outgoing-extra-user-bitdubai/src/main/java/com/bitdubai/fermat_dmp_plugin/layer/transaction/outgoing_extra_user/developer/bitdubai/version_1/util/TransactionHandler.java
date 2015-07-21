package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.InconsistentTableStateException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.UnexpectedCryptoStatusException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDao;

/**
 * Created by eze on 2015.07.08..
 */
public class TransactionHandler {

    public static void handleTransaction(TransactionWrapper transaction, CryptoStatus cryptoStatus,
                                         BitcoinWalletWallet bitcoinWallet,OutgoingExtraUserDao dao,
                                         ErrorManager errorManager
                                        ){
        CryptoStatus oldStatus = transaction.getCryptoStatus();

        if(oldStatus.equals(CryptoStatus.PENDING_SUBMIT)) {
            switch (cryptoStatus) {
                case PENDING_SUBMIT:
                    return;
                case ON_CRYPTO_NETWORK:
                    try {
                        dao.setToCryptoStatus(transaction, CryptoStatus.ON_CRYPTO_NETWORK);
                        return;
                    } catch (CantUpdateRecordException | CantLoadTableToMemoryException | InconsistentTableStateException e) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                        return;
                    }
                case REVERSED_ON_CRYPTO_NETWORK:
                    try {
                        transaction.setMemo("Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed");
                        bitcoinWallet.getAvailableBalance().credit(transaction);
                        dao.cancelTransaction(transaction);
                        return;
                    } catch (InconsistentTableStateException | CantUpdateRecordException | CantLoadTableToMemoryException | CantRegisterCreditException e) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                        return;
                    }
                case ON_BLOCKCHAIN:
                    try {
                        bitcoinWallet.getBookBalance().debit(transaction);
                        dao.setToCryptoStatus(transaction, CryptoStatus.ON_BLOCKCHAIN);
                        return;
                    } catch (CantRegisterDebitException | CantUpdateRecordException | CantLoadTableToMemoryException | InconsistentTableStateException e) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                        return;
                    }
                case REVERSED_ON_BLOCKCHAIN:
                    // En este punto en teoría no se aplicó el credit en el book balance
                    // por lo que no lo revertimos
                    // TODO: Analizar si eso puede fallar
                    try {
                        transaction.setMemo("Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed");
                        bitcoinWallet.getAvailableBalance().credit(transaction);
                        dao.cancelTransaction(transaction);
                        return;
                    } catch (InconsistentTableStateException | CantUpdateRecordException | CantLoadTableToMemoryException | CantRegisterCreditException e) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                        return;
                    }
                case IRREVERSIBLE:
                    try {
                        dao.setToPIW(transaction);
                        return;
                    } catch (CantUpdateRecordException | CantLoadTableToMemoryException | InconsistentTableStateException e) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                        return;
                    }
                default:
                    FermatException e = new UnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + oldStatus.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + cryptoStatus.getCode(),"");
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    return;
            }
        }

        if(oldStatus.equals(CryptoStatus.ON_CRYPTO_NETWORK)) {
            switch (cryptoStatus) {
                case PENDING_SUBMIT:
                    FermatException e = new UnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + oldStatus.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + cryptoStatus.getCode(),"");
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
                    return;
                case ON_CRYPTO_NETWORK:
                    return;
                case REVERSED_ON_CRYPTO_NETWORK:
                    try {
                        transaction.setMemo("Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed");
                        bitcoinWallet.getAvailableBalance().credit(transaction);
                        dao.cancelTransaction(transaction);
                        return;
                    } catch (InconsistentTableStateException | CantUpdateRecordException | CantLoadTableToMemoryException | CantRegisterCreditException e1) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e1);
                        return;
                    }
                case ON_BLOCKCHAIN:
                    try {
                        bitcoinWallet.getBookBalance().debit(transaction);
                        dao.setToCryptoStatus(transaction, CryptoStatus.ON_BLOCKCHAIN);
                        return;
                    } catch (CantRegisterDebitException | CantUpdateRecordException | CantLoadTableToMemoryException | InconsistentTableStateException e1) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e1);
                        return;
                    }
                case REVERSED_ON_BLOCKCHAIN:
                    // En este punto en teoría no se aplicó el credit en el book balance
                    // por lo que no lo revertimos
                    // TODO: Analizar si eso puede fallar
                    try {
                        transaction.setMemo("Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed");
                        bitcoinWallet.getAvailableBalance().credit(transaction);
                        dao.cancelTransaction(transaction);
                        return;
                    } catch (InconsistentTableStateException | CantUpdateRecordException | CantLoadTableToMemoryException | CantRegisterCreditException e1) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e1);
                        return;
                    }
                case IRREVERSIBLE:
                    try {
                        dao.setToPIW(transaction);
                        return;
                    } catch (CantUpdateRecordException | InconsistentTableStateException | CantLoadTableToMemoryException e1) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e1);
                        return;
                    }
                default:
                    FermatException e2 = new UnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + oldStatus.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + cryptoStatus.getCode(),"");
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e2);
                    return;
            }
        }

        if(oldStatus.equals(CryptoStatus.ON_BLOCKCHAIN)) {
            switch (cryptoStatus) {
                case PENDING_SUBMIT:
                    FermatException e3 = new UnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + oldStatus.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + cryptoStatus.getCode(),"");
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e3);                case ON_CRYPTO_NETWORK:
                    return;
                case REVERSED_ON_CRYPTO_NETWORK:
                    FermatException e4 = new UnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + oldStatus.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + cryptoStatus.getCode(),"");
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e4);
                    return;
                case ON_BLOCKCHAIN:
                    return;
                case REVERSED_ON_BLOCKCHAIN:
                    try {
                        transaction.setMemo("Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed");
                        bitcoinWallet.getBookBalance().credit(transaction);
                        bitcoinWallet.getAvailableBalance().credit(transaction);
                        dao.cancelTransaction(transaction);
                        return;
                    } catch (InconsistentTableStateException | CantUpdateRecordException | CantLoadTableToMemoryException | CantRegisterCreditException e1) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e1);
                        return;
                    }
                case IRREVERSIBLE:
                    try {
                        dao.setToPIW(transaction);
                        return;
                    } catch (CantUpdateRecordException | InconsistentTableStateException | CantLoadTableToMemoryException e1) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e1);
                        return;
                    }
                default:
                    FermatException e2 = new UnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + oldStatus.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + cryptoStatus.getCode(),"");
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e2);
                    return;
            }
        }


    }
}
