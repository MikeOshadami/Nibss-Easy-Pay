package com.systemspecs.remita.easypay.service;

import com.systemspecs.remita.easypay.domain.*; // for static metamodels
import com.systemspecs.remita.easypay.domain.FundsTransfer;
import com.systemspecs.remita.easypay.repository.FundsTransferRepository;
import com.systemspecs.remita.easypay.service.criteria.FundsTransferCriteria;
import com.systemspecs.remita.easypay.service.dto.FundsTransferDTO;
import com.systemspecs.remita.easypay.service.mapper.FundsTransferMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link FundsTransfer} entities in the database.
 * The main input is a {@link FundsTransferCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FundsTransferDTO} or a {@link Page} of {@link FundsTransferDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FundsTransferQueryService extends QueryService<FundsTransfer> {

    private final Logger log = LoggerFactory.getLogger(FundsTransferQueryService.class);

    private final FundsTransferRepository fundsTransferRepository;

    private final FundsTransferMapper fundsTransferMapper;

    public FundsTransferQueryService(FundsTransferRepository fundsTransferRepository, FundsTransferMapper fundsTransferMapper) {
        this.fundsTransferRepository = fundsTransferRepository;
        this.fundsTransferMapper = fundsTransferMapper;
    }

    /**
     * Return a {@link List} of {@link FundsTransferDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FundsTransferDTO> findByCriteria(FundsTransferCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FundsTransfer> specification = createSpecification(criteria);
        return fundsTransferMapper.toDto(fundsTransferRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FundsTransferDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FundsTransferDTO> findByCriteria(FundsTransferCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FundsTransfer> specification = createSpecification(criteria);
        return fundsTransferRepository.findAll(specification, page).map(fundsTransferMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FundsTransferCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FundsTransfer> specification = createSpecification(criteria);
        return fundsTransferRepository.count(specification);
    }

    /**
     * Function to convert {@link FundsTransferCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FundsTransfer> createSpecification(FundsTransferCriteria criteria) {
        Specification<FundsTransfer> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FundsTransfer_.id));
            }
            if (criteria.getTransactionId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTransactionId(), FundsTransfer_.transactionId));
            }
            if (criteria.getResponseCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResponseCode(), FundsTransfer_.responseCode));
            }
            if (criteria.getSessionID() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSessionID(), FundsTransfer_.sessionID));
            }
            if (criteria.getChannelCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getChannelCode(), FundsTransfer_.channelCode));
            }
            if (criteria.getNameEnquiryRef() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameEnquiryRef(), FundsTransfer_.nameEnquiryRef));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), FundsTransfer_.amount));
            }
            if (criteria.getDestinationInstitutionCode() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getDestinationInstitutionCode(), FundsTransfer_.destinationInstitutionCode)
                    );
            }
            if (criteria.getBeneficiaryAccountName() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getBeneficiaryAccountName(), FundsTransfer_.beneficiaryAccountName)
                    );
            }
            if (criteria.getBeneficiaryAccountNumber() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getBeneficiaryAccountNumber(), FundsTransfer_.beneficiaryAccountNumber)
                    );
            }
            if (criteria.getBeneficiaryKYCLevel() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getBeneficiaryKYCLevel(), FundsTransfer_.beneficiaryKYCLevel));
            }
            if (criteria.getBeneficiaryBankVerificationNumber() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getBeneficiaryBankVerificationNumber(),
                            FundsTransfer_.beneficiaryBankVerificationNumber
                        )
                    );
            }
            if (criteria.getBeneficiaryNarration() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getBeneficiaryNarration(), FundsTransfer_.beneficiaryNarration));
            }
            if (criteria.getOriginatorAccountName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getOriginatorAccountName(), FundsTransfer_.originatorAccountName));
            }
            if (criteria.getOriginatorAccountNumber() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getOriginatorAccountNumber(), FundsTransfer_.originatorAccountNumber)
                    );
            }
            if (criteria.getOriginatorBankVerificationNumber() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getOriginatorBankVerificationNumber(),
                            FundsTransfer_.originatorBankVerificationNumber
                        )
                    );
            }
            if (criteria.getOriginatorKYCLevel() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getOriginatorKYCLevel(), FundsTransfer_.originatorKYCLevel));
            }
            if (criteria.getOriginatorNarration() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getOriginatorNarration(), FundsTransfer_.originatorNarration));
            }
            if (criteria.getTransactionLocation() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getTransactionLocation(), FundsTransfer_.transactionLocation));
            }
            if (criteria.getMandateReferenceNumber() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getMandateReferenceNumber(), FundsTransfer_.mandateReferenceNumber)
                    );
            }
            if (criteria.getBillerId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBillerId(), FundsTransfer_.billerId));
            }
            if (criteria.getPaymentReference() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPaymentReference(), FundsTransfer_.paymentReference));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), FundsTransfer_.createdDate));
            }
        }
        return specification;
    }
}
