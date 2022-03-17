package com.techvg.inventory.management.web.rest;

import com.techvg.inventory.management.repository.TransferRecievedRepository;
import com.techvg.inventory.management.service.TransferRecievedQueryService;
import com.techvg.inventory.management.service.TransferRecievedService;
import com.techvg.inventory.management.service.criteria.TransferRecievedCriteria;
import com.techvg.inventory.management.service.dto.TransferRecievedDTO;
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
 * REST controller for managing {@link com.techvg.inventory.management.domain.TransferRecieved}.
 */
@RestController
@RequestMapping("/api")
public class TransferRecievedResource {

    private final Logger log = LoggerFactory.getLogger(TransferRecievedResource.class);

    private static final String ENTITY_NAME = "transferRecieved";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransferRecievedService transferRecievedService;

    private final TransferRecievedRepository transferRecievedRepository;

    private final TransferRecievedQueryService transferRecievedQueryService;

    public TransferRecievedResource(
        TransferRecievedService transferRecievedService,
        TransferRecievedRepository transferRecievedRepository,
        TransferRecievedQueryService transferRecievedQueryService
    ) {
        this.transferRecievedService = transferRecievedService;
        this.transferRecievedRepository = transferRecievedRepository;
        this.transferRecievedQueryService = transferRecievedQueryService;
    }

    /**
     * {@code POST  /transfer-recieveds} : Create a new transferRecieved.
     *
     * @param transferRecievedDTO the transferRecievedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transferRecievedDTO, or with status {@code 400 (Bad Request)} if the transferRecieved has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transfer-recieveds")
    public ResponseEntity<TransferRecievedDTO> createTransferRecieved(@RequestBody TransferRecievedDTO transferRecievedDTO)
        throws URISyntaxException {
        log.debug("REST request to save TransferRecieved : {}", transferRecievedDTO);
        if (transferRecievedDTO.getId() != null) {
            throw new BadRequestAlertException("A new transferRecieved cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransferRecievedDTO result = transferRecievedService.save(transferRecievedDTO);
        return ResponseEntity
            .created(new URI("/api/transfer-recieveds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transfer-recieveds/:id} : Updates an existing transferRecieved.
     *
     * @param id the id of the transferRecievedDTO to save.
     * @param transferRecievedDTO the transferRecievedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferRecievedDTO,
     * or with status {@code 400 (Bad Request)} if the transferRecievedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transferRecievedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transfer-recieveds/{id}")
    public ResponseEntity<TransferRecievedDTO> updateTransferRecieved(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransferRecievedDTO transferRecievedDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TransferRecieved : {}, {}", id, transferRecievedDTO);
        if (transferRecievedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferRecievedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferRecievedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransferRecievedDTO result = transferRecievedService.save(transferRecievedDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transferRecievedDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transfer-recieveds/:id} : Partial updates given fields of an existing transferRecieved, field will ignore if it is null
     *
     * @param id the id of the transferRecievedDTO to save.
     * @param transferRecievedDTO the transferRecievedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferRecievedDTO,
     * or with status {@code 400 (Bad Request)} if the transferRecievedDTO is not valid,
     * or with status {@code 404 (Not Found)} if the transferRecievedDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the transferRecievedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transfer-recieveds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TransferRecievedDTO> partialUpdateTransferRecieved(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TransferRecievedDTO transferRecievedDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TransferRecieved partially : {}, {}", id, transferRecievedDTO);
        if (transferRecievedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferRecievedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferRecievedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransferRecievedDTO> result = transferRecievedService.partialUpdate(transferRecievedDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transferRecievedDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /transfer-recieveds} : get all the transferRecieveds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transferRecieveds in body.
     */
    @GetMapping("/transfer-recieveds")
    public ResponseEntity<List<TransferRecievedDTO>> getAllTransferRecieveds(
        TransferRecievedCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TransferRecieveds by criteria: {}", criteria);
        Page<TransferRecievedDTO> page = transferRecievedQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transfer-recieveds/count} : count all the transferRecieveds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/transfer-recieveds/count")
    public ResponseEntity<Long> countTransferRecieveds(TransferRecievedCriteria criteria) {
        log.debug("REST request to count TransferRecieveds by criteria: {}", criteria);
        return ResponseEntity.ok().body(transferRecievedQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transfer-recieveds/:id} : get the "id" transferRecieved.
     *
     * @param id the id of the transferRecievedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transferRecievedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transfer-recieveds/{id}")
    public ResponseEntity<TransferRecievedDTO> getTransferRecieved(@PathVariable Long id) {
        log.debug("REST request to get TransferRecieved : {}", id);
        Optional<TransferRecievedDTO> transferRecievedDTO = transferRecievedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transferRecievedDTO);
    }

    /**
     * {@code DELETE  /transfer-recieveds/:id} : delete the "id" transferRecieved.
     *
     * @param id the id of the transferRecievedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transfer-recieveds/{id}")
    public ResponseEntity<Void> deleteTransferRecieved(@PathVariable Long id) {
        log.debug("REST request to delete TransferRecieved : {}", id);
        transferRecievedService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
