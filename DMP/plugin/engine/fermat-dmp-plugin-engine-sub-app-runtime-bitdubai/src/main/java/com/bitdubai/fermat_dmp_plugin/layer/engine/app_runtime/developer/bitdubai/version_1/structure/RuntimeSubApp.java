package com.bitdubai.fermat_dmp_plugin.layer.engine.app_runtime.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.LanguagePackage;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wallet;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by ciencias on 2/14/15.
 */
public class RuntimeSubApp implements SubApp {

    SubApps type;
    Map<Activities, Activity> activities = new  HashMap<Activities, Activity>();
    Map<Wallets, Wallet> wallets= new  HashMap<Wallets, Wallet>();
    Map<String,LanguagePackage> languagePackages = new HashMap<String,LanguagePackage>();


    /**
     * RuntimeSubApp interface implementation.
     */
    public void setType(SubApps type) {
        this.type = type;
    }
    
    public void addActivity (Activity activity){
        activities.put(activity.getType(), activity);
    }

    public void addWallet (Wallet wallet){
        wallets.put(wallet.getType(), wallet);
    }

    public void addLanguagePackage (LanguagePackage languagePackage){
        languagePackages.put(languagePackage.getName(),languagePackage);
    }

    /**
     * SubApp interface implementation.
     */

    @Override
    public SubApps getType() {
        return type;
    }

    @Override
    public Map<Activities, Activity> getActivities(){
        return activities;
    }

    @Override
    public Map<Wallets, Wallet> getWallets(){
        return wallets;
    }

    @Override
    public Map<String,LanguagePackage> getLanguagePackages(){
        return languagePackages;
    }

}
