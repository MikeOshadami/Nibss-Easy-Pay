package com.systemspecs.remita.easypay.web.rest;

import com.systemspecs.remita.easypay.repository.FundsTransferRepository;
import com.systemspecs.remita.easypay.service.FundsTransferQueryService;
import com.systemspecs.remita.easypay.service.FundsTransferService;
import com.systemspecs.remita.easypay.service.criteria.FundsTransferCriteria;
import com.systemspecs.remita.easypay.service.dto.FundsTransferDTO;
import com.systemspecs.remita.easypay.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.systemspecs.remita.easypay.domain.FundsTransfer}.
 */
@RestController
@RequestMapping("/api")
public class FundsTransferResource {

    private final Logger log = LoggerFactory.getLogger(FundsTransferResource.class);

    private static final String ENTITY_NAME = "nibsseasypayFundsTransfer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FundsTransferService fundsTransferService;

    private final FundsTransferRepository fundsTransferRepository;

    private final FundsTransferQueryService fundsTransferQueryService;

    public FundsTransferResource(
        FundsTransferService fundsTransferService,
        FundsTransferRepository fundsTransferRepository,
        FundsTransferQueryService fundsTransferQueryService
    ) {
        this.fundsTransferService = fundsTransferService;
        this.fundsTransferRepository = fundsTransferRepository;
        this.fundsTransferQueryService = fundsTransferQueryService;
    }

    /**
     * {@code POST  /funds-transfers} : Create a new fundsTransfer.
     *
     * @param fundsTransferDTO the fundsTransferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fundsTransferDTO, or with status {@code 400 (Bad Request)} if the fundsTransfer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/funds-transfers")
    public ResponseEntity<FundsTransferDTO> createFundsTransfer(@Valid @RequestBody FundsTransferDTO fundsTransferDTO)
        throws URISyntaxException {
        log.debug("REST request to save FundsTransfer : {}", fundsTransferDTO);
        if (fundsTransferDTO.getId() != null) {
            throw new BadRequestAlertException("A new fundsTransfer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FundsTransferDTO result = fundsTransferService.save(fundsTransferDTO);
        return ResponseEntity
            .created(new URI("/api/funds-transfers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /funds-transfers/:id} : Updates an existing fundsTransfer.
     *
     * @param id the id of the fundsTransferDTO to save.
     * @param fundsTransferDTO the fundsTransferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fundsTransferDTO,
     * or with status {@code 400 (Bad Request)} if the fundsTransferDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fundsTransferDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/funds-transfers/{id}")
    public ResponseEntity<FundsTransferDTO> updateFundsTransfer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FundsTransferDTO fundsTransferDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FundsTransfer : {}, {}", id, fundsTransferDTO);
        if (fundsTransferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fundsTransferDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fundsTransferRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FundsTransferDTO result = fundsTransferService.update(fundsTransferDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fundsTransferDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /funds-transfers/:id} : Partial updates given fields of an existing fundsTransfer, field will ignore if it is null
     *
     * @param id the id of the fundsTransferDTO to save.
     * @param fundsTransferDTO the fundsTransferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fundsTransferDTO,
     * or with status {@code 400 (Bad Request)} if the fundsTransferDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fundsTransferDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fundsTransferDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/funds-transfers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FundsTransferDTO> partialUpdateFundsTransfer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FundsTransferDTO fundsTransferDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FundsTransfer partially : {}, {}", id, fundsTransferDTO);
        if (fundsTransferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fundsTransferDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fundsTransferRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FundsTransferDTO> result = fundsTransferService.partialUpdate(fundsTransferDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fundsTransferDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /funds-transfers} : get all the fundsTransfers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fundsTransfers in body.
     */
    @GetMapping("/funds-transfers")
    public ResponseEntity<List<FundsTransferDTO>> getAllFundsTransfers(
        FundsTransferCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FundsTransfers by criteria: {}", criteria);
        Page<FundsTransferDTO> page = fundsTransferQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /funds-transfers/count} : count all the fundsTransfers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/funds-transfers/count")
    public ResponseEntity<Long> countFundsTransfers(FundsTransferCriteria criteria) {
        log.debug("REST request to count FundsTransfers by criteria: {}", criteria);
        return ResponseEntity.ok().body(fundsTransferQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /funds-transfers/:id} : get the "id" fundsTransfer.
     *
     * @param id the id of the fundsTransferDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fundsTransferDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/funds-transfers/{id}")
    public ResponseEntity<FundsTransferDTO> getFundsTransfer(@PathVariable Long id) {
        log.debug("REST request to get FundsTransfer : {}", id);
        Optional<FundsTransferDTO> fundsTransferDTO = fundsTransferService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fundsTransferDTO);
    }

    /**
     * {@code DELETE  /funds-transfers/:id} : delete the "id" fundsTransfer.
     *
     * @param id the id of the fundsTransferDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/funds-transfers/{id}")
    public ResponseEntity<Void> deleteFundsTransfer(@PathVariable Long id) {
        log.debug("REST request to delete FundsTransfer : {}", id);
        fundsTransferService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
