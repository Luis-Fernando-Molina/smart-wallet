package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/08/15.
 */
public interface AssetIssuingManager /*extends TransactionProtocolManager<CryptoTransaction>*/ {

    /*void createDigitalAsset(String publicKey, String name,
                            String description,
                            List<Resource> resources,
                            DigitalAssetContract contract,
                            long genesisAmount) throws CantCreateDigitalAssetTransactionException;*/
    /**
     * This method will create and deliver digital assets according to the amount indicated by the assetsAmount argument.
     * The process includes creating the transaction and send the crypto genesisAmount from the bitcoin wallet to wallet asset.
     * */
    void issueAssets(DigitalAsset digitalAssetToIssue,
                     int assetsAmount,
                     String walletPublicKey,
                     BlockchainNetworkType blockchainNetworkType) throws CantIssueDigitalAssetsException;
    void issuePendingDigitalAssets(String assetPublicKey);
    void setCryptoWallet(CryptoWallet cryptoWallet);
    /**
     * This method must be used from the Asset Wallet to confirm the DigitalAssetMetadata reception.
     * @param genesisTransaction is a DigitalAssetMetadata parameter.
     * @throws CantConfirmTransactionException
     */
    void confirmReception(String genesisTransaction)throws CantConfirmTransactionException;
    /*void setActors(String deliveredByActorPublicKey,
                        Actors deliveredByType,
                        String deliveredToActorPublicKey,
                        Actors deliveredToType) throws CantSetObjectException;
    void setWallet(String walletPublicKey, ReferenceWallet walletType) throws CantSetObjectException;*/

}
