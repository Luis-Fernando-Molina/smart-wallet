package unit.com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.util.TransactionExecutorFactory;

import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.interfaces.TransactionExecutor;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.executors.BitcoinBasicWalletTransactionExecutor;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.util.TransactionExecutorFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.googlecode.catchexception.CatchException.*;
/**
 * Created by jorgegonzalez on 2015.07.08..
 */
@RunWith(MockitoJUnitRunner.class)
public class NewTransactionExecutorTest {

    @Mock
    private ActorAddressBookManager mockActorAddressBookManager;
    @Mock
    private BitcoinWalletManager mockBitcoinWalletManager;
    @Mock
    private BitcoinWalletWallet mockBitcoinWallet;

    private TransactionExecutorFactory testExecutorFactory;
    private TransactionExecutor testExecutor;


    @Test
    public void newTransactionExecutor_PlatformWalletTypeNotSupported_TransactionExecutorCreated() throws Exception{
        when(mockBitcoinWalletManager.loadWallet(any(UUID.class))).thenReturn(mockBitcoinWallet);

        testExecutorFactory = new TransactionExecutorFactory(mockBitcoinWalletManager, mockActorAddressBookManager);
        testExecutor = testExecutorFactory.newTransactionExecutor(ReferenceWallet.COMPOSITE_WALLET_MULTI_ACCOUNT, UUID.randomUUID());
        assertThat(testExecutor).isNull();
    }

    @Test
    public void newTransactionExecutor_WalletRecognizedByManager_TransactionExecutorCreated() throws Exception{
        when(mockBitcoinWalletManager.loadWallet(any(UUID.class))).thenReturn(mockBitcoinWallet);

        testExecutorFactory = new TransactionExecutorFactory(mockBitcoinWalletManager, mockActorAddressBookManager);
        testExecutor = testExecutorFactory.newTransactionExecutor(ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET, UUID.randomUUID());
        assertThat(testExecutor)
                .isNotNull()
                .isInstanceOf(BitcoinBasicWalletTransactionExecutor.class);
    }

    @Test
    public void newTransactionExecutor_WalletNotRecognizedByManager_ThrowsCantLoadWalletException() throws Exception{
        when(mockBitcoinWalletManager.loadWallet(any(UUID.class))).thenThrow(new CantLoadWalletException("MOCK", null, null, null));

        testExecutorFactory = new TransactionExecutorFactory(mockBitcoinWalletManager, mockActorAddressBookManager);
        catchException(testExecutorFactory).newTransactionExecutor(ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET, UUID.randomUUID());

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantLoadWalletException.class);
    }

}
