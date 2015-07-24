package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.NicheWallet;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetLanguageException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetLanguagesException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetSkinException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetSkinsException;

import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 7/17/15.
 */
public interface DetailedCatalogItem {

    /**
     * Language associated with this catalog item
     */
    public Language getDefaultLanguage() throws CantGetLanguageException;
    public List<Language> getLanguages() throws CantGetLanguagesException;

    /**
     * Skins associated with this catalog item
     */
    public Skin getDefaultSkin() throws CantGetSkinException;
    public List<Skin> getSkins() throws CantGetSkinsException;


    /**
     * Wallet version information, current and initial and final version of the platform on which this wallet will run.
     */
    public Version getVersion();
    public Version getPlatformInitialVersion();
    public Version getPlatformFinalVersion();
}
