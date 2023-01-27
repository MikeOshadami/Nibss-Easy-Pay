package com.systemspecs.remita.easypay.extended.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("nibss.easypay")
public class EasypayProperties {

    private Boolean testMode;

    private String institutionCode;

    private String sourceInstitutionCode;

    private String billerId;

    private String mandateReferenceNumber;

    private String originatorAccountName;

    private String originatorAccountNumber;

    private String originatorBankVerificationNumber;

    private Integer originatorKYCLevel;

    private String transactionLocation;

    private String clientId;

    private String clientSecret;

    private String tokenUrl;

    private String balanceEnquiryUrl;

    private String nameEnquiryUrl;

    private String fundsTransferUrl;

    private String tsqUrl;

}
