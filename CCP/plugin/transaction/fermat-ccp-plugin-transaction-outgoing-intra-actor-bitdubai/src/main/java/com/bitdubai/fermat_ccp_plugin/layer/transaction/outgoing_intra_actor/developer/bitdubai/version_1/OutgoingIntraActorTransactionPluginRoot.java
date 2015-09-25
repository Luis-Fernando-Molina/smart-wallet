package com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_intra_actor.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.interfaces.DealsWithCryptoTransmissionNetworkService;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.exceptions.CantGetOutgoingIntraActorTransactionManagerException;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.exceptions.OutgoingIntraActorCantGetCryptoStatusException;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.interfaces.IntraActorCryptoTransactionManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_intra_actor.developer.bitdubai.version_1.database.OutgoingIntraActorDao;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_intra_actor.developer.bitdubai.version_1.structure.OutgoingIntraActorTransactionManager;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_intra_actor.developer.bitdubai.version_1.structure.OutgoingIntraActorTransactionProcessorAgent;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_intra_actor.developer.bitdubai.version_1.util.OutgoingIntraActorTransactionHandlerFactory;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.DealsWithCryptoVault;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.CantInitializeOutgoingIntraActorDaoException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.UUID;

/**
 * Created by loui on 20/02/15.
 */
public class OutgoingIntraActorTransactionPluginRoot implements DealsWithBitcoinWallet, DealsWithCryptoTransmissionNetworkService, DealsWithCryptoVault, DealsWithErrors, DealsWithEvents, DealsWithPluginDatabaseSystem, OutgoingIntraActorManager, Plugin, Service{

    /*
     * DealsWithBitcoinWallet Interface member variables.
     */
    private BitcoinWalletManager bitcoinWalletManager;

    /*
     * DealsWithCryptoTransmissionNetworkService Interface member variables.
     */
    private CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;

    /*
     * DealsWithCryptoVault Interface member variables.
     */
    private CryptoVaultManager cryptoVaultManager;

    /*
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /*
     * DealsWithEvents Interface member variables
     */
    private EventManager eventManager;
    /*
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
     * OutgoingIntraActorManager Interface member variables.
     */

    /*
     * Plugin Interface member variables.
     */
    private UUID pluginId;


    /*
     * Service Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;


    /*
     * OutgoingIntraUserTransaction member variables
     */
    private OutgoingIntraActorDao outgoingIntraActorDao;
    private OutgoingIntraActorTransactionProcessorAgent transactionProcessorAgent;
    private OutgoingIntraActorTransactionHandlerFactory transactionHandlerFactory;


    /*
     * DealsWithBitcoinWallet Interface implementation
     */
    @Override
    public void setBitcoinWalletManager(BitcoinWalletManager bitcoinWalletManager) {
        this.bitcoinWalletManager = bitcoinWalletManager;
    }

    /*
     * DealsWithCryptoTransmissionNetworkService Interface implementation
     */
    @Override
    public void setCryptoTransmissionNetworkService(CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager) {
        this.cryptoTransmissionNetworkServiceManager = cryptoTransmissionNetworkServiceManager;
    }

    /*
     * DealsWithCryptoVault Interface implementation
     */
    @Override
    public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager) {
        this.cryptoVaultManager = cryptoVaultManager;
    }

    /*
     * DealsWithErrors Interface implementation
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /*
     * DealsWithEvents Interface implementation
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /*
     * DealsWithPluginDatabaseSystem Interface implementation
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystemManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystemManager;
    }

    /*
     * OutgoingIntraActorManager Interface implementation
     */
    @Override
    public IntraActorCryptoTransactionManager getTransactionManager() throws CantGetOutgoingIntraActorTransactionManagerException {
        return new OutgoingIntraActorTransactionManager(this.pluginId,this.errorManager,this.bitcoinWalletManager,this.pluginDatabaseSystem);
    }

    @Override
    public CryptoStatus getTransactionStatus(String transactionHash) throws OutgoingIntraActorCantGetCryptoStatusException {
        return this.outgoingIntraActorDao.getCryptoStatus(transactionHash);
    }

    /**
     * Plugin Interface implementation.
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /*
     * Service Interface implementation
     */
    @Override
    public void start() {
        try {
            this.outgoingIntraActorDao = new OutgoingIntraActorDao(this.errorManager, this.pluginDatabaseSystem);
            this.outgoingIntraActorDao.initialize(this.pluginId);
            this.transactionHandlerFactory = new OutgoingIntraActorTransactionHandlerFactory(this.eventManager,this.bitcoinWalletManager, this.outgoingIntraActorDao);
            this.transactionProcessorAgent = new OutgoingIntraActorTransactionProcessorAgent(this.errorManager,
                                                                                            this.cryptoVaultManager,
                                                                                            this.bitcoinWalletManager,
                                                                                            this.outgoingIntraActorDao,
                                                                                            this.transactionHandlerFactory,
                                                                                            this.cryptoTransmissionNetworkServiceManager);
            this.transactionProcessorAgent.start();
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (CantInitializeOutgoingIntraActorDaoException e) {
            reportUnexpectedException(e);
        } catch (Exception e) {
            reportUnexpectedException(FermatException.wrapException(e));
        }
    }

    private void reportUnexpectedException(Exception e) {
        this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_OUTGOING_INTRA_ACTOR_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.transactionProcessorAgent.stop();

        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }
}
