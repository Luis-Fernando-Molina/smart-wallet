package unit.com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserTransactionProcessorAgent;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDao;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserTransactionProcessorAgent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;

/**
 * Created by natalia on 10/07/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class StopTest {

    @Mock
    private BitcoinWalletManager mockBitcoinWalletManager;

    @Mock
    private CryptoVaultManager mockCryptoVaultManager;

    @Mock
    private ErrorManager mockErrorManager;

    @Mock
    private OutgoingExtraUserDao mockDao;

    private OutgoingExtraUserTransactionProcessorAgent testMonitorAgent;

    @Before
    public void setUpRegistry() throws Exception{
        testMonitorAgent = new OutgoingExtraUserTransactionProcessorAgent();
        testMonitorAgent.setErrorManager(mockErrorManager);
        testMonitorAgent.setBitcoinWalletManager(mockBitcoinWalletManager);
        testMonitorAgent.setCryptoVaultManager(mockCryptoVaultManager);
        mockDao = new OutgoingExtraUserDao();
        testMonitorAgent.setOutgoingExtraUserDao(mockDao);
    }

    @Test
    public void Stop_AgentStops_TheThreadIsStoppedInmediately() throws Exception{

         testMonitorAgent.start();
        Thread.sleep(100);
        assertThat(testMonitorAgent.isRunning()).isTrue();

        int i = 0;
        while(testMonitorAgent.isRunning()){
            ++i;
            if(i>5)
                testMonitorAgent.stop();
            Thread.sleep(100);
            if(i>20)
                break;
        }
        assertThat(i).isLessThan(20);

    }



}
