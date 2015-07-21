package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;

import java.util.UUID;

/**
 * Created by eze on 2015.07.19..
 */
public interface InstalledLanguage {

    /**
     * This method gives us the language package identifier
     *
     * @return the identifier
     */
    public UUID getId();

    /**
     * This method gives us the language of the package
     *
     * @return the language
     */
    public Languages getLanguage();
}
