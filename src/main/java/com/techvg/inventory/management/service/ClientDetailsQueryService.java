package com.techvg.inventory.management.service;

import com.techvg.inventory.management.domain.*; // for static metamodels
import com.techvg.inventory.management.domain.ClientDetails;
import com.techvg.inventory.management.repository.ClientDetailsRepository;
import com.techvg.inventory.management.service.criteria.ClientDetailsCriteria;
import com.techvg.inventory.management.service.dto.ClientDetailsDTO;
import com.techvg.inventory.management.service.mapper.ClientDetailsMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ClientDetails} entities in the database.
 * The main input is a {@link ClientDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClientDetailsDTO} or a {@link Page} of {@link ClientDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClientDetailsQueryService extends QueryService<ClientDetails> {

    private final Logger log = LoggerFactory.getLogger(ClientDetailsQueryService.class);

    private final ClientDetailsRepository clientDetailsRepository;

    private final ClientDetailsMapper clientDetailsMapper;

    public ClientDetailsQueryService(ClientDetailsRepository clientDetailsRepository, ClientDetailsMapper clientDetailsMapper) {
        this.clientDetailsRepository = clientDetailsRepository;
        this.clientDetailsMapper = clientDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link ClientDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClientDetailsDTO> findByCriteria(ClientDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClientDetails> specification = createSpecification(criteria);
        return clientDetailsMapper.toDto(clientDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClientDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientDetailsDTO> findByCriteria(ClientDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClientDetails> specification = createSpecification(criteria);
        return clientDetailsRepository.findAll(specification, page).map(clientDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClientDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClientDetails> specification = createSpecification(criteria);
        return clientDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link ClientDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClientDetails> createSpecification(ClientDetailsCriteria criteria) {
        Specification<ClientDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClientDetails_.id));
            }
            if (criteria.getClientName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClientName(), ClientDetails_.clientName));
            }
            if (criteria.getMobileNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileNo(), ClientDetails_.mobileNo));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), ClientDetails_.email));
            }
            if (criteria.getBillingAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBillingAddress(), ClientDetails_.billingAddress));
            }
            if (criteria.getCompanyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyName(), ClientDetails_.companyName));
            }
            if (criteria.getCompanyContactNo() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCompanyContactNo(), ClientDetails_.companyContactNo));
            }
            if (criteria.getWebsite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebsite(), ClientDetails_.website));
            }
            if (criteria.getGstinNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGstinNumber(), ClientDetails_.gstinNumber));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ClientDetails_.description));
            }
            if (criteria.getClientType() != null) {
                specification = specification.and(buildSpecification(criteria.getClientType(), ClientDetails_.clientType));
            }
            if (criteria.getIsactivated() != null) {
                specification = specification.and(buildSpecification(criteria.getIsactivated(), ClientDetails_.isactivated));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), ClientDetails_.freeField1));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), ClientDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ClientDetails_.lastModifiedBy));
            }
        }
        return specification;
    }
}
