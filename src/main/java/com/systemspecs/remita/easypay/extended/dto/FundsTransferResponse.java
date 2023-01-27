package com.systemspecs.remita.easypay.extended.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FundsTransferResponse {

    private String sessionID;

    private String transactionId;

    private String nameEnquiryRef;

    private String destinationInstitutionCode;

    private Integer channelCode;

    private String beneficiaryAccountName;

    private String beneficiaryAccountNumber;

    private String beneficiaryBankVerificationNumber;

    private String beneficiaryKYCLevel;

    private String originatorAccountName;

    private String originatorAccountNumber;

    private String originatorBankVerificationNumber;

    private String originatorKYCLevel;

    private String transactionLocation;

    private String narration;

    private String paymentReference;

    private BigDecimal amount;

    private String responseCode;

}
