package com.systemspecs.remita.easypay.web.rest;

import static com.systemspecs.remita.easypay.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.systemspecs.remita.easypay.IntegrationTest;
import com.systemspecs.remita.easypay.domain.FundsTransfer;
import com.systemspecs.remita.easypay.repository.FundsTransferRepository;
import com.systemspecs.remita.easypay.service.criteria.FundsTransferCriteria;
import com.systemspecs.remita.easypay.service.dto.FundsTransferDTO;
import com.systemspecs.remita.easypay.service.mapper.FundsTransferMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FundsTransferResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FundsTransferResourceIT {

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SESSION_ID = "AAAAAAAAAA";
    private static final String UPDATED_SESSION_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_CHANNEL_CODE = 1;
    private static final Integer UPDATED_CHANNEL_CODE = 2;
    private static final Integer SMALLER_CHANNEL_CODE = 1 - 1;

    private static final String DEFAULT_NAME_ENQUIRY_REF = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ENQUIRY_REF = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    private static final String DEFAULT_DESTINATION_INSTITUTION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION_INSTITUTION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFICIARY_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BENEFICIARY_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFICIARY_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BENEFICIARY_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_BENEFICIARY_KYC_LEVEL = 1;
    private static final Integer UPDATED_BENEFICIARY_KYC_LEVEL = 2;
    private static final Integer SMALLER_BENEFICIARY_KYC_LEVEL = 1 - 1;

    private static final String DEFAULT_BENEFICIARY_BANK_VERIFICATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BENEFICIARY_BANK_VERIFICATION_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFICIARY_NARRATION = "AAAAAAAAAA";
    private static final String UPDATED_BENEFICIARY_NARRATION = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINATOR_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINATOR_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINATOR_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINATOR_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINATOR_BANK_VERIFICATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINATOR_BANK_VERIFICATION_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORIGINATOR_KYC_LEVEL = 1;
    private static final Integer UPDATED_ORIGINATOR_KYC_LEVEL = 2;
    private static final Integer SMALLER_ORIGINATOR_KYC_LEVEL = 1 - 1;

    private static final String DEFAULT_ORIGINATOR_NARRATION = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINATOR_NARRATION = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_MANDATE_REFERENCE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MANDATE_REFERENCE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BILLER_ID = "AAAAAAAAAA";
    private static final String UPDATED_BILLER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_REFERENCE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/funds-transfers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FundsTransferRepository fundsTransferRepository;

    @Autowired
    private FundsTransferMapper fundsTransferMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFundsTransferMockMvc;

    private FundsTransfer fundsTransfer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FundsTransfer createEntity(EntityManager em) {
        FundsTransfer fundsTransfer = new FundsTransfer()
            .transactionId(DEFAULT_TRANSACTION_ID)
            .responseCode(DEFAULT_RESPONSE_CODE)
            .sessionID(DEFAULT_SESSION_ID)
            .channelCode(DEFAULT_CHANNEL_CODE)
            .nameEnquiryRef(DEFAULT_NAME_ENQUIRY_REF)
            .amount(DEFAULT_AMOUNT)
            .destinationInstitutionCode(DEFAULT_DESTINATION_INSTITUTION_CODE)
            .beneficiaryAccountName(DEFAULT_BENEFICIARY_ACCOUNT_NAME)
            .beneficiaryAccountNumber(DEFAULT_BENEFICIARY_ACCOUNT_NUMBER)
            .beneficiaryKYCLevel(DEFAULT_BENEFICIARY_KYC_LEVEL)
            .beneficiaryBankVerificationNumber(DEFAULT_BENEFICIARY_BANK_VERIFICATION_NUMBER)
            .beneficiaryNarration(DEFAULT_BENEFICIARY_NARRATION)
            .originatorAccountName(DEFAULT_ORIGINATOR_ACCOUNT_NAME)
            .originatorAccountNumber(DEFAULT_ORIGINATOR_ACCOUNT_NUMBER)
            .originatorBankVerificationNumber(DEFAULT_ORIGINATOR_BANK_VERIFICATION_NUMBER)
            .originatorKYCLevel(DEFAULT_ORIGINATOR_KYC_LEVEL)
            .originatorNarration(DEFAULT_ORIGINATOR_NARRATION)
            .transactionLocation(DEFAULT_TRANSACTION_LOCATION)
            .mandateReferenceNumber(DEFAULT_MANDATE_REFERENCE_NUMBER)
            .billerId(DEFAULT_BILLER_ID)
            .paymentReference(DEFAULT_PAYMENT_REFERENCE)
            .createdDate(DEFAULT_CREATED_DATE);
        return fundsTransfer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FundsTransfer createUpdatedEntity(EntityManager em) {
        FundsTransfer fundsTransfer = new FundsTransfer()
            .transactionId(UPDATED_TRANSACTION_ID)
            .responseCode(UPDATED_RESPONSE_CODE)
            .sessionID(UPDATED_SESSION_ID)
            .channelCode(UPDATED_CHANNEL_CODE)
            .nameEnquiryRef(UPDATED_NAME_ENQUIRY_REF)
            .amount(UPDATED_AMOUNT)
            .destinationInstitutionCode(UPDATED_DESTINATION_INSTITUTION_CODE)
            .beneficiaryAccountName(UPDATED_BENEFICIARY_ACCOUNT_NAME)
            .beneficiaryAccountNumber(UPDATED_BENEFICIARY_ACCOUNT_NUMBER)
            .beneficiaryKYCLevel(UPDATED_BENEFICIARY_KYC_LEVEL)
            .beneficiaryBankVerificationNumber(UPDATED_BENEFICIARY_BANK_VERIFICATION_NUMBER)
            .beneficiaryNarration(UPDATED_BENEFICIARY_NARRATION)
            .originatorAccountName(UPDATED_ORIGINATOR_ACCOUNT_NAME)
            .originatorAccountNumber(UPDATED_ORIGINATOR_ACCOUNT_NUMBER)
            .originatorBankVerificationNumber(UPDATED_ORIGINATOR_BANK_VERIFICATION_NUMBER)
            .originatorKYCLevel(UPDATED_ORIGINATOR_KYC_LEVEL)
            .originatorNarration(UPDATED_ORIGINATOR_NARRATION)
            .transactionLocation(UPDATED_TRANSACTION_LOCATION)
            .mandateReferenceNumber(UPDATED_MANDATE_REFERENCE_NUMBER)
            .billerId(UPDATED_BILLER_ID)
            .paymentReference(UPDATED_PAYMENT_REFERENCE)
            .createdDate(UPDATED_CREATED_DATE);
        return fundsTransfer;
    }

    @BeforeEach
    public void initTest() {
        fundsTransfer = createEntity(em);
    }

    @Test
    @Transactional
    void createFundsTransfer() throws Exception {
        int databaseSizeBeforeCreate = fundsTransferRepository.findAll().size();
        // Create the FundsTransfer
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);
        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FundsTransfer in the database
        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeCreate + 1);
        FundsTransfer testFundsTransfer = fundsTransferList.get(fundsTransferList.size() - 1);
        assertThat(testFundsTransfer.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testFundsTransfer.getResponseCode()).isEqualTo(DEFAULT_RESPONSE_CODE);
        assertThat(testFundsTransfer.getSessionID()).isEqualTo(DEFAULT_SESSION_ID);
        assertThat(testFundsTransfer.getChannelCode()).isEqualTo(DEFAULT_CHANNEL_CODE);
        assertThat(testFundsTransfer.getNameEnquiryRef()).isEqualTo(DEFAULT_NAME_ENQUIRY_REF);
        assertThat(testFundsTransfer.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testFundsTransfer.getDestinationInstitutionCode()).isEqualTo(DEFAULT_DESTINATION_INSTITUTION_CODE);
        assertThat(testFundsTransfer.getBeneficiaryAccountName()).isEqualTo(DEFAULT_BENEFICIARY_ACCOUNT_NAME);
        assertThat(testFundsTransfer.getBeneficiaryAccountNumber()).isEqualTo(DEFAULT_BENEFICIARY_ACCOUNT_NUMBER);
        assertThat(testFundsTransfer.getBeneficiaryKYCLevel()).isEqualTo(DEFAULT_BENEFICIARY_KYC_LEVEL);
        assertThat(testFundsTransfer.getBeneficiaryBankVerificationNumber()).isEqualTo(DEFAULT_BENEFICIARY_BANK_VERIFICATION_NUMBER);
        assertThat(testFundsTransfer.getBeneficiaryNarration()).isEqualTo(DEFAULT_BENEFICIARY_NARRATION);
        assertThat(testFundsTransfer.getOriginatorAccountName()).isEqualTo(DEFAULT_ORIGINATOR_ACCOUNT_NAME);
        assertThat(testFundsTransfer.getOriginatorAccountNumber()).isEqualTo(DEFAULT_ORIGINATOR_ACCOUNT_NUMBER);
        assertThat(testFundsTransfer.getOriginatorBankVerificationNumber()).isEqualTo(DEFAULT_ORIGINATOR_BANK_VERIFICATION_NUMBER);
        assertThat(testFundsTransfer.getOriginatorKYCLevel()).isEqualTo(DEFAULT_ORIGINATOR_KYC_LEVEL);
        assertThat(testFundsTransfer.getOriginatorNarration()).isEqualTo(DEFAULT_ORIGINATOR_NARRATION);
        assertThat(testFundsTransfer.getTransactionLocation()).isEqualTo(DEFAULT_TRANSACTION_LOCATION);
        assertThat(testFundsTransfer.getMandateReferenceNumber()).isEqualTo(DEFAULT_MANDATE_REFERENCE_NUMBER);
        assertThat(testFundsTransfer.getBillerId()).isEqualTo(DEFAULT_BILLER_ID);
        assertThat(testFundsTransfer.getPaymentReference()).isEqualTo(DEFAULT_PAYMENT_REFERENCE);
        assertThat(testFundsTransfer.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createFundsTransferWithExistingId() throws Exception {
        // Create the FundsTransfer with an existing ID
        fundsTransfer.setId(1L);
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        int databaseSizeBeforeCreate = fundsTransferRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FundsTransfer in the database
        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTransactionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundsTransferRepository.findAll().size();
        // set the field null
        fundsTransfer.setTransactionId(null);

        // Create the FundsTransfer, which fails.
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkChannelCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundsTransferRepository.findAll().size();
        // set the field null
        fundsTransfer.setChannelCode(null);

        // Create the FundsTransfer, which fails.
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameEnquiryRefIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundsTransferRepository.findAll().size();
        // set the field null
        fundsTransfer.setNameEnquiryRef(null);

        // Create the FundsTransfer, which fails.
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundsTransferRepository.findAll().size();
        // set the field null
        fundsTransfer.setAmount(null);

        // Create the FundsTransfer, which fails.
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDestinationInstitutionCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundsTransferRepository.findAll().size();
        // set the field null
        fundsTransfer.setDestinationInstitutionCode(null);

        // Create the FundsTransfer, which fails.
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBeneficiaryAccountNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundsTransferRepository.findAll().size();
        // set the field null
        fundsTransfer.setBeneficiaryAccountName(null);

        // Create the FundsTransfer, which fails.
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBeneficiaryAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundsTransferRepository.findAll().size();
        // set the field null
        fundsTransfer.setBeneficiaryAccountNumber(null);

        // Create the FundsTransfer, which fails.
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBeneficiaryKYCLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundsTransferRepository.findAll().size();
        // set the field null
        fundsTransfer.setBeneficiaryKYCLevel(null);

        // Create the FundsTransfer, which fails.
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBeneficiaryBankVerificationNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundsTransferRepository.findAll().size();
        // set the field null
        fundsTransfer.setBeneficiaryBankVerificationNumber(null);

        // Create the FundsTransfer, which fails.
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBeneficiaryNarrationIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundsTransferRepository.findAll().size();
        // set the field null
        fundsTransfer.setBeneficiaryNarration(null);

        // Create the FundsTransfer, which fails.
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOriginatorAccountNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundsTransferRepository.findAll().size();
        // set the field null
        fundsTransfer.setOriginatorAccountName(null);

        // Create the FundsTransfer, which fails.
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOriginatorAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundsTransferRepository.findAll().size();
        // set the field null
        fundsTransfer.setOriginatorAccountNumber(null);

        // Create the FundsTransfer, which fails.
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOriginatorBankVerificationNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundsTransferRepository.findAll().size();
        // set the field null
        fundsTransfer.setOriginatorBankVerificationNumber(null);

        // Create the FundsTransfer, which fails.
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOriginatorKYCLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundsTransferRepository.findAll().size();
        // set the field null
        fundsTransfer.setOriginatorKYCLevel(null);

        // Create the FundsTransfer, which fails.
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOriginatorNarrationIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundsTransferRepository.findAll().size();
        // set the field null
        fundsTransfer.setOriginatorNarration(null);

        // Create the FundsTransfer, which fails.
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTransactionLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundsTransferRepository.findAll().size();
        // set the field null
        fundsTransfer.setTransactionLocation(null);

        // Create the FundsTransfer, which fails.
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMandateReferenceNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundsTransferRepository.findAll().size();
        // set the field null
        fundsTransfer.setMandateReferenceNumber(null);

        // Create the FundsTransfer, which fails.
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBillerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundsTransferRepository.findAll().size();
        // set the field null
        fundsTransfer.setBillerId(null);

        // Create the FundsTransfer, which fails.
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundsTransferRepository.findAll().size();
        // set the field null
        fundsTransfer.setPaymentReference(null);

        // Create the FundsTransfer, which fails.
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = fundsTransferRepository.findAll().size();
        // set the field null
        fundsTransfer.setCreatedDate(null);

        // Create the FundsTransfer, which fails.
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        restFundsTransferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFundsTransfers() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList
        restFundsTransferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fundsTransfer.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].responseCode").value(hasItem(DEFAULT_RESPONSE_CODE)))
            .andExpect(jsonPath("$.[*].sessionID").value(hasItem(DEFAULT_SESSION_ID)))
            .andExpect(jsonPath("$.[*].channelCode").value(hasItem(DEFAULT_CHANNEL_CODE)))
            .andExpect(jsonPath("$.[*].nameEnquiryRef").value(hasItem(DEFAULT_NAME_ENQUIRY_REF)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].destinationInstitutionCode").value(hasItem(DEFAULT_DESTINATION_INSTITUTION_CODE)))
            .andExpect(jsonPath("$.[*].beneficiaryAccountName").value(hasItem(DEFAULT_BENEFICIARY_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].beneficiaryAccountNumber").value(hasItem(DEFAULT_BENEFICIARY_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].beneficiaryKYCLevel").value(hasItem(DEFAULT_BENEFICIARY_KYC_LEVEL)))
            .andExpect(jsonPath("$.[*].beneficiaryBankVerificationNumber").value(hasItem(DEFAULT_BENEFICIARY_BANK_VERIFICATION_NUMBER)))
            .andExpect(jsonPath("$.[*].beneficiaryNarration").value(hasItem(DEFAULT_BENEFICIARY_NARRATION)))
            .andExpect(jsonPath("$.[*].originatorAccountName").value(hasItem(DEFAULT_ORIGINATOR_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].originatorAccountNumber").value(hasItem(DEFAULT_ORIGINATOR_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].originatorBankVerificationNumber").value(hasItem(DEFAULT_ORIGINATOR_BANK_VERIFICATION_NUMBER)))
            .andExpect(jsonPath("$.[*].originatorKYCLevel").value(hasItem(DEFAULT_ORIGINATOR_KYC_LEVEL)))
            .andExpect(jsonPath("$.[*].originatorNarration").value(hasItem(DEFAULT_ORIGINATOR_NARRATION)))
            .andExpect(jsonPath("$.[*].transactionLocation").value(hasItem(DEFAULT_TRANSACTION_LOCATION)))
            .andExpect(jsonPath("$.[*].mandateReferenceNumber").value(hasItem(DEFAULT_MANDATE_REFERENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].billerId").value(hasItem(DEFAULT_BILLER_ID)))
            .andExpect(jsonPath("$.[*].paymentReference").value(hasItem(DEFAULT_PAYMENT_REFERENCE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getFundsTransfer() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get the fundsTransfer
        restFundsTransferMockMvc
            .perform(get(ENTITY_API_URL_ID, fundsTransfer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fundsTransfer.getId().intValue()))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID))
            .andExpect(jsonPath("$.responseCode").value(DEFAULT_RESPONSE_CODE))
            .andExpect(jsonPath("$.sessionID").value(DEFAULT_SESSION_ID))
            .andExpect(jsonPath("$.channelCode").value(DEFAULT_CHANNEL_CODE))
            .andExpect(jsonPath("$.nameEnquiryRef").value(DEFAULT_NAME_ENQUIRY_REF))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.destinationInstitutionCode").value(DEFAULT_DESTINATION_INSTITUTION_CODE))
            .andExpect(jsonPath("$.beneficiaryAccountName").value(DEFAULT_BENEFICIARY_ACCOUNT_NAME))
            .andExpect(jsonPath("$.beneficiaryAccountNumber").value(DEFAULT_BENEFICIARY_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.beneficiaryKYCLevel").value(DEFAULT_BENEFICIARY_KYC_LEVEL))
            .andExpect(jsonPath("$.beneficiaryBankVerificationNumber").value(DEFAULT_BENEFICIARY_BANK_VERIFICATION_NUMBER))
            .andExpect(jsonPath("$.beneficiaryNarration").value(DEFAULT_BENEFICIARY_NARRATION))
            .andExpect(jsonPath("$.originatorAccountName").value(DEFAULT_ORIGINATOR_ACCOUNT_NAME))
            .andExpect(jsonPath("$.originatorAccountNumber").value(DEFAULT_ORIGINATOR_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.originatorBankVerificationNumber").value(DEFAULT_ORIGINATOR_BANK_VERIFICATION_NUMBER))
            .andExpect(jsonPath("$.originatorKYCLevel").value(DEFAULT_ORIGINATOR_KYC_LEVEL))
            .andExpect(jsonPath("$.originatorNarration").value(DEFAULT_ORIGINATOR_NARRATION))
            .andExpect(jsonPath("$.transactionLocation").value(DEFAULT_TRANSACTION_LOCATION))
            .andExpect(jsonPath("$.mandateReferenceNumber").value(DEFAULT_MANDATE_REFERENCE_NUMBER))
            .andExpect(jsonPath("$.billerId").value(DEFAULT_BILLER_ID))
            .andExpect(jsonPath("$.paymentReference").value(DEFAULT_PAYMENT_REFERENCE))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getFundsTransfersByIdFiltering() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        Long id = fundsTransfer.getId();

        defaultFundsTransferShouldBeFound("id.equals=" + id);
        defaultFundsTransferShouldNotBeFound("id.notEquals=" + id);

        defaultFundsTransferShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFundsTransferShouldNotBeFound("id.greaterThan=" + id);

        defaultFundsTransferShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFundsTransferShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByTransactionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where transactionId equals to DEFAULT_TRANSACTION_ID
        defaultFundsTransferShouldBeFound("transactionId.equals=" + DEFAULT_TRANSACTION_ID);

        // Get all the fundsTransferList where transactionId equals to UPDATED_TRANSACTION_ID
        defaultFundsTransferShouldNotBeFound("transactionId.equals=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByTransactionIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where transactionId not equals to DEFAULT_TRANSACTION_ID
        defaultFundsTransferShouldNotBeFound("transactionId.notEquals=" + DEFAULT_TRANSACTION_ID);

        // Get all the fundsTransferList where transactionId not equals to UPDATED_TRANSACTION_ID
        defaultFundsTransferShouldBeFound("transactionId.notEquals=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByTransactionIdIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where transactionId in DEFAULT_TRANSACTION_ID or UPDATED_TRANSACTION_ID
        defaultFundsTransferShouldBeFound("transactionId.in=" + DEFAULT_TRANSACTION_ID + "," + UPDATED_TRANSACTION_ID);

        // Get all the fundsTransferList where transactionId equals to UPDATED_TRANSACTION_ID
        defaultFundsTransferShouldNotBeFound("transactionId.in=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByTransactionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where transactionId is not null
        defaultFundsTransferShouldBeFound("transactionId.specified=true");

        // Get all the fundsTransferList where transactionId is null
        defaultFundsTransferShouldNotBeFound("transactionId.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersByTransactionIdContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where transactionId contains DEFAULT_TRANSACTION_ID
        defaultFundsTransferShouldBeFound("transactionId.contains=" + DEFAULT_TRANSACTION_ID);

        // Get all the fundsTransferList where transactionId contains UPDATED_TRANSACTION_ID
        defaultFundsTransferShouldNotBeFound("transactionId.contains=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByTransactionIdNotContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where transactionId does not contain DEFAULT_TRANSACTION_ID
        defaultFundsTransferShouldNotBeFound("transactionId.doesNotContain=" + DEFAULT_TRANSACTION_ID);

        // Get all the fundsTransferList where transactionId does not contain UPDATED_TRANSACTION_ID
        defaultFundsTransferShouldBeFound("transactionId.doesNotContain=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByResponseCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where responseCode equals to DEFAULT_RESPONSE_CODE
        defaultFundsTransferShouldBeFound("responseCode.equals=" + DEFAULT_RESPONSE_CODE);

        // Get all the fundsTransferList where responseCode equals to UPDATED_RESPONSE_CODE
        defaultFundsTransferShouldNotBeFound("responseCode.equals=" + UPDATED_RESPONSE_CODE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByResponseCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where responseCode not equals to DEFAULT_RESPONSE_CODE
        defaultFundsTransferShouldNotBeFound("responseCode.notEquals=" + DEFAULT_RESPONSE_CODE);

        // Get all the fundsTransferList where responseCode not equals to UPDATED_RESPONSE_CODE
        defaultFundsTransferShouldBeFound("responseCode.notEquals=" + UPDATED_RESPONSE_CODE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByResponseCodeIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where responseCode in DEFAULT_RESPONSE_CODE or UPDATED_RESPONSE_CODE
        defaultFundsTransferShouldBeFound("responseCode.in=" + DEFAULT_RESPONSE_CODE + "," + UPDATED_RESPONSE_CODE);

        // Get all the fundsTransferList where responseCode equals to UPDATED_RESPONSE_CODE
        defaultFundsTransferShouldNotBeFound("responseCode.in=" + UPDATED_RESPONSE_CODE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByResponseCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where responseCode is not null
        defaultFundsTransferShouldBeFound("responseCode.specified=true");

        // Get all the fundsTransferList where responseCode is null
        defaultFundsTransferShouldNotBeFound("responseCode.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersByResponseCodeContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where responseCode contains DEFAULT_RESPONSE_CODE
        defaultFundsTransferShouldBeFound("responseCode.contains=" + DEFAULT_RESPONSE_CODE);

        // Get all the fundsTransferList where responseCode contains UPDATED_RESPONSE_CODE
        defaultFundsTransferShouldNotBeFound("responseCode.contains=" + UPDATED_RESPONSE_CODE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByResponseCodeNotContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where responseCode does not contain DEFAULT_RESPONSE_CODE
        defaultFundsTransferShouldNotBeFound("responseCode.doesNotContain=" + DEFAULT_RESPONSE_CODE);

        // Get all the fundsTransferList where responseCode does not contain UPDATED_RESPONSE_CODE
        defaultFundsTransferShouldBeFound("responseCode.doesNotContain=" + UPDATED_RESPONSE_CODE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersBySessionIDIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where sessionID equals to DEFAULT_SESSION_ID
        defaultFundsTransferShouldBeFound("sessionID.equals=" + DEFAULT_SESSION_ID);

        // Get all the fundsTransferList where sessionID equals to UPDATED_SESSION_ID
        defaultFundsTransferShouldNotBeFound("sessionID.equals=" + UPDATED_SESSION_ID);
    }

    @Test
    @Transactional
    void getAllFundsTransfersBySessionIDIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where sessionID not equals to DEFAULT_SESSION_ID
        defaultFundsTransferShouldNotBeFound("sessionID.notEquals=" + DEFAULT_SESSION_ID);

        // Get all the fundsTransferList where sessionID not equals to UPDATED_SESSION_ID
        defaultFundsTransferShouldBeFound("sessionID.notEquals=" + UPDATED_SESSION_ID);
    }

    @Test
    @Transactional
    void getAllFundsTransfersBySessionIDIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where sessionID in DEFAULT_SESSION_ID or UPDATED_SESSION_ID
        defaultFundsTransferShouldBeFound("sessionID.in=" + DEFAULT_SESSION_ID + "," + UPDATED_SESSION_ID);

        // Get all the fundsTransferList where sessionID equals to UPDATED_SESSION_ID
        defaultFundsTransferShouldNotBeFound("sessionID.in=" + UPDATED_SESSION_ID);
    }

    @Test
    @Transactional
    void getAllFundsTransfersBySessionIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where sessionID is not null
        defaultFundsTransferShouldBeFound("sessionID.specified=true");

        // Get all the fundsTransferList where sessionID is null
        defaultFundsTransferShouldNotBeFound("sessionID.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersBySessionIDContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where sessionID contains DEFAULT_SESSION_ID
        defaultFundsTransferShouldBeFound("sessionID.contains=" + DEFAULT_SESSION_ID);

        // Get all the fundsTransferList where sessionID contains UPDATED_SESSION_ID
        defaultFundsTransferShouldNotBeFound("sessionID.contains=" + UPDATED_SESSION_ID);
    }

    @Test
    @Transactional
    void getAllFundsTransfersBySessionIDNotContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where sessionID does not contain DEFAULT_SESSION_ID
        defaultFundsTransferShouldNotBeFound("sessionID.doesNotContain=" + DEFAULT_SESSION_ID);

        // Get all the fundsTransferList where sessionID does not contain UPDATED_SESSION_ID
        defaultFundsTransferShouldBeFound("sessionID.doesNotContain=" + UPDATED_SESSION_ID);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByChannelCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where channelCode equals to DEFAULT_CHANNEL_CODE
        defaultFundsTransferShouldBeFound("channelCode.equals=" + DEFAULT_CHANNEL_CODE);

        // Get all the fundsTransferList where channelCode equals to UPDATED_CHANNEL_CODE
        defaultFundsTransferShouldNotBeFound("channelCode.equals=" + UPDATED_CHANNEL_CODE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByChannelCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where channelCode not equals to DEFAULT_CHANNEL_CODE
        defaultFundsTransferShouldNotBeFound("channelCode.notEquals=" + DEFAULT_CHANNEL_CODE);

        // Get all the fundsTransferList where channelCode not equals to UPDATED_CHANNEL_CODE
        defaultFundsTransferShouldBeFound("channelCode.notEquals=" + UPDATED_CHANNEL_CODE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByChannelCodeIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where channelCode in DEFAULT_CHANNEL_CODE or UPDATED_CHANNEL_CODE
        defaultFundsTransferShouldBeFound("channelCode.in=" + DEFAULT_CHANNEL_CODE + "," + UPDATED_CHANNEL_CODE);

        // Get all the fundsTransferList where channelCode equals to UPDATED_CHANNEL_CODE
        defaultFundsTransferShouldNotBeFound("channelCode.in=" + UPDATED_CHANNEL_CODE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByChannelCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where channelCode is not null
        defaultFundsTransferShouldBeFound("channelCode.specified=true");

        // Get all the fundsTransferList where channelCode is null
        defaultFundsTransferShouldNotBeFound("channelCode.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersByChannelCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where channelCode is greater than or equal to DEFAULT_CHANNEL_CODE
        defaultFundsTransferShouldBeFound("channelCode.greaterThanOrEqual=" + DEFAULT_CHANNEL_CODE);

        // Get all the fundsTransferList where channelCode is greater than or equal to UPDATED_CHANNEL_CODE
        defaultFundsTransferShouldNotBeFound("channelCode.greaterThanOrEqual=" + UPDATED_CHANNEL_CODE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByChannelCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where channelCode is less than or equal to DEFAULT_CHANNEL_CODE
        defaultFundsTransferShouldBeFound("channelCode.lessThanOrEqual=" + DEFAULT_CHANNEL_CODE);

        // Get all the fundsTransferList where channelCode is less than or equal to SMALLER_CHANNEL_CODE
        defaultFundsTransferShouldNotBeFound("channelCode.lessThanOrEqual=" + SMALLER_CHANNEL_CODE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByChannelCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where channelCode is less than DEFAULT_CHANNEL_CODE
        defaultFundsTransferShouldNotBeFound("channelCode.lessThan=" + DEFAULT_CHANNEL_CODE);

        // Get all the fundsTransferList where channelCode is less than UPDATED_CHANNEL_CODE
        defaultFundsTransferShouldBeFound("channelCode.lessThan=" + UPDATED_CHANNEL_CODE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByChannelCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where channelCode is greater than DEFAULT_CHANNEL_CODE
        defaultFundsTransferShouldNotBeFound("channelCode.greaterThan=" + DEFAULT_CHANNEL_CODE);

        // Get all the fundsTransferList where channelCode is greater than SMALLER_CHANNEL_CODE
        defaultFundsTransferShouldBeFound("channelCode.greaterThan=" + SMALLER_CHANNEL_CODE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByNameEnquiryRefIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where nameEnquiryRef equals to DEFAULT_NAME_ENQUIRY_REF
        defaultFundsTransferShouldBeFound("nameEnquiryRef.equals=" + DEFAULT_NAME_ENQUIRY_REF);

        // Get all the fundsTransferList where nameEnquiryRef equals to UPDATED_NAME_ENQUIRY_REF
        defaultFundsTransferShouldNotBeFound("nameEnquiryRef.equals=" + UPDATED_NAME_ENQUIRY_REF);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByNameEnquiryRefIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where nameEnquiryRef not equals to DEFAULT_NAME_ENQUIRY_REF
        defaultFundsTransferShouldNotBeFound("nameEnquiryRef.notEquals=" + DEFAULT_NAME_ENQUIRY_REF);

        // Get all the fundsTransferList where nameEnquiryRef not equals to UPDATED_NAME_ENQUIRY_REF
        defaultFundsTransferShouldBeFound("nameEnquiryRef.notEquals=" + UPDATED_NAME_ENQUIRY_REF);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByNameEnquiryRefIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where nameEnquiryRef in DEFAULT_NAME_ENQUIRY_REF or UPDATED_NAME_ENQUIRY_REF
        defaultFundsTransferShouldBeFound("nameEnquiryRef.in=" + DEFAULT_NAME_ENQUIRY_REF + "," + UPDATED_NAME_ENQUIRY_REF);

        // Get all the fundsTransferList where nameEnquiryRef equals to UPDATED_NAME_ENQUIRY_REF
        defaultFundsTransferShouldNotBeFound("nameEnquiryRef.in=" + UPDATED_NAME_ENQUIRY_REF);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByNameEnquiryRefIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where nameEnquiryRef is not null
        defaultFundsTransferShouldBeFound("nameEnquiryRef.specified=true");

        // Get all the fundsTransferList where nameEnquiryRef is null
        defaultFundsTransferShouldNotBeFound("nameEnquiryRef.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersByNameEnquiryRefContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where nameEnquiryRef contains DEFAULT_NAME_ENQUIRY_REF
        defaultFundsTransferShouldBeFound("nameEnquiryRef.contains=" + DEFAULT_NAME_ENQUIRY_REF);

        // Get all the fundsTransferList where nameEnquiryRef contains UPDATED_NAME_ENQUIRY_REF
        defaultFundsTransferShouldNotBeFound("nameEnquiryRef.contains=" + UPDATED_NAME_ENQUIRY_REF);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByNameEnquiryRefNotContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where nameEnquiryRef does not contain DEFAULT_NAME_ENQUIRY_REF
        defaultFundsTransferShouldNotBeFound("nameEnquiryRef.doesNotContain=" + DEFAULT_NAME_ENQUIRY_REF);

        // Get all the fundsTransferList where nameEnquiryRef does not contain UPDATED_NAME_ENQUIRY_REF
        defaultFundsTransferShouldBeFound("nameEnquiryRef.doesNotContain=" + UPDATED_NAME_ENQUIRY_REF);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where amount equals to DEFAULT_AMOUNT
        defaultFundsTransferShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the fundsTransferList where amount equals to UPDATED_AMOUNT
        defaultFundsTransferShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where amount not equals to DEFAULT_AMOUNT
        defaultFundsTransferShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the fundsTransferList where amount not equals to UPDATED_AMOUNT
        defaultFundsTransferShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultFundsTransferShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the fundsTransferList where amount equals to UPDATED_AMOUNT
        defaultFundsTransferShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where amount is not null
        defaultFundsTransferShouldBeFound("amount.specified=true");

        // Get all the fundsTransferList where amount is null
        defaultFundsTransferShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultFundsTransferShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the fundsTransferList where amount is greater than or equal to UPDATED_AMOUNT
        defaultFundsTransferShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where amount is less than or equal to DEFAULT_AMOUNT
        defaultFundsTransferShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the fundsTransferList where amount is less than or equal to SMALLER_AMOUNT
        defaultFundsTransferShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where amount is less than DEFAULT_AMOUNT
        defaultFundsTransferShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the fundsTransferList where amount is less than UPDATED_AMOUNT
        defaultFundsTransferShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where amount is greater than DEFAULT_AMOUNT
        defaultFundsTransferShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the fundsTransferList where amount is greater than SMALLER_AMOUNT
        defaultFundsTransferShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByDestinationInstitutionCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where destinationInstitutionCode equals to DEFAULT_DESTINATION_INSTITUTION_CODE
        defaultFundsTransferShouldBeFound("destinationInstitutionCode.equals=" + DEFAULT_DESTINATION_INSTITUTION_CODE);

        // Get all the fundsTransferList where destinationInstitutionCode equals to UPDATED_DESTINATION_INSTITUTION_CODE
        defaultFundsTransferShouldNotBeFound("destinationInstitutionCode.equals=" + UPDATED_DESTINATION_INSTITUTION_CODE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByDestinationInstitutionCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where destinationInstitutionCode not equals to DEFAULT_DESTINATION_INSTITUTION_CODE
        defaultFundsTransferShouldNotBeFound("destinationInstitutionCode.notEquals=" + DEFAULT_DESTINATION_INSTITUTION_CODE);

        // Get all the fundsTransferList where destinationInstitutionCode not equals to UPDATED_DESTINATION_INSTITUTION_CODE
        defaultFundsTransferShouldBeFound("destinationInstitutionCode.notEquals=" + UPDATED_DESTINATION_INSTITUTION_CODE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByDestinationInstitutionCodeIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where destinationInstitutionCode in DEFAULT_DESTINATION_INSTITUTION_CODE or UPDATED_DESTINATION_INSTITUTION_CODE
        defaultFundsTransferShouldBeFound(
            "destinationInstitutionCode.in=" + DEFAULT_DESTINATION_INSTITUTION_CODE + "," + UPDATED_DESTINATION_INSTITUTION_CODE
        );

        // Get all the fundsTransferList where destinationInstitutionCode equals to UPDATED_DESTINATION_INSTITUTION_CODE
        defaultFundsTransferShouldNotBeFound("destinationInstitutionCode.in=" + UPDATED_DESTINATION_INSTITUTION_CODE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByDestinationInstitutionCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where destinationInstitutionCode is not null
        defaultFundsTransferShouldBeFound("destinationInstitutionCode.specified=true");

        // Get all the fundsTransferList where destinationInstitutionCode is null
        defaultFundsTransferShouldNotBeFound("destinationInstitutionCode.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersByDestinationInstitutionCodeContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where destinationInstitutionCode contains DEFAULT_DESTINATION_INSTITUTION_CODE
        defaultFundsTransferShouldBeFound("destinationInstitutionCode.contains=" + DEFAULT_DESTINATION_INSTITUTION_CODE);

        // Get all the fundsTransferList where destinationInstitutionCode contains UPDATED_DESTINATION_INSTITUTION_CODE
        defaultFundsTransferShouldNotBeFound("destinationInstitutionCode.contains=" + UPDATED_DESTINATION_INSTITUTION_CODE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByDestinationInstitutionCodeNotContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where destinationInstitutionCode does not contain DEFAULT_DESTINATION_INSTITUTION_CODE
        defaultFundsTransferShouldNotBeFound("destinationInstitutionCode.doesNotContain=" + DEFAULT_DESTINATION_INSTITUTION_CODE);

        // Get all the fundsTransferList where destinationInstitutionCode does not contain UPDATED_DESTINATION_INSTITUTION_CODE
        defaultFundsTransferShouldBeFound("destinationInstitutionCode.doesNotContain=" + UPDATED_DESTINATION_INSTITUTION_CODE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryAccountNameIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryAccountName equals to DEFAULT_BENEFICIARY_ACCOUNT_NAME
        defaultFundsTransferShouldBeFound("beneficiaryAccountName.equals=" + DEFAULT_BENEFICIARY_ACCOUNT_NAME);

        // Get all the fundsTransferList where beneficiaryAccountName equals to UPDATED_BENEFICIARY_ACCOUNT_NAME
        defaultFundsTransferShouldNotBeFound("beneficiaryAccountName.equals=" + UPDATED_BENEFICIARY_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryAccountNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryAccountName not equals to DEFAULT_BENEFICIARY_ACCOUNT_NAME
        defaultFundsTransferShouldNotBeFound("beneficiaryAccountName.notEquals=" + DEFAULT_BENEFICIARY_ACCOUNT_NAME);

        // Get all the fundsTransferList where beneficiaryAccountName not equals to UPDATED_BENEFICIARY_ACCOUNT_NAME
        defaultFundsTransferShouldBeFound("beneficiaryAccountName.notEquals=" + UPDATED_BENEFICIARY_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryAccountNameIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryAccountName in DEFAULT_BENEFICIARY_ACCOUNT_NAME or UPDATED_BENEFICIARY_ACCOUNT_NAME
        defaultFundsTransferShouldBeFound(
            "beneficiaryAccountName.in=" + DEFAULT_BENEFICIARY_ACCOUNT_NAME + "," + UPDATED_BENEFICIARY_ACCOUNT_NAME
        );

        // Get all the fundsTransferList where beneficiaryAccountName equals to UPDATED_BENEFICIARY_ACCOUNT_NAME
        defaultFundsTransferShouldNotBeFound("beneficiaryAccountName.in=" + UPDATED_BENEFICIARY_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryAccountNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryAccountName is not null
        defaultFundsTransferShouldBeFound("beneficiaryAccountName.specified=true");

        // Get all the fundsTransferList where beneficiaryAccountName is null
        defaultFundsTransferShouldNotBeFound("beneficiaryAccountName.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryAccountNameContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryAccountName contains DEFAULT_BENEFICIARY_ACCOUNT_NAME
        defaultFundsTransferShouldBeFound("beneficiaryAccountName.contains=" + DEFAULT_BENEFICIARY_ACCOUNT_NAME);

        // Get all the fundsTransferList where beneficiaryAccountName contains UPDATED_BENEFICIARY_ACCOUNT_NAME
        defaultFundsTransferShouldNotBeFound("beneficiaryAccountName.contains=" + UPDATED_BENEFICIARY_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryAccountNameNotContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryAccountName does not contain DEFAULT_BENEFICIARY_ACCOUNT_NAME
        defaultFundsTransferShouldNotBeFound("beneficiaryAccountName.doesNotContain=" + DEFAULT_BENEFICIARY_ACCOUNT_NAME);

        // Get all the fundsTransferList where beneficiaryAccountName does not contain UPDATED_BENEFICIARY_ACCOUNT_NAME
        defaultFundsTransferShouldBeFound("beneficiaryAccountName.doesNotContain=" + UPDATED_BENEFICIARY_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryAccountNumber equals to DEFAULT_BENEFICIARY_ACCOUNT_NUMBER
        defaultFundsTransferShouldBeFound("beneficiaryAccountNumber.equals=" + DEFAULT_BENEFICIARY_ACCOUNT_NUMBER);

        // Get all the fundsTransferList where beneficiaryAccountNumber equals to UPDATED_BENEFICIARY_ACCOUNT_NUMBER
        defaultFundsTransferShouldNotBeFound("beneficiaryAccountNumber.equals=" + UPDATED_BENEFICIARY_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryAccountNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryAccountNumber not equals to DEFAULT_BENEFICIARY_ACCOUNT_NUMBER
        defaultFundsTransferShouldNotBeFound("beneficiaryAccountNumber.notEquals=" + DEFAULT_BENEFICIARY_ACCOUNT_NUMBER);

        // Get all the fundsTransferList where beneficiaryAccountNumber not equals to UPDATED_BENEFICIARY_ACCOUNT_NUMBER
        defaultFundsTransferShouldBeFound("beneficiaryAccountNumber.notEquals=" + UPDATED_BENEFICIARY_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryAccountNumber in DEFAULT_BENEFICIARY_ACCOUNT_NUMBER or UPDATED_BENEFICIARY_ACCOUNT_NUMBER
        defaultFundsTransferShouldBeFound(
            "beneficiaryAccountNumber.in=" + DEFAULT_BENEFICIARY_ACCOUNT_NUMBER + "," + UPDATED_BENEFICIARY_ACCOUNT_NUMBER
        );

        // Get all the fundsTransferList where beneficiaryAccountNumber equals to UPDATED_BENEFICIARY_ACCOUNT_NUMBER
        defaultFundsTransferShouldNotBeFound("beneficiaryAccountNumber.in=" + UPDATED_BENEFICIARY_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryAccountNumber is not null
        defaultFundsTransferShouldBeFound("beneficiaryAccountNumber.specified=true");

        // Get all the fundsTransferList where beneficiaryAccountNumber is null
        defaultFundsTransferShouldNotBeFound("beneficiaryAccountNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryAccountNumber contains DEFAULT_BENEFICIARY_ACCOUNT_NUMBER
        defaultFundsTransferShouldBeFound("beneficiaryAccountNumber.contains=" + DEFAULT_BENEFICIARY_ACCOUNT_NUMBER);

        // Get all the fundsTransferList where beneficiaryAccountNumber contains UPDATED_BENEFICIARY_ACCOUNT_NUMBER
        defaultFundsTransferShouldNotBeFound("beneficiaryAccountNumber.contains=" + UPDATED_BENEFICIARY_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryAccountNumber does not contain DEFAULT_BENEFICIARY_ACCOUNT_NUMBER
        defaultFundsTransferShouldNotBeFound("beneficiaryAccountNumber.doesNotContain=" + DEFAULT_BENEFICIARY_ACCOUNT_NUMBER);

        // Get all the fundsTransferList where beneficiaryAccountNumber does not contain UPDATED_BENEFICIARY_ACCOUNT_NUMBER
        defaultFundsTransferShouldBeFound("beneficiaryAccountNumber.doesNotContain=" + UPDATED_BENEFICIARY_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryKYCLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryKYCLevel equals to DEFAULT_BENEFICIARY_KYC_LEVEL
        defaultFundsTransferShouldBeFound("beneficiaryKYCLevel.equals=" + DEFAULT_BENEFICIARY_KYC_LEVEL);

        // Get all the fundsTransferList where beneficiaryKYCLevel equals to UPDATED_BENEFICIARY_KYC_LEVEL
        defaultFundsTransferShouldNotBeFound("beneficiaryKYCLevel.equals=" + UPDATED_BENEFICIARY_KYC_LEVEL);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryKYCLevelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryKYCLevel not equals to DEFAULT_BENEFICIARY_KYC_LEVEL
        defaultFundsTransferShouldNotBeFound("beneficiaryKYCLevel.notEquals=" + DEFAULT_BENEFICIARY_KYC_LEVEL);

        // Get all the fundsTransferList where beneficiaryKYCLevel not equals to UPDATED_BENEFICIARY_KYC_LEVEL
        defaultFundsTransferShouldBeFound("beneficiaryKYCLevel.notEquals=" + UPDATED_BENEFICIARY_KYC_LEVEL);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryKYCLevelIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryKYCLevel in DEFAULT_BENEFICIARY_KYC_LEVEL or UPDATED_BENEFICIARY_KYC_LEVEL
        defaultFundsTransferShouldBeFound("beneficiaryKYCLevel.in=" + DEFAULT_BENEFICIARY_KYC_LEVEL + "," + UPDATED_BENEFICIARY_KYC_LEVEL);

        // Get all the fundsTransferList where beneficiaryKYCLevel equals to UPDATED_BENEFICIARY_KYC_LEVEL
        defaultFundsTransferShouldNotBeFound("beneficiaryKYCLevel.in=" + UPDATED_BENEFICIARY_KYC_LEVEL);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryKYCLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryKYCLevel is not null
        defaultFundsTransferShouldBeFound("beneficiaryKYCLevel.specified=true");

        // Get all the fundsTransferList where beneficiaryKYCLevel is null
        defaultFundsTransferShouldNotBeFound("beneficiaryKYCLevel.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryKYCLevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryKYCLevel is greater than or equal to DEFAULT_BENEFICIARY_KYC_LEVEL
        defaultFundsTransferShouldBeFound("beneficiaryKYCLevel.greaterThanOrEqual=" + DEFAULT_BENEFICIARY_KYC_LEVEL);

        // Get all the fundsTransferList where beneficiaryKYCLevel is greater than or equal to UPDATED_BENEFICIARY_KYC_LEVEL
        defaultFundsTransferShouldNotBeFound("beneficiaryKYCLevel.greaterThanOrEqual=" + UPDATED_BENEFICIARY_KYC_LEVEL);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryKYCLevelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryKYCLevel is less than or equal to DEFAULT_BENEFICIARY_KYC_LEVEL
        defaultFundsTransferShouldBeFound("beneficiaryKYCLevel.lessThanOrEqual=" + DEFAULT_BENEFICIARY_KYC_LEVEL);

        // Get all the fundsTransferList where beneficiaryKYCLevel is less than or equal to SMALLER_BENEFICIARY_KYC_LEVEL
        defaultFundsTransferShouldNotBeFound("beneficiaryKYCLevel.lessThanOrEqual=" + SMALLER_BENEFICIARY_KYC_LEVEL);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryKYCLevelIsLessThanSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryKYCLevel is less than DEFAULT_BENEFICIARY_KYC_LEVEL
        defaultFundsTransferShouldNotBeFound("beneficiaryKYCLevel.lessThan=" + DEFAULT_BENEFICIARY_KYC_LEVEL);

        // Get all the fundsTransferList where beneficiaryKYCLevel is less than UPDATED_BENEFICIARY_KYC_LEVEL
        defaultFundsTransferShouldBeFound("beneficiaryKYCLevel.lessThan=" + UPDATED_BENEFICIARY_KYC_LEVEL);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryKYCLevelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryKYCLevel is greater than DEFAULT_BENEFICIARY_KYC_LEVEL
        defaultFundsTransferShouldNotBeFound("beneficiaryKYCLevel.greaterThan=" + DEFAULT_BENEFICIARY_KYC_LEVEL);

        // Get all the fundsTransferList where beneficiaryKYCLevel is greater than SMALLER_BENEFICIARY_KYC_LEVEL
        defaultFundsTransferShouldBeFound("beneficiaryKYCLevel.greaterThan=" + SMALLER_BENEFICIARY_KYC_LEVEL);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryBankVerificationNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryBankVerificationNumber equals to DEFAULT_BENEFICIARY_BANK_VERIFICATION_NUMBER
        defaultFundsTransferShouldBeFound("beneficiaryBankVerificationNumber.equals=" + DEFAULT_BENEFICIARY_BANK_VERIFICATION_NUMBER);

        // Get all the fundsTransferList where beneficiaryBankVerificationNumber equals to UPDATED_BENEFICIARY_BANK_VERIFICATION_NUMBER
        defaultFundsTransferShouldNotBeFound("beneficiaryBankVerificationNumber.equals=" + UPDATED_BENEFICIARY_BANK_VERIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryBankVerificationNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryBankVerificationNumber not equals to DEFAULT_BENEFICIARY_BANK_VERIFICATION_NUMBER
        defaultFundsTransferShouldNotBeFound("beneficiaryBankVerificationNumber.notEquals=" + DEFAULT_BENEFICIARY_BANK_VERIFICATION_NUMBER);

        // Get all the fundsTransferList where beneficiaryBankVerificationNumber not equals to UPDATED_BENEFICIARY_BANK_VERIFICATION_NUMBER
        defaultFundsTransferShouldBeFound("beneficiaryBankVerificationNumber.notEquals=" + UPDATED_BENEFICIARY_BANK_VERIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryBankVerificationNumberIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryBankVerificationNumber in DEFAULT_BENEFICIARY_BANK_VERIFICATION_NUMBER or UPDATED_BENEFICIARY_BANK_VERIFICATION_NUMBER
        defaultFundsTransferShouldBeFound(
            "beneficiaryBankVerificationNumber.in=" +
            DEFAULT_BENEFICIARY_BANK_VERIFICATION_NUMBER +
            "," +
            UPDATED_BENEFICIARY_BANK_VERIFICATION_NUMBER
        );

        // Get all the fundsTransferList where beneficiaryBankVerificationNumber equals to UPDATED_BENEFICIARY_BANK_VERIFICATION_NUMBER
        defaultFundsTransferShouldNotBeFound("beneficiaryBankVerificationNumber.in=" + UPDATED_BENEFICIARY_BANK_VERIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryBankVerificationNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryBankVerificationNumber is not null
        defaultFundsTransferShouldBeFound("beneficiaryBankVerificationNumber.specified=true");

        // Get all the fundsTransferList where beneficiaryBankVerificationNumber is null
        defaultFundsTransferShouldNotBeFound("beneficiaryBankVerificationNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryBankVerificationNumberContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryBankVerificationNumber contains DEFAULT_BENEFICIARY_BANK_VERIFICATION_NUMBER
        defaultFundsTransferShouldBeFound("beneficiaryBankVerificationNumber.contains=" + DEFAULT_BENEFICIARY_BANK_VERIFICATION_NUMBER);

        // Get all the fundsTransferList where beneficiaryBankVerificationNumber contains UPDATED_BENEFICIARY_BANK_VERIFICATION_NUMBER
        defaultFundsTransferShouldNotBeFound("beneficiaryBankVerificationNumber.contains=" + UPDATED_BENEFICIARY_BANK_VERIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryBankVerificationNumberNotContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryBankVerificationNumber does not contain DEFAULT_BENEFICIARY_BANK_VERIFICATION_NUMBER
        defaultFundsTransferShouldNotBeFound(
            "beneficiaryBankVerificationNumber.doesNotContain=" + DEFAULT_BENEFICIARY_BANK_VERIFICATION_NUMBER
        );

        // Get all the fundsTransferList where beneficiaryBankVerificationNumber does not contain UPDATED_BENEFICIARY_BANK_VERIFICATION_NUMBER
        defaultFundsTransferShouldBeFound(
            "beneficiaryBankVerificationNumber.doesNotContain=" + UPDATED_BENEFICIARY_BANK_VERIFICATION_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryNarrationIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryNarration equals to DEFAULT_BENEFICIARY_NARRATION
        defaultFundsTransferShouldBeFound("beneficiaryNarration.equals=" + DEFAULT_BENEFICIARY_NARRATION);

        // Get all the fundsTransferList where beneficiaryNarration equals to UPDATED_BENEFICIARY_NARRATION
        defaultFundsTransferShouldNotBeFound("beneficiaryNarration.equals=" + UPDATED_BENEFICIARY_NARRATION);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryNarrationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryNarration not equals to DEFAULT_BENEFICIARY_NARRATION
        defaultFundsTransferShouldNotBeFound("beneficiaryNarration.notEquals=" + DEFAULT_BENEFICIARY_NARRATION);

        // Get all the fundsTransferList where beneficiaryNarration not equals to UPDATED_BENEFICIARY_NARRATION
        defaultFundsTransferShouldBeFound("beneficiaryNarration.notEquals=" + UPDATED_BENEFICIARY_NARRATION);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryNarrationIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryNarration in DEFAULT_BENEFICIARY_NARRATION or UPDATED_BENEFICIARY_NARRATION
        defaultFundsTransferShouldBeFound("beneficiaryNarration.in=" + DEFAULT_BENEFICIARY_NARRATION + "," + UPDATED_BENEFICIARY_NARRATION);

        // Get all the fundsTransferList where beneficiaryNarration equals to UPDATED_BENEFICIARY_NARRATION
        defaultFundsTransferShouldNotBeFound("beneficiaryNarration.in=" + UPDATED_BENEFICIARY_NARRATION);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryNarrationIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryNarration is not null
        defaultFundsTransferShouldBeFound("beneficiaryNarration.specified=true");

        // Get all the fundsTransferList where beneficiaryNarration is null
        defaultFundsTransferShouldNotBeFound("beneficiaryNarration.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryNarrationContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryNarration contains DEFAULT_BENEFICIARY_NARRATION
        defaultFundsTransferShouldBeFound("beneficiaryNarration.contains=" + DEFAULT_BENEFICIARY_NARRATION);

        // Get all the fundsTransferList where beneficiaryNarration contains UPDATED_BENEFICIARY_NARRATION
        defaultFundsTransferShouldNotBeFound("beneficiaryNarration.contains=" + UPDATED_BENEFICIARY_NARRATION);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBeneficiaryNarrationNotContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where beneficiaryNarration does not contain DEFAULT_BENEFICIARY_NARRATION
        defaultFundsTransferShouldNotBeFound("beneficiaryNarration.doesNotContain=" + DEFAULT_BENEFICIARY_NARRATION);

        // Get all the fundsTransferList where beneficiaryNarration does not contain UPDATED_BENEFICIARY_NARRATION
        defaultFundsTransferShouldBeFound("beneficiaryNarration.doesNotContain=" + UPDATED_BENEFICIARY_NARRATION);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorAccountNameIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorAccountName equals to DEFAULT_ORIGINATOR_ACCOUNT_NAME
        defaultFundsTransferShouldBeFound("originatorAccountName.equals=" + DEFAULT_ORIGINATOR_ACCOUNT_NAME);

        // Get all the fundsTransferList where originatorAccountName equals to UPDATED_ORIGINATOR_ACCOUNT_NAME
        defaultFundsTransferShouldNotBeFound("originatorAccountName.equals=" + UPDATED_ORIGINATOR_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorAccountNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorAccountName not equals to DEFAULT_ORIGINATOR_ACCOUNT_NAME
        defaultFundsTransferShouldNotBeFound("originatorAccountName.notEquals=" + DEFAULT_ORIGINATOR_ACCOUNT_NAME);

        // Get all the fundsTransferList where originatorAccountName not equals to UPDATED_ORIGINATOR_ACCOUNT_NAME
        defaultFundsTransferShouldBeFound("originatorAccountName.notEquals=" + UPDATED_ORIGINATOR_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorAccountNameIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorAccountName in DEFAULT_ORIGINATOR_ACCOUNT_NAME or UPDATED_ORIGINATOR_ACCOUNT_NAME
        defaultFundsTransferShouldBeFound(
            "originatorAccountName.in=" + DEFAULT_ORIGINATOR_ACCOUNT_NAME + "," + UPDATED_ORIGINATOR_ACCOUNT_NAME
        );

        // Get all the fundsTransferList where originatorAccountName equals to UPDATED_ORIGINATOR_ACCOUNT_NAME
        defaultFundsTransferShouldNotBeFound("originatorAccountName.in=" + UPDATED_ORIGINATOR_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorAccountNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorAccountName is not null
        defaultFundsTransferShouldBeFound("originatorAccountName.specified=true");

        // Get all the fundsTransferList where originatorAccountName is null
        defaultFundsTransferShouldNotBeFound("originatorAccountName.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorAccountNameContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorAccountName contains DEFAULT_ORIGINATOR_ACCOUNT_NAME
        defaultFundsTransferShouldBeFound("originatorAccountName.contains=" + DEFAULT_ORIGINATOR_ACCOUNT_NAME);

        // Get all the fundsTransferList where originatorAccountName contains UPDATED_ORIGINATOR_ACCOUNT_NAME
        defaultFundsTransferShouldNotBeFound("originatorAccountName.contains=" + UPDATED_ORIGINATOR_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorAccountNameNotContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorAccountName does not contain DEFAULT_ORIGINATOR_ACCOUNT_NAME
        defaultFundsTransferShouldNotBeFound("originatorAccountName.doesNotContain=" + DEFAULT_ORIGINATOR_ACCOUNT_NAME);

        // Get all the fundsTransferList where originatorAccountName does not contain UPDATED_ORIGINATOR_ACCOUNT_NAME
        defaultFundsTransferShouldBeFound("originatorAccountName.doesNotContain=" + UPDATED_ORIGINATOR_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorAccountNumber equals to DEFAULT_ORIGINATOR_ACCOUNT_NUMBER
        defaultFundsTransferShouldBeFound("originatorAccountNumber.equals=" + DEFAULT_ORIGINATOR_ACCOUNT_NUMBER);

        // Get all the fundsTransferList where originatorAccountNumber equals to UPDATED_ORIGINATOR_ACCOUNT_NUMBER
        defaultFundsTransferShouldNotBeFound("originatorAccountNumber.equals=" + UPDATED_ORIGINATOR_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorAccountNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorAccountNumber not equals to DEFAULT_ORIGINATOR_ACCOUNT_NUMBER
        defaultFundsTransferShouldNotBeFound("originatorAccountNumber.notEquals=" + DEFAULT_ORIGINATOR_ACCOUNT_NUMBER);

        // Get all the fundsTransferList where originatorAccountNumber not equals to UPDATED_ORIGINATOR_ACCOUNT_NUMBER
        defaultFundsTransferShouldBeFound("originatorAccountNumber.notEquals=" + UPDATED_ORIGINATOR_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorAccountNumber in DEFAULT_ORIGINATOR_ACCOUNT_NUMBER or UPDATED_ORIGINATOR_ACCOUNT_NUMBER
        defaultFundsTransferShouldBeFound(
            "originatorAccountNumber.in=" + DEFAULT_ORIGINATOR_ACCOUNT_NUMBER + "," + UPDATED_ORIGINATOR_ACCOUNT_NUMBER
        );

        // Get all the fundsTransferList where originatorAccountNumber equals to UPDATED_ORIGINATOR_ACCOUNT_NUMBER
        defaultFundsTransferShouldNotBeFound("originatorAccountNumber.in=" + UPDATED_ORIGINATOR_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorAccountNumber is not null
        defaultFundsTransferShouldBeFound("originatorAccountNumber.specified=true");

        // Get all the fundsTransferList where originatorAccountNumber is null
        defaultFundsTransferShouldNotBeFound("originatorAccountNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorAccountNumber contains DEFAULT_ORIGINATOR_ACCOUNT_NUMBER
        defaultFundsTransferShouldBeFound("originatorAccountNumber.contains=" + DEFAULT_ORIGINATOR_ACCOUNT_NUMBER);

        // Get all the fundsTransferList where originatorAccountNumber contains UPDATED_ORIGINATOR_ACCOUNT_NUMBER
        defaultFundsTransferShouldNotBeFound("originatorAccountNumber.contains=" + UPDATED_ORIGINATOR_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorAccountNumber does not contain DEFAULT_ORIGINATOR_ACCOUNT_NUMBER
        defaultFundsTransferShouldNotBeFound("originatorAccountNumber.doesNotContain=" + DEFAULT_ORIGINATOR_ACCOUNT_NUMBER);

        // Get all the fundsTransferList where originatorAccountNumber does not contain UPDATED_ORIGINATOR_ACCOUNT_NUMBER
        defaultFundsTransferShouldBeFound("originatorAccountNumber.doesNotContain=" + UPDATED_ORIGINATOR_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorBankVerificationNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorBankVerificationNumber equals to DEFAULT_ORIGINATOR_BANK_VERIFICATION_NUMBER
        defaultFundsTransferShouldBeFound("originatorBankVerificationNumber.equals=" + DEFAULT_ORIGINATOR_BANK_VERIFICATION_NUMBER);

        // Get all the fundsTransferList where originatorBankVerificationNumber equals to UPDATED_ORIGINATOR_BANK_VERIFICATION_NUMBER
        defaultFundsTransferShouldNotBeFound("originatorBankVerificationNumber.equals=" + UPDATED_ORIGINATOR_BANK_VERIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorBankVerificationNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorBankVerificationNumber not equals to DEFAULT_ORIGINATOR_BANK_VERIFICATION_NUMBER
        defaultFundsTransferShouldNotBeFound("originatorBankVerificationNumber.notEquals=" + DEFAULT_ORIGINATOR_BANK_VERIFICATION_NUMBER);

        // Get all the fundsTransferList where originatorBankVerificationNumber not equals to UPDATED_ORIGINATOR_BANK_VERIFICATION_NUMBER
        defaultFundsTransferShouldBeFound("originatorBankVerificationNumber.notEquals=" + UPDATED_ORIGINATOR_BANK_VERIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorBankVerificationNumberIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorBankVerificationNumber in DEFAULT_ORIGINATOR_BANK_VERIFICATION_NUMBER or UPDATED_ORIGINATOR_BANK_VERIFICATION_NUMBER
        defaultFundsTransferShouldBeFound(
            "originatorBankVerificationNumber.in=" +
            DEFAULT_ORIGINATOR_BANK_VERIFICATION_NUMBER +
            "," +
            UPDATED_ORIGINATOR_BANK_VERIFICATION_NUMBER
        );

        // Get all the fundsTransferList where originatorBankVerificationNumber equals to UPDATED_ORIGINATOR_BANK_VERIFICATION_NUMBER
        defaultFundsTransferShouldNotBeFound("originatorBankVerificationNumber.in=" + UPDATED_ORIGINATOR_BANK_VERIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorBankVerificationNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorBankVerificationNumber is not null
        defaultFundsTransferShouldBeFound("originatorBankVerificationNumber.specified=true");

        // Get all the fundsTransferList where originatorBankVerificationNumber is null
        defaultFundsTransferShouldNotBeFound("originatorBankVerificationNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorBankVerificationNumberContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorBankVerificationNumber contains DEFAULT_ORIGINATOR_BANK_VERIFICATION_NUMBER
        defaultFundsTransferShouldBeFound("originatorBankVerificationNumber.contains=" + DEFAULT_ORIGINATOR_BANK_VERIFICATION_NUMBER);

        // Get all the fundsTransferList where originatorBankVerificationNumber contains UPDATED_ORIGINATOR_BANK_VERIFICATION_NUMBER
        defaultFundsTransferShouldNotBeFound("originatorBankVerificationNumber.contains=" + UPDATED_ORIGINATOR_BANK_VERIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorBankVerificationNumberNotContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorBankVerificationNumber does not contain DEFAULT_ORIGINATOR_BANK_VERIFICATION_NUMBER
        defaultFundsTransferShouldNotBeFound(
            "originatorBankVerificationNumber.doesNotContain=" + DEFAULT_ORIGINATOR_BANK_VERIFICATION_NUMBER
        );

        // Get all the fundsTransferList where originatorBankVerificationNumber does not contain UPDATED_ORIGINATOR_BANK_VERIFICATION_NUMBER
        defaultFundsTransferShouldBeFound("originatorBankVerificationNumber.doesNotContain=" + UPDATED_ORIGINATOR_BANK_VERIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorKYCLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorKYCLevel equals to DEFAULT_ORIGINATOR_KYC_LEVEL
        defaultFundsTransferShouldBeFound("originatorKYCLevel.equals=" + DEFAULT_ORIGINATOR_KYC_LEVEL);

        // Get all the fundsTransferList where originatorKYCLevel equals to UPDATED_ORIGINATOR_KYC_LEVEL
        defaultFundsTransferShouldNotBeFound("originatorKYCLevel.equals=" + UPDATED_ORIGINATOR_KYC_LEVEL);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorKYCLevelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorKYCLevel not equals to DEFAULT_ORIGINATOR_KYC_LEVEL
        defaultFundsTransferShouldNotBeFound("originatorKYCLevel.notEquals=" + DEFAULT_ORIGINATOR_KYC_LEVEL);

        // Get all the fundsTransferList where originatorKYCLevel not equals to UPDATED_ORIGINATOR_KYC_LEVEL
        defaultFundsTransferShouldBeFound("originatorKYCLevel.notEquals=" + UPDATED_ORIGINATOR_KYC_LEVEL);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorKYCLevelIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorKYCLevel in DEFAULT_ORIGINATOR_KYC_LEVEL or UPDATED_ORIGINATOR_KYC_LEVEL
        defaultFundsTransferShouldBeFound("originatorKYCLevel.in=" + DEFAULT_ORIGINATOR_KYC_LEVEL + "," + UPDATED_ORIGINATOR_KYC_LEVEL);

        // Get all the fundsTransferList where originatorKYCLevel equals to UPDATED_ORIGINATOR_KYC_LEVEL
        defaultFundsTransferShouldNotBeFound("originatorKYCLevel.in=" + UPDATED_ORIGINATOR_KYC_LEVEL);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorKYCLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorKYCLevel is not null
        defaultFundsTransferShouldBeFound("originatorKYCLevel.specified=true");

        // Get all the fundsTransferList where originatorKYCLevel is null
        defaultFundsTransferShouldNotBeFound("originatorKYCLevel.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorKYCLevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorKYCLevel is greater than or equal to DEFAULT_ORIGINATOR_KYC_LEVEL
        defaultFundsTransferShouldBeFound("originatorKYCLevel.greaterThanOrEqual=" + DEFAULT_ORIGINATOR_KYC_LEVEL);

        // Get all the fundsTransferList where originatorKYCLevel is greater than or equal to UPDATED_ORIGINATOR_KYC_LEVEL
        defaultFundsTransferShouldNotBeFound("originatorKYCLevel.greaterThanOrEqual=" + UPDATED_ORIGINATOR_KYC_LEVEL);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorKYCLevelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorKYCLevel is less than or equal to DEFAULT_ORIGINATOR_KYC_LEVEL
        defaultFundsTransferShouldBeFound("originatorKYCLevel.lessThanOrEqual=" + DEFAULT_ORIGINATOR_KYC_LEVEL);

        // Get all the fundsTransferList where originatorKYCLevel is less than or equal to SMALLER_ORIGINATOR_KYC_LEVEL
        defaultFundsTransferShouldNotBeFound("originatorKYCLevel.lessThanOrEqual=" + SMALLER_ORIGINATOR_KYC_LEVEL);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorKYCLevelIsLessThanSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorKYCLevel is less than DEFAULT_ORIGINATOR_KYC_LEVEL
        defaultFundsTransferShouldNotBeFound("originatorKYCLevel.lessThan=" + DEFAULT_ORIGINATOR_KYC_LEVEL);

        // Get all the fundsTransferList where originatorKYCLevel is less than UPDATED_ORIGINATOR_KYC_LEVEL
        defaultFundsTransferShouldBeFound("originatorKYCLevel.lessThan=" + UPDATED_ORIGINATOR_KYC_LEVEL);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorKYCLevelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorKYCLevel is greater than DEFAULT_ORIGINATOR_KYC_LEVEL
        defaultFundsTransferShouldNotBeFound("originatorKYCLevel.greaterThan=" + DEFAULT_ORIGINATOR_KYC_LEVEL);

        // Get all the fundsTransferList where originatorKYCLevel is greater than SMALLER_ORIGINATOR_KYC_LEVEL
        defaultFundsTransferShouldBeFound("originatorKYCLevel.greaterThan=" + SMALLER_ORIGINATOR_KYC_LEVEL);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorNarrationIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorNarration equals to DEFAULT_ORIGINATOR_NARRATION
        defaultFundsTransferShouldBeFound("originatorNarration.equals=" + DEFAULT_ORIGINATOR_NARRATION);

        // Get all the fundsTransferList where originatorNarration equals to UPDATED_ORIGINATOR_NARRATION
        defaultFundsTransferShouldNotBeFound("originatorNarration.equals=" + UPDATED_ORIGINATOR_NARRATION);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorNarrationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorNarration not equals to DEFAULT_ORIGINATOR_NARRATION
        defaultFundsTransferShouldNotBeFound("originatorNarration.notEquals=" + DEFAULT_ORIGINATOR_NARRATION);

        // Get all the fundsTransferList where originatorNarration not equals to UPDATED_ORIGINATOR_NARRATION
        defaultFundsTransferShouldBeFound("originatorNarration.notEquals=" + UPDATED_ORIGINATOR_NARRATION);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorNarrationIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorNarration in DEFAULT_ORIGINATOR_NARRATION or UPDATED_ORIGINATOR_NARRATION
        defaultFundsTransferShouldBeFound("originatorNarration.in=" + DEFAULT_ORIGINATOR_NARRATION + "," + UPDATED_ORIGINATOR_NARRATION);

        // Get all the fundsTransferList where originatorNarration equals to UPDATED_ORIGINATOR_NARRATION
        defaultFundsTransferShouldNotBeFound("originatorNarration.in=" + UPDATED_ORIGINATOR_NARRATION);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorNarrationIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorNarration is not null
        defaultFundsTransferShouldBeFound("originatorNarration.specified=true");

        // Get all the fundsTransferList where originatorNarration is null
        defaultFundsTransferShouldNotBeFound("originatorNarration.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorNarrationContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorNarration contains DEFAULT_ORIGINATOR_NARRATION
        defaultFundsTransferShouldBeFound("originatorNarration.contains=" + DEFAULT_ORIGINATOR_NARRATION);

        // Get all the fundsTransferList where originatorNarration contains UPDATED_ORIGINATOR_NARRATION
        defaultFundsTransferShouldNotBeFound("originatorNarration.contains=" + UPDATED_ORIGINATOR_NARRATION);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByOriginatorNarrationNotContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where originatorNarration does not contain DEFAULT_ORIGINATOR_NARRATION
        defaultFundsTransferShouldNotBeFound("originatorNarration.doesNotContain=" + DEFAULT_ORIGINATOR_NARRATION);

        // Get all the fundsTransferList where originatorNarration does not contain UPDATED_ORIGINATOR_NARRATION
        defaultFundsTransferShouldBeFound("originatorNarration.doesNotContain=" + UPDATED_ORIGINATOR_NARRATION);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByTransactionLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where transactionLocation equals to DEFAULT_TRANSACTION_LOCATION
        defaultFundsTransferShouldBeFound("transactionLocation.equals=" + DEFAULT_TRANSACTION_LOCATION);

        // Get all the fundsTransferList where transactionLocation equals to UPDATED_TRANSACTION_LOCATION
        defaultFundsTransferShouldNotBeFound("transactionLocation.equals=" + UPDATED_TRANSACTION_LOCATION);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByTransactionLocationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where transactionLocation not equals to DEFAULT_TRANSACTION_LOCATION
        defaultFundsTransferShouldNotBeFound("transactionLocation.notEquals=" + DEFAULT_TRANSACTION_LOCATION);

        // Get all the fundsTransferList where transactionLocation not equals to UPDATED_TRANSACTION_LOCATION
        defaultFundsTransferShouldBeFound("transactionLocation.notEquals=" + UPDATED_TRANSACTION_LOCATION);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByTransactionLocationIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where transactionLocation in DEFAULT_TRANSACTION_LOCATION or UPDATED_TRANSACTION_LOCATION
        defaultFundsTransferShouldBeFound("transactionLocation.in=" + DEFAULT_TRANSACTION_LOCATION + "," + UPDATED_TRANSACTION_LOCATION);

        // Get all the fundsTransferList where transactionLocation equals to UPDATED_TRANSACTION_LOCATION
        defaultFundsTransferShouldNotBeFound("transactionLocation.in=" + UPDATED_TRANSACTION_LOCATION);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByTransactionLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where transactionLocation is not null
        defaultFundsTransferShouldBeFound("transactionLocation.specified=true");

        // Get all the fundsTransferList where transactionLocation is null
        defaultFundsTransferShouldNotBeFound("transactionLocation.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersByTransactionLocationContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where transactionLocation contains DEFAULT_TRANSACTION_LOCATION
        defaultFundsTransferShouldBeFound("transactionLocation.contains=" + DEFAULT_TRANSACTION_LOCATION);

        // Get all the fundsTransferList where transactionLocation contains UPDATED_TRANSACTION_LOCATION
        defaultFundsTransferShouldNotBeFound("transactionLocation.contains=" + UPDATED_TRANSACTION_LOCATION);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByTransactionLocationNotContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where transactionLocation does not contain DEFAULT_TRANSACTION_LOCATION
        defaultFundsTransferShouldNotBeFound("transactionLocation.doesNotContain=" + DEFAULT_TRANSACTION_LOCATION);

        // Get all the fundsTransferList where transactionLocation does not contain UPDATED_TRANSACTION_LOCATION
        defaultFundsTransferShouldBeFound("transactionLocation.doesNotContain=" + UPDATED_TRANSACTION_LOCATION);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByMandateReferenceNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where mandateReferenceNumber equals to DEFAULT_MANDATE_REFERENCE_NUMBER
        defaultFundsTransferShouldBeFound("mandateReferenceNumber.equals=" + DEFAULT_MANDATE_REFERENCE_NUMBER);

        // Get all the fundsTransferList where mandateReferenceNumber equals to UPDATED_MANDATE_REFERENCE_NUMBER
        defaultFundsTransferShouldNotBeFound("mandateReferenceNumber.equals=" + UPDATED_MANDATE_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByMandateReferenceNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where mandateReferenceNumber not equals to DEFAULT_MANDATE_REFERENCE_NUMBER
        defaultFundsTransferShouldNotBeFound("mandateReferenceNumber.notEquals=" + DEFAULT_MANDATE_REFERENCE_NUMBER);

        // Get all the fundsTransferList where mandateReferenceNumber not equals to UPDATED_MANDATE_REFERENCE_NUMBER
        defaultFundsTransferShouldBeFound("mandateReferenceNumber.notEquals=" + UPDATED_MANDATE_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByMandateReferenceNumberIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where mandateReferenceNumber in DEFAULT_MANDATE_REFERENCE_NUMBER or UPDATED_MANDATE_REFERENCE_NUMBER
        defaultFundsTransferShouldBeFound(
            "mandateReferenceNumber.in=" + DEFAULT_MANDATE_REFERENCE_NUMBER + "," + UPDATED_MANDATE_REFERENCE_NUMBER
        );

        // Get all the fundsTransferList where mandateReferenceNumber equals to UPDATED_MANDATE_REFERENCE_NUMBER
        defaultFundsTransferShouldNotBeFound("mandateReferenceNumber.in=" + UPDATED_MANDATE_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByMandateReferenceNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where mandateReferenceNumber is not null
        defaultFundsTransferShouldBeFound("mandateReferenceNumber.specified=true");

        // Get all the fundsTransferList where mandateReferenceNumber is null
        defaultFundsTransferShouldNotBeFound("mandateReferenceNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersByMandateReferenceNumberContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where mandateReferenceNumber contains DEFAULT_MANDATE_REFERENCE_NUMBER
        defaultFundsTransferShouldBeFound("mandateReferenceNumber.contains=" + DEFAULT_MANDATE_REFERENCE_NUMBER);

        // Get all the fundsTransferList where mandateReferenceNumber contains UPDATED_MANDATE_REFERENCE_NUMBER
        defaultFundsTransferShouldNotBeFound("mandateReferenceNumber.contains=" + UPDATED_MANDATE_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByMandateReferenceNumberNotContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where mandateReferenceNumber does not contain DEFAULT_MANDATE_REFERENCE_NUMBER
        defaultFundsTransferShouldNotBeFound("mandateReferenceNumber.doesNotContain=" + DEFAULT_MANDATE_REFERENCE_NUMBER);

        // Get all the fundsTransferList where mandateReferenceNumber does not contain UPDATED_MANDATE_REFERENCE_NUMBER
        defaultFundsTransferShouldBeFound("mandateReferenceNumber.doesNotContain=" + UPDATED_MANDATE_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBillerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where billerId equals to DEFAULT_BILLER_ID
        defaultFundsTransferShouldBeFound("billerId.equals=" + DEFAULT_BILLER_ID);

        // Get all the fundsTransferList where billerId equals to UPDATED_BILLER_ID
        defaultFundsTransferShouldNotBeFound("billerId.equals=" + UPDATED_BILLER_ID);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBillerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where billerId not equals to DEFAULT_BILLER_ID
        defaultFundsTransferShouldNotBeFound("billerId.notEquals=" + DEFAULT_BILLER_ID);

        // Get all the fundsTransferList where billerId not equals to UPDATED_BILLER_ID
        defaultFundsTransferShouldBeFound("billerId.notEquals=" + UPDATED_BILLER_ID);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBillerIdIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where billerId in DEFAULT_BILLER_ID or UPDATED_BILLER_ID
        defaultFundsTransferShouldBeFound("billerId.in=" + DEFAULT_BILLER_ID + "," + UPDATED_BILLER_ID);

        // Get all the fundsTransferList where billerId equals to UPDATED_BILLER_ID
        defaultFundsTransferShouldNotBeFound("billerId.in=" + UPDATED_BILLER_ID);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBillerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where billerId is not null
        defaultFundsTransferShouldBeFound("billerId.specified=true");

        // Get all the fundsTransferList where billerId is null
        defaultFundsTransferShouldNotBeFound("billerId.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBillerIdContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where billerId contains DEFAULT_BILLER_ID
        defaultFundsTransferShouldBeFound("billerId.contains=" + DEFAULT_BILLER_ID);

        // Get all the fundsTransferList where billerId contains UPDATED_BILLER_ID
        defaultFundsTransferShouldNotBeFound("billerId.contains=" + UPDATED_BILLER_ID);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByBillerIdNotContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where billerId does not contain DEFAULT_BILLER_ID
        defaultFundsTransferShouldNotBeFound("billerId.doesNotContain=" + DEFAULT_BILLER_ID);

        // Get all the fundsTransferList where billerId does not contain UPDATED_BILLER_ID
        defaultFundsTransferShouldBeFound("billerId.doesNotContain=" + UPDATED_BILLER_ID);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByPaymentReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where paymentReference equals to DEFAULT_PAYMENT_REFERENCE
        defaultFundsTransferShouldBeFound("paymentReference.equals=" + DEFAULT_PAYMENT_REFERENCE);

        // Get all the fundsTransferList where paymentReference equals to UPDATED_PAYMENT_REFERENCE
        defaultFundsTransferShouldNotBeFound("paymentReference.equals=" + UPDATED_PAYMENT_REFERENCE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByPaymentReferenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where paymentReference not equals to DEFAULT_PAYMENT_REFERENCE
        defaultFundsTransferShouldNotBeFound("paymentReference.notEquals=" + DEFAULT_PAYMENT_REFERENCE);

        // Get all the fundsTransferList where paymentReference not equals to UPDATED_PAYMENT_REFERENCE
        defaultFundsTransferShouldBeFound("paymentReference.notEquals=" + UPDATED_PAYMENT_REFERENCE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByPaymentReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where paymentReference in DEFAULT_PAYMENT_REFERENCE or UPDATED_PAYMENT_REFERENCE
        defaultFundsTransferShouldBeFound("paymentReference.in=" + DEFAULT_PAYMENT_REFERENCE + "," + UPDATED_PAYMENT_REFERENCE);

        // Get all the fundsTransferList where paymentReference equals to UPDATED_PAYMENT_REFERENCE
        defaultFundsTransferShouldNotBeFound("paymentReference.in=" + UPDATED_PAYMENT_REFERENCE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByPaymentReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where paymentReference is not null
        defaultFundsTransferShouldBeFound("paymentReference.specified=true");

        // Get all the fundsTransferList where paymentReference is null
        defaultFundsTransferShouldNotBeFound("paymentReference.specified=false");
    }

    @Test
    @Transactional
    void getAllFundsTransfersByPaymentReferenceContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where paymentReference contains DEFAULT_PAYMENT_REFERENCE
        defaultFundsTransferShouldBeFound("paymentReference.contains=" + DEFAULT_PAYMENT_REFERENCE);

        // Get all the fundsTransferList where paymentReference contains UPDATED_PAYMENT_REFERENCE
        defaultFundsTransferShouldNotBeFound("paymentReference.contains=" + UPDATED_PAYMENT_REFERENCE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByPaymentReferenceNotContainsSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where paymentReference does not contain DEFAULT_PAYMENT_REFERENCE
        defaultFundsTransferShouldNotBeFound("paymentReference.doesNotContain=" + DEFAULT_PAYMENT_REFERENCE);

        // Get all the fundsTransferList where paymentReference does not contain UPDATED_PAYMENT_REFERENCE
        defaultFundsTransferShouldBeFound("paymentReference.doesNotContain=" + UPDATED_PAYMENT_REFERENCE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where createdDate equals to DEFAULT_CREATED_DATE
        defaultFundsTransferShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the fundsTransferList where createdDate equals to UPDATED_CREATED_DATE
        defaultFundsTransferShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultFundsTransferShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the fundsTransferList where createdDate not equals to UPDATED_CREATED_DATE
        defaultFundsTransferShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultFundsTransferShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the fundsTransferList where createdDate equals to UPDATED_CREATED_DATE
        defaultFundsTransferShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllFundsTransfersByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        // Get all the fundsTransferList where createdDate is not null
        defaultFundsTransferShouldBeFound("createdDate.specified=true");

        // Get all the fundsTransferList where createdDate is null
        defaultFundsTransferShouldNotBeFound("createdDate.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFundsTransferShouldBeFound(String filter) throws Exception {
        restFundsTransferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fundsTransfer.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].responseCode").value(hasItem(DEFAULT_RESPONSE_CODE)))
            .andExpect(jsonPath("$.[*].sessionID").value(hasItem(DEFAULT_SESSION_ID)))
            .andExpect(jsonPath("$.[*].channelCode").value(hasItem(DEFAULT_CHANNEL_CODE)))
            .andExpect(jsonPath("$.[*].nameEnquiryRef").value(hasItem(DEFAULT_NAME_ENQUIRY_REF)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].destinationInstitutionCode").value(hasItem(DEFAULT_DESTINATION_INSTITUTION_CODE)))
            .andExpect(jsonPath("$.[*].beneficiaryAccountName").value(hasItem(DEFAULT_BENEFICIARY_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].beneficiaryAccountNumber").value(hasItem(DEFAULT_BENEFICIARY_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].beneficiaryKYCLevel").value(hasItem(DEFAULT_BENEFICIARY_KYC_LEVEL)))
            .andExpect(jsonPath("$.[*].beneficiaryBankVerificationNumber").value(hasItem(DEFAULT_BENEFICIARY_BANK_VERIFICATION_NUMBER)))
            .andExpect(jsonPath("$.[*].beneficiaryNarration").value(hasItem(DEFAULT_BENEFICIARY_NARRATION)))
            .andExpect(jsonPath("$.[*].originatorAccountName").value(hasItem(DEFAULT_ORIGINATOR_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].originatorAccountNumber").value(hasItem(DEFAULT_ORIGINATOR_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].originatorBankVerificationNumber").value(hasItem(DEFAULT_ORIGINATOR_BANK_VERIFICATION_NUMBER)))
            .andExpect(jsonPath("$.[*].originatorKYCLevel").value(hasItem(DEFAULT_ORIGINATOR_KYC_LEVEL)))
            .andExpect(jsonPath("$.[*].originatorNarration").value(hasItem(DEFAULT_ORIGINATOR_NARRATION)))
            .andExpect(jsonPath("$.[*].transactionLocation").value(hasItem(DEFAULT_TRANSACTION_LOCATION)))
            .andExpect(jsonPath("$.[*].mandateReferenceNumber").value(hasItem(DEFAULT_MANDATE_REFERENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].billerId").value(hasItem(DEFAULT_BILLER_ID)))
            .andExpect(jsonPath("$.[*].paymentReference").value(hasItem(DEFAULT_PAYMENT_REFERENCE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restFundsTransferMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFundsTransferShouldNotBeFound(String filter) throws Exception {
        restFundsTransferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFundsTransferMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFundsTransfer() throws Exception {
        // Get the fundsTransfer
        restFundsTransferMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFundsTransfer() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        int databaseSizeBeforeUpdate = fundsTransferRepository.findAll().size();

        // Update the fundsTransfer
        FundsTransfer updatedFundsTransfer = fundsTransferRepository.findById(fundsTransfer.getId()).get();
        // Disconnect from session so that the updates on updatedFundsTransfer are not directly saved in db
        em.detach(updatedFundsTransfer);
        updatedFundsTransfer
            .transactionId(UPDATED_TRANSACTION_ID)
            .responseCode(UPDATED_RESPONSE_CODE)
            .sessionID(UPDATED_SESSION_ID)
            .channelCode(UPDATED_CHANNEL_CODE)
            .nameEnquiryRef(UPDATED_NAME_ENQUIRY_REF)
            .amount(UPDATED_AMOUNT)
            .destinationInstitutionCode(UPDATED_DESTINATION_INSTITUTION_CODE)
            .beneficiaryAccountName(UPDATED_BENEFICIARY_ACCOUNT_NAME)
            .beneficiaryAccountNumber(UPDATED_BENEFICIARY_ACCOUNT_NUMBER)
            .beneficiaryKYCLevel(UPDATED_BENEFICIARY_KYC_LEVEL)
            .beneficiaryBankVerificationNumber(UPDATED_BENEFICIARY_BANK_VERIFICATION_NUMBER)
            .beneficiaryNarration(UPDATED_BENEFICIARY_NARRATION)
            .originatorAccountName(UPDATED_ORIGINATOR_ACCOUNT_NAME)
            .originatorAccountNumber(UPDATED_ORIGINATOR_ACCOUNT_NUMBER)
            .originatorBankVerificationNumber(UPDATED_ORIGINATOR_BANK_VERIFICATION_NUMBER)
            .originatorKYCLevel(UPDATED_ORIGINATOR_KYC_LEVEL)
            .originatorNarration(UPDATED_ORIGINATOR_NARRATION)
            .transactionLocation(UPDATED_TRANSACTION_LOCATION)
            .mandateReferenceNumber(UPDATED_MANDATE_REFERENCE_NUMBER)
            .billerId(UPDATED_BILLER_ID)
            .paymentReference(UPDATED_PAYMENT_REFERENCE)
            .createdDate(UPDATED_CREATED_DATE);
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(updatedFundsTransfer);

        restFundsTransferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fundsTransferDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isOk());

        // Validate the FundsTransfer in the database
        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeUpdate);
        FundsTransfer testFundsTransfer = fundsTransferList.get(fundsTransferList.size() - 1);
        assertThat(testFundsTransfer.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testFundsTransfer.getResponseCode()).isEqualTo(UPDATED_RESPONSE_CODE);
        assertThat(testFundsTransfer.getSessionID()).isEqualTo(UPDATED_SESSION_ID);
        assertThat(testFundsTransfer.getChannelCode()).isEqualTo(UPDATED_CHANNEL_CODE);
        assertThat(testFundsTransfer.getNameEnquiryRef()).isEqualTo(UPDATED_NAME_ENQUIRY_REF);
        assertThat(testFundsTransfer.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testFundsTransfer.getDestinationInstitutionCode()).isEqualTo(UPDATED_DESTINATION_INSTITUTION_CODE);
        assertThat(testFundsTransfer.getBeneficiaryAccountName()).isEqualTo(UPDATED_BENEFICIARY_ACCOUNT_NAME);
        assertThat(testFundsTransfer.getBeneficiaryAccountNumber()).isEqualTo(UPDATED_BENEFICIARY_ACCOUNT_NUMBER);
        assertThat(testFundsTransfer.getBeneficiaryKYCLevel()).isEqualTo(UPDATED_BENEFICIARY_KYC_LEVEL);
        assertThat(testFundsTransfer.getBeneficiaryBankVerificationNumber()).isEqualTo(UPDATED_BENEFICIARY_BANK_VERIFICATION_NUMBER);
        assertThat(testFundsTransfer.getBeneficiaryNarration()).isEqualTo(UPDATED_BENEFICIARY_NARRATION);
        assertThat(testFundsTransfer.getOriginatorAccountName()).isEqualTo(UPDATED_ORIGINATOR_ACCOUNT_NAME);
        assertThat(testFundsTransfer.getOriginatorAccountNumber()).isEqualTo(UPDATED_ORIGINATOR_ACCOUNT_NUMBER);
        assertThat(testFundsTransfer.getOriginatorBankVerificationNumber()).isEqualTo(UPDATED_ORIGINATOR_BANK_VERIFICATION_NUMBER);
        assertThat(testFundsTransfer.getOriginatorKYCLevel()).isEqualTo(UPDATED_ORIGINATOR_KYC_LEVEL);
        assertThat(testFundsTransfer.getOriginatorNarration()).isEqualTo(UPDATED_ORIGINATOR_NARRATION);
        assertThat(testFundsTransfer.getTransactionLocation()).isEqualTo(UPDATED_TRANSACTION_LOCATION);
        assertThat(testFundsTransfer.getMandateReferenceNumber()).isEqualTo(UPDATED_MANDATE_REFERENCE_NUMBER);
        assertThat(testFundsTransfer.getBillerId()).isEqualTo(UPDATED_BILLER_ID);
        assertThat(testFundsTransfer.getPaymentReference()).isEqualTo(UPDATED_PAYMENT_REFERENCE);
        assertThat(testFundsTransfer.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingFundsTransfer() throws Exception {
        int databaseSizeBeforeUpdate = fundsTransferRepository.findAll().size();
        fundsTransfer.setId(count.incrementAndGet());

        // Create the FundsTransfer
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFundsTransferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fundsTransferDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FundsTransfer in the database
        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFundsTransfer() throws Exception {
        int databaseSizeBeforeUpdate = fundsTransferRepository.findAll().size();
        fundsTransfer.setId(count.incrementAndGet());

        // Create the FundsTransfer
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFundsTransferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FundsTransfer in the database
        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFundsTransfer() throws Exception {
        int databaseSizeBeforeUpdate = fundsTransferRepository.findAll().size();
        fundsTransfer.setId(count.incrementAndGet());

        // Create the FundsTransfer
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFundsTransferMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FundsTransfer in the database
        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFundsTransferWithPatch() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        int databaseSizeBeforeUpdate = fundsTransferRepository.findAll().size();

        // Update the fundsTransfer using partial update
        FundsTransfer partialUpdatedFundsTransfer = new FundsTransfer();
        partialUpdatedFundsTransfer.setId(fundsTransfer.getId());

        partialUpdatedFundsTransfer
            .nameEnquiryRef(UPDATED_NAME_ENQUIRY_REF)
            .destinationInstitutionCode(UPDATED_DESTINATION_INSTITUTION_CODE)
            .beneficiaryKYCLevel(UPDATED_BENEFICIARY_KYC_LEVEL)
            .beneficiaryBankVerificationNumber(UPDATED_BENEFICIARY_BANK_VERIFICATION_NUMBER)
            .originatorAccountName(UPDATED_ORIGINATOR_ACCOUNT_NAME)
            .originatorBankVerificationNumber(UPDATED_ORIGINATOR_BANK_VERIFICATION_NUMBER)
            .originatorKYCLevel(UPDATED_ORIGINATOR_KYC_LEVEL)
            .transactionLocation(UPDATED_TRANSACTION_LOCATION)
            .billerId(UPDATED_BILLER_ID)
            .paymentReference(UPDATED_PAYMENT_REFERENCE);

        restFundsTransferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFundsTransfer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFundsTransfer))
            )
            .andExpect(status().isOk());

        // Validate the FundsTransfer in the database
        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeUpdate);
        FundsTransfer testFundsTransfer = fundsTransferList.get(fundsTransferList.size() - 1);
        assertThat(testFundsTransfer.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testFundsTransfer.getResponseCode()).isEqualTo(DEFAULT_RESPONSE_CODE);
        assertThat(testFundsTransfer.getSessionID()).isEqualTo(DEFAULT_SESSION_ID);
        assertThat(testFundsTransfer.getChannelCode()).isEqualTo(DEFAULT_CHANNEL_CODE);
        assertThat(testFundsTransfer.getNameEnquiryRef()).isEqualTo(UPDATED_NAME_ENQUIRY_REF);
        assertThat(testFundsTransfer.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testFundsTransfer.getDestinationInstitutionCode()).isEqualTo(UPDATED_DESTINATION_INSTITUTION_CODE);
        assertThat(testFundsTransfer.getBeneficiaryAccountName()).isEqualTo(DEFAULT_BENEFICIARY_ACCOUNT_NAME);
        assertThat(testFundsTransfer.getBeneficiaryAccountNumber()).isEqualTo(DEFAULT_BENEFICIARY_ACCOUNT_NUMBER);
        assertThat(testFundsTransfer.getBeneficiaryKYCLevel()).isEqualTo(UPDATED_BENEFICIARY_KYC_LEVEL);
        assertThat(testFundsTransfer.getBeneficiaryBankVerificationNumber()).isEqualTo(UPDATED_BENEFICIARY_BANK_VERIFICATION_NUMBER);
        assertThat(testFundsTransfer.getBeneficiaryNarration()).isEqualTo(DEFAULT_BENEFICIARY_NARRATION);
        assertThat(testFundsTransfer.getOriginatorAccountName()).isEqualTo(UPDATED_ORIGINATOR_ACCOUNT_NAME);
        assertThat(testFundsTransfer.getOriginatorAccountNumber()).isEqualTo(DEFAULT_ORIGINATOR_ACCOUNT_NUMBER);
        assertThat(testFundsTransfer.getOriginatorBankVerificationNumber()).isEqualTo(UPDATED_ORIGINATOR_BANK_VERIFICATION_NUMBER);
        assertThat(testFundsTransfer.getOriginatorKYCLevel()).isEqualTo(UPDATED_ORIGINATOR_KYC_LEVEL);
        assertThat(testFundsTransfer.getOriginatorNarration()).isEqualTo(DEFAULT_ORIGINATOR_NARRATION);
        assertThat(testFundsTransfer.getTransactionLocation()).isEqualTo(UPDATED_TRANSACTION_LOCATION);
        assertThat(testFundsTransfer.getMandateReferenceNumber()).isEqualTo(DEFAULT_MANDATE_REFERENCE_NUMBER);
        assertThat(testFundsTransfer.getBillerId()).isEqualTo(UPDATED_BILLER_ID);
        assertThat(testFundsTransfer.getPaymentReference()).isEqualTo(UPDATED_PAYMENT_REFERENCE);
        assertThat(testFundsTransfer.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateFundsTransferWithPatch() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        int databaseSizeBeforeUpdate = fundsTransferRepository.findAll().size();

        // Update the fundsTransfer using partial update
        FundsTransfer partialUpdatedFundsTransfer = new FundsTransfer();
        partialUpdatedFundsTransfer.setId(fundsTransfer.getId());

        partialUpdatedFundsTransfer
            .transactionId(UPDATED_TRANSACTION_ID)
            .responseCode(UPDATED_RESPONSE_CODE)
            .sessionID(UPDATED_SESSION_ID)
            .channelCode(UPDATED_CHANNEL_CODE)
            .nameEnquiryRef(UPDATED_NAME_ENQUIRY_REF)
            .amount(UPDATED_AMOUNT)
            .destinationInstitutionCode(UPDATED_DESTINATION_INSTITUTION_CODE)
            .beneficiaryAccountName(UPDATED_BENEFICIARY_ACCOUNT_NAME)
            .beneficiaryAccountNumber(UPDATED_BENEFICIARY_ACCOUNT_NUMBER)
            .beneficiaryKYCLevel(UPDATED_BENEFICIARY_KYC_LEVEL)
            .beneficiaryBankVerificationNumber(UPDATED_BENEFICIARY_BANK_VERIFICATION_NUMBER)
            .beneficiaryNarration(UPDATED_BENEFICIARY_NARRATION)
            .originatorAccountName(UPDATED_ORIGINATOR_ACCOUNT_NAME)
            .originatorAccountNumber(UPDATED_ORIGINATOR_ACCOUNT_NUMBER)
            .originatorBankVerificationNumber(UPDATED_ORIGINATOR_BANK_VERIFICATION_NUMBER)
            .originatorKYCLevel(UPDATED_ORIGINATOR_KYC_LEVEL)
            .originatorNarration(UPDATED_ORIGINATOR_NARRATION)
            .transactionLocation(UPDATED_TRANSACTION_LOCATION)
            .mandateReferenceNumber(UPDATED_MANDATE_REFERENCE_NUMBER)
            .billerId(UPDATED_BILLER_ID)
            .paymentReference(UPDATED_PAYMENT_REFERENCE)
            .createdDate(UPDATED_CREATED_DATE);

        restFundsTransferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFundsTransfer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFundsTransfer))
            )
            .andExpect(status().isOk());

        // Validate the FundsTransfer in the database
        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeUpdate);
        FundsTransfer testFundsTransfer = fundsTransferList.get(fundsTransferList.size() - 1);
        assertThat(testFundsTransfer.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testFundsTransfer.getResponseCode()).isEqualTo(UPDATED_RESPONSE_CODE);
        assertThat(testFundsTransfer.getSessionID()).isEqualTo(UPDATED_SESSION_ID);
        assertThat(testFundsTransfer.getChannelCode()).isEqualTo(UPDATED_CHANNEL_CODE);
        assertThat(testFundsTransfer.getNameEnquiryRef()).isEqualTo(UPDATED_NAME_ENQUIRY_REF);
        assertThat(testFundsTransfer.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testFundsTransfer.getDestinationInstitutionCode()).isEqualTo(UPDATED_DESTINATION_INSTITUTION_CODE);
        assertThat(testFundsTransfer.getBeneficiaryAccountName()).isEqualTo(UPDATED_BENEFICIARY_ACCOUNT_NAME);
        assertThat(testFundsTransfer.getBeneficiaryAccountNumber()).isEqualTo(UPDATED_BENEFICIARY_ACCOUNT_NUMBER);
        assertThat(testFundsTransfer.getBeneficiaryKYCLevel()).isEqualTo(UPDATED_BENEFICIARY_KYC_LEVEL);
        assertThat(testFundsTransfer.getBeneficiaryBankVerificationNumber()).isEqualTo(UPDATED_BENEFICIARY_BANK_VERIFICATION_NUMBER);
        assertThat(testFundsTransfer.getBeneficiaryNarration()).isEqualTo(UPDATED_BENEFICIARY_NARRATION);
        assertThat(testFundsTransfer.getOriginatorAccountName()).isEqualTo(UPDATED_ORIGINATOR_ACCOUNT_NAME);
        assertThat(testFundsTransfer.getOriginatorAccountNumber()).isEqualTo(UPDATED_ORIGINATOR_ACCOUNT_NUMBER);
        assertThat(testFundsTransfer.getOriginatorBankVerificationNumber()).isEqualTo(UPDATED_ORIGINATOR_BANK_VERIFICATION_NUMBER);
        assertThat(testFundsTransfer.getOriginatorKYCLevel()).isEqualTo(UPDATED_ORIGINATOR_KYC_LEVEL);
        assertThat(testFundsTransfer.getOriginatorNarration()).isEqualTo(UPDATED_ORIGINATOR_NARRATION);
        assertThat(testFundsTransfer.getTransactionLocation()).isEqualTo(UPDATED_TRANSACTION_LOCATION);
        assertThat(testFundsTransfer.getMandateReferenceNumber()).isEqualTo(UPDATED_MANDATE_REFERENCE_NUMBER);
        assertThat(testFundsTransfer.getBillerId()).isEqualTo(UPDATED_BILLER_ID);
        assertThat(testFundsTransfer.getPaymentReference()).isEqualTo(UPDATED_PAYMENT_REFERENCE);
        assertThat(testFundsTransfer.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingFundsTransfer() throws Exception {
        int databaseSizeBeforeUpdate = fundsTransferRepository.findAll().size();
        fundsTransfer.setId(count.incrementAndGet());

        // Create the FundsTransfer
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFundsTransferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fundsTransferDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FundsTransfer in the database
        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFundsTransfer() throws Exception {
        int databaseSizeBeforeUpdate = fundsTransferRepository.findAll().size();
        fundsTransfer.setId(count.incrementAndGet());

        // Create the FundsTransfer
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFundsTransferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FundsTransfer in the database
        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFundsTransfer() throws Exception {
        int databaseSizeBeforeUpdate = fundsTransferRepository.findAll().size();
        fundsTransfer.setId(count.incrementAndGet());

        // Create the FundsTransfer
        FundsTransferDTO fundsTransferDTO = fundsTransferMapper.toDto(fundsTransfer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFundsTransferMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fundsTransferDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FundsTransfer in the database
        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFundsTransfer() throws Exception {
        // Initialize the database
        fundsTransferRepository.saveAndFlush(fundsTransfer);

        int databaseSizeBeforeDelete = fundsTransferRepository.findAll().size();

        // Delete the fundsTransfer
        restFundsTransferMockMvc
            .perform(delete(ENTITY_API_URL_ID, fundsTransfer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FundsTransfer> fundsTransferList = fundsTransferRepository.findAll();
        assertThat(fundsTransferList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
