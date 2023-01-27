package com.systemspecs.remita.easypay.extended.dto;

import lombok.Data;

@Data
public class BalanceEnquiryRawRequest {

    private String targetAccountName;

    private String destinationInstitutionCode;

    private String billerId;

    private String targetBankVerificationNumber;

    private String authorizationCode;

    private String targetAccountNumber;

    private String transactionId;

    private Integer channelCode;

}
