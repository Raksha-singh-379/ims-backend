package com.techvg.inventory.management.web.rest;

import com.techvg.inventory.management.repository.WareHouseRepository;
import com.techvg.inventory.management.service.WareHouseQueryService;
import com.techvg.inventory.management.service.WareHouseService;
import com.techvg.inventory.management.service.criteria.WareHouseCriteria;
import com.techvg.inventory.management.service.dto.WareHouseDTO;
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
 * REST controller for managing {@link com.techvg.inventory.management.domain.WareHouse}.
 */
@RestController
@RequestMapping("/api")
public class WareHouseResource {

    private final Logger log = LoggerFactory.getLogger(WareHouseResource.class);

    private static final String ENTITY_NAME = "wareHouse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WareHouseService wareHouseService;

    private final WareHouseRepository wareHouseRepository;

    private final WareHouseQueryService wareHouseQueryService;

    public WareHouseResource(
        WareHouseService wareHouseService,
        WareHouseRepository wareHouseRepository,
        WareHouseQueryService wareHouseQueryService
    ) {
        this.wareHouseService = wareHouseService;
        this.wareHouseRepository = wareHouseRepository;
        this.wareHouseQueryService = wareHouseQueryService;
    }

    /**
     * {@code POST  /ware-houses} : Create a new wareHouse.
     *
     * @param wareHouseDTO the wareHouseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wareHouseDTO, or with status {@code 400 (Bad Request)} if the wareHouse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ware-houses")
    public ResponseEntity<WareHouseDTO> createWareHouse(@RequestBody WareHouseDTO wareHouseDTO) throws URISyntaxException {
        log.debug("REST request to save WareHouse : {}", wareHouseDTO);
        if (wareHouseDTO.getId() != null) {
            throw new BadRequestAlertException("A new wareHouse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WareHouseDTO result = wareHouseService.save(wareHouseDTO);
        return ResponseEntity
            .created(new URI("/api/ware-houses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ware-houses/:id} : Updates an existing wareHouse.
     *
     * @param id the id of the wareHouseDTO to save.
     * @param wareHouseDTO the wareHouseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wareHouseDTO,
     * or with status {@code 400 (Bad Request)} if the wareHouseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wareHouseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ware-houses/{id}")
    public ResponseEntity<WareHouseDTO> updateWareHouse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WareHouseDTO wareHouseDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WareHouse : {}, {}", id, wareHouseDTO);
        if (wareHouseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wareHouseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wareHouseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WareHouseDTO result = wareHouseService.save(wareHouseDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wareHouseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ware-houses/:id} : Partial updates given fields of an existing wareHouse, field will ignore if it is null
     *
     * @param id the id of the wareHouseDTO to save.
     * @param wareHouseDTO the wareHouseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wareHouseDTO,
     * or with status {@code 400 (Bad Request)} if the wareHouseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the wareHouseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the wareHouseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ware-houses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WareHouseDTO> partialUpdateWareHouse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WareHouseDTO wareHouseDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WareHouse partially : {}, {}", id, wareHouseDTO);
        if (wareHouseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wareHouseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wareHouseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WareHouseDTO> result = wareHouseService.partialUpdate(wareHouseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wareHouseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ware-houses} : get all the wareHouses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wareHouses in body.
     */
    @GetMapping("/ware-houses")
    public ResponseEntity<List<WareHouseDTO>> getAllWareHouses(
        WareHouseCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get WareHouses by criteria: {}", criteria);
        Page<WareHouseDTO> page = wareHouseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ware-houses/count} : count all the wareHouses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ware-houses/count")
    public ResponseEntity<Long> countWareHouses(WareHouseCriteria criteria) {
        log.debug("REST request to count WareHouses by criteria: {}", criteria);
        return ResponseEntity.ok().body(wareHouseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ware-houses/:id} : get the "id" wareHouse.
     *
     * @param id the id of the wareHouseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wareHouseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ware-houses/{id}")
    public ResponseEntity<WareHouseDTO> getWareHouse(@PathVariable Long id) {
        log.debug("REST request to get WareHouse : {}", id);
        Optional<WareHouseDTO> wareHouseDTO = wareHouseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wareHouseDTO);
    }

    /**
     * {@code DELETE  /ware-houses/:id} : delete the "id" wareHouse.
     *
     * @param id the id of the wareHouseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ware-houses/{id}")
    public ResponseEntity<Void> deleteWareHouse(@PathVariable Long id) {
        log.debug("REST request to delete WareHouse : {}", id);
        wareHouseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
