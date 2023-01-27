package com.systemspecs.remita.easypay.extended.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceEnquiryResponse {

    private String sessionID;

    private String transactionId;

    private String destinationInstitutionCode;

    private Integer channelCode;

    private String targetAccountNumber;

    private String targetAccountName;

    private String targetBankVerificationNumber;

    private String authorizationCode;

    private BigDecimal availableBalance;

    private String responseCode;

}
