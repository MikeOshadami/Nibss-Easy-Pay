package com.systemspecs.remita.easypay.service.mapper;

import com.systemspecs.remita.easypay.domain.FundsTransfer;
import com.systemspecs.remita.easypay.service.dto.FundsTransferDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FundsTransfer} and its DTO {@link FundsTransferDTO}.
 */
@Mapper(componentModel = "spring")
public interface FundsTransferMapper extends EntityMapper<FundsTransferDTO, FundsTransfer> {}
