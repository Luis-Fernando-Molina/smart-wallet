package com.bitdubai.android_core.app.common.version_1.Sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;

import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */

public class WalletSessionManager implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSessionManager{

    private Map<String,WalletSession> lstWalletSession;

    public WalletSessionManager(){
        lstWalletSession= new HashMap<String,WalletSession>();
    }

    @Override
    public Map<String,WalletSession> listOpenWallets() {
        return lstWalletSession;
    }


    @Override
    public WalletSession openWalletSession(InstalledWallet installedWallet,CryptoWalletManager cryptoWalletManager,ErrorManager errorManager) {

        switch (installedWallet.getWalletCategory()){
            case REFERENCE_WALLET:
                WalletSession walletSession= new ReferenceWalletSession(installedWallet,cryptoWalletManager,errorManager);
                lstWalletSession.put(installedWallet.getWalletPublicKey(),walletSession);
                return walletSession;
            case NICHE_WALLET:
                break;
            case BRANDED_NICHE_WALLET:
                break;
            case BRANDED_REFERENCE_WALLET:
                break;
        }



        return null;
    }

    @Override
    public boolean closeWalletSession(String publicKey) {
        try {
            lstWalletSession.remove(publicKey);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean isWalletOpen(String publicKey) {
        return lstWalletSession.containsKey(publicKey);
    }

    @Override
    public WalletSession getWalletSession(String publicKey) {
        return lstWalletSession.get(publicKey);
    }

}
