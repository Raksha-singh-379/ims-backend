package com.techvg.inventory.management.web.rest;

import com.techvg.inventory.management.repository.ConsumptionDetailsRepository;
import com.techvg.inventory.management.service.ConsumptionDetailsQueryService;
import com.techvg.inventory.management.service.ConsumptionDetailsService;
import com.techvg.inventory.management.service.criteria.ConsumptionDetailsCriteria;
import com.techvg.inventory.management.service.dto.ConsumptionDetailsDTO;
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
 * REST controller for managing {@link com.techvg.inventory.management.domain.ConsumptionDetails}.
 */
@RestController
@RequestMapping("/api")
public class ConsumptionDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ConsumptionDetailsResource.class);

    private static final String ENTITY_NAME = "consumptionDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsumptionDetailsService consumptionDetailsService;

    private final ConsumptionDetailsRepository consumptionDetailsRepository;

    private final ConsumptionDetailsQueryService consumptionDetailsQueryService;

    public ConsumptionDetailsResource(
        ConsumptionDetailsService consumptionDetailsService,
        ConsumptionDetailsRepository consumptionDetailsRepository,
        ConsumptionDetailsQueryService consumptionDetailsQueryService
    ) {
        this.consumptionDetailsService = consumptionDetailsService;
        this.consumptionDetailsRepository = consumptionDetailsRepository;
        this.consumptionDetailsQueryService = consumptionDetailsQueryService;
    }

    /**
     * {@code POST  /consumption-details} : Create a new consumptionDetails.
     *
     * @param consumptionDetailsDTO the consumptionDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consumptionDetailsDTO, or with status {@code 400 (Bad Request)} if the consumptionDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consumption-details")
    public ResponseEntity<ConsumptionDetailsDTO> createConsumptionDetails(@RequestBody ConsumptionDetailsDTO consumptionDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save ConsumptionDetails : {}", consumptionDetailsDTO);
        if (consumptionDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new consumptionDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsumptionDetailsDTO result = consumptionDetailsService.save(consumptionDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/consumption-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consumption-details/:id} : Updates an existing consumptionDetails.
     *
     * @param id the id of the consumptionDetailsDTO to save.
     * @param consumptionDetailsDTO the consumptionDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consumptionDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the consumptionDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consumptionDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consumption-details/{id}")
    public ResponseEntity<ConsumptionDetailsDTO> updateConsumptionDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConsumptionDetailsDTO consumptionDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ConsumptionDetails : {}, {}", id, consumptionDetailsDTO);
        if (consumptionDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consumptionDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consumptionDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConsumptionDetailsDTO result = consumptionDetailsService.save(consumptionDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, consumptionDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /consumption-details/:id} : Partial updates given fields of an existing consumptionDetails, field will ignore if it is null
     *
     * @param id the id of the consumptionDetailsDTO to save.
     * @param consumptionDetailsDTO the consumptionDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consumptionDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the consumptionDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the consumptionDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the consumptionDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/consumption-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConsumptionDetailsDTO> partialUpdateConsumptionDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConsumptionDetailsDTO consumptionDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ConsumptionDetails partially : {}, {}", id, consumptionDetailsDTO);
        if (consumptionDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consumptionDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consumptionDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConsumptionDetailsDTO> result = consumptionDetailsService.partialUpdate(consumptionDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, consumptionDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /consumption-details} : get all the consumptionDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consumptionDetails in body.
     */
    @GetMapping("/consumption-details")
    public ResponseEntity<List<ConsumptionDetailsDTO>> getAllConsumptionDetails(
        ConsumptionDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ConsumptionDetails by criteria: {}", criteria);
        Page<ConsumptionDetailsDTO> page = consumptionDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /consumption-details/count} : count all the consumptionDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/consumption-details/count")
    public ResponseEntity<Long> countConsumptionDetails(ConsumptionDetailsCriteria criteria) {
        log.debug("REST request to count ConsumptionDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(consumptionDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /consumption-details/:id} : get the "id" consumptionDetails.
     *
     * @param id the id of the consumptionDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consumptionDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consumption-details/{id}")
    public ResponseEntity<ConsumptionDetailsDTO> getConsumptionDetails(@PathVariable Long id) {
        log.debug("REST request to get ConsumptionDetails : {}", id);
        Optional<ConsumptionDetailsDTO> consumptionDetailsDTO = consumptionDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consumptionDetailsDTO);
    }

    /**
     * {@code DELETE  /consumption-details/:id} : delete the "id" consumptionDetails.
     *
     * @param id the id of the consumptionDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consumption-details/{id}")
    public ResponseEntity<Void> deleteConsumptionDetails(@PathVariable Long id) {
        log.debug("REST request to delete ConsumptionDetails : {}", id);
        consumptionDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
