package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/13/15.
 */
public enum Plugins {

    //Modified by Manuel Perez on 03/08/2015
    BITDUBAI_LICENSE_MANAGER("BLICM", Developers.BITDUBAI),
    BITDUBAI_BLOCKCHAIN_INFO_WORLD("BBLOCKIW", Developers.BITDUBAI),
    BITDUBAI_SHAPE_SHIFT_WORLD("BSHAPESW", Developers.BITDUBAI),
    BITDUBAI_COINAPULT_WORLD("BCOINAW", Developers.BITDUBAI),
    BITDUBAI_CRYPTO_INDEX("BCRYPTOINW", Developers.BITDUBAI),
    BITDUBAI_BITCOIN_CRYPTO_NETWORK("BBTCCNET", Developers.BITDUBAI),
    BITDUBAI_CLOUD_CHANNEL("BCLOUDC", Developers.BITDUBAI),
    BITDUBAI_CLOUD_SERVER_COMMUNICATION("BCLOUSC", Developers.BITDUBAI),
    BITDUBAI_USER_NETWORK_SERVICE("BUSERNETS", Developers.BITDUBAI),
    BITDUBAI_TEMPLATE_NETWORK_SERVICE("BTEMNETS", Developers.BITDUBAI),
    BITDUBAI_INTRAUSER_NETWORK_SERVICE("BINUSERNS", Developers.BITDUBAI),
    BITDUBAI_APP_RUNTIME_MIDDLEWARE("BAPPRUNM", Developers.BITDUBAI),
    BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET("BDWALLBW", Developers.BITDUBAI),
    BITDUBAI_WALLET_RUNTIME_MODULE("BWALLRUNM", Developers.BITDUBAI),
    BITDUBAI_WALLET_MANAGER_MODULE("BWALLMANM", Developers.BITDUBAI),
    BITDUBAI_WALLET_FACTORY_MODULE("BWALLFACM", Developers.BITDUBAI),
    BITDUBAI_BITCOIN_CRYPTO_VAULT("BBTCCRYV", Developers.BITDUBAI),
    BITDUBAI_INTRA_USER_FACTORY_MODULE("BINUSFACM", Developers.BITDUBAI),
    BITDUBAI_BANK_NOTES_WALLET_NICHE_WALLET_TYPE("BBNWNWT", Developers.BITDUBAI),
    BITDUBAI_CRYPTO_LOSS_PROTECTED_WALLET_NICHE_WALLET_TYPE("BCLPWNWT", Developers.BITDUBAI),
    BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE("BCWNWT", Developers.BITDUBAI),
    BITDUBAI_DISCOUNT_WALLET_NICHE_WALLET_TYPE("BDWNWT", Developers.BITDUBAI),
    BITDUBAI_FIAT_OVER_CRYPTO_LOSS_PROTECTED_WALLET_NICHE_WALLET_TYPE("BFOCLPWNWT", Developers.BITDUBAI),
    BITDUBAI_FIAT_OVER_CRYPTO_WALLET_NICHE_WALLET_TYPE("BFOCWNWT", Developers.BITDUBAI),
    BITDUBAI_MULTI_ACCOUNT_WALLET_NICHE_WALLET_TYPE("BMAWNWT", Developers.BITDUBAI),
    BITDUBAI_INCOMING_INTRA_USER_TRANSACTION("BININUST", Developers.BITDUBAI),
    BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION("BOUINUST", Developers.BITDUBAI),
    BITDUBAI_INCOMING_DEVICE_USER_TRANSACTION("BINDEVUT", Developers.BITDUBAI),
    BITDUBAI_OUTGOING_DEVICE_USER_TRANSACTION("BODEVUST", Developers.BITDUBAI),
    BITDUBAI_INTER_WALLET_TRANSACTION("BINWALLT", Developers.BITDUBAI),
    BITDUBAI_BANK_NOTES_MIDDLEWARE("BBNMIDD", Developers.BITDUBAI),
    BITDUBAI_BANK_NOTES_NETWORK_SERVICE("BBNNETSER", Developers.BITDUBAI),
    BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE("BWRNETSER", Developers.BITDUBAI),
    BITDUBAI_WALLET_STORE_NETWORK_SERVICE("BWSNETSER", Developers.BITDUBAI),
    BITDUBAI_WALLET_CONTACTS_MIDDLEWARE("BWALLCMIDD", Developers.BITDUBAI),
    BITDUBAI_WALLET_COMMUNITY_NETWORK_SERVICE("BWCNETSER", Developers.BITDUBAI),
    BITDUBAI_USER_ADDRESS_BOOK_CRYPTO("BUADDBCRY", Developers.BITDUBAI),
    BITDUBAI_WALLET_ADDRESS_BOOK_CRYPTO("BWADDBCRY", Developers.BITDUBAI),
    BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION("BOUEXUT", Developers.BITDUBAI),
    BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION("BINEXUT", Developers.BITDUBAI),
    BITDUBAI_INCOMING_CRYPTO_TRANSACTION("BINCRYT", Developers.BITDUBAI),
    BITDUBAI_USER_DEVICE_USER("BUDEVU", Developers.BITDUBAI),
    BITDUBAI_USER_EXTRA_USER("BUEXU", Developers.BITDUBAI),
    BITDUBAI_USER_INTRA_USER("BUINU", Developers.BITDUBAI),
    BITDUBAI_COINBASE_WORLD("BCOINW", Developers.BITDUBAI),
    BITDUBAI_BITCOIN_WALLET_BASIC_WALLET("BBTCWBW", Developers.BITDUBAI ),
    BITDUBAI_DEVICE_CONNECTIVITY("BBTCDEVC", Developers.BITDUBAI ),
    BITDUBAI_LOCATION_WORLD("BLOCW", Developers.BITDUBAI),
    BITDUBAI_ACTOR_DEVELOPER("BACTORD", Developers.BITDUBAI),
    BITDUBAI_WALLET_FACTORY_MIDDLEWARE("BWFMIDD", Developers.BITDUBAI),
    BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE("BWLMIDD", Developers.BITDUBAI),
    BITDUBAI_WALLET_MANAGER_MIDDLEWARE("BWMMIDD", Developers.BITDUBAI),
    BITDUBAI_WALLET_PUBLISHER_MIDDLEWARE("BWPMIDD", Developers.BITDUBAI),
    BITDUBAI_WALLET_SKIN_MIDDLEWARE("BWSMIDD", Developers.BITDUBAI),
    BITDUBAI_WALLET_STORE_MIDDLEWARE("BWSTMIDD", Developers.BITDUBAI),
    BITDUBAI_WALLET_STORE_MODULE("BWSM", Developers.BITDUBAI),
    BITDUBAI_WALLET_SETTINGS_MIDDLEWARE("BWSEMIDD", Developers.BITDUBAI),
    BITDUBAI_WALLET_STATISTICS_NETWORK_SERVICE("BWSNETSER", Developers.BITDUBAI),
    BITDUBAI_SUBAPP_RESOURCES_NETWORK_SERVICE("BSRNETSER", Developers.BITDUBAI),
    BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE("BCTNSER", Developers.BITDUBAI),
    BITDUBAI_REQUEST_MONEY_REQUEST("BRMR", Developers.BITDUBAI),

