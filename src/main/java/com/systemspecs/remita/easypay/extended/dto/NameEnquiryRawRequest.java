package com.systemspecs.remita.easypay.extended.dto;

import lombok.Data;

@Data
public class NameEnquiryRawRequest {

    private String transactionId;

    private String destinationInstitutionCode;

    private Integer channelCode;

    private String accountNumber;

}
