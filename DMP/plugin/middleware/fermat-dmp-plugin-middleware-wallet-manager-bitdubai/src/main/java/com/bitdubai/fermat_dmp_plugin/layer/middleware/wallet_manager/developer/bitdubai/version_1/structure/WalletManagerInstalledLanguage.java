package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledLanguage;

import java.util.UUID;

/**
 * Created by natalia on 21/07/15.
 */
public class WalletManagerInstalledLanguage implements InstalledLanguage {


    private Languages languages;
    private UUID id;

    /**
     * InstalledLanguage Interface implementation.
     */

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public Languages getLanguage() {
        return this.languages;
    }

    @Override
    public String getLabel() {
        return null;
    }

    @Override
    public Version getVersion() {
        return null;
    }
}
