package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleRegistry;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.ActorAddressBookNotFoundException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBookException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleRegistry;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions.CantGetActorAddressBookRegistryTestException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class GetActorAddressBookByCryptoAddressTest extends TestCase {

    @Mock
    ErrorManager errorManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    Database database;

    @Mock
    DatabaseTable databaseTable;

    @Mock
    DatabaseTableRecord databaseTableRecord;

    CryptoAddress cryptoAddress;


    ActorAddressBookCryptoModuleRegistry registry;

    UUID pluginId;

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException
    * */
    /*@Before
    public void setUp() throws Exception {
        cryptoAddress = new CryptoAddress("asdadas", CryptoCurrency.BITCOIN);
        pluginId = UUID.randomUUID();
        registry = new ActorAddressBookCryptoModuleRegistry();
        registry.setErrorManager(errorManager);
        registry.setPluginDatabaseSystem(pluginDatabaseSystem);
        registry.setPluginId(pluginId);
        when(pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenReturn(database);
        registry.initialize();

    }*/
    @Before
    public void setUp() throws CantGetActorAddressBookRegistryTestException {
        try {
            cryptoAddress = new CryptoAddress("asdadas", CryptoCurrency.BITCOIN);
            pluginId = UUID.randomUUID();
            registry = new ActorAddressBookCryptoModuleRegistry();
            registry.setErrorManager(errorManager);
            registry.setPluginDatabaseSystem(pluginDatabaseSystem);
            registry.setPluginId(pluginId);
            when(pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenReturn(database);
            registry.initialize();
            throw new CantGetActorAddressBookRegistryTestException();
        } catch (CantGetActorAddressBookRegistryTestException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        } catch (Exception exception) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException
    * */
    /*@Test
    public void testGetActorAddressBookByCryptoAddress_NotNull() throws Exception {
        when(database.getTable(anyString())).thenReturn(databaseTable);
        when(databaseTableRecord.getStringValue(anyString())).thenReturn("EUS");
        List<DatabaseTableRecord> databaseTableRecordList = new ArrayList<>();
        databaseTableRecordList.add(databaseTableRecord);
        when(databaseTable.getRecords()).thenReturn(databaseTableRecordList);

        registry.getActorAddressBookByCryptoAddress(cryptoAddress);
    }*/
    @Test
    public void testGetActorAddressBookByCryptoAddress_NotNull() throws CantGetActorAddressBookRegistryTestException {
        try {
            when(database.getTable(anyString())).thenReturn(databaseTable);
            when(databaseTableRecord.getStringValue(anyString())).thenReturn("EUS");
            List<DatabaseTableRecord> databaseTableRecordList = new ArrayList<>();
            databaseTableRecordList.add(databaseTableRecord);
            when(databaseTable.getRecords()).thenReturn(databaseTableRecordList);
            registry.getActorAddressBookByCryptoAddress(cryptoAddress);
            throw new CantGetActorAddressBookRegistryTestException();
        } catch (CantGetActorAddressBookRegistryTestException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        } catch (Exception exception) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException
    * */
    /*@Test(expected=ActorAddressBookNotFoundException.class)
    public void testGetActorAddressBookByCryptoAddress_ActorAddressBookNotFoundException() throws Exception {
        when(database.getTable(anyString())).thenReturn(databaseTable);

        registry.getActorAddressBookByCryptoAddress(cryptoAddress);
    }*/
    @Test(expected=ActorAddressBookNotFoundException.class)
    public void testGetActorAddressBookByCryptoAddress_ActorAddressBookNotFoundException() throws CantGetActorAddressBookRegistryTestException {
        try {
            when(database.getTable(anyString())).thenReturn(databaseTable);
            registry.getActorAddressBookByCryptoAddress(cryptoAddress);
            throw new CantGetActorAddressBookRegistryTestException();
        } catch (CantGetActorAddressBookRegistryTestException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        } catch (Exception exception) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException
    * */
    /*@Test(expected=CantGetActorAddressBookException.class)
    public void testGetActorAddressBookByCryptoAddress_CantLoadTableToMemoryException_CantGetActorAddressBookException() throws Exception {
        doThrow(new CantLoadTableToMemoryException()).when(databaseTable).loadToMemory();
        when(database.getTable(anyString())).thenReturn(databaseTable);

        registry.getActorAddressBookByCryptoAddress(cryptoAddress);
    }*/
    @Test(expected=CantGetActorAddressBookException.class)
    public void testGetActorAddressBookByCryptoAddress_CantLoadTableToMemoryException_CantGetActorAddressBookException() throws CantGetActorAddressBookRegistryTestException {
        try {
            doThrow(new CantLoadTableToMemoryException()).when(databaseTable).loadToMemory();
            when(database.getTable(anyString())).thenReturn(databaseTable);
            registry.getActorAddressBookByCryptoAddress(cryptoAddress);
            throw new CantGetActorAddressBookRegistryTestException();
        } catch (CantGetActorAddressBookRegistryTestException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        } catch (Exception exception) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException
    * */
    /*@Test(expected=CantGetActorAddressBookException.class)
    public void testGetActorAddressBookByCryptoAddress_CryptoAddressNull_CantGetActorAddressBookException() throws Exception {
        registry.getActorAddressBookByCryptoAddress(null);
    }*/
    @Test(expected=CantGetActorAddressBookException.class)
    public void testGetActorAddressBookByCryptoAddress_CryptoAddressNull_CantGetActorAddressBookException() throws CantGetActorAddressBookRegistryTestException {
        try {
            registry.getActorAddressBookByCryptoAddress(null);
            throw new CantGetActorAddressBookRegistryTestException();
        } catch (CantGetActorAddressBookRegistryTestException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        } catch (Exception exception) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }
}
