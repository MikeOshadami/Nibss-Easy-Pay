package com.systemspecs.remita.easypay.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.systemspecs.remita.easypay.domain.FundsTransfer} entity.
 */
public class FundsTransferDTO implements Serializable {

    private Long id;

    @NotNull
    private String transactionId;

    private String responseCode;

    private String sessionID;

    @NotNull
    private Integer channelCode;

    @NotNull
    private String nameEnquiryRef;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String destinationInstitutionCode;

    @NotNull
    private String beneficiaryAccountName;

    @NotNull
    private String beneficiaryAccountNumber;

    @NotNull
    private Integer beneficiaryKYCLevel;

    @NotNull
    private String beneficiaryBankVerificationNumber;

    @NotNull
    private String beneficiaryNarration;

    @NotNull
    private String originatorAccountName;

    @NotNull
    private String originatorAccountNumber;

    @NotNull
    private String originatorBankVerificationNumber;

    @NotNull
    private Integer originatorKYCLevel;

    @NotNull
    private String originatorNarration;

    @NotNull
    private String transactionLocation;

    @NotNull
    private String mandateReferenceNumber;

    @NotNull
    private String billerId;

    @NotNull
    private String paymentReference;

    @NotNull
    private Instant createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public Integer getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(Integer channelCode) {
        this.channelCode = channelCode;
    }

    public String getNameEnquiryRef() {
        return nameEnquiryRef;
    }

    public void setNameEnquiryRef(String nameEnquiryRef) {
        this.nameEnquiryRef = nameEnquiryRef;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDestinationInstitutionCode() {
        return destinationInstitutionCode;
    }

    public void setDestinationInstitutionCode(String destinationInstitutionCode) {
        this.destinationInstitutionCode = destinationInstitutionCode;
    }

    public String getBeneficiaryAccountName() {
        return beneficiaryAccountName;
    }

    public void setBeneficiaryAccountName(String beneficiaryAccountName) {
        this.beneficiaryAccountName = beneficiaryAccountName;
    }

    public String getBeneficiaryAccountNumber() {
        return beneficiaryAccountNumber;
    }

    public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
    }

    public Integer getBeneficiaryKYCLevel() {
        return beneficiaryKYCLevel;
    }

    public void setBeneficiaryKYCLevel(Integer beneficiaryKYCLevel) {
        this.beneficiaryKYCLevel = beneficiaryKYCLevel;
    }

    public String getBeneficiaryBankVerificationNumber() {
        return beneficiaryBankVerificationNumber;
    }

    public void setBeneficiaryBankVerificationNumber(String beneficiaryBankVerificationNumber) {
        this.beneficiaryBankVerificationNumber = beneficiaryBankVerificationNumber;
    }

    public String getBeneficiaryNarration() {
        return beneficiaryNarration;
    }

    public void setBeneficiaryNarration(String beneficiaryNarration) {
        this.beneficiaryNarration = beneficiaryNarration;
    }

    public String getOriginatorAccountName() {
        return originatorAccountName;
    }

    public void setOriginatorAccountName(String originatorAccountName) {
        this.originatorAccountName = originatorAccountName;
    }

    public String getOriginatorAccountNumber() {
        return originatorAccountNumber;
    }

    public void setOriginatorAccountNumber(String originatorAccountNumber) {
        this.originatorAccountNumber = originatorAccountNumber;
    }

    public String getOriginatorBankVerificationNumber() {
        return originatorBankVerificationNumber;
    }

    public void setOriginatorBankVerificationNumber(String originatorBankVerificationNumber) {
        this.originatorBankVerificationNumber = originatorBankVerificationNumber;
    }

    public Integer getOriginatorKYCLevel() {
        return originatorKYCLevel;
    }

    public void setOriginatorKYCLevel(Integer originatorKYCLevel) {
        this.originatorKYCLevel = originatorKYCLevel;
    }

    public String getOriginatorNarration() {
        return originatorNarration;
    }

    public void setOriginatorNarration(String originatorNarration) {
        this.originatorNarration = originatorNarration;
    }

    public String getTransactionLocation() {
        return transactionLocation;
    }

    public void setTransactionLocation(String transactionLocation) {
        this.transactionLocation = transactionLocation;
    }

    public String getMandateReferenceNumber() {
        return mandateReferenceNumber;
    }

    public void setMandateReferenceNumber(String mandateReferenceNumber) {
        this.mandateReferenceNumber = mandateReferenceNumber;
    }

    public String getBillerId() {
        return billerId;
    }

    public void setBillerId(String billerId) {
        this.billerId = billerId;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FundsTransferDTO)) {
            return false;
        }

        FundsTransferDTO fundsTransferDTO = (FundsTransferDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fundsTransferDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FundsTransferDTO{" +
            "id=" + getId() +
            ", transactionId='" + getTransactionId() + "'" +
            ", responseCode='" + getResponseCode() + "'" +
            ", sessionID='" + getSessionID() + "'" +
            ", channelCode=" + getChannelCode() +
            ", nameEnquiryRef='" + getNameEnquiryRef() + "'" +
            ", amount=" + getAmount() +
            ", destinationInstitutionCode='" + getDestinationInstitutionCode() + "'" +
            ", beneficiaryAccountName='" + getBeneficiaryAccountName() + "'" +
            ", beneficiaryAccountNumber='" + getBeneficiaryAccountNumber() + "'" +
            ", beneficiaryKYCLevel=" + getBeneficiaryKYCLevel() +
            ", beneficiaryBankVerificationNumber='" + getBeneficiaryBankVerificationNumber() + "'" +
            ", beneficiaryNarration='" + getBeneficiaryNarration() + "'" +
            ", originatorAccountName='" + getOriginatorAccountName() + "'" +
            ", originatorAccountNumber='" + getOriginatorAccountNumber() + "'" +
            ", originatorBankVerificationNumber='" + getOriginatorBankVerificationNumber() + "'" +
            ", originatorKYCLevel=" + getOriginatorKYCLevel() +
            ", originatorNarration='" + getOriginatorNarration() + "'" +
            ", transactionLocation='" + getTransactionLocation() + "'" +
            ", mandateReferenceNumber='" + getMandateReferenceNumber() + "'" +
            ", billerId='" + getBillerId() + "'" +
            ", paymentReference='" + getPaymentReference() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
