package com.techvg.inventory.management.web.rest;

import com.techvg.inventory.management.repository.ProductTransactionRepository;
import com.techvg.inventory.management.service.ProductTransactionQueryService;
import com.techvg.inventory.management.service.ProductTransactionService;
import com.techvg.inventory.management.service.criteria.ProductTransactionCriteria;
import com.techvg.inventory.management.service.dto.ProductTransactionDTO;
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
 * REST controller for managing {@link com.techvg.inventory.management.domain.ProductTransaction}.
 */
@RestController
@RequestMapping("/api")
public class ProductTransactionResource {

    private final Logger log = LoggerFactory.getLogger(ProductTransactionResource.class);

    private static final String ENTITY_NAME = "productTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductTransactionService productTransactionService;

    private final ProductTransactionRepository productTransactionRepository;

    private final ProductTransactionQueryService productTransactionQueryService;

    public ProductTransactionResource(
        ProductTransactionService productTransactionService,
        ProductTransactionRepository productTransactionRepository,
        ProductTransactionQueryService productTransactionQueryService
    ) {
        this.productTransactionService = productTransactionService;
        this.productTransactionRepository = productTransactionRepository;
        this.productTransactionQueryService = productTransactionQueryService;
    }

    /**
     * {@code POST  /product-transactions} : Create a new productTransaction.
     *
     * @param productTransactionDTO the productTransactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productTransactionDTO, or with status {@code 400 (Bad Request)} if the productTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-transactions")
    public ResponseEntity<ProductTransactionDTO> createProductTransaction(@RequestBody ProductTransactionDTO productTransactionDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductTransaction : {}", productTransactionDTO);
        if (productTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new productTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductTransactionDTO result = productTransactionService.save(productTransactionDTO);
        return ResponseEntity
            .created(new URI("/api/product-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-transactions/:id} : Updates an existing productTransaction.
     *
     * @param id the id of the productTransactionDTO to save.
     * @param productTransactionDTO the productTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the productTransactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-transactions/{id}")
    public ResponseEntity<ProductTransactionDTO> updateProductTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductTransactionDTO productTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductTransaction : {}, {}", id, productTransactionDTO);
        if (productTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productTransactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductTransactionDTO result = productTransactionService.save(productTransactionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-transactions/:id} : Partial updates given fields of an existing productTransaction, field will ignore if it is null
     *
     * @param id the id of the productTransactionDTO to save.
     * @param productTransactionDTO the productTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the productTransactionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productTransactionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-transactions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductTransactionDTO> partialUpdateProductTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductTransactionDTO productTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductTransaction partially : {}, {}", id, productTransactionDTO);
        if (productTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productTransactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductTransactionDTO> result = productTransactionService.partialUpdate(productTransactionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productTransactionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-transactions} : get all the productTransactions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productTransactions in body.
     */
    @GetMapping("/product-transactions")
    public ResponseEntity<List<ProductTransactionDTO>> getAllProductTransactions(
        ProductTransactionCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ProductTransactions by criteria: {}", criteria);
        Page<ProductTransactionDTO> page = productTransactionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-transactions/count} : count all the productTransactions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-transactions/count")
    public ResponseEntity<Long> countProductTransactions(ProductTransactionCriteria criteria) {
        log.debug("REST request to count ProductTransactions by criteria: {}", criteria);
        return ResponseEntity.ok().body(productTransactionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-transactions/:id} : get the "id" productTransaction.
     *
     * @param id the id of the productTransactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productTransactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-transactions/{id}")
    public ResponseEntity<ProductTransactionDTO> getProductTransaction(@PathVariable Long id) {
        log.debug("REST request to get ProductTransaction : {}", id);
        Optional<ProductTransactionDTO> productTransactionDTO = productTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productTransactionDTO);
    }

    /**
     * {@code DELETE  /product-transactions/:id} : delete the "id" productTransaction.
     *
     * @param id the id of the productTransactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-transactions/{id}")
    public ResponseEntity<Void> deleteProductTransaction(@PathVariable Long id) {
        log.debug("REST request to delete ProductTransaction : {}", id);
        productTransactionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
