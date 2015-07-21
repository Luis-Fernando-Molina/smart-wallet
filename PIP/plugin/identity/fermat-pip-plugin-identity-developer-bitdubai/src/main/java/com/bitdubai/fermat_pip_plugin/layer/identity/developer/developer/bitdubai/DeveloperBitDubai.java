package com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;
import com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.DeveloperIdentityPluginRoot;

/**
 * I will choose from the different versions of my implementations which one to start. Now there is only one, so
 * it is easy to choose.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DeveloperBitDubai implements PluginDeveloper, PluginLicensor {

    Plugin plugin;

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    public DeveloperBitDubai () {
        //TODO: Comentado por error al iniciar la APP
      //  plugin = new DeveloperIdentityPluginRoot();
    }

    @Override
    public int getAmountToPay() {
        return 0;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return null;
    }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public TimeFrequency getTimePeriod() {
        return null;
    }
}
