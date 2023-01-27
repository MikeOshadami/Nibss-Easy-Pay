package com.systemspecs.remita.easypay.extended.dto;

import lombok.Data;

@Data
public class TSQResponse {

    private String sessionID;

    private String transactionId;

    private String sourceInstitutionCode;

    private String channelCode;

    private String responseCode;

}
