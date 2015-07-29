package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;



import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.adapters.ActivitiesMapAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.adapters.WalletsAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;

import java.util.HashMap;
import java.util.Map;

import ae.javax.xml.bind.annotation.XmlAttribute;
import ae.javax.xml.bind.annotation.XmlElement;
import ae.javax.xml.bind.annotation.XmlRootElement;
import ae.javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by Matias Furszyfer on 2015.07.23..
 */

@XmlRootElement(name = "navigationStructure")
public class Wallet implements com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatWallet{

    /**
     * Wallet identifiers
     */

    private String publicKey;


    /**
     * Screens in a Wallet
     */

    private Map<Activities, Activity> activities = new HashMap<Activities, Activity>();

    /**
     * Main screen of the wallet
     */

    private Activities startActivity;

    /**
     * Last active screen
     */

    private Activities lastActivity;


    /**
     * Wallet constructor
     */
    public Wallet() {
    }

    /**
     *
     * @param activities
     */
    public Wallet( Map<Activities, Activity> activities) {
        this.activities = activities;
    }




    /**
     * Wallet interface implementation.
     */
    @XmlJavaTypeAdapter(WalletsAdapter.class)
    @XmlAttribute(name = "wallettype", required = true)
    @Override
    public String getPublicKey() {
        return publicKey;
    }


    /**
     * RuntimeWallet interface implementation.
     */
    @Override
    public Activity getActivity(Activities activities) {
        this.lastActivity=activities;
        return this.activities.get(activities);
    }

    /**
     *  Get main screen
     *
     * @return Activity
     */
    @Override
    public Activity getStartActivity() {
        return activities.get(startActivity);
    }

    /**
     *  Set the main screen
     *
     * @param activity
     */
    @Override
    public void setStartActivity(Activities activity) {
        this.startActivity=activity;
    }

    /**
     *  Get the last Screen used
     * @return Activity
     */
    @Override
    public Activity getLastActivity() {
        if (lastActivity == null) {
            return activities.get(startActivity);
        }
        return activities.get(lastActivity);
    }

    @Override
    public void setPublicKey(String publicKey) {
        this.publicKey=publicKey;
    }

    /**
     *  Screens in a Wallet
     *
     * @return Map<Activities, Activity>
     */
    @XmlJavaTypeAdapter(ActivitiesMapAdapter.class)
    @XmlElement(name = "activities")
    public Map<Activities, Activity> getActivities() {
        return activities;
    }

    public void setActivities(Map<Activities, Activity> activities) {
        this.activities = activities;
    }

    /**
     *  Add Screen to Wallet
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activities.put(activity.getType(), activity);
    }


}
