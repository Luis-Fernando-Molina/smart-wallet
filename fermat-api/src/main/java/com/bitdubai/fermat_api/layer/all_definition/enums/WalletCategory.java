package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 2015.07.19..
 */
public enum WalletCategory {
    BRANDED_NICHE_WALLET ("BRDNW"),
    BRANDED_REFERENCE_WALLET ("BRDRW"),
    REFERENCE_WALLET ("REFW"),
    NICHE_WALLET ("NCHW");

    private String code;

    WalletCategory(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }

    public static WalletCategory getByCode(String code) throws InvalidParameterException{
        switch (code) {
            case "BRDNW": return WalletCategory.BRANDED_NICHE_WALLET;
            case "BRDRW": return WalletCategory.BRANDED_REFERENCE_WALLET;
            case "REFW": return WalletCategory.REFERENCE_WALLET;
            case "NCHW": return WalletCategory.NICHE_WALLET;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the WalletCategory enum");
        }
    }
}
