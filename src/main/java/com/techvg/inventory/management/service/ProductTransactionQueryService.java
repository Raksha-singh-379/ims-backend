package com.techvg.inventory.management.service;

import com.techvg.inventory.management.domain.*; // for static metamodels
import com.techvg.inventory.management.domain.ProductTransaction;
import com.techvg.inventory.management.repository.ProductTransactionRepository;
import com.techvg.inventory.management.service.criteria.ProductTransactionCriteria;
import com.techvg.inventory.management.service.dto.ProductTransactionDTO;
import com.techvg.inventory.management.service.mapper.ProductTransactionMapper;
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
 * Service for executing complex queries for {@link ProductTransaction} entities in the database.
 * The main input is a {@link ProductTransactionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductTransactionDTO} or a {@link Page} of {@link ProductTransactionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductTransactionQueryService extends QueryService<ProductTransaction> {

    private final Logger log = LoggerFactory.getLogger(ProductTransactionQueryService.class);

    private final ProductTransactionRepository productTransactionRepository;

    private final ProductTransactionMapper productTransactionMapper;

    public ProductTransactionQueryService(
        ProductTransactionRepository productTransactionRepository,
        ProductTransactionMapper productTransactionMapper
    ) {
        this.productTransactionRepository = productTransactionRepository;
        this.productTransactionMapper = productTransactionMapper;
    }

    /**
     * Return a {@link List} of {@link ProductTransactionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductTransactionDTO> findByCriteria(ProductTransactionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductTransaction> specification = createSpecification(criteria);
        return productTransactionMapper.toDto(productTransactionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductTransactionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductTransactionDTO> findByCriteria(ProductTransactionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductTransaction> specification = createSpecification(criteria);
        return productTransactionRepository.findAll(specification, page).map(productTransactionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductTransactionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductTransaction> specification = createSpecification(criteria);
        return productTransactionRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductTransactionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductTransaction> createSpecification(ProductTransactionCriteria criteria) {
        Specification<ProductTransaction> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductTransaction_.id));
            }
            if (criteria.getRefrenceId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRefrenceId(), ProductTransaction_.refrenceId));
            }
            if (criteria.getTransactionType() != null) {
                specification = specification.and(buildSpecification(criteria.getTransactionType(), ProductTransaction_.transactionType));
            }
            if (criteria.getTransactionStatus() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getTransactionStatus(), ProductTransaction_.transactionStatus));
            }
            if (criteria.getTransactionDate() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getTransactionDate(), ProductTransaction_.transactionDate));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProductTransaction_.description));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), ProductTransaction_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), ProductTransaction_.freeField2));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), ProductTransaction_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductTransaction_.lastModifiedBy));
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(ProductTransaction_.securityUser, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getWareHouseId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getWareHouseId(),
                            root -> root.join(ProductTransaction_.wareHouse, JoinType.LEFT).get(WareHouse_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
