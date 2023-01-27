package com.systemspecs.remita.easypay.extended.dto;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class FundsTransferDCRawRequest {

    private String transactionId;

    private String nameEnquiryRef;

    private String sourceInstitutionCode;

    private String destinationInstitutionCode;

    private Integer channelCode;

    private String beneficiaryAccountName;

    private String beneficiaryAccountNumber;

    private String beneficiaryBankVerificationNumber;

    private String beneficiaryNarration;

    private Integer beneficiaryKYCLevel;

    private String originatorNarration;

    private String originatorAccountName;

    private String originatorAccountNumber;

    private String originatorBankVerificationNumber;

    private Integer originatorKYCLevel;

    private String transactionLocation;

    private String paymentReference;

    private BigDecimal amount;

    private String mandateReferenceNumber;

    private String billerId;


}
