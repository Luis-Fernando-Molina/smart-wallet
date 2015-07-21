package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledSkin;

import java.util.UUID;

/**
 * Created by natalia on 21/07/15.
 */
public class WalletManagerInstalledSkin implements InstalledSkin {

    private String alias;
    private String preview;
    private UUID id;


    /**
     * InstalledSkin Interface implementation.
     */

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public String getPreview() {
        return this.preview;
    }

    @Override
    public UUID getId() {
        return this.id;
    }
}
