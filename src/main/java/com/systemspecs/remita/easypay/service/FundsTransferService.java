package com.systemspecs.remita.easypay.service;

import com.systemspecs.remita.easypay.domain.FundsTransfer;
import com.systemspecs.remita.easypay.repository.FundsTransferRepository;
import com.systemspecs.remita.easypay.service.dto.FundsTransferDTO;
import com.systemspecs.remita.easypay.service.mapper.FundsTransferMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FundsTransfer}.
 */
@Service
@Transactional
public class FundsTransferService {

    private final Logger log = LoggerFactory.getLogger(FundsTransferService.class);

    private final FundsTransferRepository fundsTransferRepository;

    private final FundsTransferMapper fundsTransferMapper;

    public FundsTransferService(FundsTransferRepository fundsTransferRepository, FundsTransferMapper fundsTransferMapper) {
        this.fundsTransferRepository = fundsTransferRepository;
        this.fundsTransferMapper = fundsTransferMapper;
    }

    /**
     * Save a fundsTransfer.
     *
     * @param fundsTransferDTO the entity to save.
     * @return the persisted entity.
     */
    public FundsTransferDTO save(FundsTransferDTO fundsTransferDTO) {
        log.debug("Request to save FundsTransfer : {}", fundsTransferDTO);
        FundsTransfer fundsTransfer = fundsTransferMapper.toEntity(fundsTransferDTO);
        fundsTransfer = fundsTransferRepository.save(fundsTransfer);
        return fundsTransferMapper.toDto(fundsTransfer);
    }

    /**
     * Update a fundsTransfer.
     *
     * @param fundsTransferDTO the entity to save.
     * @return the persisted entity.
     */
    public FundsTransferDTO update(FundsTransferDTO fundsTransferDTO) {
        log.debug("Request to save FundsTransfer : {}", fundsTransferDTO);
        FundsTransfer fundsTransfer = fundsTransferMapper.toEntity(fundsTransferDTO);
        fundsTransfer = fundsTransferRepository.save(fundsTransfer);
        return fundsTransferMapper.toDto(fundsTransfer);
    }

    /**
     * Partially update a fundsTransfer.
     *
     * @param fundsTransferDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FundsTransferDTO> partialUpdate(FundsTransferDTO fundsTransferDTO) {
        log.debug("Request to partially update FundsTransfer : {}", fundsTransferDTO);

        return fundsTransferRepository
            .findById(fundsTransferDTO.getId())
            .map(existingFundsTransfer -> {
                fundsTransferMapper.partialUpdate(existingFundsTransfer, fundsTransferDTO);

                return existingFundsTransfer;
            })
            .map(fundsTransferRepository::save)
            .map(fundsTransferMapper::toDto);
    }

    /**
     * Get all the fundsTransfers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FundsTransferDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FundsTransfers");
        return fundsTransferRepository.findAll(pageable).map(fundsTransferMapper::toDto);
    }

    /**
     * Get one fundsTransfer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FundsTransferDTO> findOne(Long id) {
        log.debug("Request to get FundsTransfer : {}", id);
        return fundsTransferRepository.findById(id).map(fundsTransferMapper::toDto);
    }

    /**
     * Delete the fundsTransfer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FundsTransfer : {}", id);
        fundsTransferRepository.deleteById(id);
    }
}
