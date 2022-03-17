package com.techvg.inventory.management.web.rest;

import com.techvg.inventory.management.repository.TransferDetailsRepository;
import com.techvg.inventory.management.service.TransferDetailsQueryService;
import com.techvg.inventory.management.service.TransferDetailsService;
import com.techvg.inventory.management.service.criteria.TransferDetailsCriteria;
import com.techvg.inventory.management.service.dto.TransferDetailsDTO;
import com.techvg.inventory.management.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.techvg.inventory.management.domain.TransferDetails}.
 */
@RestController
@RequestMapping("/api")
public class TransferDetailsResource {

    private final Logger log = LoggerFactory.getLogger(TransferDetailsResource.class);

    private static final String ENTITY_NAME = "transferDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransferDetailsService transferDetailsService;

    private final TransferDetailsRepository transferDetailsRepository;

    private final TransferDetailsQueryService transferDetailsQueryService;

    public TransferDetailsResource(
        TransferDetailsService transferDetailsService,
        TransferDetailsRepository transferDetailsRepository,
        TransferDetailsQueryService transferDetailsQueryService
    ) {
        this.transferDetailsService = transferDetailsService;
        this.transferDetailsRepository = transferDetailsRepository;
        this.transferDetailsQueryService = transferDetailsQueryService;
    }

    /**
     * {@code POST  /transfer-details} : Create a new transferDetails.
     *
     * @param transferDetailsDTO the transferDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transferDetailsDTO, or with status {@code 400 (Bad Request)} if the transferDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transfer-details")
    public ResponseEntity<TransferDetailsDTO> createTransferDetails(@RequestBody TransferDetailsDTO transferDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save TransferDetails : {}", transferDetailsDTO);
        if (transferDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new transferDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransferDetailsDTO result = transferDetailsService.save(transferDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/transfer-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transfer-details/:id} : Updates an existing transferDetails.
     *
     * @param id the id of the transferDetailsDTO to save.
     * @param transferDetailsDTO the transferDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the transferDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transferDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transfer-details/{id}")
    public ResponseEntity<TransferDetailsDTO> updateTransferDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransferDetailsDTO transferDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TransferDetails : {}, {}", id, transferDetailsDTO);
        if (transferDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransferDetailsDTO result = transferDetailsService.save(transferDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transferDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transfer-details/:id} : Partial updates given fields of an existing transferDetails, field will ignore if it is null
     *
     * @param id the id of the transferDetailsDTO to save.
     * @param transferDetailsDTO the transferDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the transferDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the transferDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the transferDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transfer-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TransferDetailsDTO> partialUpdateTransferDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransferDetailsDTO transferDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TransferDetails partially : {}, {}", id, transferDetailsDTO);
        if (transferDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransferDetailsDTO> result = transferDetailsService.partialUpdate(transferDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transferDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /transfer-details} : get all the transferDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transferDetails in body.
     */
    @GetMapping("/transfer-details")
    public ResponseEntity<List<TransferDetailsDTO>> getAllTransferDetails(
        TransferDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TransferDetails by criteria: {}", criteria);
        Page<TransferDetailsDTO> page = transferDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transfer-details/count} : count all the transferDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/transfer-details/count")
    public ResponseEntity<Long> countTransferDetails(TransferDetailsCriteria criteria) {
        log.debug("REST request to count TransferDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(transferDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transfer-details/:id} : get the "id" transferDetails.
     *
     * @param id the id of the transferDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transferDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transfer-details/{id}")
    public ResponseEntity<TransferDetailsDTO> getTransferDetails(@PathVariable Long id) {
        log.debug("REST request to get TransferDetails : {}", id);
        Optional<TransferDetailsDTO> transferDetailsDTO = transferDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transferDetailsDTO);
    }

    /**
     * {@code DELETE  /transfer-details/:id} : delete the "id" transferDetails.
     *
     * @param id the id of the transferDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transfer-details/{id}")
    public ResponseEntity<Void> deleteTransferDetails(@PathVariable Long id) {
        log.debug("REST request to delete TransferDetails : {}", id);
        transferDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
