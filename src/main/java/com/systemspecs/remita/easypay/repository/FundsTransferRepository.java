package com.systemspecs.remita.easypay.repository;

import com.systemspecs.remita.easypay.domain.FundsTransfer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FundsTransfer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FundsTransferRepository extends JpaRepository<FundsTransfer, Long>, JpaSpecificationExecutor<FundsTransfer> {}
