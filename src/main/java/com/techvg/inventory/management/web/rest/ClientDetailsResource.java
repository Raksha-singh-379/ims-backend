package com.techvg.inventory.management.web.rest;

import com.techvg.inventory.management.repository.ClientDetailsRepository;
import com.techvg.inventory.management.service.ClientDetailsQueryService;
import com.techvg.inventory.management.service.ClientDetailsService;
import com.techvg.inventory.management.service.criteria.ClientDetailsCriteria;
import com.techvg.inventory.management.service.dto.ClientDetailsDTO;
import com.techvg.inventory.management.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.techvg.inventory.management.domain.ClientDetails}.
 */
@RestController
@RequestMapping("/api")
public class ClientDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ClientDetailsResource.class);

    private static final String ENTITY_NAME = "clientDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientDetailsService clientDetailsService;

    private final ClientDetailsRepository clientDetailsRepository;

    private final ClientDetailsQueryService clientDetailsQueryService;

    public ClientDetailsResource(
        ClientDetailsService clientDetailsService,
        ClientDetailsRepository clientDetailsRepository,
        ClientDetailsQueryService clientDetailsQueryService
    ) {
        this.clientDetailsService = clientDetailsService;
        this.clientDetailsRepository = clientDetailsRepository;
        this.clientDetailsQueryService = clientDetailsQueryService;
    }

    /**
     * {@code POST  /client-details} : Create a new clientDetails.
     *
     * @param clientDetailsDTO the clientDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientDetailsDTO, or with status {@code 400 (Bad Request)} if the clientDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-details")
    public ResponseEntity<ClientDetailsDTO> createClientDetails(@Valid @RequestBody ClientDetailsDTO clientDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save ClientDetails : {}", clientDetailsDTO);
        if (clientDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new clientDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientDetailsDTO result = clientDetailsService.save(clientDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/client-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-details/:id} : Updates an existing clientDetails.
     *
     * @param id the id of the clientDetailsDTO to save.
     * @param clientDetailsDTO the clientDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the clientDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client-details/{id}")
    public ResponseEntity<ClientDetailsDTO> updateClientDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClientDetailsDTO clientDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ClientDetails : {}, {}", id, clientDetailsDTO);
        if (clientDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClientDetailsDTO result = clientDetailsService.save(clientDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /client-details/:id} : Partial updates given fields of an existing clientDetails, field will ignore if it is null
     *
     * @param id the id of the clientDetailsDTO to save.
     * @param clientDetailsDTO the clientDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the clientDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the clientDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the clientDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/client-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClientDetailsDTO> partialUpdateClientDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClientDetailsDTO clientDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClientDetails partially : {}, {}", id, clientDetailsDTO);
        if (clientDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClientDetailsDTO> result = clientDetailsService.partialUpdate(clientDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /client-details} : get all the clientDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientDetails in body.
     */
    @GetMapping("/client-details")
    public ResponseEntity<List<ClientDetailsDTO>> getAllClientDetails(
        ClientDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ClientDetails by criteria: {}", criteria);
        Page<ClientDetailsDTO> page = clientDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /client-details/count} : count all the clientDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/client-details/count")
    public ResponseEntity<Long> countClientDetails(ClientDetailsCriteria criteria) {
        log.debug("REST request to count ClientDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(clientDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /client-details/:id} : get the "id" clientDetails.
     *
     * @param id the id of the clientDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-details/{id}")
    public ResponseEntity<ClientDetailsDTO> getClientDetails(@PathVariable Long id) {
        log.debug("REST request to get ClientDetails : {}", id);
        Optional<ClientDetailsDTO> clientDetailsDTO = clientDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientDetailsDTO);
    }

    /**
     * {@code DELETE  /client-details/:id} : delete the "id" clientDetails.
     *
     * @param id the id of the clientDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/client-details/{id}")
    public ResponseEntity<Void> deleteClientDetails(@PathVariable Long id) {
        log.debug("REST request to delete ClientDetails : {}", id);
        clientDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
