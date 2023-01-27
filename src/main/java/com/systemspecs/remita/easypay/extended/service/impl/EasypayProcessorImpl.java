package com.systemspecs.remita.easypay.extended.service.impl;


import com.systemspecs.remita.easypay.extended.config.EasypayProperties;
import com.systemspecs.remita.easypay.extended.dto.*;
import com.systemspecs.remita.easypay.extended.service.EasypayHttpService;
import com.systemspecs.remita.easypay.extended.service.EasypayProcessor;
import com.systemspecs.remita.easypay.extended.utils.Helper;
import com.systemspecs.remita.easypay.service.FundsTransferService;
import com.systemspecs.remita.easypay.service.dto.FundsTransferDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@Slf4j
public class EasypayProcessorImpl implements EasypayProcessor {

    @Getter
    private final EasypayProperties properties;
    @Autowired
    private EasypayHttpService httpService;
    @Autowired
    private Helper helper;

    @Autowired
    private FundsTransferService fundsTransferService;

    public EasypayProcessorImpl(EasypayProperties properties) {
        this.properties = properties;
    }

    @Override
    public BalanceEnquiryResponse balanceEnquiry() {
        BalanceEnquiryRawRequest rawRequest = new BalanceEnquiryRawRequest();
        BalanceEnquiryResponse response = new BalanceEnquiryResponse();
        String sessionId = helper.generateNIBSS_SessionID();
        rawRequest.setTransactionId(sessionId);
        if (properties.getTestMode()) {
            response.setResponseCode("00");
            response.setTransactionId(sessionId);
            response.setSessionID(sessionId);
            response.setTargetAccountName("John Doe");
            response.setTargetAccountNumber("1234567890");
            response.setDestinationInstitutionCode(rawRequest.getDestinationInstitutionCode());
            response.setAvailableBalance(BigDecimal.valueOf(200000));
            response.setChannelCode(rawRequest.getChannelCode());
        } else {
            rawRequest.setDestinationInstitutionCode(properties.getSourceInstitutionCode());
            rawRequest.setBillerId(properties.getBillerId());
            rawRequest.setAuthorizationCode(properties.getMandateReferenceNumber());
            response = httpService.balanceEnquiry(rawRequest);
        }
        return response;
    }

    @Override
    public NameEnquiryResponse nameEnquiry(NameEnquiryRawRequest rawRequest) {
        NameEnquiryResponse response = new NameEnquiryResponse();
        String sessionId = helper.generateNIBSS_SessionID();
        rawRequest.setTransactionId(sessionId);
        if (properties.getTestMode()) {
            response.setResponseCode("00");
            response.setTransactionId(sessionId);
            response.setSessionID(sessionId);
            response.setAccountName("John Doe");
            response.setAccountNumber(rawRequest.getAccountNumber());
            response.setDestinationInstitutionCode(rawRequest.getDestinationInstitutionCode());
            response.setBankVerificationNumber("1234567890");
            response.setKycLevel(2);
            response.setChannelCode(rawRequest.getChannelCode());
        } else {
            response = httpService.nameEnquiry(rawRequest);
        }
        return response;
    }

    @Override
    public FundsTransferResponse fundsTransfer(FundsTransferDCRawRequest rawRequest) {
        FundsTransferResponse response = new FundsTransferResponse();
        NameEnquiryRawRequest neRawRequest = new NameEnquiryRawRequest();
        neRawRequest.setAccountNumber(rawRequest.getBeneficiaryAccountNumber());
        neRawRequest.setDestinationInstitutionCode(rawRequest.getDestinationInstitutionCode());
        neRawRequest.setChannelCode(rawRequest.getChannelCode());
        NameEnquiryResponse nameEnquiryResponse = nameEnquiry(neRawRequest);
        if (nameEnquiryResponse.getResponseCode().equals("00")) {
            rawRequest.setSourceInstitutionCode(properties.getSourceInstitutionCode());
            rawRequest.setNameEnquiryRef(nameEnquiryResponse.getSessionID());
            rawRequest.setBeneficiaryAccountName(nameEnquiryResponse.getAccountName());
            rawRequest.setBeneficiaryKYCLevel(nameEnquiryResponse.getKycLevel());
            rawRequest.setBeneficiaryAccountNumber(nameEnquiryResponse.getAccountNumber());
            rawRequest.setBeneficiaryBankVerificationNumber(nameEnquiryResponse.getBankVerificationNumber());
            rawRequest.setOriginatorAccountName(properties.getOriginatorAccountName());
            rawRequest.setOriginatorAccountNumber(properties.getOriginatorAccountNumber());
            rawRequest.setOriginatorBankVerificationNumber(properties.getOriginatorBankVerificationNumber());
            rawRequest.setOriginatorKYCLevel(properties.getOriginatorKYCLevel());
            rawRequest.setBillerId(properties.getBillerId());
            rawRequest.setMandateReferenceNumber(properties.getMandateReferenceNumber());
            rawRequest.setTransactionLocation(properties.getTransactionLocation());
            rawRequest.setTransactionId(helper.generateNIBSS_SessionID());
            FundsTransferDTO savedTransferDTO = getFundsTransferDTO(rawRequest);
            if (properties.getTestMode()) {
                BeanUtils.copyProperties(rawRequest, response);
                response.setResponseCode("00");
                response.setSessionID(rawRequest.getTransactionId());
            } else {
                response = httpService.fundsTransfer(rawRequest);
            }
            updatedResponse(response, savedTransferDTO);
        } else {
            response.setResponseCode(nameEnquiryResponse.getResponseCode());
        }
        return response;
    }

    private void updatedResponse(FundsTransferResponse response, FundsTransferDTO savedTransferDTO) {
        savedTransferDTO.setResponseCode(response.getResponseCode());
        savedTransferDTO.setSessionID(response.getSessionID());
        fundsTransferService.save(savedTransferDTO);
    }

    private FundsTransferDTO getFundsTransferDTO(FundsTransferDCRawRequest rawRequest) {
        FundsTransferDTO fundsTransferDto = new FundsTransferDTO();
        BeanUtils.copyProperties(rawRequest, fundsTransferDto);
        Instant instant = Instant.ofEpochSecond(Instant.now().getEpochSecond());
        fundsTransferDto.setCreatedDate(instant);
        return fundsTransferService.save(fundsTransferDto);
    }

    @Override
    public TSQResponse txnStatusQuery(String transactionId) {
        TSQRawRequest rawRequest = new TSQRawRequest();
        rawRequest.setTransactionId(transactionId);
        TSQResponse response = new TSQResponse();
        if (properties.getTestMode()) {
            BeanUtils.copyProperties(rawRequest, response);
            response.setResponseCode("00");
            response.setTransactionId(rawRequest.getTransactionId());
        } else {
            response = httpService.txnStatusQuery(rawRequest);
        }
        return response;
    }
}
