package com.techvg.inventory.management.service;

import com.techvg.inventory.management.domain.*; // for static metamodels
import com.techvg.inventory.management.domain.ConsumptionDetails;
import com.techvg.inventory.management.repository.ConsumptionDetailsRepository;
import com.techvg.inventory.management.service.criteria.ConsumptionDetailsCriteria;
import com.techvg.inventory.management.service.dto.ConsumptionDetailsDTO;
import com.techvg.inventory.management.service.mapper.ConsumptionDetailsMapper;
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
 * Service for executing complex queries for {@link ConsumptionDetails} entities in the database.
 * The main input is a {@link ConsumptionDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConsumptionDetailsDTO} or a {@link Page} of {@link ConsumptionDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConsumptionDetailsQueryService extends QueryService<ConsumptionDetails> {

    private final Logger log = LoggerFactory.getLogger(ConsumptionDetailsQueryService.class);

    private final ConsumptionDetailsRepository consumptionDetailsRepository;

    private final ConsumptionDetailsMapper consumptionDetailsMapper;

    public ConsumptionDetailsQueryService(
        ConsumptionDetailsRepository consumptionDetailsRepository,
        ConsumptionDetailsMapper consumptionDetailsMapper
    ) {
        this.consumptionDetailsRepository = consumptionDetailsRepository;
        this.consumptionDetailsMapper = consumptionDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link ConsumptionDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConsumptionDetailsDTO> findByCriteria(ConsumptionDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ConsumptionDetails> specification = createSpecification(criteria);
        return consumptionDetailsMapper.toDto(consumptionDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ConsumptionDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConsumptionDetailsDTO> findByCriteria(ConsumptionDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ConsumptionDetails> specification = createSpecification(criteria);
        return consumptionDetailsRepository.findAll(specification, page).map(consumptionDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConsumptionDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ConsumptionDetails> specification = createSpecification(criteria);
        return consumptionDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link ConsumptionDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ConsumptionDetails> createSpecification(ConsumptionDetailsCriteria criteria) {
        Specification<ConsumptionDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ConsumptionDetails_.id));
            }
            if (criteria.getComsumptionDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getComsumptionDate(), ConsumptionDetails_.comsumptionDate));
            }
            if (criteria.getQtyConsumed() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQtyConsumed(), ConsumptionDetails_.qtyConsumed));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), ConsumptionDetails_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), ConsumptionDetails_.freeField2));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), ConsumptionDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ConsumptionDetails_.lastModifiedBy));
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(ConsumptionDetails_.securityUser, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getProjectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectId(),
                            root -> root.join(ConsumptionDetails_.project, JoinType.LEFT).get(Project_.id)
                        )
                    );
            }
            if (criteria.getProductInventoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductInventoryId(),
                            root -> root.join(ConsumptionDetails_.productInventory, JoinType.LEFT).get(ProductInventory_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
