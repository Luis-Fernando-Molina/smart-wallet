package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.ActorAddressBookCryptoModulePluginRoot;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions.CantInitializeActorAddressBookCryptoModuleException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDatabaseFactory;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBookRegistryException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRegistry;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.ActorAddressBookCryptoModulePluginRoot;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import java.util.UUID;

import unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions.CantGetActorAddressBookRegistryTestException;


@RunWith(MockitoJUnitRunner.class)
public class GetActorAddressBookRegistryTest extends TestCase {

    @Mock
    ErrorManager errorManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    Database database;

    @Mock
    DatabaseTableFactory table;

    UUID pluginId;

    ActorAddressBookCryptoModulePluginRoot actorAddressBookCryptoModulePluginRoot;


    /* YORDIN DA ROCHA 07/08/15
    * SE AGREGO EXCEPCIONES FermatException
    * */
    @Before
    public void setUp() throws CantGetActorAddressBookRegistryTestException {
        try {
            pluginId = UUID.randomUUID();
            actorAddressBookCryptoModulePluginRoot = new ActorAddressBookCryptoModulePluginRoot();
            actorAddressBookCryptoModulePluginRoot.setId(pluginId);
            actorAddressBookCryptoModulePluginRoot.setErrorManager(errorManager);
            actorAddressBookCryptoModulePluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);
            throw new CantGetActorAddressBookRegistryTestException();
        } catch (CantGetActorAddressBookRegistryTestException exception){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        } catch (Exception exception){
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }


    /* YORDIN DA ROCHA 07/08/15
    * SE AGREGO EXCEPCIONES FermatException
    * */
    /*@Test
    public void testGetActorAddressBookRegistryTeste_NotNul() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenReturn(database);
        ActorAddressBookRegistry actorAddressBookRegistry = actorAddressBookCryptoModulePluginRoot.getActorAddressBookRegistry();
        assertNotNull(actorAddressBookRegistry);
    }*/

    @Test
    public void testGetActorAddressBookRegistryTeste_NotNul() throws CantGetActorAddressBookRegistryTestException {
        try {
            when(pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenReturn(database);
            ActorAddressBookRegistry actorAddressBookRegistry = actorAddressBookCryptoModulePluginRoot.getActorAddressBookRegistry();
            assertNotNull(actorAddressBookRegistry);
            throw new CantGetActorAddressBookRegistryTestException();
        } catch (CantGetActorAddressBookRegistryTestException exception){
            throw exception;
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, e, "", "Check the cause");
        } catch(Exception exception){
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, FermatException.wrapException(exception  ), null, null);
        }
    }

    /* YORDIN DA ROCHA 07/08/15
    * SE AGREGO EXCEPCIONES FermatException
    * */
    /*@Test(expected=CantGetActorAddressBookRegistryException.class)
    public void testGetActorAddressBookRegistryTest_CantOpenDatabaseException() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenThrow(new CantOpenDatabaseException());
        actorAddressBookCryptoModulePluginRoot.getActorAddressBookRegistry();
    }*/
    @Test(expected=CantGetActorAddressBookRegistryException.class)
    public void testGetActorAddressBookRegistryTest_CantOpenDatabaseException() throws CantGetActorAddressBookRegistryTestException {
        try {
            //database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
            actorAddressBookCryptoModulePluginRoot.getActorAddressBookRegistry();
            database.closeDatabase();
            throw new CantGetActorAddressBookRegistryTestException();
        } catch(CantGetActorAddressBookRegistryTestException exception){
            database.closeDatabase();
            throw exception;
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, e, "", "Check the cause");
        } catch(Exception exception){
            database.closeDatabase();
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, FermatException.wrapException(exception  ), null, null);
        }
    }

    /*
     * TODO This test should pass but there is a wrong design decision that makes a cast of the Database interface into the DatabaseFactory; we should really look into that
     */
    /*
    @Ignore
    public void testGetActorAddressBookRegistryTest_DatabaseNotFoundException() throws Exception {

        when(pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenThrow(new DatabaseNotFoundException());
        when(pluginDatabaseSystem.createDatabase(pluginId, pluginId.toString())).thenReturn(database);
        when(((DatabaseFactory) database).newTableFactory(pluginId, "crypto_address_book")).thenReturn(table);

        ActorAddressBookRegistry actorAddressBookRegistry = actorAddressBookCryptoModulePluginRoot.getActorAddressBookRegistry();
        assertNotNull(actorAddressBookRegistry);
    }
    */
    /* YORDIN DA ROCHA 07/08/15
    * SE AGREGO EXCEPCIONES FermatException
    * */
    @Ignore
    public void testGetActorAddressBookRegistryTest_DatabaseNotFoundException() throws CantGetActorAddressBookRegistryTestException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
            when(pluginDatabaseSystem.createDatabase(pluginId, pluginId.toString())).thenReturn(database);
            when(((DatabaseFactory) database).newTableFactory(pluginId, "crypto_address_book")).thenReturn(table);
            ActorAddressBookRegistry actorAddressBookRegistry = actorAddressBookCryptoModulePluginRoot.getActorAddressBookRegistry();
            assertNotNull(actorAddressBookRegistry);
            database.closeDatabase();
            throw new CantGetActorAddressBookRegistryTestException();
        } catch (CantGetActorAddressBookRegistryTestException exception) {
            database.closeDatabase();
            throw exception;
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, e, "", "Check the cause");
        } catch (Exception exception) {
            database.closeDatabase();
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /* YORDIN DA ROCHA 07/08/15
    * SE AGREGO EXCEPCIONES FermatException
    * */
    /*
    @Test(expected=CantGetActorAddressBookRegistryException.class)
    public void testGetActorAddressBookRegistryTest_DatabaseNotFoundException_CantCreateDatabaseException() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenThrow(new DatabaseNotFoundException());
        when(pluginDatabaseSystem.createDatabase(pluginId, pluginId.toString())).thenThrow(new CantCreateDatabaseException());

        actorAddressBookCryptoModulePluginRoot.getActorAddressBookRegistry();
    }
    */
    @Test(expected=CantGetActorAddressBookRegistryException.class)
    public void testGetActorAddressBookRegistryTest_DatabaseNotFoundException_CantCreateDatabaseException() throws CantGetActorAddressBookRegistryTestException{
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
            database = this.pluginDatabaseSystem.createDatabase(pluginId, pluginId.toString());
            actorAddressBookCryptoModulePluginRoot.getActorAddressBookRegistry();
            database.closeDatabase();
            throw new CantGetActorAddressBookRegistryTestException();
        } catch(CantGetActorAddressBookRegistryTestException exception){
            database.closeDatabase();
            throw exception;
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, e, "", "Check the cause");
        } catch(Exception exception){
            database.closeDatabase();
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, FermatException.wrapException(exception  ), null, null);
        }
    }
}
