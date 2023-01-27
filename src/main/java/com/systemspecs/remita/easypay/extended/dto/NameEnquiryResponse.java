package com.systemspecs.remita.easypay.extended.dto;

import lombok.Data;

@Data
public class NameEnquiryResponse {

    private String sessionID;

    private String transactionId;

    private String destinationInstitutionCode;

    private Integer channelCode;

    private String accountNumber;

    private String accountName;

    private String bankVerificationNumber;

    private Integer kycLevel;

    private String responseCode;

}
