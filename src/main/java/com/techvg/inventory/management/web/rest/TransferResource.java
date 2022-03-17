package com.techvg.inventory.management.web.rest;

import com.techvg.inventory.management.repository.TransferRepository;
import com.techvg.inventory.management.service.TransferQueryService;
import com.techvg.inventory.management.service.TransferService;
import com.techvg.inventory.management.service.criteria.TransferCriteria;
import com.techvg.inventory.management.service.dto.TransferDTO;
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
 * REST controller for managing {@link com.techvg.inventory.management.domain.Transfer}.
 */
@RestController
@RequestMapping("/api")
public class TransferResource {

    private final Logger log = LoggerFactory.getLogger(TransferResource.class);

    private static final String ENTITY_NAME = "transfer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransferService transferService;

    private final TransferRepository transferRepository;

    private final TransferQueryService transferQueryService;

    public TransferResource(
        TransferService transferService,
        TransferRepository transferRepository,
        TransferQueryService transferQueryService
    ) {
        this.transferService = transferService;
        this.transferRepository = transferRepository;
        this.transferQueryService = transferQueryService;
    }

    /**
     * {@code POST  /transfers} : Create a new transfer.
     *
     * @param transferDTO the transferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transferDTO, or with status {@code 400 (Bad Request)} if the transfer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transfers")
    public ResponseEntity<TransferDTO> createTransfer(@RequestBody TransferDTO transferDTO) throws URISyntaxException {
        log.debug("REST request to save Transfer : {}", transferDTO);
        if (transferDTO.getId() != null) {
            throw new BadRequestAlertException("A new transfer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransferDTO result = transferService.save(transferDTO);
        return ResponseEntity
            .created(new URI("/api/transfers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transfers/:id} : Updates an existing transfer.
     *
     * @param id the id of the transferDTO to save.
     * @param transferDTO the transferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferDTO,
     * or with status {@code 400 (Bad Request)} if the transferDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transferDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transfers/{id}")
    public ResponseEntity<TransferDTO> updateTransfer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransferDTO transferDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Transfer : {}, {}", id, transferDTO);
        if (transferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransferDTO result = transferService.save(transferDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transferDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transfers/:id} : Partial updates given fields of an existing transfer, field will ignore if it is null
     *
     * @param id the id of the transferDTO to save.
     * @param transferDTO the transferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferDTO,
     * or with status {@code 400 (Bad Request)} if the transferDTO is not valid,
     * or with status {@code 404 (Not Found)} if the transferDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the transferDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transfers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TransferDTO> partialUpdateTransfer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransferDTO transferDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Transfer partially : {}, {}", id, transferDTO);
        if (transferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransferDTO> result = transferService.partialUpdate(transferDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transferDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /transfers} : get all the transfers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transfers in body.
     */
    @GetMapping("/transfers")
    public ResponseEntity<List<TransferDTO>> getAllTransfers(
        TransferCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Transfers by criteria: {}", criteria);
        Page<TransferDTO> page = transferQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transfers/count} : count all the transfers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/transfers/count")
    public ResponseEntity<Long> countTransfers(TransferCriteria criteria) {
        log.debug("REST request to count Transfers by criteria: {}", criteria);
        return ResponseEntity.ok().body(transferQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transfers/:id} : get the "id" transfer.
     *
     * @param id the id of the transferDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transferDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transfers/{id}")
    public ResponseEntity<TransferDTO> getTransfer(@PathVariable Long id) {
        log.debug("REST request to get Transfer : {}", id);
        Optional<TransferDTO> transferDTO = transferService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transferDTO);
    }

    /**
     * {@code DELETE  /transfers/:id} : delete the "id" transfer.
     *
     * @param id the id of the transferDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transfers/{id}")
    public ResponseEntity<Void> deleteTransfer(@PathVariable Long id) {
        log.debug("REST request to delete Transfer : {}", id);
        transferService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
