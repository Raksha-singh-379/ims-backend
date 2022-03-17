package com.techvg.inventory.management.web.rest;

import com.techvg.inventory.management.repository.CategoriesRepository;
import com.techvg.inventory.management.service.CategoriesQueryService;
import com.techvg.inventory.management.service.CategoriesService;
import com.techvg.inventory.management.service.criteria.CategoriesCriteria;
import com.techvg.inventory.management.service.dto.CategoriesDTO;
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
 * REST controller for managing {@link com.techvg.inventory.management.domain.Categories}.
 */
@RestController
@RequestMapping("/api")
public class CategoriesResource {

    private final Logger log = LoggerFactory.getLogger(CategoriesResource.class);

    private static final String ENTITY_NAME = "categories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriesService categoriesService;

    private final CategoriesRepository categoriesRepository;

    private final CategoriesQueryService categoriesQueryService;

    public CategoriesResource(
        CategoriesService categoriesService,
        CategoriesRepository categoriesRepository,
        CategoriesQueryService categoriesQueryService
    ) {
        this.categoriesService = categoriesService;
        this.categoriesRepository = categoriesRepository;
        this.categoriesQueryService = categoriesQueryService;
    }

    /**
     * {@code POST  /categories} : Create a new categories.
     *
     * @param categoriesDTO the categoriesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoriesDTO, or with status {@code 400 (Bad Request)} if the categories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categories")
    public ResponseEntity<CategoriesDTO> createCategories(@RequestBody CategoriesDTO categoriesDTO) throws URISyntaxException {
        log.debug("REST request to save Categories : {}", categoriesDTO);
        if (categoriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new categories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoriesDTO result = categoriesService.save(categoriesDTO);
        return ResponseEntity
            .created(new URI("/api/categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categories/:id} : Updates an existing categories.
     *
     * @param id the id of the categoriesDTO to save.
     * @param categoriesDTO the categoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriesDTO,
     * or with status {@code 400 (Bad Request)} if the categoriesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoriesDTO> updateCategories(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategoriesDTO categoriesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Categories : {}, {}", id, categoriesDTO);
        if (categoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoriesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CategoriesDTO result = categoriesService.save(categoriesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /categories/:id} : Partial updates given fields of an existing categories, field will ignore if it is null
     *
     * @param id the id of the categoriesDTO to save.
     * @param categoriesDTO the categoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriesDTO,
     * or with status {@code 400 (Bad Request)} if the categoriesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the categoriesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the categoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CategoriesDTO> partialUpdateCategories(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategoriesDTO categoriesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Categories partially : {}, {}", id, categoriesDTO);
        if (categoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoriesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategoriesDTO> result = categoriesService.partialUpdate(categoriesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /categories} : get all the categories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping("/categories")
    public ResponseEntity<List<CategoriesDTO>> getAllCategories(
        CategoriesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Categories by criteria: {}", criteria);
        Page<CategoriesDTO> page = categoriesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categories/count} : count all the categories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/categories/count")
    public ResponseEntity<Long> countCategories(CategoriesCriteria criteria) {
        log.debug("REST request to count Categories by criteria: {}", criteria);
        return ResponseEntity.ok().body(categoriesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /categories/:id} : get the "id" categories.
     *
     * @param id the id of the categoriesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoriesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoriesDTO> getCategories(@PathVariable Long id) {
        log.debug("REST request to get Categories : {}", id);
        Optional<CategoriesDTO> categoriesDTO = categoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoriesDTO);
    }

    /**
     * {@code DELETE  /categories/:id} : delete the "id" categories.
     *
     * @param id the id of the categoriesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategories(@PathVariable Long id) {
        log.debug("REST request to delete Categories : {}", id);
        categoriesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
