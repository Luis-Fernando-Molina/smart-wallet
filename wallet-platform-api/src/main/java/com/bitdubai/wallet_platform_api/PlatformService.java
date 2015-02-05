package com.bitdubai.wallet_platform_api;

import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;

/**
 * Created by ciencias on 30.12.14.
 */
public interface PlatformService {

    public void start();

    public void pause();
    
    public void resume();

    public void stop();

    public ServiceStatus getStatus();

}
