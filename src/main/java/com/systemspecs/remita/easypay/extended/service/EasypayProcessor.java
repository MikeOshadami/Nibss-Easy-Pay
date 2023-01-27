package com.systemspecs.remita.easypay.extended.service;

import com.systemspecs.remita.easypay.extended.dto.*;

public interface EasypayProcessor {

    BalanceEnquiryResponse balanceEnquiry();

    NameEnquiryResponse nameEnquiry(NameEnquiryRawRequest rawRequest);

    FundsTransferResponse fundsTransfer(FundsTransferDCRawRequest fundsTransferDCRawRequest);

    TSQResponse txnStatusQuery(String transactionId);

}
