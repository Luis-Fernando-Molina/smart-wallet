package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InsufficientCryptoFundsException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletBalance;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.IssuingStatus;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingDAO;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/03/16.
 */
public class AssetIssuingTransactionManager implements AssetIssuingManager {

    //VARIABLE DECLARATION
    private final AssetIssuingDAO dao;
    private final BitcoinWalletManager manager;

    //CONSTRUCTORS
    public AssetIssuingTransactionManager(AssetIssuingDAO dao, BitcoinWalletManager manager) {
        this.dao = dao;
        this.manager = manager;
    }

    //PUBLIC METHODS

    /**
     * This method will start the issuing, generating the needed
     * amount of digital asset metadata.
     *
     * @param digitalAssetToIssue   The asset which we want to create
     * @param assetsAmount          The amount of asset that we are willing to create
     * @param issuerWalletPk        The issuer wallet public key where the assets will go.
     * @param btcWalletPublicKey    The btc wallet public key where we'll take the bitcoins from.
     * @param blockchainNetworkType The kind of network where this asset will be created.
     * @throws CantIssueDigitalAssetsException In case something went wrong.
     */
    @Override
    public void issueAssets(DigitalAsset digitalAssetToIssue, int assetsAmount, String issuerWalletPk, String btcWalletPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantIssueDigitalAssetsException, InsufficientCryptoFundsException {
        try {
            BitcoinWalletBalance balance = manager.loadWallet(btcWalletPublicKey).getBalance(BalanceType.AVAILABLE);
            long totalAmount = digitalAssetToIssue.getGenesisAmount() * assetsAmount;
            if (balance.getBalance() < totalAmount) {
                throw new InsufficientCryptoFundsException(null, null, null, null);
            }
            dao.startIssuing(digitalAssetToIssue, assetsAmount, blockchainNetworkType, btcWalletPublicKey, issuerWalletPk);
        } catch (Exception e) {
            throw new CantIssueDigitalAssetsException(e);
        }
    }

    /**
     * This method search the number of issued assets by the time it is requested.
     *
     * @param assetPublicKey The public key of the asset that we are issuing
     * @return the number of already issued assets
     * @throws CantExecuteDatabaseOperationException
     */
    @Override
    public int getNumberOfIssuedAssets(String assetPublicKey) throws CantExecuteDatabaseOperationException {
        try {
            return dao.getRecordForAsset(assetPublicKey).getAssetsGenerated();
        } catch (RecordsNotFoundException e) {
            return 0;
        } catch (CantLoadTableToMemoryException | InvalidParameterException | CantGetDigitalAssetFromLocalStorageException e) {
            throw new CantExecuteDatabaseOperationException(e);
        }
    }

    /**
     * The issuing status for the process of publishing this asset.
     *
     * @param assetPublicKey The public key of the asset that we are issuing
     * @return The actual issuing status for the issuing process.
     * @throws CantExecuteDatabaseOperationException
     */
    @Override
    public IssuingStatus getIssuingStatus(String assetPublicKey) throws CantExecuteDatabaseOperationException {
        try {
            return dao.getRecordForAsset(assetPublicKey).getStatus();
        } catch (RecordsNotFoundException e) {
            return IssuingStatus.NOT_PUBLISHED;
        } catch (CantLoadTableToMemoryException | InvalidParameterException | CantGetDigitalAssetFromLocalStorageException e) {
            throw new CantExecuteDatabaseOperationException(e);
        }
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}