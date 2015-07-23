package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledSkin;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledWallet;

import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 21/07/15.
 */
public class WalletManagerInstalledWallet implements InstalledWallet {

    private WalletCategory walletCategory;
    private List<InstalledSkin> skinsId;
    private List<InstalledLanguage> languajesId;
    private String walletIcon;
    private UUID deciveId;
    private String walletName;

    /**
     * InstalledWallet Interface implementation.
     */

    @Override
    public List<InstalledLanguage> getLanguagesId(){

        return this.languajesId;
    }

    @Override
    public List<InstalledSkin> getSkinsId(){
        return this.skinsId;

    }

    @Override
    public WalletCategory getWalletCategory(){
        return this.walletCategory;

    }

    @Override
    public String getWalletPlatformIdentifier() {
        return null;
    }

    @Override
    public String getWalletIcon(){

        return this.walletIcon;
    }

    @Override
    public String getWalletPublicKey() {
        return null;
    }


    @Override
    public String getWalletName(){
        return this.walletName;

    }

    @Override
    public Version getWalletVersion() {
        return null;
    }

}
