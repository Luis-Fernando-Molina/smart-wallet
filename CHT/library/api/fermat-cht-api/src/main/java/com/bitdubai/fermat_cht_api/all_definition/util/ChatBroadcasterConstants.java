package com.bitdubai.fermat_cht_api.all_definition.util;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;

import java.util.List;

/**
 * Created by Franklin Marcano on 05/05/2016.
 */
public interface ChatBroadcasterConstants {

    int CHAT_NEW_INCOMING_MESSAGE_NOTIFICATION = 9001;

    String CHAT_UPDATE_VIEW = "CHAT_UPDATE_VIEW";
    String CHAT_LIST_UPDATE_VIEW = "CHAT_LIST_UPDATE_VIEW";
    String CHAT_NEW_INCOMING_MESSAGE = "CHAT_NEW_INCOMING_MESSAGE";

    String CHAT_COMM_ACTOR_RECEIVED = "CHAT_COMM_ACTOR_RECEIVED";
    String CHAT_COMM_ACTOR_LIST = "CHAT_COMM_ACTOR_LIST";


    int CHAT_COMMUNITY_REQUEST_CONNECTION_NOTIFICATION = 9002;
    int CHAT_COMMUNITY_CONNECTION_ACCEPTED_NOTIFICATION = 9003;

    String COMMUNITY_REQUEST_CONNECTION = "COMMUNITY_REQUEST_CONNECTION";
    String COMMUNITY_CONNECTION_ACCEPTED = "COMMUNITY_CONNECTION_ACCEPTED";


}
