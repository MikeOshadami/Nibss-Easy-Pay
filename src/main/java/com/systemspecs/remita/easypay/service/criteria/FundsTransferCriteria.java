package com.systemspecs.remita.easypay.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.systemspecs.remita.easypay.domain.FundsTransfer} entity. This class is used
 * in {@link com.systemspecs.remita.easypay.web.rest.FundsTransferResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /funds-transfers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class FundsTransferCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter transactionId;

    private StringFilter responseCode;

    private StringFilter sessionID;

    private IntegerFilter channelCode;

    private StringFilter nameEnquiryRef;

    private BigDecimalFilter amount;

    private StringFilter destinationInstitutionCode;

    private StringFilter beneficiaryAccountName;

    private StringFilter beneficiaryAccountNumber;

    private IntegerFilter beneficiaryKYCLevel;

    private StringFilter beneficiaryBankVerificationNumber;

    private StringFilter beneficiaryNarration;

    private StringFilter originatorAccountName;

    private StringFilter originatorAccountNumber;

    private StringFilter originatorBankVerificationNumber;

    private IntegerFilter originatorKYCLevel;

    private StringFilter originatorNarration;

    private StringFilter transactionLocation;

    private StringFilter mandateReferenceNumber;

    private StringFilter billerId;

    private StringFilter paymentReference;

    private InstantFilter createdDate;

    private Boolean distinct;

    public FundsTransferCriteria() {}

    public FundsTransferCriteria(FundsTransferCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.transactionId = other.transactionId == null ? null : other.transactionId.copy();
        this.responseCode = other.responseCode == null ? null : other.responseCode.copy();
        this.sessionID = other.sessionID == null ? null : other.sessionID.copy();
        this.channelCode = other.channelCode == null ? null : other.channelCode.copy();
        this.nameEnquiryRef = other.nameEnquiryRef == null ? null : other.nameEnquiryRef.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.destinationInstitutionCode = other.destinationInstitutionCode == null ? null : other.destinationInstitutionCode.copy();
        this.beneficiaryAccountName = other.beneficiaryAccountName == null ? null : other.beneficiaryAccountName.copy();
        this.beneficiaryAccountNumber = other.beneficiaryAccountNumber == null ? null : other.beneficiaryAccountNumber.copy();
        this.beneficiaryKYCLevel = other.beneficiaryKYCLevel == null ? null : other.beneficiaryKYCLevel.copy();
        this.beneficiaryBankVerificationNumber =
            other.beneficiaryBankVerificationNumber == null ? null : other.beneficiaryBankVerificationNumber.copy();
        this.beneficiaryNarration = other.beneficiaryNarration == null ? null : other.beneficiaryNarration.copy();
        this.originatorAccountName = other.originatorAccountName == null ? null : other.originatorAccountName.copy();
        this.originatorAccountNumber = other.originatorAccountNumber == null ? null : other.originatorAccountNumber.copy();
        this.originatorBankVerificationNumber =
            other.originatorBankVerificationNumber == null ? null : other.originatorBankVerificationNumber.copy();
        this.originatorKYCLevel = other.originatorKYCLevel == null ? null : other.originatorKYCLevel.copy();
        this.originatorNarration = other.originatorNarration == null ? null : other.originatorNarration.copy();
        this.transactionLocation = other.transactionLocation == null ? null : other.transactionLocation.copy();
        this.mandateReferenceNumber = other.mandateReferenceNumber == null ? null : other.mandateReferenceNumber.copy();
        this.billerId = other.billerId == null ? null : other.billerId.copy();
        this.paymentReference = other.paymentReference == null ? null : other.paymentReference.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FundsTransferCriteria copy() {
        return new FundsTransferCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTransactionId() {
        return transactionId;
    }

    public StringFilter transactionId() {
        if (transactionId == null) {
            transactionId = new StringFilter();
        }
        return transactionId;
    }

    public void setTransactionId(StringFilter transactionId) {
        this.transactionId = transactionId;
    }

    public StringFilter getResponseCode() {
        return responseCode;
    }

    public StringFilter responseCode() {
        if (responseCode == null) {
            responseCode = new StringFilter();
        }
        return responseCode;
    }

    public void setResponseCode(StringFilter responseCode) {
        this.responseCode = responseCode;
    }

    public StringFilter getSessionID() {
        return sessionID;
    }

    public StringFilter sessionID() {
        if (sessionID == null) {
            sessionID = new StringFilter();
        }
        return sessionID;
    }

    public void setSessionID(StringFilter sessionID) {
        this.sessionID = sessionID;
    }

    public IntegerFilter getChannelCode() {
        return channelCode;
    }

    public IntegerFilter channelCode() {
        if (channelCode == null) {
            channelCode = new IntegerFilter();
        }
        return channelCode;
    }

    public void setChannelCode(IntegerFilter channelCode) {
        this.channelCode = channelCode;
    }

    public StringFilter getNameEnquiryRef() {
        return nameEnquiryRef;
    }

    public StringFilter nameEnquiryRef() {
        if (nameEnquiryRef == null) {
            nameEnquiryRef = new StringFilter();
        }
        return nameEnquiryRef;
    }

    public void setNameEnquiryRef(StringFilter nameEnquiryRef) {
        this.nameEnquiryRef = nameEnquiryRef;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public BigDecimalFilter amount() {
        if (amount == null) {
            amount = new BigDecimalFilter();
        }
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public StringFilter getDestinationInstitutionCode() {
        return destinationInstitutionCode;
    }

    public StringFilter destinationInstitutionCode() {
        if (destinationInstitutionCode == null) {
            destinationInstitutionCode = new StringFilter();
        }
        return destinationInstitutionCode;
    }

    public void setDestinationInstitutionCode(StringFilter destinationInstitutionCode) {
        this.destinationInstitutionCode = destinationInstitutionCode;
    }

    public StringFilter getBeneficiaryAccountName() {
        return beneficiaryAccountName;
    }

    public StringFilter beneficiaryAccountName() {
        if (beneficiaryAccountName == null) {
            beneficiaryAccountName = new StringFilter();
        }
        return beneficiaryAccountName;
    }

    public void setBeneficiaryAccountName(StringFilter beneficiaryAccountName) {
        this.beneficiaryAccountName = beneficiaryAccountName;
    }

    public StringFilter getBeneficiaryAccountNumber() {
        return beneficiaryAccountNumber;
    }

    public StringFilter beneficiaryAccountNumber() {
        if (beneficiaryAccountNumber == null) {
            beneficiaryAccountNumber = new StringFilter();
        }
        return beneficiaryAccountNumber;
    }

    public void setBeneficiaryAccountNumber(StringFilter beneficiaryAccountNumber) {
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
    }

    public IntegerFilter getBeneficiaryKYCLevel() {
        return beneficiaryKYCLevel;
    }

    public IntegerFilter beneficiaryKYCLevel() {
        if (beneficiaryKYCLevel == null) {
            beneficiaryKYCLevel = new IntegerFilter();
        }
        return beneficiaryKYCLevel;
    }

    public void setBeneficiaryKYCLevel(IntegerFilter beneficiaryKYCLevel) {
        this.beneficiaryKYCLevel = beneficiaryKYCLevel;
    }

    public StringFilter getBeneficiaryBankVerificationNumber() {
        return beneficiaryBankVerificationNumber;
    }

    public StringFilter beneficiaryBankVerificationNumber() {
        if (beneficiaryBankVerificationNumber == null) {
            beneficiaryBankVerificationNumber = new StringFilter();
        }
        return beneficiaryBankVerificationNumber;
    }

    public void setBeneficiaryBankVerificationNumber(StringFilter beneficiaryBankVerificationNumber) {
        this.beneficiaryBankVerificationNumber = beneficiaryBankVerificationNumber;
    }

    public StringFilter getBeneficiaryNarration() {
        return beneficiaryNarration;
    }

    public StringFilter beneficiaryNarration() {
        if (beneficiaryNarration == null) {
            beneficiaryNarration = new StringFilter();
        }
        return beneficiaryNarration;
    }

    public void setBeneficiaryNarration(StringFilter beneficiaryNarration) {
        this.beneficiaryNarration = beneficiaryNarration;
    }

    public StringFilter getOriginatorAccountName() {
        return originatorAccountName;
    }

    public StringFilter originatorAccountName() {
        if (originatorAccountName == null) {
            originatorAccountName = new StringFilter();
        }
        return originatorAccountName;
    }

    public void setOriginatorAccountName(StringFilter originatorAccountName) {
        this.originatorAccountName = originatorAccountName;
    }

    public StringFilter getOriginatorAccountNumber() {
        return originatorAccountNumber;
    }

    public StringFilter originatorAccountNumber() {
        if (originatorAccountNumber == null) {
            originatorAccountNumber = new StringFilter();
        }
        return originatorAccountNumber;
    }

    public void setOriginatorAccountNumber(StringFilter originatorAccountNumber) {
        this.originatorAccountNumber = originatorAccountNumber;
    }

    public StringFilter getOriginatorBankVerificationNumber() {
        return originatorBankVerificationNumber;
    }

    public StringFilter originatorBankVerificationNumber() {
        if (originatorBankVerificationNumber == null) {
            originatorBankVerificationNumber = new StringFilter();
        }
        return originatorBankVerificationNumber;
    }

    public void setOriginatorBankVerificationNumber(StringFilter originatorBankVerificationNumber) {
        this.originatorBankVerificationNumber = originatorBankVerificationNumber;
    }

    public IntegerFilter getOriginatorKYCLevel() {
        return originatorKYCLevel;
    }

    public IntegerFilter originatorKYCLevel() {
        if (originatorKYCLevel == null) {
            originatorKYCLevel = new IntegerFilter();
        }
        return originatorKYCLevel;
    }

    public void setOriginatorKYCLevel(IntegerFilter originatorKYCLevel) {
        this.originatorKYCLevel = originatorKYCLevel;
    }

    public StringFilter getOriginatorNarration() {
        return originatorNarration;
    }

    public StringFilter originatorNarration() {
        if (originatorNarration == null) {
            originatorNarration = new StringFilter();
        }
        return originatorNarration;
    }

    public void setOriginatorNarration(StringFilter originatorNarration) {
        this.originatorNarration = originatorNarration;
    }

    public StringFilter getTransactionLocation() {
        return transactionLocation;
    }

    public StringFilter transactionLocation() {
        if (transactionLocation == null) {
            transactionLocation = new StringFilter();
        }
        return transactionLocation;
    }

    public void setTransactionLocation(StringFilter transactionLocation) {
        this.transactionLocation = transactionLocation;
    }

    public StringFilter getMandateReferenceNumber() {
        return mandateReferenceNumber;
    }

    public StringFilter mandateReferenceNumber() {
        if (mandateReferenceNumber == null) {
            mandateReferenceNumber = new StringFilter();
        }
        return mandateReferenceNumber;
    }

    public void setMandateReferenceNumber(StringFilter mandateReferenceNumber) {
        this.mandateReferenceNumber = mandateReferenceNumber;
    }

    public StringFilter getBillerId() {
        return billerId;
    }

    public StringFilter billerId() {
        if (billerId == null) {
            billerId = new StringFilter();
        }
        return billerId;
    }

    public void setBillerId(StringFilter billerId) {
        this.billerId = billerId;
    }

    public StringFilter getPaymentReference() {
        return paymentReference;
    }

    public StringFilter paymentReference() {
        if (paymentReference == null) {
            paymentReference = new StringFilter();
        }
        return paymentReference;
    }

    public void setPaymentReference(StringFilter paymentReference) {
        this.paymentReference = paymentReference;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            createdDate = new InstantFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FundsTransferCriteria that = (FundsTransferCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(transactionId, that.transactionId) &&
            Objects.equals(responseCode, that.responseCode) &&
            Objects.equals(sessionID, that.sessionID) &&
            Objects.equals(channelCode, that.channelCode) &&
            Objects.equals(nameEnquiryRef, that.nameEnquiryRef) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(destinationInstitutionCode, that.destinationInstitutionCode) &&
            Objects.equals(beneficiaryAccountName, that.beneficiaryAccountName) &&
            Objects.equals(beneficiaryAccountNumber, that.beneficiaryAccountNumber) &&
            Objects.equals(beneficiaryKYCLevel, that.beneficiaryKYCLevel) &&
            Objects.equals(beneficiaryBankVerificationNumber, that.beneficiaryBankVerificationNumber) &&
            Objects.equals(beneficiaryNarration, that.beneficiaryNarration) &&
            Objects.equals(originatorAccountName, that.originatorAccountName) &&
            Objects.equals(originatorAccountNumber, that.originatorAccountNumber) &&
            Objects.equals(originatorBankVerificationNumber, that.originatorBankVerificationNumber) &&
            Objects.equals(originatorKYCLevel, that.originatorKYCLevel) &&
            Objects.equals(originatorNarration, that.originatorNarration) &&
            Objects.equals(transactionLocation, that.transactionLocation) &&
            Objects.equals(mandateReferenceNumber, that.mandateReferenceNumber) &&
            Objects.equals(billerId, that.billerId) &&
            Objects.equals(paymentReference, that.paymentReference) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            transactionId,
            responseCode,
            sessionID,
            channelCode,
            nameEnquiryRef,
            amount,
            destinationInstitutionCode,
            beneficiaryAccountName,
            beneficiaryAccountNumber,
            beneficiaryKYCLevel,
            beneficiaryBankVerificationNumber,
            beneficiaryNarration,
            originatorAccountName,
            originatorAccountNumber,
            originatorBankVerificationNumber,
            originatorKYCLevel,
            originatorNarration,
            transactionLocation,
            mandateReferenceNumber,
            billerId,
            paymentReference,
            createdDate,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FundsTransferCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (transactionId != null ? "transactionId=" + transactionId + ", " : "") +
            (responseCode != null ? "responseCode=" + responseCode + ", " : "") +
            (sessionID != null ? "sessionID=" + sessionID + ", " : "") +
            (channelCode != null ? "channelCode=" + channelCode + ", " : "") +
            (nameEnquiryRef != null ? "nameEnquiryRef=" + nameEnquiryRef + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (destinationInstitutionCode != null ? "destinationInstitutionCode=" + destinationInstitutionCode + ", " : "") +
            (beneficiaryAccountName != null ? "beneficiaryAccountName=" + beneficiaryAccountName + ", " : "") +
            (beneficiaryAccountNumber != null ? "beneficiaryAccountNumber=" + beneficiaryAccountNumber + ", " : "") +
            (beneficiaryKYCLevel != null ? "beneficiaryKYCLevel=" + beneficiaryKYCLevel + ", " : "") +
            (beneficiaryBankVerificationNumber != null ? "beneficiaryBankVerificationNumber=" + beneficiaryBankVerificationNumber + ", " : "") +
            (beneficiaryNarration != null ? "beneficiaryNarration=" + beneficiaryNarration + ", " : "") +
            (originatorAccountName != null ? "originatorAccountName=" + originatorAccountName + ", " : "") +
            (originatorAccountNumber != null ? "originatorAccountNumber=" + originatorAccountNumber + ", " : "") +
            (originatorBankVerificationNumber != null ? "originatorBankVerificationNumber=" + originatorBankVerificationNumber + ", " : "") +
            (originatorKYCLevel != null ? "originatorKYCLevel=" + originatorKYCLevel + ", " : "") +
            (originatorNarration != null ? "originatorNarration=" + originatorNarration + ", " : "") +
            (transactionLocation != null ? "transactionLocation=" + transactionLocation + ", " : "") +
            (mandateReferenceNumber != null ? "mandateReferenceNumber=" + mandateReferenceNumber + ", " : "") +
            (billerId != null ? "billerId=" + billerId + ", " : "") +
            (paymentReference != null ? "paymentReference=" + paymentReference + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
