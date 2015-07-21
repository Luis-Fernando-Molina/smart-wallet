package com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransaction;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletTransaction</code>
 * TODO WRITE DETAILS
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 11/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface CryptoWalletTransaction {

    BitcoinWalletTransaction getBitcoinWalletTransaction();

    String getInvolvedActorName();

}
