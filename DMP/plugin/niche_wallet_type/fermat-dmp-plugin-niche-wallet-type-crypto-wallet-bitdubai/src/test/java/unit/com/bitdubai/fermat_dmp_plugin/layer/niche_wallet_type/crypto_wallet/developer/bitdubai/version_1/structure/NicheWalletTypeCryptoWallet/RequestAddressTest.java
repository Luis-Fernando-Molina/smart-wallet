package unit.com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.structure.NicheWalletTypeCryptoWallet;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_actor.Actor;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantRequestCryptoAddressException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.ExtraUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantRegisterActorAddressBookException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRegistry;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantRegisterWalletAddressBookException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookRegistry;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.structure.NicheWalletTypeCryptoWallet;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class RequestAddressTest extends TestCase {

    /**
     * DealsWithActorAddressBook interface Mocked
     */
    @Mock
    ActorAddressBookManager actorAddressBookManager;

    /**
     * DealsWithExtraUserManager interface Mocked
     */
    @Mock
    CryptoVaultManager cryptoVaultManager;

    /**
     * DealsWithErrors interface Mocked
     */
    @Mock
    ErrorManager errorManager;

    /**
     * DealsWithExtraUserManager interface Mocked
     */
    @Mock
    ExtraUserManager extraUserManager;

    /**
     * DealsWithWalletAddressBook interface Mocked
     */
    @Mock
    WalletAddressBookManager walletAddressBookManager;

    /**
     * DealsWithWalletContacts interface Mocked
     */
    @Mock
    WalletContactsManager walletContactsManager;

    @Mock
    ActorAddressBookRegistry actorAddressBookRegistry;

    @Mock
    WalletAddressBookRegistry walletAddressBookRegistry;

    @Mock
    Actor user;


    @Mock
    CryptoAddress cryptoAddress;

    String actorName;
    UUID actorId;
    Actors actorType;
    ReferenceWallet referenceWallet;
    UUID walletId;

    NicheWalletTypeCryptoWallet nicheWalletTypeCryptoWallet;

    @Before
    public void setUp() throws Exception {
        actorName = "Ricardo Darin";
        actorId = UUID.randomUUID();
        actorType = Actors.EXTRA_USER;
        referenceWallet = ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET;
        walletId = UUID.randomUUID();
        nicheWalletTypeCryptoWallet = new NicheWalletTypeCryptoWallet();
        nicheWalletTypeCryptoWallet.setActorAddressBookManager(actorAddressBookManager);
        nicheWalletTypeCryptoWallet.setErrorManager(errorManager);
        nicheWalletTypeCryptoWallet.setExtraUserManager(extraUserManager);
        nicheWalletTypeCryptoWallet.setWalletAddressBookManager(walletAddressBookManager);
        nicheWalletTypeCryptoWallet.setWalletContactsManager(walletContactsManager);
        nicheWalletTypeCryptoWallet.setCryptoVaultManager(cryptoVaultManager);
        doReturn(actorAddressBookRegistry).when(actorAddressBookManager).getActorAddressBookRegistry();
        doReturn(walletAddressBookRegistry).when(walletAddressBookManager).getWalletAddressBookRegistry();
        doReturn(user).when(extraUserManager).createActor(anyString());
        doReturn(cryptoAddress).when(cryptoVaultManager).getAddress();
        nicheWalletTypeCryptoWallet.initialize();
    }

    @Test
    public void testRequestAddress_NotNull() throws Exception {
        CryptoAddress cryptoAddress = nicheWalletTypeCryptoWallet.requestAddress(actorId, actorType, actorId, actorType, referenceWallet, walletId);
        assertNotNull(cryptoAddress);
    }

    // TYPE OF WALLET NOT RECOGNIZED BY THE PLUGIN
    @Test(expected=CantRequestCryptoAddressException.class)
    public void testRequestAddress_PlatformWalletTypeNotRecognized() throws Exception {
        referenceWallet = ReferenceWallet.BASIC_WALLET_DISCOUNT_WALLET;

        nicheWalletTypeCryptoWallet.requestAddress(actorId, actorType, actorId, actorType, referenceWallet, walletId);
    }

    /**
     * TODO: CANT GET ADDRESS TEST / CRYPTO VAULT EXCEPTION DOESN'T EXIST
     */

    // TYPE OF ACTOR NOT RECOGNIZED BY THE PLUGIN
    @Ignore
    @Test(expected=CantRequestCryptoAddressException.class)
    public void testRequestAddress_ActorTypeNotRecognized() throws Exception {
        actorType = Actors.INTRA_USER;

        nicheWalletTypeCryptoWallet.requestAddress(actorId, actorType, actorId, actorType, referenceWallet, walletId);
    }

    /**
     * TODO: CANT CREATE USER TEST / EXTRA USER EXCEPTION DOESN'T EXIST
     */


    // CANT REGISTER ACTOR ADDRESS BOOK TEST
    @Test(expected=CantRequestCryptoAddressException.class)
    public void testRequestAddress_CantRegisterActorAddressBookException() throws Exception {
        doThrow(new CantRegisterActorAddressBookException("gasdil", null, null, null))
                .when(actorAddressBookRegistry).registerActorAddressBook(any(UUID.class), any(Actors.class),any(UUID.class), any(Actors.class), any(CryptoAddress.class));

        nicheWalletTypeCryptoWallet.requestAddress(actorId, actorType, actorId, actorType, referenceWallet, walletId);
    }

    // CANT REGISTER WALLET ADDRESS BOOK TEST
    @Test(expected=CantRequestCryptoAddressException.class)
    public void testRequestAddress_CantRequestOrRegisterCryptoAddressException() throws Exception {
        doThrow(new CantRegisterWalletAddressBookException("gasdil", null, null, null))
                .when(walletAddressBookRegistry).registerWalletCryptoAddressBook(any(CryptoAddress.class), any(ReferenceWallet.class), any(UUID.class));

        nicheWalletTypeCryptoWallet.requestAddress(actorId, actorType, actorId, actorType, referenceWallet, walletId);
    }
}
