package com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces;


import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.enums.ContactState;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.ActorIntraUser</code>
 * provides the methods to consult the information of an Intra User
 */
public interface ActorIntraUser  {

    /**
     * The metho <code>getPublicKey</code> gives us the public key of the represented intra user
     *
     * @return the public key
     */
    public String getPublicKey();

    /**
     * The method <code>getName</code> gives us the name of the represented intra user
     *
     * @return the name of the intra user
     */
    public String getName();

    /**
     * The method <code>getContactRegistrationDate</code> gives us the date when both intra users
     * exchanged their information and accepted each other as contacts.
     *
     * @return the date
     */
    public long getContactRegistrationDate();

    /**
     * The method <coda>getProfileImage</coda> gives us the profile image of the represented intra user
     *
     * @return the image
     */
    public byte[] getProfileImage();

    /**
     * The method <code>getContactState</code> gives us the contact state of the represented intra
     * user
     *
     * @return the contact state
     */
    public ContactState getContactState();

}
