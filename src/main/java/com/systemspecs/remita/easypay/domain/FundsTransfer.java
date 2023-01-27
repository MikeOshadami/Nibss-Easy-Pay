package com.systemspecs.remita.easypay.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FundsTransfer.
 */
@Entity
@Table(name = "funds_transfer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FundsTransfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;

    @Column(name = "response_code")
    private String responseCode;

    @Column(name = "session_id")
    private String sessionID;

    @NotNull
    @Column(name = "channel_code", nullable = false)
    private Integer channelCode;

    @NotNull
    @Column(name = "name_enquiry_ref", nullable = false)
    private String nameEnquiryRef;

    @NotNull
    @Column(name = "amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "destination_institution_code", nullable = false)
    private String destinationInstitutionCode;

    @NotNull
    @Column(name = "beneficiary_account_name", nullable = false)
    private String beneficiaryAccountName;

    @NotNull
    @Column(name = "beneficiary_account_number", nullable = false)
    private String beneficiaryAccountNumber;

    @NotNull
    @Column(name = "beneficiary_kyc_level", nullable = false)
    private Integer beneficiaryKYCLevel;

    @NotNull
    @Column(name = "beneficiary_bank_verification_number", nullable = false)
    private String beneficiaryBankVerificationNumber;

    @NotNull
    @Column(name = "beneficiary_narration", nullable = false)
    private String beneficiaryNarration;

    @NotNull
    @Column(name = "originator_account_name", nullable = false)
    private String originatorAccountName;

    @NotNull
    @Column(name = "originator_account_number", nullable = false)
    private String originatorAccountNumber;

    @NotNull
    @Column(name = "originator_bank_verification_number", nullable = false)
    private String originatorBankVerificationNumber;

    @NotNull
    @Column(name = "originator_kyc_level", nullable = false)
    private Integer originatorKYCLevel;

    @NotNull
    @Column(name = "originator_narration", nullable = false)
    private String originatorNarration;

    @NotNull
    @Column(name = "transaction_location", nullable = false)
    private String transactionLocation;

    @NotNull
    @Column(name = "mandate_reference_number", nullable = false)
    private String mandateReferenceNumber;

    @NotNull
    @Column(name = "biller_id", nullable = false)
    private String billerId;

    @NotNull
    @Column(name = "payment_reference", nullable = false)
    private String paymentReference;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FundsTransfer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public FundsTransfer transactionId(String transactionId) {
        this.setTransactionId(transactionId);
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public FundsTransfer responseCode(String responseCode) {
        this.setResponseCode(responseCode);
        return this;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getSessionID() {
        return this.sessionID;
    }

    public FundsTransfer sessionID(String sessionID) {
        this.setSessionID(sessionID);
        return this;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public Integer getChannelCode() {
        return this.channelCode;
    }

    public FundsTransfer channelCode(Integer channelCode) {
        this.setChannelCode(channelCode);
        return this;
    }

    public void setChannelCode(Integer channelCode) {
        this.channelCode = channelCode;
    }

    public String getNameEnquiryRef() {
        return this.nameEnquiryRef;
    }

    public FundsTransfer nameEnquiryRef(String nameEnquiryRef) {
        this.setNameEnquiryRef(nameEnquiryRef);
        return this;
    }

    public void setNameEnquiryRef(String nameEnquiryRef) {
        this.nameEnquiryRef = nameEnquiryRef;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public FundsTransfer amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDestinationInstitutionCode() {
        return this.destinationInstitutionCode;
    }

    public FundsTransfer destinationInstitutionCode(String destinationInstitutionCode) {
        this.setDestinationInstitutionCode(destinationInstitutionCode);
        return this;
    }

    public void setDestinationInstitutionCode(String destinationInstitutionCode) {
        this.destinationInstitutionCode = destinationInstitutionCode;
    }

    public String getBeneficiaryAccountName() {
        return this.beneficiaryAccountName;
    }

    public FundsTransfer beneficiaryAccountName(String beneficiaryAccountName) {
        this.setBeneficiaryAccountName(beneficiaryAccountName);
        return this;
    }

    public void setBeneficiaryAccountName(String beneficiaryAccountName) {
        this.beneficiaryAccountName = beneficiaryAccountName;
    }

    public String getBeneficiaryAccountNumber() {
        return this.beneficiaryAccountNumber;
    }

    public FundsTransfer beneficiaryAccountNumber(String beneficiaryAccountNumber) {
        this.setBeneficiaryAccountNumber(beneficiaryAccountNumber);
        return this;
    }

    public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
        this.beneficiaryAccountNumber = beneficiaryAccountNumber;
    }

    public Integer getBeneficiaryKYCLevel() {
        return this.beneficiaryKYCLevel;
    }

    public FundsTransfer beneficiaryKYCLevel(Integer beneficiaryKYCLevel) {
        this.setBeneficiaryKYCLevel(beneficiaryKYCLevel);
        return this;
    }

    public void setBeneficiaryKYCLevel(Integer beneficiaryKYCLevel) {
        this.beneficiaryKYCLevel = beneficiaryKYCLevel;
    }

    public String getBeneficiaryBankVerificationNumber() {
        return this.beneficiaryBankVerificationNumber;
    }

    public FundsTransfer beneficiaryBankVerificationNumber(String beneficiaryBankVerificationNumber) {
        this.setBeneficiaryBankVerificationNumber(beneficiaryBankVerificationNumber);
        return this;
    }

    public void setBeneficiaryBankVerificationNumber(String beneficiaryBankVerificationNumber) {
        this.beneficiaryBankVerificationNumber = beneficiaryBankVerificationNumber;
    }

    public String getBeneficiaryNarration() {
        return this.beneficiaryNarration;
    }

    public FundsTransfer beneficiaryNarration(String beneficiaryNarration) {
        this.setBeneficiaryNarration(beneficiaryNarration);
        return this;
    }

    public void setBeneficiaryNarration(String beneficiaryNarration) {
        this.beneficiaryNarration = beneficiaryNarration;
    }

    public String getOriginatorAccountName() {
        return this.originatorAccountName;
    }

    public FundsTransfer originatorAccountName(String originatorAccountName) {
        this.setOriginatorAccountName(originatorAccountName);
        return this;
    }

    public void setOriginatorAccountName(String originatorAccountName) {
        this.originatorAccountName = originatorAccountName;
    }

    public String getOriginatorAccountNumber() {
        return this.originatorAccountNumber;
    }

    public FundsTransfer originatorAccountNumber(String originatorAccountNumber) {
        this.setOriginatorAccountNumber(originatorAccountNumber);
        return this;
    }

    public void setOriginatorAccountNumber(String originatorAccountNumber) {
        this.originatorAccountNumber = originatorAccountNumber;
    }

    public String getOriginatorBankVerificationNumber() {
        return this.originatorBankVerificationNumber;
    }

    public FundsTransfer originatorBankVerificationNumber(String originatorBankVerificationNumber) {
        this.setOriginatorBankVerificationNumber(originatorBankVerificationNumber);
        return this;
    }

    public void setOriginatorBankVerificationNumber(String originatorBankVerificationNumber) {
        this.originatorBankVerificationNumber = originatorBankVerificationNumber;
    }

    public Integer getOriginatorKYCLevel() {
        return this.originatorKYCLevel;
    }

    public FundsTransfer originatorKYCLevel(Integer originatorKYCLevel) {
        this.setOriginatorKYCLevel(originatorKYCLevel);
        return this;
    }

    public void setOriginatorKYCLevel(Integer originatorKYCLevel) {
        this.originatorKYCLevel = originatorKYCLevel;
    }

    public String getOriginatorNarration() {
        return this.originatorNarration;
    }

    public FundsTransfer originatorNarration(String originatorNarration) {
        this.setOriginatorNarration(originatorNarration);
        return this;
    }

    public void setOriginatorNarration(String originatorNarration) {
        this.originatorNarration = originatorNarration;
    }

    public String getTransactionLocation() {
        return this.transactionLocation;
    }

    public FundsTransfer transactionLocation(String transactionLocation) {
        this.setTransactionLocation(transactionLocation);
        return this;
    }

    public void setTransactionLocation(String transactionLocation) {
        this.transactionLocation = transactionLocation;
    }

    public String getMandateReferenceNumber() {
        return this.mandateReferenceNumber;
    }

    public FundsTransfer mandateReferenceNumber(String mandateReferenceNumber) {
        this.setMandateReferenceNumber(mandateReferenceNumber);
        return this;
    }

    public void setMandateReferenceNumber(String mandateReferenceNumber) {
        this.mandateReferenceNumber = mandateReferenceNumber;
    }

    public String getBillerId() {
        return this.billerId;
    }

    public FundsTransfer billerId(String billerId) {
        this.setBillerId(billerId);
        return this;
    }

    public void setBillerId(String billerId) {
        this.billerId = billerId;
    }

    public String getPaymentReference() {
        return this.paymentReference;
    }

    public FundsTransfer paymentReference(String paymentReference) {
        this.setPaymentReference(paymentReference);
        return this;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public FundsTransfer createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FundsTransfer)) {
            return false;
        }
        return id != null && id.equals(((FundsTransfer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FundsTransfer{" +
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
