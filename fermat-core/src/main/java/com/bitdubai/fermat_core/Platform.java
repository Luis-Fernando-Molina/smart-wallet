/*
 * @#Platform.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_core;


import com.bitdubai.fermat_api.*;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;


import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealWithDatabaseManagers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealsWithLogManagers;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformLayers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.event.DealWithEventMonitor;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.ActorIntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.DealsWithIntraUsers;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.exceptions.CantCreateNewIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.exceptions.CantGetUserIntraUserIdentitiesException;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.interfaces.IntraUserIdentityManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.DealsWithWalletContacts;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.DealsWithWalletFactory;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletDescriptorFactoryProjectManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.DealsWithWalletManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.WalletManagerManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.DealsWithWalletPublisher;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.DealsWithWalletPublisherMiddleware;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.WalletPublisherManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.WalletPublisherMiddlewareManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces.DealsWithWalletStoreMiddleware;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.DealsWithIntraUsersModule;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.DealsWithWalletStoreModule;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.DealsWithWalletResources;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResourcesInstalationManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_statistics.interfaces.DealsWithWalletStatisticsNetworkService;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_statistics.interfaces.WalletStatisticsManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.DealsWithWalletStoreNetworkService;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.DealsWithNicheWalletTypeCryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.DealsWithOutgoingExtraUser;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.OutgoingExtraUserManager;
import com.bitdubai.fermat_api.layer.osa_android.DataBaseSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.FileSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.LocationSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.LoggerSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_core.layer.dmp_request.RequestServiceLayer;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationLayerManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.DealsWithCommunicationLayerManager;
import com.bitdubai.fermat_pip_api.layer.pip_actor.developer.DealsWithToolManager;
import com.bitdubai.fermat_pip_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.fermat_pip_api.layer.pip_identity.designer.DealsWithDesigner;
import com.bitdubai.fermat_pip_api.layer.pip_identity.designer.interfaces.DesignerManager;
import com.bitdubai.fermat_pip_api.layer.pip_identity.developer.interfaces.DealsWithDeveloperIdentity;
import com.bitdubai.fermat_pip_api.layer.pip_identity.developer.interfaces.DeveloperIdentityManager;
import com.bitdubai.fermat_pip_api.layer.pip_identity.translator.DealsWithTranslator;
import com.bitdubai.fermat_pip_api.layer.pip_identity.translator.interfaces.TranslatorManager;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.DealsWithDeveloperModule;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.DeveloperModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.DealsWithExtraUsers;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.ExtraUserManager;
import com.bitdubai.fermat_core.layer.cry_crypto_router.CryptoRouterLayer;
import com.bitdubai.fermat_core.layer.dmp_basic_wallet.BasicWalletLayer;
import com.bitdubai.fermat_core.layer.dmp_transaction.TransactionLayer;
import com.bitdubai.fermat_core.layer.dmp_niche_wallet_type.NicheWalletTypeLayer;
import com.bitdubai.fermat_core.layer.pip_actor.ActorLayer;
import com.bitdubai.fermat_core.layer.pip_identity.IdentityLayer;
import com.bitdubai.fermat_core.layer.pip_platform_service.PlatformServiceLayer;

import com.bitdubai.fermat_core.layer.all_definition.DefinitionLayer;
import com.bitdubai.fermat_core.layer.pip_hardware.HardwareLayer;
import com.bitdubai.fermat_core.layer.pip_user.UserLayer;
import com.bitdubai.fermat_core.layer.pip_license.LicenseLayer;
import com.bitdubai.fermat_core.layer.dmp_world.WorldLayer;
import com.bitdubai.fermat_core.layer.cry_crypto_network.CryptoNetworkLayer;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.DealsWithActorAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.DealsWithWalletAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.CryptoNetworks;
import com.bitdubai.fermat_core.layer.cry_cypto_vault.CryptoVaultLayer;
import com.bitdubai.fermat_core.layer.cry_crypto_module.CryptoLayer;
import com.bitdubai.fermat_core.layer.p2p_communication.CommunicationLayer;
import com.bitdubai.fermat_core.layer.dmp_network_service.NetworkServiceLayer;
import com.bitdubai.fermat_core.layer.dmp_middleware.MiddlewareLayer;
import com.bitdubai.fermat_core.layer.dmp_module.ModuleLayer;
import com.bitdubai.fermat_core.layer.dmp_agent.AgentLayer;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.DealsWithBitcoinCryptoNetwork;
import com.bitdubai.fermat_cry_api.layer.crypto_router.incoming_crypto.DealsWithIncomingCrypto;
import com.bitdubai.fermat_cry_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.DealsWithCryptoVault;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.DealsWithPlatformInfo;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.PlatformInfoManager;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DealsWithDeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Class <code>com.bitdubai.fermat_core.CorePlatformContext</code> start all
 * component of the platform and manage it
 * <p/>
 *
 * Created by ciencias on 20/01/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 24/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class Platform  {

    /**
     * Represent the Logger
     */
    private static Logger LOG = Logger.getGlobal();

    /**
     * Represent the dealsWithDatabaseManagersPlugins
     */
    private Map<Plugins, Plugin> dealsWithDatabaseManagersPlugins;

    /**
     * Represent the dealsWithLogManagersPlugins
     */
    private Map<Plugins, Plugin> dealsWithLogManagersPlugins;

    /**
     * Represent the dealsWithDatabaseManagersAddons
     */
    private Map<Addons, Addon> dealsWithDatabaseManagersAddons;

    /**
     * Represent the dealsWithLogManagersAddons
     */
    private Map<Addons, Addon> dealsWithLogManagersAddons;

    /**
     * Represent the eventMonitor
     */
    private PlatformEventMonitor eventMonitor;

    /**
     * Represent the pluginsIdentityManager
     */
    private PluginsIdentityManager pluginsIdentityManager;

    /**
     * Represent the corePlatformContext
     */
    private CorePlatformContext corePlatformContext;

    /**
     * Represent the osContext
     */
    private Object osContext;

    /**
     * Represent the fileSystemOs
     */
    private FileSystemOs fileSystemOs;

    /**
     * Represent the databaseSystemOs
     */
    private DataBaseSystemOs databaseSystemOs;

    /**
     * Represent the locationSystemOs
     */
    private LocationSystemOs locationSystemOs;

    /**
     * Represent the loggerSystemOs
     */
    private LoggerSystemOs loggerSystemOs;

    /**
     * Constructor
     */
    public Platform () {

        /**
         * The event monitor is intended to handle exceptions on listeners, in order to take appropiate action.
         */
        eventMonitor = new PlatformEventMonitor();
        corePlatformContext = new CorePlatformContext();

        dealsWithDatabaseManagersPlugins = new ConcurrentHashMap<>();
        dealsWithLogManagersPlugins = new ConcurrentHashMap<>();
        dealsWithDatabaseManagersAddons = new ConcurrentHashMap<>();
        dealsWithLogManagersAddons = new ConcurrentHashMap<>();
    }

    /**
     * Somebody is starting the com.bitdubai.platform. The com.bitdubai.platform is portable. That somebody is OS dependent and has access to
     * the OS. I have to transport a reference to that somebody to the OS subsystem in other to allow it to access
     * the OS through this reference.
     */
    public void setOsContext(Object osContext) {
        this.osContext = osContext;
    }

    /**
     * An unresolved bug in either Android or Gradle does not allow us to create the os object on a library outside the
     * main module. While this situation persists, we will create it inside the wallet package and receive it throw this
     * method.
     */
    public void setFileSystemOs (FileSystemOs fileSystemOs) {
        this.fileSystemOs = fileSystemOs;
    }

    /**
     * Set the DataBaseSystemOs
     *
     * @param databaseSystemOs
     */
    public void setDataBaseSystemOs (DataBaseSystemOs databaseSystemOs) {
        this.databaseSystemOs = databaseSystemOs;
    }

    /**
     * Set the LocationSystemOs
     *
     * @param locationSystemOs
     */
    public void setLocationSystemOs(LocationSystemOs locationSystemOs) {
        this.locationSystemOs  = locationSystemOs;
    }

    /**
     * Set the LoggerSystemOs
     *
     * @param loggerSystemOs
     */
    public void setLoggerSystemOs(LoggerSystemOs loggerSystemOs) {
        this.loggerSystemOs  = loggerSystemOs;
    }


    /**
     * Method that start the platform
     *
     * @throws CantStartPlatformException
     * @throws CantReportCriticalStartingProblemException
     */
    public void start() throws CantStartPlatformException, CantReportCriticalStartingProblemException {

        LOG.info("Platform - start()");

        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         * ------------------------------------------------------------------------------------------------------------*
         * Layers initialization                                                                                       *
         * ------------------------------------------------------------------------------------------------------------*
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        initializePlatformLayers();


        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         * ------------------------------------------------------------------------------------------------------------*
         * Addons initialization                                                                                       *
         * ------------------------------------------------------------------------------------------------------------*
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        initializeAddons();

        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         * ------------------------------------------------------------------------------------------------------------*
         * Plugin initialization                                                                                       *
         * ------------------------------------------------------------------------------------------------------------*
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        initializePlugins();

        /*
         * Check addon for developer interfaces
         */
        for(Addons registeredDescriptor : corePlatformContext.getRegisteredAddonskeys()) {
            checkAddonForDeveloperInterfaces(registeredDescriptor);
        }

        /*
         * Check plugin for developer interfaces
         */
        for(Plugins registeredDescriptor : corePlatformContext.getRegisteredPluginskeys()) {
            checkPluginForDeveloperInterfaces(registeredDescriptor);
        }


        /**
         * Set Actor developer like manager
         */
        //((DealWithDatabaseManagers) corePlatformContext.getPlugin(Plugins.BITDUBAI_ACTOR_DEVELOPER)).setDatabaseManagers(dealsWithDatabaseManagersPlugins, dealsWithDatabaseManagersAddons);
        //((DealsWithLogManagers)     corePlatformContext.getPlugin(Plugins.BITDUBAI_ACTOR_DEVELOPER)).setLogManagers(dealsWithLogManagersPlugins, dealsWithLogManagersAddons);

        ((DealWithDatabaseManagers) corePlatformContext.getPlugin(Plugins.BITDUBAI_DEVELOPER_MODULE)).setDatabaseManagers(dealsWithDatabaseManagersPlugins, dealsWithDatabaseManagersAddons);
        ((DealsWithLogManagers)     corePlatformContext.getPlugin(Plugins.BITDUBAI_DEVELOPER_MODULE)).setLogManagers(dealsWithLogManagersPlugins, dealsWithLogManagersAddons);

    }

    /**
     * Method tha initialize all platform layer component
     */
    private void initializePlatformLayers() throws CantReportCriticalStartingProblemException {

        LOG.info("Platform - initializing Platform Layers ...");

        /**
         * Here I will be starting all the platforms layers. It is required that none of them fails. That does not mean
         * that a layer will have at least one service to offer. It depends on each layer. If one believes its lack of
         * services prevent the whole platform to start, then it will throw an exception that will effectively prevent
         * the platform to start.
         */
        try {


            /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
             * ------------------------------------------------------------------------------------------------------------*
             * Critical Layer initialization                                                                               *
             * ------------------------------------------------------------------------------------------------------------*
             * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

            /*
             * Create and star Definition Layer
             */
            PlatformLayer mDefinitionLayer = new DefinitionLayer();
            mDefinitionLayer.start();

            /*
             * Create and star Platform Service Layer
             */
            PlatformLayer mPlatformServiceLayer = new PlatformServiceLayer();
            mPlatformServiceLayer.start();


            /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
             * ------------------------------------------------------------------------------------------------------------*
             * Other Layer initialization                                                                                  *
             * ------------------------------------------------------------------------------------------------------------*
             * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

            /*
             * Created and register into the platform context
             */

            //corePlatformContext.registerPlatformLayer(new OsLayer(),              PlatformLayers.BITDUBAI_OS_LAYER);  Due to an Android bug is not possible to handle this here.
            corePlatformContext.registerPlatformLayer(new HardwareLayer(), PlatformLayers.BITDUBAI_HARDWARE_LAYER);
            corePlatformContext.registerPlatformLayer(new UserLayer(), PlatformLayers.BITDUBAI_USER_LAYER);
            corePlatformContext.registerPlatformLayer(new LicenseLayer(),         PlatformLayers.BITDUBAI_LICENSE_LAYER);
            corePlatformContext.registerPlatformLayer(new WorldLayer(), PlatformLayers.BITDUBAI_WORLD_LAYER);
            corePlatformContext.registerPlatformLayer(new CryptoNetworkLayer(), PlatformLayers.BITDUBAI_CRYPTO_NETWORK_LAYER);
            corePlatformContext.registerPlatformLayer(new CryptoVaultLayer(), PlatformLayers.BITDUBAI_CRYPTO_VAULT_LAYER);
            corePlatformContext.registerPlatformLayer(new CryptoLayer(),          PlatformLayers.BITDUBAI_CRYPTO_LAYER);
            corePlatformContext.registerPlatformLayer(new CryptoRouterLayer(),    PlatformLayers.BITDUBAI_CRYPTO_ROUTER_LAYER);
            corePlatformContext.registerPlatformLayer(new CommunicationLayer(), PlatformLayers.BITDUBAI_COMMUNICATION_LAYER);
            corePlatformContext.registerPlatformLayer(new NetworkServiceLayer(), PlatformLayers.BITDUBAI_NETWORK_SERVICE_LAYER);
            corePlatformContext.registerPlatformLayer(new TransactionLayer(), PlatformLayers.BITDUBAI_TRANSACTION_LAYER);
            corePlatformContext.registerPlatformLayer(new MiddlewareLayer(),      PlatformLayers.BITDUBAI_MIDDLEWARE_LAYER);
            corePlatformContext.registerPlatformLayer(new ModuleLayer(), PlatformLayers.BITDUBAI_MODULE_LAYER);
            corePlatformContext.registerPlatformLayer(new AgentLayer(), PlatformLayers.BITDUBAI_AGENT_LAYER);
            corePlatformContext.registerPlatformLayer(new BasicWalletLayer(), PlatformLayers.BITDUBAI_BASIC_WALLET_LAYER);
            corePlatformContext.registerPlatformLayer(new NicheWalletTypeLayer(), PlatformLayers.BITDUBAI_NICHE_WALLET_TYPE_LAYER);
            corePlatformContext.registerPlatformLayer(new ActorLayer(), PlatformLayers.BITDUBAI_PIP_ACTOR_LAYER);
            corePlatformContext.registerPlatformLayer(new IdentityLayer(), PlatformLayers.BITDUBAI_PIP_IDENTITY_LAYER);
            corePlatformContext.registerPlatformLayer(new com.bitdubai.fermat_core.layer.pip_module.ModuleLayer(), PlatformLayers.BITDUBAI_PIP_MODULE_LAYER);
            corePlatformContext.registerPlatformLayer(new com.bitdubai.fermat_core.layer.pip_network_service.NetworkServiceLayer(), PlatformLayers.BITDUBAI_PIP_NETWORK_SERVICE_LAYER);
            corePlatformContext.registerPlatformLayer(new RequestServiceLayer(), PlatformLayers.BITDUBAI_REQUEST_LAYER);
            corePlatformContext.registerPlatformLayer(new com.bitdubai.fermat_core.layer.dmp_actor.ActorLayer(), PlatformLayers.BITDUBAI_ACTOR_LAYER);
            corePlatformContext.registerPlatformLayer(new com.bitdubai.fermat_core.layer.dmp_identity.IdentityLayer(), PlatformLayers.BITDUBAI_IDENTITY_LAYER);


            /*
             * Start all other platform layers
             */
            for (PlatformLayers key : corePlatformContext.getRegisteredPlatformLayerskeys()){

                /*
                 * Get the platformLayer  by key
                 */
                PlatformLayer platformLayer = corePlatformContext.getPlatformLayer(key);

                /*
                 * If not null
                 */
                if (platformLayer != null){

                    /*
                     * Start the layer
                     */
                    platformLayer.start();
                }
            }

            /*
             * Register the critical layer after the other layer are started
             * to prevent start again the critical layer in the previous code
             */
            corePlatformContext.registerPlatformLayer(mDefinitionLayer, PlatformLayers.BITDUBAI_DEFINITION_LAYER);
            corePlatformContext.registerPlatformLayer(mPlatformServiceLayer, PlatformLayers.BITDUBAI_PLATFORM_SERVICE_LAYER);

        } catch (CantStartLayerException cantStartLayerException) {

            LOG.log(Level.SEVERE, cantStartLayerException.getLocalizedMessage());

            /**
             * At this point the platform not only can not be started but also the problem can not be reported. That is
             * the reason why I am going to throw a special exception in order to alert the situation to whoever is running
             * the GUI. In this way it can alert the end user of what is going on and provide them with some information.
             * * * *
             */
            throw new CantReportCriticalStartingProblemException();
        }

    }

    /**
     * Method tha initialize all addons component
     */
    private void initializeAddons() throws CantStartPlatformException {

        LOG.info("Platform - initializing Addons ...");

        try {

            /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
             * ------------------------------------------------------------------------------------------------------------*
             * Critical Addons initialization                                                                              *
             * ------------------------------------------------------------------------------------------------------------*
             * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

            /*
             * Addon Error Manager
             * -------------------
             */
            Service errorManager = (Service) ((PlatformServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PLATFORM_SERVICE_LAYER)).getErrorManager();
            ((DealsWithPlatformDatabaseSystem) errorManager).setPlatformDatabaseSystem(databaseSystemOs.getPlatformDatabaseSystem());
            corePlatformContext.registerAddon((Addon) errorManager, Addons.ERROR_MANAGER);
            errorManager.start();

            /*
             * Addon Event Manager
             * -------------------
             */
            Service eventManager = (Service) ((PlatformServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PLATFORM_SERVICE_LAYER)).getEventManager();
            ((DealWithEventMonitor) eventManager).setEventMonitor(eventMonitor);
            corePlatformContext.registerAddon((Addon) eventManager, Addons.EVENT_MANAGER);
            eventManager.start();


            /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
             * ------------------------------------------------------------------------------------------------------------*
             * Other addons initialization                                                                                 *
             * ------------------------------------------------------------------------------------------------------------*
             * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

            /*
             * Addon Remote Device Manager
             * ---------------------------
             *
             * Give the Remote Device Manager access to the File System so it can load and save user information from
             * persistent media.
             */
            Service remoteDevice = (Service) ((HardwareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_HARDWARE_LAYER)).getRemoteDeviceManager();
            ((DealsWithPlatformFileSystem) remoteDevice).setPlatformFileSystem(fileSystemOs.getPlatformFileSystem());
            ((DealsWithEvents) remoteDevice).setEventManager((EventManager) eventManager);
            corePlatformContext.registerAddon((Addon) remoteDevice, Addons.REMOTE_DEVICE);


            /*
             * Addon Local Device Manager
             * -----------------------------
             *
             * Give the Local Device Manager access to the File System so it can load and save user information from
             * persistent media.
             */
            Service localDevice = (Service) ((HardwareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_HARDWARE_LAYER)).getLocalDeviceManager();
            ((DealsWithPlatformFileSystem) localDevice).setPlatformFileSystem(fileSystemOs.getPlatformFileSystem());
            ((DealsWithEvents) localDevice).setEventManager((EventManager) eventManager);
            corePlatformContext.registerAddon((Addon) localDevice, Addons.LOCAL_DEVICE);


            /*
             * Addon User Manager
             * -----------------------------
             *
             * Give the User Manager access to the File System so it can load and save user information from
             * persistent media.
             */
            Service deviceUser = (Service) ((UserLayer)  corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_USER_LAYER)).getDeviceUser();
            ((DealsWithPlatformFileSystem) deviceUser).setPlatformFileSystem(fileSystemOs.getPlatformFileSystem());
            ((DealsWithEvents) deviceUser).setEventManager((EventManager) eventManager);
            corePlatformContext.registerAddon((Addon) deviceUser, Addons.DEVICE_USER);




             /*
             * Addon PlatformInfo
             * -----------------------------
             *
             * Give the PlatformInfo Manager access to the File System so it can load and save user information from
             * persistent media.
             */
            Service platformInfo = (Service) ((PlatformServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PLATFORM_SERVICE_LAYER)).getPlatformInfo();
            ((DealsWithPlatformFileSystem) platformInfo).setPlatformFileSystem(fileSystemOs.getPlatformFileSystem());

            corePlatformContext.registerAddon((Addon) platformInfo, Addons.PLATFORM_INFO);
            platformInfo.start();

        } catch (CantStartPluginException cantStartPluginException) {

            LOG.log(Level.SEVERE, cantStartPluginException.getLocalizedMessage());
            throw new CantStartPlatformException();
        }

    }

    /**
     * Method tha initialize all addons component
     */
    private void initializePlugins() throws CantStartPlatformException {

        LOG.info("Platform - initializing Plugins ...");

        try {

            /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
             * ------------------------------------------------------------------------------------------------------------*
             * Critical Plugins initialization                                                                             *
             * ------------------------------------------------------------------------------------------------------------*
             * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

            /*
             * PluginsIdentityManager
             * -----------------------
             *
             * Initialize the Plugin Identity Manager, the one who assigns identities to each plug in.
             */
            pluginsIdentityManager = new PluginsIdentityManager(fileSystemOs.getPlatformFileSystem());

            /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
             * ------------------------------------------------------------------------------------------------------------*
             * Other Plugins initialization                                                                                *
             * ------------------------------------------------------------------------------------------------------------*
             * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

            /*
             * Plugin Cloud Server Communication
             * -----------------------------
             */
            Plugin cloudServerCommunication = ((CommunicationLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_COMMUNICATION_LAYER)).getCloudServerPlugin();
            injectPluginReferencesAndStart(cloudServerCommunication, Plugins.BITDUBAI_CLOUD_SERVER_COMMUNICATION);

            /*
             * Plugin Cloud Client Communication
             * -----------------------------
             */
            Plugin cloudCommunication = ((CommunicationLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_COMMUNICATION_LAYER)).getCloudPlugin();
            injectPluginReferencesAndStart(cloudCommunication, Plugins.BITDUBAI_CLOUD_CHANNEL);

            /*
             * Plugin Blockchain Info World
             * -----------------------------
             */
            // Plugin blockchainInfoWorld = ((WorldLayer)  mWorldLayer).getBlockchainInfo();
            //injectLayerReferences(blockchainInfoWorld);
            // injectPluginReferencesAndStart(blockchainInfoWorld, Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD);

            /*
             * Plugin Shape Shift World
             * -----------------------------
             */
            // Plugin shapeShiftWorld = ((WorldLayer)  mWorldLayer).getShapeShift();
            //injectLayerReferences(shapeShiftWorld);
            // injectPluginReferencesAndStart(shapeShiftWorld, Plugins.BITDUBAI_SHAPE_SHIFT_WORLD);

            /*
             * Plugin Coinapult World
             * -----------------------------
             */
            // Plugin coinapultWorld = ((WorldLayer)  mWorldLayer).getCoinapult();
            //injectLayerReferences(coinapultWorld);
            // injectPluginReferencesAndStart(coinapultWorld, Plugins.BITDUBAI_COINAPULT_WORLD);

            /*
             * Plugin Coinbase World
             * -----------------------------
             */
            // Plugin coinbaseWorld = ((WorldLayer)  mWorldLayer).getCoinbase();
            //injectLayerReferences(coinbaseWorld);
            // injectPluginReferencesAndStart(coinbaseWorld, Plugins.BITDUBAI_COINBASE_WORLD);

            /*
             * Plugin Location World
             * -----------------------------
             */
            // Plugin locationWorld = ((WorldLayer)  mWorldLayer).getLocation();
            //injectLayerReferences(locationWorld);
            // injectPluginReferencesAndStart(locationWorld, Plugins.BITDUBAI_LOCATION_WORLD);

            /*
             * Plugin Crypto Index World
             * -----------------------------
             */
            // Plugin cryptoIndexWorld = ((WorldLayer)  mWorldLayer).getCryptoIndex();
            //injectLayerReferences(cryptoIndexWorld);
            // injectPluginReferencesAndStart(cryptoIndexWorld, Plugins.BITDUBAI_CRYPTO_INDEX);

            /*
             * Plugin Actor Developer
             * -----------------------------
             */
            Plugin actorDeveloper = ((ActorLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PIP_ACTOR_LAYER)).getmActorDeveloper();
            injectPluginReferencesAndStart(actorDeveloper, Plugins.BITDUBAI_ACTOR_DEVELOPER);

            /*
             * Plugin Developer Identity
             * -----------------------------
             */
              Plugin developerIdentity = ((IdentityLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PIP_IDENTITY_LAYER)).getDeveloperIdentity();
               injectPluginReferencesAndStart(developerIdentity, Plugins.BITDUBAI_DEVELOPER_IDENTITY);

            /*
             * Plugin Developer Module
             * -----------------------------
             */
            Plugin developerModule = ((com.bitdubai.fermat_core.layer.pip_module.ModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PIP_MODULE_LAYER)).getmDeveloperModule();
            injectPluginReferencesAndStart(developerModule, Plugins.BITDUBAI_DEVELOPER_MODULE);


             /*
             * Plugin Translator Identity
             * -----------------------------
             */
            Plugin translatorIdentity = ((IdentityLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PIP_IDENTITY_LAYER)).getTranslatorIdentity();
            injectPluginReferencesAndStart(translatorIdentity, Plugins.BITDUBAI_TRANSLATOR_IDENTITY);


             /*
             * Plugin Designer Identity
             * -----------------------------
             */
            Plugin designerIdentity = ((IdentityLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PIP_IDENTITY_LAYER)).getDesignerIdentity();
            injectPluginReferencesAndStart(designerIdentity, Plugins.BITDUBAI_DESIGNER_IDENTITY);


            /*
             * Plugin Extra User
             * -------------------------------
             */
            Plugin extraUser = ((ActorLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PIP_ACTOR_LAYER)).getmActorExtraUser();
            injectPluginReferencesAndStart(extraUser, Plugins.BITDUBAI_USER_EXTRA_USER);

             /*
             * Plugin Intra User
             * -------------------------------
             */
            Plugin intraUser = ((ActorLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PIP_ACTOR_LAYER)).getmActorIntraUser();
            injectPluginReferencesAndStart(intraUser, Plugins.BITDUBAI_USER_INTRA_USER);



            /*
             * Plugin Bitcoin Crypto Network
             * -----------------------------
             */
            Plugin cryptoNetwork = ((CryptoNetworkLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CRYPTO_NETWORK_LAYER)).getCryptoNetwork(CryptoNetworks.BITCOIN);
            injectPluginReferencesAndStart(cryptoNetwork, Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK);

            /*
             *  Plugin Wallet Address Book Crypto  *
             * ------------------------------------*
             */
            Plugin walletAddressBookCrypto = ((CryptoLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CRYPTO_LAYER)).getmWalletAddressBook();
            injectPluginReferencesAndStart(walletAddressBookCrypto, Plugins.BITDUBAI_WALLET_ADDRESS_BOOK_CRYPTO);

            /*
             *  Plugin Actor Address Book Crypto
             * ------------------------------
             */
            Plugin actorAddressBookCrypto = ((CryptoLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CRYPTO_LAYER)).getmUserAddressBook();
            injectPluginReferencesAndStart(actorAddressBookCrypto, Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO);


            /*
             * Plugin Bank Notes Network Service
             * -----------------------------
             */
            Plugin bankNotesNetworkService = ((NetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_NETWORK_SERVICE_LAYER)).getBankNotesPlugin();
            injectPluginReferencesAndStart(bankNotesNetworkService, Plugins.BITDUBAI_BANK_NOTES_NETWORK_SERVICE);

            /*
             * Plugin Wallet Resources Network Service
             * -----------------------------
             */
            Plugin walletResourcesNetworkService = ((NetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_NETWORK_SERVICE_LAYER)).getWalletResources();
            injectPluginReferencesAndStart(walletResourcesNetworkService, Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE);

            /*
             * Plugin Wallet Community Network Service
             * -----------------------------
             */
            Plugin walletCommunityNetworkService = ((NetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_NETWORK_SERVICE_LAYER)).getWalletCommunity();
            injectPluginReferencesAndStart(walletCommunityNetworkService, Plugins.BITDUBAI_WALLET_COMMUNITY_NETWORK_SERVICE);

            /*
             * Plugin Wallet Store Network Service
             * -----------------------------
             */
            Plugin walletStoreNetworkService = ((NetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_NETWORK_SERVICE_LAYER)).getWalletStore();
            injectLayerReferences(walletStoreNetworkService);
            injectPluginReferencesAndStart(walletStoreNetworkService, Plugins.BITDUBAI_WALLET_STORE_NETWORK_SERVICE);

            /*
             * Plugin Wallet Statistics Network Service
             * -----------------------------
             */
            Plugin walletStatisticsNetworkService = ((NetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_NETWORK_SERVICE_LAYER)).getWalletStatistics();
            injectPluginReferencesAndStart(walletStatisticsNetworkService, Plugins.BITDUBAI_WALLET_STATISTICS_NETWORK_SERVICE);


             /*
             * Plugin Crypto Transmission Network Service
             * -----------------------------
             */
            Plugin cryptoTransmissionNetworkService = ((NetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_NETWORK_SERVICE_LAYER)).getCryptoTransmission();
            injectPluginReferencesAndStart(cryptoTransmissionNetworkService, Plugins.BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE);


            /*
             * Plugin App Runtime Middleware
             * -------------------------------
             */
            Plugin appRuntimeMiddleware = ((MiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_MIDDLEWARE_LAYER)).getAppRuntimePlugin();
            injectPluginReferencesAndStart(appRuntimeMiddleware, Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);

            /*
             * Plugin Bank Notes Middleware
             * -----------------------------
             */
            Plugin bankNotesMiddleware = ((MiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_MIDDLEWARE_LAYER)).getBankNotesPlugin();
            injectPluginReferencesAndStart(bankNotesMiddleware, Plugins.BITDUBAI_BANK_NOTES_MIDDLEWARE);

            /*
             * Plugin Bitcoin Wallet Basic Wallet
             * -----------------------------
             */
            Plugin bitcoinWalletBasicWallet = ((BasicWalletLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_BASIC_WALLET_LAYER)).getBitcoinWallet();
            injectPluginReferencesAndStart(bitcoinWalletBasicWallet, Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET);

            /*
             * Plugin Discount Wallet Basic Wallet
             * -----------------------------
             */
            //Plugin discountWalletBasicWallet = ((BasicWalletLayer) mBasicWalletLayer).getDiscountWallet();
            //injectPluginReferencesAndStart(discountWalletBasicWallet, Plugins.BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET);

            /*
             * Plugin Wallet Contacts Middleware
             * ----------------------------------
             */
            Plugin walletContactsMiddleware = ((MiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_MIDDLEWARE_LAYER)).getWalletContactsPlugin();
            injectPluginReferencesAndStart(walletContactsMiddleware, Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE);

            /*
             * Plugin Wallet Factory Middleware
             * ----------------------------------
             */
            Plugin walletFactoryMiddleware = ((MiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_MIDDLEWARE_LAYER)).getmWalletFactoryPlugin();
            injectPluginReferencesAndStart(walletFactoryMiddleware, Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE);


            /*
             * Plugin Wallet Manager Middleware
             * ----------------------------------
             */
            Plugin walletManagerMiddleware = ((MiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_MIDDLEWARE_LAYER)).getmWalletManagerPlugin();
            injectPluginReferencesAndStart(walletManagerMiddleware, Plugins.BITDUBAI_WALLET_MANAGER_MIDDLEWARE);


            /*
             * Plugin Wallet Publisher Middleware
             * ----------------------------------
             */
            Plugin walletPublisherMiddleware = ((MiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_MIDDLEWARE_LAYER)).getmWalletPublisherPlugin();
            injectPluginReferencesAndStart(walletPublisherMiddleware, Plugins.BITDUBAI_WALLET_PUBLISHER_MIDDLEWARE);

            /*
             * Plugin Wallet Store Middleware
             * ----------------------------------
             */

            Plugin walletStoreMiddleware = ((MiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_MIDDLEWARE_LAYER)).getmWalletStorePlugin();
            injectPluginReferencesAndStart(walletStoreMiddleware, Plugins.BITDUBAI_WALLET_STORE_MIDDLEWARE);


            /*
             * Plugin Wallet Store Module
             * ----------------------------------
             */

            Plugin walletStoreModule = ((ModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_MODULE_LAYER)).getWalletStore();
            injectPluginReferencesAndStart(walletStoreModule, Plugins.BITDUBAI_WALLET_STORE_MODULE);


              /*
             * Plugin Wallet Navigation Structure Middleware
             * ----------------------------------
             */
            Plugin walletNavigationStructureMiddleware = ((MiddlewareLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_MIDDLEWARE_LAYER)).getWalletNavigationStructurePlugin();
            injectPluginReferencesAndStart(walletNavigationStructureMiddleware, Plugins.BITDUBAI_WALLET_NAVIGATION_STRUCTURE_MIDDLEWARE);


            /*
             * Plugin Bitcoin Crypto Vault
             * ----------------------------------
             */
            Plugin bitcoinCryptoVault = ((CryptoVaultLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CRYPTO_VAULT_LAYER)).getmBitcoin();
            injectPluginReferencesAndStart(bitcoinCryptoVault, Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT);

            /*
             * Plugin Incoming Crypto Crypto Router
             * ----------------------------------
             */
            Plugin incomingCryptoTransaction = ((CryptoRouterLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_CRYPTO_ROUTER_LAYER)).getIncomingCrypto();
            injectPluginReferencesAndStart(incomingCryptoTransaction, Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION);

            /*
             * Plugin Incoming Extra User Transaction
             * ----------------------------------
             */
            Plugin incomingExtraUserTransaction = ((TransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_TRANSACTION_LAYER)).getIncomingExtraUserPlugin();
            injectPluginReferencesAndStart(incomingExtraUserTransaction, Plugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION);
            //System.out.println("EXTRA USER START SUCCESS");


            /*
             * Plugin Incoming Intra User Transaction
             * ----------------------------------
             */
            Plugin incomingIntraUserTransaction = ((TransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_TRANSACTION_LAYER)).getIncomingIntraUserPlugin();
            injectPluginReferencesAndStart(incomingIntraUserTransaction, Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION);

            /*
             * Plugin Inter Wallet Transaction
             * ----------------------------------
             */
            Plugin interWalletTransaction = ((TransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_TRANSACTION_LAYER)).getInterWalletPlugin();
            injectPluginReferencesAndStart(interWalletTransaction, Plugins.BITDUBAI_INTER_WALLET_TRANSACTION);

            /*
             * Plugin Outgoing Extra User Transaction
             * ----------------------------------
             */
            Plugin outgoingExtraUserTransaction = ((TransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_TRANSACTION_LAYER)).getOutgoingExtraUserPlugin();
            injectPluginReferencesAndStart(outgoingExtraUserTransaction, Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION);

            /*
             * Plugin Outgoing Device user Transaction
             * ----------------------------------
             */
            Plugin outgoingDeviceUserTransaction = ((TransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_TRANSACTION_LAYER)).getOutgoingDeviceUserPlugin();
            injectPluginReferencesAndStart(outgoingDeviceUserTransaction, Plugins.BITDUBAI_OUTGOING_DEVICE_USER_TRANSACTION);

            /*
             * Plugin Incoming Device user Transaction
             * ----------------------------------
             */
            Plugin outgoingIntraUserTransaction = ((TransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_TRANSACTION_LAYER)).getOutgoingIntraUserPlugin();
            injectPluginReferencesAndStart(outgoingIntraUserTransaction, Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION);

            /*
             * Plugin Incoming Device user Transaction
             * ----------------------------------
             */
            Plugin incomingDeviceUserTransaction = ((TransactionLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_TRANSACTION_LAYER)).getIncomingDeviceUserPlugin();
            injectPluginReferencesAndStart(incomingDeviceUserTransaction, Plugins.BITDUBAI_INCOMING_DEVICE_USER_TRANSACTION);

            /*
             * Plugin Crypto Loss Protected Wallet Niche Type Wallet
             * ----------------------------------
             */
            //TODO lo comente porque la variable cryptoLossProtectedWalletNicheWalletType es null y da error al inicializar la APP (Natalia)
            //Plugin cryptoLossProtectedWalletNicheWalletType = ((NicheWalletTypeLayer) mNicheWalletTypeLayer).getmCryptoLossProtectedWallet();
            //injectPluginReferencesAndStart(cryptoLossProtectedWalletNicheWalletType, Plugins.BITDUBAI_CRYPTO_LOSS_PROTECTED_WALLET_NICHE_WALLET_TYPE);

            /*
             * Plugin crypto Wallet Niche Type Wallet
             * ----------------------------------
             */
            Plugin cryptoWalletNicheWalletType = ((NicheWalletTypeLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_NICHE_WALLET_TYPE_LAYER)).getmCryptoWallet();
            injectPluginReferencesAndStart(cryptoWalletNicheWalletType, Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE);

            /*
             * Plugin Discount Wallet Niche Type Wallet
             * ----------------------------------
             */
            Plugin discountWalletNicheWalletType = ((NicheWalletTypeLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_NICHE_WALLET_TYPE_LAYER)).getmDiscountWallet();
            injectPluginReferencesAndStart(discountWalletNicheWalletType, Plugins.BITDUBAI_DISCOUNT_WALLET_NICHE_WALLET_TYPE);

            /*
             * Plugin Fiat Over Crypto Loss Protected Wallet Wallet Niche Type Wallet
             * ----------------------------------
             */
            //TODO lo comente porque la variable fiatOverCryptoLossProtectedWalletNicheWalletType es null  y da error al levantar la APP (Natalia)
            //Plugin fiatOverCryptoLossProtectedWalletNicheWalletType = ((NicheWalletTypeLayer) mNicheWalletTypeLayer).getmFiatOverCryptoLossProtectedWallet();
            //injectPluginReferencesAndStart(fiatOverCryptoLossProtectedWalletNicheWalletType, Plugins.BITDUBAI_FIAT_OVER_CRYPTO_LOSS_PROTECTED_WALLET_NICHE_WALLET_TYPE);


            /*
             * Plugin Fiat Over Crypto Wallet Niche Type Wallet
             * ----------------------------------
             */
            Plugin fiatOverCryptoWalletNicheWalletType = ((NicheWalletTypeLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_NICHE_WALLET_TYPE_LAYER)).getmFiatOverCryptoWallet();
            injectPluginReferencesAndStart(fiatOverCryptoWalletNicheWalletType, Plugins.BITDUBAI_FIAT_OVER_CRYPTO_WALLET_NICHE_WALLET_TYPE);

            /*
             * Plugin Multi account Wallet Niche Type Wallet
             * ----------------------------------
             */
            Plugin multiAccountWalletNicheWalletType = ((NicheWalletTypeLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_NICHE_WALLET_TYPE_LAYER)).getmMultiAccountWallet();
            injectPluginReferencesAndStart(multiAccountWalletNicheWalletType, Plugins.BITDUBAI_MULTI_ACCOUNT_WALLET_NICHE_WALLET_TYPE);

            /*
             * Plugin Bank Notes Wallet Niche Type Wallet
             * ----------------------------------
             */
            Plugin bankNotesWalletNicheWalletType = ((NicheWalletTypeLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_NICHE_WALLET_TYPE_LAYER)).getmBankNotesWallet();
            injectPluginReferencesAndStart(bankNotesWalletNicheWalletType, Plugins.BITDUBAI_BANK_NOTES_WALLET_NICHE_WALLET_TYPE);

            /*
             * Plugin Wallet factory
             * -----------------------------
             */
            //  Plugin walletFactoryModule =  ((ModuleLayer) mModuleLayer).getWalletFactory();
            //  injectPluginReferencesAndStart(walletFactoryModule, Plugins.BITDUBAI_WALLET_FACTORY_MODULE);

            /*
             * Plugin Wallet Manager
             * -----------------------------
             */
            Plugin walletManager =  ((ModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_MODULE_LAYER)).getWalletManager();
            injectPluginReferencesAndStart(walletManager, Plugins.BITDUBAI_WALLET_MANAGER_MODULE);

            /*
             * Plugin Wallet Runtime
             * -----------------------------
             */
            Plugin walletRuntime =  ((ModuleLayer)  corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_MODULE_LAYER)).getWalletRuntime();
            injectPluginReferencesAndStart(walletRuntime, Plugins.BITDUBAI_WALLET_RUNTIME_MODULE);

            /*
             * Plugin Wallet Runtime
             * -----------------------------
             */
            Plugin subAppResourcesNetworkService = ((com.bitdubai.fermat_core.layer.pip_network_service.NetworkServiceLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_PIP_NETWORK_SERVICE_LAYER)).getSubAppResources();
            injectPluginReferencesAndStart(subAppResourcesNetworkService, Plugins.BITDUBAI_SUBAPP_RESOURCES_NETWORK_SERVICE);

            /**
             * Plugin Crypto Loss Protected Wallet Niche Type Wallet
             * ----------------------------------
             */
            //TODO lo comente porque la variable cryptoLossProtectedWalletNicheWalletType es null y da error al inicializar la APP (Natalia)
            //Plugin cryptoLossProtectedWalletNicheWalletType = ((NicheWalletTypeLayer) mNicheWalletTypeLayer).getmCryptoLossProtectedWallet();
            //injectPluginReferencesAndStart(cryptoLossProtectedWalletNicheWalletType, Plugins.BITDUBAI_CRYPTO_LOSS_PROTECTED_WALLET_NICHE_WALLET_TYPE);

            /*
             * Plugin Wallet factory
             * -----------------------------
             */
            //  Plugin walletFactoryModule =  ((ModuleLayer) mModuleLayer).getWalletFactory();
            //  injectPluginReferencesAndStart(walletFactoryModule, Plugins.BITDUBAI_WALLET_FACTORY_MODULE);



            /*
             * Plugin Intra User Module
             * -----------------------------
             */
            Plugin intraUserModule = ((ModuleLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_MODULE_LAYER)).getIntraUser();
            injectPluginReferencesAndStart(intraUserModule, Plugins.BITDUBAI_INTRA_USER_FACTORY_MODULE);


             /*
             * Plugin Intra User Actor
             * -----------------------------
             */
            Plugin intraUserActor = ((com.bitdubai.fermat_core.layer.dmp_actor.ActorLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_ACTOR_LAYER)).getActorIntraUser();
            injectPluginReferencesAndStart(intraUserActor, Plugins.BITDUBAI_INTRA_USER_ACTOR);


              /*
             * Plugin Intra User Identity
             * -----------------------------
             */
            Plugin intraUserIdentity = ((com.bitdubai.fermat_core.layer.dmp_identity.IdentityLayer) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_IDENTITY_LAYER)).getIntraUser();
            injectPluginReferencesAndStart(intraUserIdentity, Plugins.BITDUBAI_INTRA_USER_IDENTITY);

           /*
             * Plugin Template Network Service
             * -----------------------------
             */
            Plugin templateNetworkService = ((NetworkServiceLayer)  corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_NETWORK_SERVICE_LAYER)).getTemplate();
            injectLayerReferences(templateNetworkService);
            injectPluginReferencesAndStart(templateNetworkService, Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE);

            /*
             * Plugin Request
             * -----------------------------
             */
            Plugin moneyRequest = ((RequestServiceLayer)  corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_REQUEST_LAYER)).getMoney();
            injectPluginReferencesAndStart(moneyRequest, Plugins.BITDUBAI_REQUEST_MONEY_REQUEST);


        } catch (CantInitializePluginsManagerException cantInitializePluginsManagerException) {

            LOG.log(Level.SEVERE, cantInitializePluginsManagerException.getLocalizedMessage());
            throw new CantStartPlatformException();
        }
    }


    /**
     * Method that inject all references to another plugin that required a
     * plugin to work
     *
     * @param plugin
     * @param descriptor
     */
    private void injectPluginReferencesAndStart(Plugin plugin, Plugins descriptor) {

        /*
         * Get the errorm manager instance
         */
        ErrorManager errorManager = (ErrorManager) corePlatformContext.getAddon(Addons.ERROR_MANAGER);

        try {

            if (plugin instanceof DealsWithActorAddressBook) {
                ((DealsWithActorAddressBook) plugin).setActorAddressBookManager((ActorAddressBookManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO));
            }

            if (plugin instanceof DealsWithBitcoinWallet) {
                ((DealsWithBitcoinWallet) plugin).setBitcoinWalletManager((BitcoinWalletManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET));
            }

            if (plugin instanceof DealsWithBitcoinCryptoNetwork) {
                ((DealsWithBitcoinCryptoNetwork) plugin).setBitcoinCryptoNetworkManager((BitcoinCryptoNetworkManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK));
            }

            if (plugin instanceof DealsWithCryptoVault) {
                ((DealsWithCryptoVault) plugin).setCryptoVaultManager((CryptoVaultManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT));
            }

            if (plugin instanceof DealsWithDeveloperIdentity) {
                ((DealsWithDeveloperIdentity) plugin).setDeveloperIdentityManager((DeveloperIdentityManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DEVELOPER_IDENTITY));
            }

            if (plugin instanceof DealsWithDeveloperModule) {
                ((DealsWithDeveloperModule) plugin).setDeveloperModuleManager((DeveloperModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DEVELOPER_MODULE));
            }

            if (plugin instanceof DealsWithTranslator) {
                ((DealsWithTranslator) plugin).setTranslatorIdentityManager((TranslatorManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_TRANSLATOR_IDENTITY));
            }

            if (plugin instanceof DealsWithErrors) {
                ((DealsWithErrors) plugin).setErrorManager(errorManager);
            }

            if (plugin instanceof DealsWithEvents) {
                ((DealsWithEvents) plugin).setEventManager((EventManager) corePlatformContext.getAddon(Addons.EVENT_MANAGER));
            }

            if (plugin instanceof DealsWithExtraUsers) {
                ((DealsWithExtraUsers) plugin).setExtraUserManager((ExtraUserManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_USER_EXTRA_USER));
            }

            if (plugin instanceof DealsWithLogger) {
                ((DealsWithLogger) plugin).setLogManager(loggerSystemOs.getLoggerManager());
            }

            if (plugin instanceof DealsWithNicheWalletTypeCryptoWallet) {
                ((DealsWithNicheWalletTypeCryptoWallet) plugin).setNicheWalletTypeCryptoWalletManager((CryptoWalletManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE));
            }

            if (plugin instanceof DealsWithOutgoingExtraUser) {
                ((DealsWithOutgoingExtraUser) plugin).setOutgoingExtraUserManager((OutgoingExtraUserManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION));
            }

            if (plugin instanceof DealsWithPluginFileSystem) {
                ((DealsWithPluginFileSystem) plugin).setPluginFileSystem(fileSystemOs.getPlugInFileSystem());
            }

            if (plugin instanceof DealsWithPluginDatabaseSystem) {
                ((DealsWithPluginDatabaseSystem) plugin).setPluginDatabaseSystem(databaseSystemOs.getPluginDatabaseSystem());
            }

            if (plugin instanceof DealsWithToolManager) {
                ((DealsWithToolManager) plugin).setToolManager((ToolManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_ACTOR_DEVELOPER));
            }

            if (plugin instanceof DealsWithWalletAddressBook) {
                ((DealsWithWalletAddressBook) plugin).setWalletAddressBookManager((WalletAddressBookManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_ADDRESS_BOOK_CRYPTO));
            }

            if (plugin instanceof DealsWithWalletContacts) {
                ((DealsWithWalletContacts) plugin).setWalletContactsManager((WalletContactsManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE));
            }

            if (plugin instanceof DealsWithWalletFactory) {
                ((DealsWithWalletFactory) plugin).setWalletDescriptorFactoryProjectManager((WalletDescriptorFactoryProjectManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE));
            }

            if (plugin instanceof DealsWithWalletManager) {
                ((DealsWithWalletManager) plugin).setWalletManagerManager((WalletManagerManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_MANAGER_MIDDLEWARE));
            }

            if (plugin instanceof DealsWithWalletPublisher) {
                ((DealsWithWalletPublisher) plugin).setWalletPublisherManager((WalletPublisherManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_PUBLISHER_MODULE));
            }

            if (plugin instanceof DealsWithWalletResources) {
                ((DealsWithWalletResources) plugin).setWalletResourcesManager((WalletResourcesInstalationManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE));
            }

            if (plugin instanceof DealsWithWalletStatisticsNetworkService) {
                ((DealsWithWalletStatisticsNetworkService) plugin).setWalletStatisticsManager((WalletStatisticsManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_STATISTICS_NETWORK_SERVICE));
            }

            if (plugin instanceof DealsWithWalletStoreMiddleware) {
                ((DealsWithWalletStoreMiddleware) plugin).setWalletStoreManager((WalletStoreManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_STORE_MIDDLEWARE));
            }

            if (plugin instanceof DealsWithWalletStoreNetworkService) {
                ((DealsWithWalletStoreNetworkService) plugin).setWalletStoreManager((com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.WalletStoreManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_STORE_NETWORK_SERVICE));
            }

            if (plugin instanceof DealsWithIncomingCrypto) {
                ((DealsWithIncomingCrypto) plugin).setIncomingCryptoManager((IncomingCryptoManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION));
            }
            if (plugin instanceof DealsWithDeviceUser) {
                ((DealsWithDeviceUser) plugin).setDeviceUserManager((DeviceUserManager) corePlatformContext.getAddon(Addons.DEVICE_USER));
            }
            if (plugin instanceof DealsWithWalletStoreModule) {
                ((DealsWithWalletStoreModule) plugin).setWalletStoreModuleManager((WalletStoreModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_STORE_MODULE));
            }
            if (plugin instanceof DealsWithDesigner) {
                ((DealsWithDesigner) plugin).setDesignerIdentityManager((DesignerManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DESIGNER_IDENTITY));
            }

            if (plugin instanceof DealsWithPlatformInfo) {
                ((DealsWithPlatformInfo) plugin).setPlatformInfoManager((PlatformInfoManager) corePlatformContext.getAddon(Addons.PLATFORM_INFO));
            }

            if (plugin instanceof DealsWithWalletPublisherMiddleware) {
                ((DealsWithWalletPublisherMiddleware) plugin).setWalletPublisherMiddlewareManager((WalletPublisherMiddlewareManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WALLET_PUBLISHER_MIDDLEWARE));
            }

            if (plugin instanceof DealsWithIntraUsers) {
                ((DealsWithIntraUsers) plugin).setActorIntraUserManager((ActorIntraUserManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE));
            }

            if (plugin instanceof DealsWithIntraUsersModule) {
                ((DealsWithIntraUsersModule) plugin).setIntraUserModuleManager((IntraUserModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_INTRA_USER_FACTORY_MODULE));
            }
            /*
             * Register the plugin into the platform context
             */
            corePlatformContext.registerPlugin(plugin, descriptor);


            /*
             * As any other plugin, this one will need its identity in order to access the data it persisted before.
             */
            plugin.setId(pluginsIdentityManager.getPluginId(plugin, descriptor));

            /*
             * Start the plugin service
             */
            ((Service) plugin).start();


        } catch (CantStartPluginException cantStartPluginException) {

            /**
             * This plugin wont disable the whole platform, so I will allow the Platform to start even if this one
             * will be disabled. In the future, I will re-try the start of plugins that are not starting at once.
             */
            errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, cantStartPluginException);

        } catch (PluginNotRecognizedException pluginNotRecognizedException) {

            /**
             * This plugin wont disable the whole platform, so I will allow the Platform to start even if this one
             * will be disabled. In the future, I will re-try the start of plugins that are not starting at once.
             */
            errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, pluginNotRecognizedException);

        } catch (Exception e){

            /**
             * This plugin wont disable the whole platform, so I will allow the Platform to start even if this one
             * will be disabled. In the future, I will re-try the start of plugins that are not starting at once.
             */
            errorManager.reportUnexpectedPlatformException(PlatformComponents.PLATFORM, UnexpectedPlatformExceptionSeverity.DISABLES_ONE_PLUGIN, e);
        }
    }

    /**
     * This method is responsible to inject PlatformLayer referent object, since in special cases some plugin interact
     * directly with a layer instance. For example in the case of Network Services
     *
     * NOTE: This method should always call before @see Platform#injectPluginReferencesAndStart(Plugin, Plugins)
     * always and when it is required by the plugin
     *
     * @param plugin
     */
    private void injectLayerReferences(Plugin plugin) throws CantStartPlatformException {

        try {

            /*
             * Validate if this plugin required the Communication Layer instance
             */
            if (plugin instanceof DealsWithCommunicationLayerManager) {

                /*
                 * Inject the instance
                 */
                ((DealsWithCommunicationLayerManager) plugin).setCommunicationLayerManager((CommunicationLayerManager) corePlatformContext.getPlatformLayer(PlatformLayers.BITDUBAI_COMMUNICATION_LAYER));

            }

        } catch (Exception exception){
            LOG.log(Level.SEVERE, exception.getLocalizedMessage());
            throw new CantStartPlatformException();
        }
    }

    /**
     * Method that check addons for developer interfaces
     *
     * @param descriptor
     */
    private void checkAddonForDeveloperInterfaces(final Addons descriptor){

        /*
         * Get the addon by description
         */
        Addon addon = corePlatformContext.getAddon(descriptor);

        /*
         * Validate is not null
         */
        if(addon == null) {
            return;
        }

        /*
         * Validate if is instance of DatabaseManagerForDevelopers
         */
        if(addon instanceof DatabaseManagerForDevelopers) {

            /*
             * Put into dealsWithDatabaseManagersAddons
             */
            dealsWithDatabaseManagersAddons.put(descriptor, addon);
        }

        /*
         * Validate if is instance of LogManagerForDevelopers
         */
        if(addon instanceof LogManagerForDevelopers) {

            /*
             * Put into dealsWithLogManagersAddons
             */
            dealsWithLogManagersAddons.put(descriptor, addon);
        }
    }

    /**
     * Method that check plugin for developer interfaces
     *
     * @param descriptor
     */
    private void checkPluginForDeveloperInterfaces(final Plugins descriptor){

        /*
         * Get the plugin by description
         */
        Plugin plugin = corePlatformContext.getPlugin(descriptor);

        /*
         * Validate is not null
         */
        if(plugin == null) {
            return;
        }

        /*
         * Validate if is instance of DatabaseManagerForDevelopers
         */
        if(plugin instanceof DatabaseManagerForDevelopers) {

            /*
             * Put into dealsWithDatabaseManagersPlugins
             */
            dealsWithDatabaseManagersPlugins.put(descriptor, plugin);
        }

        /*
         * Validate if is instance of LogManagerForDevelopers
         */
        if(plugin instanceof LogManagerForDevelopers) {

            /*
             * Put into dealsWithDatabaseManagersPlugins
             */
            dealsWithLogManagersPlugins.put(descriptor, plugin);
        }

    }

    /**
     * Get the CorePlatformContext
     * @return CorePlatformContext
     */
    public CorePlatformContext getCorePlatformContext() {
        return corePlatformContext;
        // Luis: TODO: Este metodo debe ser removido y lo que se debe devolver es un context con referencias a los plugins que la interfaz grafica puede acceder, no a todos los que existen como esta ahora mismo.
    }

    /**
     * Get the plugin reference from the context
     *
     * @param key
     * @return Plugin
     */
    public Plugin getPlugin(Plugins key){
        return corePlatformContext.getPlugin(key);
    }
}