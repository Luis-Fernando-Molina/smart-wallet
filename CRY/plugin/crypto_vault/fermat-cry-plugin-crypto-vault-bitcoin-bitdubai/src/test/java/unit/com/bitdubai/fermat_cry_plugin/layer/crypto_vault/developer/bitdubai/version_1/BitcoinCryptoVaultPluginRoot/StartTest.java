package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.Common.MockedPluginFileSystem;
import unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.Common.MockedPluginFileSystemWithError;

/**
 * Created by rodrigo on 2015.07.15..
 */
@RunWith(MockitoJUnitRunner.class)
public class StartTest {
    @Mock
    ErrorManager errorManager;

    @Mock
    LogManager logManager;

    @Mock
    EventManager eventManager;

    @Mock
    DeviceUserManager deviceUserManager;

    @Mock
    BitcoinCryptoNetworkManager bitcoinCryptoNetworkManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    MockedPluginFileSystem pluginFileSystem;


    @Test
    public void testValidStart() throws CantStartPluginException {
        BitcoinCryptoVaultPluginRoot root = new BitcoinCryptoVaultPluginRoot();
        pluginFileSystem = new MockedPluginFileSystem();
        root.setErrorManager(errorManager);
        root.setEventManager(eventManager);
        root.setId(UUID.randomUUID());
        root.setDeviceUserManager(deviceUserManager);
        root.setLogManager(logManager);
        root.setBitcoinCryptoNetworkManager(bitcoinCryptoNetworkManager);
        root.setPluginDatabaseSystem(pluginDatabaseSystem);
        root.setPluginFileSystem(pluginFileSystem);

        /**
         * I will start it and get a valid address from the vault.
         */
        root.start();
        Assert.assertNotNull(root.getAddress());
    }

    /**
     * I will simulate an error saving the vault to verify the exception.
     * @throws CantStartPluginException
     */
    @Test (expected = CantStartPluginException.class)
    public void testStartWithError() throws CantStartPluginException {
        BitcoinCryptoVaultPluginRoot root = new BitcoinCryptoVaultPluginRoot();
        MockedPluginFileSystemWithError pluginFileSystemWithError = new MockedPluginFileSystemWithError();
        root.setErrorManager(errorManager);
        root.setEventManager(eventManager);
        root.setId(UUID.randomUUID());
        root.setDeviceUserManager(deviceUserManager);
        root.setLogManager(logManager);
        root.setBitcoinCryptoNetworkManager(bitcoinCryptoNetworkManager);
        root.setPluginDatabaseSystem(pluginDatabaseSystem);
        root.setPluginFileSystem(pluginFileSystemWithError);

        /**
         * I will start it and get a valid address from the vault.
         */
        root.start();
    }

    @Test
    public void getAddressTest() throws CantStartPluginException {
        BitcoinCryptoVaultPluginRoot root = new BitcoinCryptoVaultPluginRoot();
        pluginFileSystem = new MockedPluginFileSystem();
        root.setErrorManager(errorManager);
        root.setEventManager(eventManager);
        root.setId(UUID.randomUUID());
        root.setDeviceUserManager(deviceUserManager);
        root.setLogManager(logManager);
        root.setBitcoinCryptoNetworkManager(bitcoinCryptoNetworkManager);
        root.setPluginDatabaseSystem(pluginDatabaseSystem);
        root.setPluginFileSystem(pluginFileSystem);

        /**
         * I will start it and get a valid address from the vault.
         */
        root.start();

        Assert.assertEquals(root.getAddresses(5).size(), 5);
    }

    public void testTransactionProtocolManager() throws CantStartPluginException {
        BitcoinCryptoVaultPluginRoot root = new BitcoinCryptoVaultPluginRoot();
        pluginFileSystem = new MockedPluginFileSystem();
        root.setErrorManager(errorManager);
        root.setEventManager(eventManager);
        root.setId(UUID.randomUUID());
        root.setDeviceUserManager(deviceUserManager);
        root.setLogManager(logManager);
        root.setBitcoinCryptoNetworkManager(bitcoinCryptoNetworkManager);
        root.setPluginDatabaseSystem(pluginDatabaseSystem);
        root.setPluginFileSystem(pluginFileSystem);

        /**
         * I will start it and get a valid address from the vault.
         */
        root.start();
        Assert.assertNotNull(root.getTransactionManager());
    }

    @Test
    public void stopTest() throws CantStartPluginException {
        BitcoinCryptoVaultPluginRoot root = new BitcoinCryptoVaultPluginRoot();
        pluginFileSystem = new MockedPluginFileSystem();
        root.setErrorManager(errorManager);
        root.setEventManager(eventManager);
        root.setId(UUID.randomUUID());
        root.setDeviceUserManager(deviceUserManager);
        root.setLogManager(logManager);
        root.setBitcoinCryptoNetworkManager(bitcoinCryptoNetworkManager);
        root.setPluginDatabaseSystem(pluginDatabaseSystem);
        root.setPluginFileSystem(pluginFileSystem);

        /**
         * I will start it and get a valid address from the vault.
         */
        root.start();
        root.stop();
        org.junit.Assert.assertEquals(root.getStatus(), ServiceStatus.STOPPED);
    }
}
