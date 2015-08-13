package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDatabaseFactory;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDatabaseFactory;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
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

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CreateDatabaseTest extends TestCase {
    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    DatabaseTableFactory table;

    @Mock
    Database database;

    ActorAddressBookCryptoModuleDatabaseFactory databaseFactory;

    UUID pluginId;

    /* YORDIN DA ROCHA 08/08/15
    * SE AGREGO EXCEPCIONES FermatException
    * */
    /*
    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        databaseFactory = new ActorAddressBookCryptoModuleDatabaseFactory();
        databaseFactory.setPluginDatabaseSystem(pluginDatabaseSystem);
    }*/
    @Before
    public void setUp() throws CantGetActorAddressBookRegistryTestException {
        try {
            pluginId = UUID.randomUUID();
            databaseFactory = new ActorAddressBookCryptoModuleDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(pluginDatabaseSystem);
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
    /*
     * TODO This test should pass but there is a wrong design decision that makes a cast of the Database interface into the DatabaseFactory; we should really look into that
     */
    /*
    @Ignore
    public void testCreateDatabase_NotNull() throws Exception {
        doReturn(database).when(pluginDatabaseSystem).createDatabase(any(UUID.class), anyString());
        Database database = databaseFactory.createDatabase(pluginId, pluginId);
        assertNotNull(database);
    }*/
    @Ignore
    public void testCreateDatabase_NotNull() throws CantGetActorAddressBookRegistryTestException {
        try {
            doReturn(database).when(pluginDatabaseSystem).createDatabase(any(UUID.class), anyString());
            Database database = databaseFactory.createDatabase(pluginId, pluginId);
            assertNotNull(database);
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
    /*
    @Test(expected=CantCreateDatabaseException.class)
    public void testCreateDatabase_CantCreateDatabaseException() throws Exception {
        when(pluginDatabaseSystem.createDatabase(pluginId, pluginId.toString())).thenThrow(new CantCreateDatabaseException());

        databaseFactory.createDatabase(pluginId, pluginId);
    }*/
    @Test(expected=CantCreateDatabaseException.class)
    public void testCreateDatabase_CantCreateDatabaseException() throws CantGetActorAddressBookRegistryTestException {
        try {
            when(pluginDatabaseSystem.createDatabase(pluginId, pluginId.toString())).thenThrow(new CantCreateDatabaseException());
            databaseFactory.createDatabase(pluginId, pluginId);
            throw new CantGetActorAddressBookRegistryTestException();
        } catch (CantGetActorAddressBookRegistryTestException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        } catch (Exception exception) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }
}
