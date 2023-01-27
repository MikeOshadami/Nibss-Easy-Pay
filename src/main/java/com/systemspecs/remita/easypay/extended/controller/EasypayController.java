package com.systemspecs.remita.easypay.extended.controller;


import com.systemspecs.remita.easypay.extended.config.AppConstants;
import com.systemspecs.remita.easypay.extended.dto.FundsTransferDCRawRequest;
import com.systemspecs.remita.easypay.extended.dto.NameEnquiryRawRequest;
import com.systemspecs.remita.easypay.extended.service.EasypayProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(AppConstants.BASE_CONTEXT_URL)
public class EasypayController {

    @Autowired
    private EasypayProcessor processor;


    @ResponseBody
    @GetMapping(AppConstants.BALANCE_ENQUIRY)
    public ResponseEntity balanceEnquiry() {
        return ResponseEntity.ok().body(processor.balanceEnquiry());
    }

    @ResponseBody
    @PostMapping(AppConstants.NAME_ENQUIRY)
    public ResponseEntity nameEnquiry(@RequestBody NameEnquiryRawRequest request) {
        return ResponseEntity.ok().body(processor.nameEnquiry(request));
    }

    @ResponseBody
    @PostMapping(AppConstants.FUNDS_TRANSFER)
    public ResponseEntity ftDirectCredit(@RequestBody FundsTransferDCRawRequest request) {
        return ResponseEntity.ok().body(processor.fundsTransfer(request));
    }

    @ResponseBody
    @GetMapping(AppConstants.TSQ)
    public ResponseEntity tsq(@PathVariable String transactionId) {
        return ResponseEntity.ok().body(processor.txnStatusQuery(transactionId));
    }

}
