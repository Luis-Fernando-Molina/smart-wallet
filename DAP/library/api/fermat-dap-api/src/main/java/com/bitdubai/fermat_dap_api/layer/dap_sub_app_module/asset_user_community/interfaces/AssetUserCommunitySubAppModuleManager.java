package com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

import java.util.List;

/**
 * Created by Nerio on 13/10/15.
 */
public interface AssetUserCommunitySubAppModuleManager extends ModuleManager {

    List<ActorAssetUser> getAllActorAssetUserRegistered() throws CantGetAssetUserActorsException;

    void connectToActorAssetUser(ActorAssetUser actorAssetUser);

//    List<ActorAssetRedeemPoint> getAllActorAssetRedeemPointRegistered() throws CantGetAssetRedeemPointActorsException;
}
