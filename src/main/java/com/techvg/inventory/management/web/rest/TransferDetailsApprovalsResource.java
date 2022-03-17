package com.techvg.inventory.management.web.rest;

import com.techvg.inventory.management.repository.TransferDetailsApprovalsRepository;
import com.techvg.inventory.management.service.TransferDetailsApprovalsQueryService;
import com.techvg.inventory.management.service.TransferDetailsApprovalsService;
import com.techvg.inventory.management.service.criteria.TransferDetailsApprovalsCriteria;
import com.techvg.inventory.management.service.dto.TransferDetailsApprovalsDTO;
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
 * REST controller for managing {@link com.techvg.inventory.management.domain.TransferDetailsApprovals}.
 */
@RestController
@RequestMapping("/api")
public class TransferDetailsApprovalsResource {

    private final Logger log = LoggerFactory.getLogger(TransferDetailsApprovalsResource.class);

    private static final String ENTITY_NAME = "transferDetailsApprovals";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransferDetailsApprovalsService transferDetailsApprovalsService;

    private final TransferDetailsApprovalsRepository transferDetailsApprovalsRepository;

    private final TransferDetailsApprovalsQueryService transferDetailsApprovalsQueryService;

    public TransferDetailsApprovalsResource(
        TransferDetailsApprovalsService transferDetailsApprovalsService,
        TransferDetailsApprovalsRepository transferDetailsApprovalsRepository,
        TransferDetailsApprovalsQueryService transferDetailsApprovalsQueryService
    ) {
        this.transferDetailsApprovalsService = transferDetailsApprovalsService;
        this.transferDetailsApprovalsRepository = transferDetailsApprovalsRepository;
        this.transferDetailsApprovalsQueryService = transferDetailsApprovalsQueryService;
    }

    /**
     * {@code POST  /transfer-details-approvals} : Create a new transferDetailsApprovals.
     *
     * @param transferDetailsApprovalsDTO the transferDetailsApprovalsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transferDetailsApprovalsDTO, or with status {@code 400 (Bad Request)} if the transferDetailsApprovals has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transfer-details-approvals")
    public ResponseEntity<TransferDetailsApprovalsDTO> createTransferDetailsApprovals(
        @RequestBody TransferDetailsApprovalsDTO transferDetailsApprovalsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save TransferDetailsApprovals : {}", transferDetailsApprovalsDTO);
        if (transferDetailsApprovalsDTO.getId() != null) {
            throw new BadRequestAlertException("A new transferDetailsApprovals cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransferDetailsApprovalsDTO result = transferDetailsApprovalsService.save(transferDetailsApprovalsDTO);
        return ResponseEntity
            .created(new URI("/api/transfer-details-approvals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transfer-details-approvals/:id} : Updates an existing transferDetailsApprovals.
     *
     * @param id the id of the transferDetailsApprovalsDTO to save.
     * @param transferDetailsApprovalsDTO the transferDetailsApprovalsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferDetailsApprovalsDTO,
     * or with status {@code 400 (Bad Request)} if the transferDetailsApprovalsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transferDetailsApprovalsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transfer-details-approvals/{id}")
    public ResponseEntity<TransferDetailsApprovalsDTO> updateTransferDetailsApprovals(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransferDetailsApprovalsDTO transferDetailsApprovalsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TransferDetailsApprovals : {}, {}", id, transferDetailsApprovalsDTO);
        if (transferDetailsApprovalsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferDetailsApprovalsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferDetailsApprovalsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransferDetailsApprovalsDTO result = transferDetailsApprovalsService.save(transferDetailsApprovalsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transferDetailsApprovalsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transfer-details-approvals/:id} : Partial updates given fields of an existing transferDetailsApprovals, field will ignore if it is null
     *
     * @param id the id of the transferDetailsApprovalsDTO to save.
     * @param transferDetailsApprovalsDTO the transferDetailsApprovalsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferDetailsApprovalsDTO,
     * or with status {@code 400 (Bad Request)} if the transferDetailsApprovalsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the transferDetailsApprovalsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the transferDetailsApprovalsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transfer-details-approvals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TransferDetailsApprovalsDTO> partialUpdateTransferDetailsApprovals(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransferDetailsApprovalsDTO transferDetailsApprovalsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TransferDetailsApprovals partially : {}, {}", id, transferDetailsApprovalsDTO);
        if (transferDetailsApprovalsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferDetailsApprovalsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferDetailsApprovalsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransferDetailsApprovalsDTO> result = transferDetailsApprovalsService.partialUpdate(transferDetailsApprovalsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transferDetailsApprovalsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /transfer-details-approvals} : get all the transferDetailsApprovals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transferDetailsApprovals in body.
     */
    @GetMapping("/transfer-details-approvals")
    public ResponseEntity<List<TransferDetailsApprovalsDTO>> getAllTransferDetailsApprovals(
        TransferDetailsApprovalsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TransferDetailsApprovals by criteria: {}", criteria);
        Page<TransferDetailsApprovalsDTO> page = transferDetailsApprovalsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transfer-details-approvals/count} : count all the transferDetailsApprovals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/transfer-details-approvals/count")
    public ResponseEntity<Long> countTransferDetailsApprovals(TransferDetailsApprovalsCriteria criteria) {
        log.debug("REST request to count TransferDetailsApprovals by criteria: {}", criteria);
        return ResponseEntity.ok().body(transferDetailsApprovalsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transfer-details-approvals/:id} : get the "id" transferDetailsApprovals.
     *
     * @param id the id of the transferDetailsApprovalsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transferDetailsApprovalsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transfer-details-approvals/{id}")
    public ResponseEntity<TransferDetailsApprovalsDTO> getTransferDetailsApprovals(@PathVariable Long id) {
        log.debug("REST request to get TransferDetailsApprovals : {}", id);
        Optional<TransferDetailsApprovalsDTO> transferDetailsApprovalsDTO = transferDetailsApprovalsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transferDetailsApprovalsDTO);
    }

    /**
     * {@code DELETE  /transfer-details-approvals/:id} : delete the "id" transferDetailsApprovals.
     *
     * @param id the id of the transferDetailsApprovalsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transfer-details-approvals/{id}")
    public ResponseEntity<Void> deleteTransferDetailsApprovals(@PathVariable Long id) {
        log.debug("REST request to delete TransferDetailsApprovals : {}", id);
        transferDetailsApprovalsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
