package unit.com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserRelayAgent;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserMonitorAgent;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserRegistry;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserRelayAgent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by jorgegonzalez on 2015.07.02..
 */
 @RunWith(MockitoJUnitRunner.class)
public class StopTest {

    @Mock
    private ActorAddressBookManager mockActorAddressBookManager;
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private Database mockDatabase;
    @Mock
    private DatabaseTable mockTable;
    private IncomingExtraUserRegistry testRegistry;

    @Mock
    private BitcoinWalletManager mockBitcoinWalletManager;
    @Mock
    private WalletAddressBookManager mockWalletAddressBookManager;

    private MockErrorManager mockErrorManager = new MockErrorManager();

    private IncomingExtraUserRelayAgent testRelayAgent;

    @Before
    public void setUpRegistry() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        testRegistry = new IncomingExtraUserRegistry();
        testRegistry.setErrorManager(mockErrorManager);
        testRegistry.setPluginDatabaseSystem(mockPluginDatabaseSystem);
    }

    @Test
    public void Stop_AgentStops_TheThreadIsStoppedInmediately() throws Exception{

        testRelayAgent = new IncomingExtraUserRelayAgent(mockBitcoinWalletManager, mockActorAddressBookManager, mockErrorManager, testRegistry, mockWalletAddressBookManager);

        testRelayAgent.start();
        Thread.sleep(100);
        assertThat(testRelayAgent.isRunning()).isTrue();

        int i = 0;
        while(testRelayAgent.isRunning()){
            ++i;
            if(i>5)
                testRelayAgent.stop();
            Thread.sleep(100);
            if(i>20)
                break;
        }
        assertThat(i).isLessThan(20);
    }

}
