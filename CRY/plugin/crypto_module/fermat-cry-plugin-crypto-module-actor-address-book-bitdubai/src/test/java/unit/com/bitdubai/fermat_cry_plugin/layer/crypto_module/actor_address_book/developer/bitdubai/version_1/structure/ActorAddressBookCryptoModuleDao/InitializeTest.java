package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDao;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions.CantInitializeActorAddressBookCryptoModuleException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDao;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions.CantGetActorAddressBookRegistryTestException;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class InitializeTest extends TestCase {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    @Mock
    ErrorManager mockErrorManager;

    @Mock
    PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    Database mockDatabase;

    ActorAddressBookCryptoModuleDao testCryptoModuleDao;

    UUID testPluginId;

    /* YORDIN DA ROCHA 07/08/15
    * SE AGREGO EXCEPCIONES FermatException
    * */
    /*@Before
    public void setUp() throws Exception {
        testPluginId = UUID.randomUUID();
        testCryptoModuleDao = new ActorAddressBookCryptoModuleDao(mockErrorManager, mockPluginDatabaseSystem, testPluginId);
    }*/
    @Before
    public void setUp() throws CantGetActorAddressBookRegistryTestException {
        try {
            testPluginId = UUID.randomUUID();
            testCryptoModuleDao = new ActorAddressBookCryptoModuleDao(mockErrorManager, mockPluginDatabaseSystem, testPluginId);
            throw new CantGetActorAddressBookRegistryTestException();
        } catch (CantGetActorAddressBookRegistryTestException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        } catch (Exception exception) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /* YORDIN DA ROCHA 07/08/15
    * SE AGREGO EXCEPCIONES FermatException
    * */
    // mockDatabase exists and can be opened
    /*@Test
    public void testInitialize_NotNull() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        testCryptoModuleDao.initialize();
    }*/
    @Test
    public void testInitialize_NotNull() throws CantGetActorAddressBookRegistryTestException {
        try {
            when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
            testCryptoModuleDao.initialize();
            throw new CantGetActorAddressBookRegistryTestException();
        } catch (CantGetActorAddressBookRegistryTestException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, e, "", "Check the cause");
        } catch (Exception exception) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /* YORDIN DA ROCHA 07/08/15
    * SE AGREGO EXCEPCIONES FermatException
    * */
    // cant open mockDatabase
    /*@Test(expected=CantInitializeActorAddressBookCryptoModuleException.class)
    public void testInitialize_CantOpenDatabaseException() throws Exception {
        doThrow(new CantOpenDatabaseException()).when(mockPluginDatabaseSystem).openDatabase(any(UUID.class), anyString());

        testCryptoModuleDao.initialize();
    }*/
    @Test(expected=CantInitializeActorAddressBookCryptoModuleException.class)
    public void testInitialize_CantOpenDatabaseException() throws CantGetActorAddressBookRegistryTestException {
        try {
            doThrow(new CantOpenDatabaseException()).when(mockPluginDatabaseSystem).openDatabase(any(UUID.class), anyString());
            testCryptoModuleDao.initialize();
            throw new CantGetActorAddressBookRegistryTestException();
        } catch (CantGetActorAddressBookRegistryTestException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, e, "", "Check the cause");
        } catch (Exception exception) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /* YORDIN DA ROCHA 07/08/15
    * SE AGREGO EXCEPCIONES FermatException
    * */
    /*
    // mockDatabase not found exception, then cant create mockDatabase.
    @Test(expected=CantInitializeActorAddressBookCryptoModuleException.class)
    public void testInitialize_DatabaseNotFoundException() throws Exception {
        doThrow(new DatabaseNotFoundException()).when(mockPluginDatabaseSystem).openDatabase(any(UUID.class), anyString());
        doThrow(new CantCreateDatabaseException()).when(mockPluginDatabaseSystem).createDatabase(any(UUID.class), anyString());

        testCryptoModuleDao.initialize();
    }
    */
    @Test(expected=CantInitializeActorAddressBookCryptoModuleException.class)
    public void testInitialize_DatabaseNotFoundException() throws CantGetActorAddressBookRegistryTestException {
        try {
            doThrow(new DatabaseNotFoundException()).when(mockPluginDatabaseSystem).openDatabase(any(UUID.class), anyString());
            doThrow(new CantCreateDatabaseException()).when(mockPluginDatabaseSystem).createDatabase(any(UUID.class), anyString());
            testCryptoModuleDao.initialize();
            throw new CantGetActorAddressBookRegistryTestException();
        } catch (CantGetActorAddressBookRegistryTestException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, e, "", "Check the cause");
        } catch (Exception exception) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /*
     * TODO This test should pass but there is a wrong design decision that makes a cast of the Database interface into the DatabaseFactory; we should really look into that
     */
    // mockDatabase not found exception, then create mockDatabase.
    /*@Ignore
    public void testInitialize_DatabaseNotFoundException_CreateDatabase() throws Exception {
        doThrow(new DatabaseNotFoundException()).when(mockPluginDatabaseSystem).openDatabase(any(UUID.class), anyString());
        doReturn(mockDatabase).when(mockPluginDatabaseSystem).createDatabase(any(UUID.class), anyString());

        testCryptoModuleDao.initialize();
    }*/
    @Ignore
    public void testInitialize_DatabaseNotFoundException_CreateDatabase() throws CantGetActorAddressBookRegistryTestException {
        try {
            doThrow(new DatabaseNotFoundException()).when(mockPluginDatabaseSystem).openDatabase(any(UUID.class), anyString());
            doReturn(mockDatabase).when(mockPluginDatabaseSystem).createDatabase(any(UUID.class), anyString());
            throw new CantGetActorAddressBookRegistryTestException();
        } catch (CantGetActorAddressBookRegistryTestException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, e, "", "Check the cause");
        } catch (Exception exception) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }
    /* YORDIN DA ROCHA 07/08/15
    * SE AGREGO EXCEPCIONES FermatException
    * */
    // error manager null
    /*
    @Test(expected=CantInitializeActorAddressBookCryptoModuleException.class)
    public void testInitialize_ErrorManagerNull_CantInitializeActorAddressBookCryptoModuleException() throws Exception {
        testCryptoModuleDao.setErrorManager(null);
        testCryptoModuleDao.initialize();
    }*/
    @Test(expected=CantInitializeActorAddressBookCryptoModuleException.class)
    public void testInitialize_ErrorManagerNull_CantInitializeActorAddressBookCryptoModuleException() throws CantGetActorAddressBookRegistryTestException {
        try {
            testCryptoModuleDao.setErrorManager(null);
            testCryptoModuleDao.initialize();
            throw new CantGetActorAddressBookRegistryTestException();
        } catch (CantGetActorAddressBookRegistryTestException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        } catch (Exception exception) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /* YORDIN DA ROCHA 07/08/15
    * SE AGREGO EXCEPCIONES FermatException
    * */
    /*
    // plugin mockDatabase system null
    @Test(expected=CantInitializeActorAddressBookCryptoModuleException.class)
    public void testInitialize_PluginDatabaseSystemNull_CantInitializeActorAddressBookCryptoModuleException() throws Exception {
        testCryptoModuleDao.setPluginDatabaseSystem(null);
        testCryptoModuleDao.initialize();
    }*/
    @Test(expected=CantInitializeActorAddressBookCryptoModuleException.class)
    public void testInitialize_PluginDatabaseSystemNull_CantInitializeActorAddressBookCryptoModuleException() throws CantGetActorAddressBookRegistryTestException {
        try {
            testCryptoModuleDao.setPluginDatabaseSystem(null);
            testCryptoModuleDao.initialize();
            throw new CantGetActorAddressBookRegistryTestException();
        } catch (CantGetActorAddressBookRegistryTestException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        } catch (Exception exception) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }
    /* YORDIN DA ROCHA 07/08/15
    * SE AGREGO EXCEPCIONES FermatException
    * */
    // plugin id null
    /*
    @Test(expected=CantInitializeActorAddressBookCryptoModuleException.class)
    public void testInitialize_PluginIdNull_CantInitializeActorAddressBookCryptoModuleException() throws Exception {
        testCryptoModuleDao.setPluginId(null);
        testCryptoModuleDao.initialize();
    }*/
    @Test(expected=CantInitializeActorAddressBookCryptoModuleException.class)
    public void testInitialize_PluginIdNull_CantInitializeActorAddressBookCryptoModuleException() throws CantGetActorAddressBookRegistryTestException {
        try {
            testCryptoModuleDao.setPluginId(null);
            testCryptoModuleDao.initialize();
            throw new CantGetActorAddressBookRegistryTestException();
        } catch (CantGetActorAddressBookRegistryTestException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        } catch (Exception exception) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

}
