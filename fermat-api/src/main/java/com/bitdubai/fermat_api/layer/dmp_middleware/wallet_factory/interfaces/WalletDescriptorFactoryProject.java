package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import java.util.List;

/**
 * Created by Franklin Marcano 12/08/15.
 */
public interface WalletDescriptorFactoryProject extends DescriptorFactoryProject{
    public List<LanguageDescriptorFactoryProject> getLanguages();
    public List<SkinDescriptorFactoryProject> getSkins();
}
