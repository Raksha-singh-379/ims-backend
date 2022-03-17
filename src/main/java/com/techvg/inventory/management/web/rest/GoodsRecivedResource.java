package com.techvg.inventory.management.web.rest;

import com.techvg.inventory.management.repository.GoodsRecivedRepository;
import com.techvg.inventory.management.service.GoodsRecivedQueryService;
import com.techvg.inventory.management.service.GoodsRecivedService;
import com.techvg.inventory.management.service.criteria.GoodsRecivedCriteria;
import com.techvg.inventory.management.service.dto.GoodsRecivedDTO;
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
 * REST controller for managing {@link com.techvg.inventory.management.domain.GoodsRecived}.
 */
@RestController
@RequestMapping("/api")
public class GoodsRecivedResource {

    private final Logger log = LoggerFactory.getLogger(GoodsRecivedResource.class);

    private static final String ENTITY_NAME = "goodsRecived";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoodsRecivedService goodsRecivedService;

    private final GoodsRecivedRepository goodsRecivedRepository;

    private final GoodsRecivedQueryService goodsRecivedQueryService;

    public GoodsRecivedResource(
        GoodsRecivedService goodsRecivedService,
        GoodsRecivedRepository goodsRecivedRepository,
        GoodsRecivedQueryService goodsRecivedQueryService
    ) {
        this.goodsRecivedService = goodsRecivedService;
        this.goodsRecivedRepository = goodsRecivedRepository;
        this.goodsRecivedQueryService = goodsRecivedQueryService;
    }

    /**
     * {@code POST  /goods-reciveds} : Create a new goodsRecived.
     *
     * @param goodsRecivedDTO the goodsRecivedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goodsRecivedDTO, or with status {@code 400 (Bad Request)} if the goodsRecived has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/goods-reciveds")
    public ResponseEntity<GoodsRecivedDTO> createGoodsRecived(@RequestBody GoodsRecivedDTO goodsRecivedDTO) throws URISyntaxException {
        log.debug("REST request to save GoodsRecived : {}", goodsRecivedDTO);
        if (goodsRecivedDTO.getId() != null) {
            throw new BadRequestAlertException("A new goodsRecived cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoodsRecivedDTO result = goodsRecivedService.save(goodsRecivedDTO);
        return ResponseEntity
            .created(new URI("/api/goods-reciveds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /goods-reciveds/:id} : Updates an existing goodsRecived.
     *
     * @param id the id of the goodsRecivedDTO to save.
     * @param goodsRecivedDTO the goodsRecivedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goodsRecivedDTO,
     * or with status {@code 400 (Bad Request)} if the goodsRecivedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goodsRecivedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/goods-reciveds/{id}")
    public ResponseEntity<GoodsRecivedDTO> updateGoodsRecived(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GoodsRecivedDTO goodsRecivedDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GoodsRecived : {}, {}", id, goodsRecivedDTO);
        if (goodsRecivedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, goodsRecivedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!goodsRecivedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GoodsRecivedDTO result = goodsRecivedService.save(goodsRecivedDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goodsRecivedDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /goods-reciveds/:id} : Partial updates given fields of an existing goodsRecived, field will ignore if it is null
     *
     * @param id the id of the goodsRecivedDTO to save.
     * @param goodsRecivedDTO the goodsRecivedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goodsRecivedDTO,
     * or with status {@code 400 (Bad Request)} if the goodsRecivedDTO is not valid,
     * or with status {@code 404 (Not Found)} if the goodsRecivedDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the goodsRecivedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/goods-reciveds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GoodsRecivedDTO> partialUpdateGoodsRecived(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GoodsRecivedDTO goodsRecivedDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GoodsRecived partially : {}, {}", id, goodsRecivedDTO);
        if (goodsRecivedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, goodsRecivedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!goodsRecivedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GoodsRecivedDTO> result = goodsRecivedService.partialUpdate(goodsRecivedDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goodsRecivedDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /goods-reciveds} : get all the goodsReciveds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goodsReciveds in body.
     */
    @GetMapping("/goods-reciveds")
    public ResponseEntity<List<GoodsRecivedDTO>> getAllGoodsReciveds(
        GoodsRecivedCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get GoodsReciveds by criteria: {}", criteria);
        Page<GoodsRecivedDTO> page = goodsRecivedQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /goods-reciveds/count} : count all the goodsReciveds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/goods-reciveds/count")
    public ResponseEntity<Long> countGoodsReciveds(GoodsRecivedCriteria criteria) {
        log.debug("REST request to count GoodsReciveds by criteria: {}", criteria);
        return ResponseEntity.ok().body(goodsRecivedQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /goods-reciveds/:id} : get the "id" goodsRecived.
     *
     * @param id the id of the goodsRecivedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goodsRecivedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/goods-reciveds/{id}")
    public ResponseEntity<GoodsRecivedDTO> getGoodsRecived(@PathVariable Long id) {
        log.debug("REST request to get GoodsRecived : {}", id);
        Optional<GoodsRecivedDTO> goodsRecivedDTO = goodsRecivedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(goodsRecivedDTO);
    }

    /**
     * {@code DELETE  /goods-reciveds/:id} : delete the "id" goodsRecived.
     *
     * @param id the id of the goodsRecivedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/goods-reciveds/{id}")
    public ResponseEntity<Void> deleteGoodsRecived(@PathVariable Long id) {
        log.debug("REST request to delete GoodsRecived : {}", id);
        goodsRecivedService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
