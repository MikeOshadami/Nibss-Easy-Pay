package com.systemspecs.remita.easypay.extended.service;

import com.google.gson.Gson;
import com.systemspecs.remita.easypay.extended.config.EasypayProperties;
import com.systemspecs.remita.easypay.extended.dto.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class EasypayHttpService {

    @Getter
    private final EasypayProperties properties;

    @Autowired
    private RestTemplate restTemplate;

    public EasypayHttpService(EasypayProperties properties) {
        this.properties = properties;
    }

    public BalanceEnquiryResponse balanceEnquiry(BalanceEnquiryRawRequest request) {
        String payload = new Gson().toJson(request);
        BalanceEnquiryResponse response = new BalanceEnquiryResponse();
        HttpHeaders defaultHeadersToken = getDefaultHeadersToken();
        log.info("balanceEnquiry request on {} with payload {} ", properties.getBalanceEnquiryUrl(), payload);
        HttpEntity<String> entity = new HttpEntity<>(payload, defaultHeadersToken);
        ResponseEntity<String> postForEntity = restTemplate.postForEntity(properties.getBalanceEnquiryUrl(), entity, String.class);
        String responsePayload = postForEntity.getBody();
        log.info("balanceEnquiry response payload {} ", responsePayload);
        response = new Gson().fromJson(responsePayload, BalanceEnquiryResponse.class);
        return response;
    }

    public NameEnquiryResponse nameEnquiry(NameEnquiryRawRequest request) {
        String payload = new Gson().toJson(request);
        NameEnquiryResponse response = new NameEnquiryResponse();
        HttpHeaders defaultHeadersToken = getDefaultHeadersToken();
        log.info("nameEnquiry request on {} with payload {} ", properties.getNameEnquiryUrl(), payload);
        HttpEntity<String> entity = new HttpEntity<>(payload, defaultHeadersToken);
        ResponseEntity<String> postForEntity = restTemplate.postForEntity(properties.getNameEnquiryUrl(), entity, String.class);
        String responsePayload = postForEntity.getBody();
        log.info("nameEnquiry response payload {} ", responsePayload);
        response = new Gson().fromJson(responsePayload, NameEnquiryResponse.class);
        return response;
    }

    public FundsTransferResponse fundsTransfer(FundsTransferDCRawRequest request) {
        String payload = new Gson().toJson(request);
        HttpHeaders defaultHeadersToken = getDefaultHeadersToken();
        log.info("fundsTransfer request on {} with payload {} ", properties.getFundsTransferUrl(), payload);
        HttpEntity<String> entity = new HttpEntity<>(payload, defaultHeadersToken);
        ResponseEntity<String> postForEntity = restTemplate.postForEntity(properties.getFundsTransferUrl(), entity, String.class);
        String responsePayload = postForEntity.getBody();
        log.info("fundsTransfer response payload {} ", responsePayload);
        FundsTransferResponse response = new Gson().fromJson(responsePayload, FundsTransferResponse.class);
        return response;
    }

    public TSQResponse txnStatusQuery(TSQRawRequest request) {
        String payload = new Gson().toJson(request);
        HttpHeaders defaultHeadersToken = getDefaultHeadersToken();
        log.info("txnStatusQuery request on {} with payload {} ", properties.getTsqUrl(), payload);
        HttpEntity<String> entity = new HttpEntity<>(payload, defaultHeadersToken);
        ResponseEntity<String> postForEntity = restTemplate.postForEntity(properties.getTsqUrl(), entity, String.class);
        String responsePayload = postForEntity.getBody();
        log.info("txnStatusQuery response payload {} ", responsePayload);
        TSQResponse response = new Gson().fromJson(responsePayload, TSQResponse.class);
        return response;
    }

    private HttpHeaders getDefaultHeadersToken() {
        HttpHeaders headers = new HttpHeaders();
        TokenResponse tokenResponseDto = transactionToken();
        if (tokenResponseDto.getToken_type().equalsIgnoreCase("Bearer")) {
            headers.setContentType(MediaType.APPLICATION_JSON);
            String accessToken = tokenResponseDto.getAccess_token();
            headers.set("Authorization", String.format("%s%s", "Bearer ", accessToken));
        }
        return headers;
    }

    public TokenResponse transactionToken() {
        TokenResponse responseDto = new TokenResponse();
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
        requestBody.add("client_id", properties.getClientId());
        requestBody.add("client_secret", properties.getClientSecret());
        requestBody.add("scope", properties.getClientId() + "/.default");
        requestBody.add("grant_type", "client_credentials");
        HttpEntity entity = new HttpEntity<MultiValueMap<String, String>>(requestBody, getDefaultHeaders());
        log.info("transactionToken request on {} with payload {} ", properties.getTokenUrl(), entity);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(properties.getTokenUrl(), entity, String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        String responsePayload = responseEntity.getBody();
        log.info("transactionToken response payload {} ", responsePayload);
        responseDto = new Gson().fromJson(responsePayload, TokenResponse.class);
        return responseDto;
    }

    private HttpHeaders getDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }


}
