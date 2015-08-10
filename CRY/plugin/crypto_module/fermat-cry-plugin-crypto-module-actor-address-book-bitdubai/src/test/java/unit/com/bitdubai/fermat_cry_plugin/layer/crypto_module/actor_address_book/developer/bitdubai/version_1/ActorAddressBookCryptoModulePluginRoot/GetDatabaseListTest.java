package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.ActorAddressBookCryptoModulePluginRoot;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.ActorAddressBookCryptoModulePluginRoot;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions.CantGetActorAddressBookRegistryTestException;

@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseListTest extends TestCase {
    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    @Mock
    DeveloperObjectFactory developerObjectFactory;

    UUID pluginId;

    ActorAddressBookCryptoModulePluginRoot actorAddressBookCryptoModulePluginRoot;

    /* YORDIN DA ROCHA 07/08/15
    * SE AGREGO EXCEPCIONES FermatException
    * */
    @Before
    //public void setUp() throws Exception {
    public void setUp() throws CantGetActorAddressBookRegistryTestException{
        try {
            pluginId = UUID.randomUUID();
            actorAddressBookCryptoModulePluginRoot = new ActorAddressBookCryptoModulePluginRoot();
            actorAddressBookCryptoModulePluginRoot.setId(pluginId);
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
    @Test
    public void testGetDatabaseListTest_NotNull() throws CantGetActorAddressBookRegistryTestException{
        try {
            List<DeveloperDatabase> developerDatabaseList = actorAddressBookCryptoModulePluginRoot.getDatabaseList(developerObjectFactory);
            assertNotNull(developerDatabaseList);
            throw new CantGetActorAddressBookRegistryTestException();
        } catch (CantGetActorAddressBookRegistryTestException exception){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        } catch (Exception exception){
            throw new CantGetActorAddressBookRegistryTestException(CantGetActorAddressBookRegistryTestException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }
}
