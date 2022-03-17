package com.techvg.inventory.management.service;

import com.techvg.inventory.management.domain.*; // for static metamodels
import com.techvg.inventory.management.domain.ProductInventory;
import com.techvg.inventory.management.repository.ProductInventoryRepository;
import com.techvg.inventory.management.service.criteria.ProductInventoryCriteria;
import com.techvg.inventory.management.service.dto.ProductInventoryDTO;
import com.techvg.inventory.management.service.mapper.ProductInventoryMapper;
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
 * Service for executing complex queries for {@link ProductInventory} entities in the database.
 * The main input is a {@link ProductInventoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductInventoryDTO} or a {@link Page} of {@link ProductInventoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductInventoryQueryService extends QueryService<ProductInventory> {

    private final Logger log = LoggerFactory.getLogger(ProductInventoryQueryService.class);

    private final ProductInventoryRepository productInventoryRepository;

    private final ProductInventoryMapper productInventoryMapper;

    public ProductInventoryQueryService(
        ProductInventoryRepository productInventoryRepository,
        ProductInventoryMapper productInventoryMapper
    ) {
        this.productInventoryRepository = productInventoryRepository;
        this.productInventoryMapper = productInventoryMapper;
    }

    /**
     * Return a {@link List} of {@link ProductInventoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductInventoryDTO> findByCriteria(ProductInventoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductInventory> specification = createSpecification(criteria);
        return productInventoryMapper.toDto(productInventoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductInventoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductInventoryDTO> findByCriteria(ProductInventoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductInventory> specification = createSpecification(criteria);
        return productInventoryRepository.findAll(specification, page).map(productInventoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductInventoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductInventory> specification = createSpecification(criteria);
        return productInventoryRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductInventoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductInventory> createSpecification(ProductInventoryCriteria criteria) {
        Specification<ProductInventory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductInventory_.id));
            }
            if (criteria.getInwardOutwardDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getInwardOutwardDate(), ProductInventory_.inwardOutwardDate));
            }
            if (criteria.getInwardQty() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInwardQty(), ProductInventory_.inwardQty));
            }
            if (criteria.getOutwardQty() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOutwardQty(), ProductInventory_.outwardQty));
            }
            if (criteria.getTotalQuanity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalQuanity(), ProductInventory_.totalQuanity));
            }
            if (criteria.getPricePerUnit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPricePerUnit(), ProductInventory_.pricePerUnit));
            }
            if (criteria.getLotNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLotNo(), ProductInventory_.lotNo));
            }
            if (criteria.getExpiryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiryDate(), ProductInventory_.expiryDate));
            }
            if (criteria.getInventoryTypeId() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getInventoryTypeId(), ProductInventory_.inventoryTypeId));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), ProductInventory_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), ProductInventory_.freeField2));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), ProductInventory_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductInventory_.lastModifiedBy));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), ProductInventory_.isDeleted));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), ProductInventory_.isActive));
            }
            if (criteria.getConsumptionDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getConsumptionDetailsId(),
                            root -> root.join(ProductInventory_.consumptionDetails, JoinType.LEFT).get(ConsumptionDetails_.id)
                        )
                    );
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(ProductInventory_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
            if (criteria.getProductTransactionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductTransactionId(),
                            root -> root.join(ProductInventory_.productTransaction, JoinType.LEFT).get(ProductTransaction_.id)
                        )
                    );
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(ProductInventory_.securityUser, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getWareHouseId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getWareHouseId(),
                            root -> root.join(ProductInventory_.wareHouse, JoinType.LEFT).get(WareHouse_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
