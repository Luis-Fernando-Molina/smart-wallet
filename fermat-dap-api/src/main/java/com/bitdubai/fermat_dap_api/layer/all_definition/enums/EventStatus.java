package com.bitdubai.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 02/10/15.
 */
public enum EventStatus {
    PENDING("PEND"),
    NOTIFIED("NOTD");

    String code;

    EventStatus(String code){
        this.code=code;
    }

    public String getCode() { return this.code ; }

    public EventStatus getByCode(String code) throws InvalidParameterException {
        switch (code){
            case "PEND":
                return EventStatus.PENDING;
            case "NOTD":
                return EventStatus.NOTIFIED;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the EventStatus enum.");
        }
    }

}
