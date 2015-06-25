package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.BitcoinTransaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterDebitDebitException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWallet;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.TransactionManager;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.exceptions.CantSendFundsException;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.exceptions.InconsistentFundsException;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.exceptions.InsufficientFundsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.DealsWithCryptoVault;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InsufficientMoneyException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.CantInitializeDaoException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;

import java.util.UUID;

/**
 * Created by eze on 2015.06.23..
 */
public class OutgoingExtraUserTransactionManager implements DealsWithBitcoinWallet, DealsWithCryptoVault, DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity, TransactionManager {

    /*
     * DealsWithBitcoinWallet Interface member Variables
     */
    private BitcoinWalletManager bitcoinWalletManager;

    /*
     * DealsWithCryptoVault Interface member Variables
     */
    private CryptoVaultManager cryptoVaultManager;

    /*
     * DealsWithErrors Interface member Variables
     */
    private ErrorManager errorManager;

    /*
     * DealsWithPluginDatabaseSystem Interface member Variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /*
     * DealsWithPluginIdentity Interface methods implementation
     */
     private  UUID pluginId;

    /*
     * OutgoingExtraUserTransactionManager member variables
     */
    private OutgoingExtraUserDao dao;

    /*
     * OutgoingExtraUserTransactionManager Constructor
     */
    public OutgoingExtraUserTransactionManager() {

    }

    public void initialize() throws CantInitializeDaoException{
        this.dao = new OutgoingExtraUserDao();
        this.dao.setErrorManager(this.errorManager);
        this.dao.setPluginDatabaseSystem(this.pluginDatabaseSystem);
        this.dao.initialize(this.pluginId);
    }

    /*
     * DealsWithBitcoinWallet Interface methods implementation
     */
    @Override
    public void setBitcoinWalletManager(BitcoinWalletManager bitcoinWalletManager) {
        this.bitcoinWalletManager = bitcoinWalletManager;
    }

    /*
     * DealsWithCryptoVault Interface methods implementation
     */
    @Override
    public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager) {
        this.cryptoVaultManager = cryptoVaultManager;
    }

    /*
     * DealsWithErrors Interface methods implementation
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /*
    * DealsWithPluginDatabaseSystem Interface methods implementation
    */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*
    * DealsWithPluginIdentity Interface methods implementation
    */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /*
     * TransactionManager Interface methods implementation
     */

    @Override
    public void send(UUID walletID, CryptoAddress destinationAddress, long cryptoAmount) throws InsufficientFundsException , CantSendFundsException , InconsistentFundsException {
        BitcoinWallet bitcoinWallet = null;

        try {
            bitcoinWallet = this.bitcoinWalletManager.loadWallet(UUID.randomUUID());
        } catch (CantLoadWalletException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw  new CantSendFundsException();
        }

        /*
         * We first check our funds
         */
        long funds = 0;
        try {
            funds = bitcoinWallet.getBalance();
            if(funds < cryptoAmount)
                throw  new InsufficientFundsException();
        } catch (CantCalculateBalanceException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw  new CantSendFundsException();
        }

        BitcoinTransaction bitcoinTransaction = new BitcoinTransaction();
        bitcoinTransaction.setAmount(cryptoAmount);

        try {
            this.cryptoVaultManager.sendBitcoins(walletID, UUID.randomUUID(), destinationAddress, cryptoAmount);
        } catch (InsufficientMoneyException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new InconsistentFundsException();
        } catch (InvalidSendToAddressException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw  new CantSendFundsException();
        }

        try {
            bitcoinWallet.debit(bitcoinTransaction);
        } catch (CantRegisterDebitDebitException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw  new CantSendFundsException();
        }
    }
}