    BITDUBAI_DEVELOPER_IDENTITY("BDEVID", Developers.BITDUBAI),
    BITDUBAI_TRANSLATOR_IDENTITY("BDTRAID", Developers.BITDUBAI),
    BITDUBAI_IDENTITY_MANAGER("BIDMAN", Developers.BITDUBAI),
    BITDUBAI_DEVELOPER_MODULE("BDEVMOD", Developers.BITDUBAI);


    private String code;
    private Developers developer;

    Plugins(String code, Developers developer) {
        this.code = code;
        this.developer = developer;
    }

    public String getKey()   { return this.code; }

    public static Plugins getByKey(String code) throws InvalidParameterException {
        switch(code){
            case "BLICM":
                return Plugins.BITDUBAI_LICENSE_MANAGER;
            case "BBLOCKIW":
                return Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD;
            case "BSHAPESW":
                return Plugins.BITDUBAI_SHAPE_SHIFT_WORLD;
            case "BCOINAW":
                return Plugins.BITDUBAI_COINAPULT_WORLD;
            case "BCRYPTOINW":
                return Plugins.BITDUBAI_CRYPTO_INDEX;
            case "BBTCCNET":
                return Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK;
            case "BCLOUDC":
                return Plugins.BITDUBAI_CLOUD_CHANNEL;
            case "BCLOUSC":
                return Plugins.BITDUBAI_CLOUD_SERVER_COMMUNICATION;
            case "BUSERNETS":
                return Plugins.BITDUBAI_USER_NETWORK_SERVICE;
            case "BAPPRUNM":
                return Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE;
            case "BDWALLBW":
                return Plugins.BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET;
            case "BWALLRUNM":
                return Plugins.BITDUBAI_WALLET_RUNTIME_MODULE;
            case "BWALLMANM":
                return Plugins.BITDUBAI_WALLET_MANAGER_MODULE;
            case "BWALLFACM":
                return Plugins.BITDUBAI_WALLET_FACTORY_MODULE;
            case "BBTCCRYV":
                return Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT;
            case "BBNWNWT":
                return Plugins.BITDUBAI_BANK_NOTES_WALLET_NICHE_WALLET_TYPE;
            case "BCLPWNWT":
                return Plugins.BITDUBAI_CRYPTO_LOSS_PROTECTED_WALLET_NICHE_WALLET_TYPE;
            case "BCWNWT":
                return Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE;
            case "BDWNWT":
                return Plugins.BITDUBAI_DISCOUNT_WALLET_NICHE_WALLET_TYPE;
            case "BFOCLPWNWT":
                return Plugins.BITDUBAI_FIAT_OVER_CRYPTO_LOSS_PROTECTED_WALLET_NICHE_WALLET_TYPE;
            case "BFOCWNWT":
                return Plugins.BITDUBAI_FIAT_OVER_CRYPTO_WALLET_NICHE_WALLET_TYPE;
            case "BMAWNWT":
                return Plugins.BITDUBAI_MULTI_ACCOUNT_WALLET_NICHE_WALLET_TYPE;
            case "BININUST":
                return Plugins.BITDUBAI_INCOMING_INTRA_USER_TRANSACTION;
            case "BOUINUST":
                return Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION;
            case "BINDEVUT":
                return Plugins.BITDUBAI_INCOMING_DEVICE_USER_TRANSACTION;
            case "BODEVUST":
                return Plugins.BITDUBAI_OUTGOING_DEVICE_USER_TRANSACTION;
            case "BINWALLT":
                return Plugins.BITDUBAI_INTER_WALLET_TRANSACTION;
            case "BBNMIDD":
                return Plugins.BITDUBAI_BANK_NOTES_MIDDLEWARE;
            case "BBNNETSER":
                return Plugins.BITDUBAI_BANK_NOTES_NETWORK_SERVICE;
            case "BWRNETSER":
                return Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE;
            case "BWSNETSER":
                return Plugins.BITDUBAI_WALLET_STORE_NETWORK_SERVICE;
            case "BWSMIDD":
                return Plugins.BITDUBAI_WALLET_STORE_MIDDLEWARE;
            case "BWFMIDD":
                return Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE;
            case "BWLMIDD":
                return Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE;
            case "BWSTMIDD":
                return Plugins.BITDUBAI_WALLET_SKIN_MIDDLEWARE;
            case "BWMMIDD":
                return Plugins.BITDUBAI_WALLET_MANAGER_MIDDLEWARE;
            case "BWPMIDD":
                return Plugins.BITDUBAI_WALLET_PUBLISHER_MIDDLEWARE;
            case "BWALLCMIDD":
                return Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE;
            case "BWCNETSER":
                return Plugins.BITDUBAI_WALLET_COMMUNITY_NETWORK_SERVICE;
            case "BUADDBCRY":
                return Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO;
            case "BWADDBCRY":
                return Plugins.BITDUBAI_WALLET_ADDRESS_BOOK_CRYPTO;
            case "BOUEXUT":
                return Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION;
            case "BINEXUT":
                return Plugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION;
            case "BINCRYT":
                return Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION;
            case "BUDEVU":
                return Plugins.BITDUBAI_USER_DEVICE_USER;
            case "BUEXU":
                return Plugins.BITDUBAI_USER_EXTRA_USER;
            case "BUINU":
                return Plugins.BITDUBAI_USER_INTRA_USER;
            case "BCOINW":
                return Plugins.BITDUBAI_COINBASE_WORLD;
            case "BBTCWBW":
                return Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET;
            case "BBTCDEVC":
                return Plugins.BITDUBAI_DEVICE_CONNECTIVITY;
            case "BLOCW":
                return Plugins.BITDUBAI_LOCATION_WORLD;
            case "BACTORD":
                return Plugins.BITDUBAI_ACTOR_DEVELOPER;
            case "BIDMAN":
                return Plugins.BITDUBAI_IDENTITY_MANAGER;
            case "BWSM":
                return Plugins.BITDUBAI_WALLET_STORE_MODULE;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the Plugins enum");
        }
    }

    public Developers getDeveloper()   { return this.developer; }


}
