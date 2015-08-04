package com.bitdubai.fermat_pip_api.layer.pip_identity.translator.interfaces;

import com.bitdubai.fermat_pip_api.layer.pip_identity.translator.exceptions.CantSingMessageException;

/**
 * This interface let you access to the Translator public Information
 *
 * @author natalia on 03/08/15.
 */

public interface Translator {

    /**
     * Get the alias of the represented translator identity
     * @return String Alias
     */
    public String getAlias();

    /**
     * Get the public key of the represented developer
     * @return string pulic key
     */
    public String getPublicKey();

    /**
     * Sign a message with translator private key
     * @param  mensage to sign
     * @return string signed message
     * @throws CantSingMessageException
     */
    public String createMessageSignature(String mensage) throws CantSingMessageException;

}
