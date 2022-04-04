package com.techvg.inventory.management.service;

import com.techvg.inventory.management.domain.*; // for static metamodels
import com.techvg.inventory.management.domain.Product;
import com.techvg.inventory.management.repository.ProductRepository;
import com.techvg.inventory.management.service.criteria.ProductCriteria;
import com.techvg.inventory.management.service.dto.ProductDTO;
import com.techvg.inventory.management.service.mapper.ProductMapper;
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
 * Service for executing complex queries for {@link Product} entities in the database.
 * The main input is a {@link ProductCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductDTO} or a {@link Page} of {@link ProductDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductQueryService extends QueryService<Product> {

    private final Logger log = LoggerFactory.getLogger(ProductQueryService.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductQueryService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Return a {@link List} of {@link ProductDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> findByCriteria(ProductCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Product> specification = createSpecification(criteria);
        return productMapper.toDto(productRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> findByCriteria(ProductCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.findAll(specification, page).map(productMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Product> createSpecification(ProductCriteria criteria) {
        Specification<Product> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Product_.id));
            }
            if (criteria.getShortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortName(), Product_.shortName));
            }
            if (criteria.getChemicalFormula() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChemicalFormula(), Product_.chemicalFormula));
            }
            if (criteria.getHsnNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHsnNo(), Product_.hsnNo));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), Product_.isDeleted));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), Product_.isActive));
            }
            if (criteria.getProductName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductName(), Product_.productName));
            }
            if (criteria.getAlertUnits() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAlertUnits(), Product_.alertUnits));
            }
            if (criteria.getCasNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCasNumber(), Product_.casNumber));
            }
            if (criteria.getCatlogNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCatlogNumber(), Product_.catlogNumber));
            }
            if (criteria.getMolecularWt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMolecularWt(), Product_.molecularWt));
            }
            if (criteria.getMolecularFormula() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMolecularFormula(), Product_.molecularFormula));
            }
            if (criteria.getChemicalName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChemicalName(), Product_.chemicalName));
            }
            if (criteria.getStructureImg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStructureImg(), Product_.structureImg));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Product_.description));
            }
            if (criteria.getQrCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQrCode(), Product_.qrCode));
            }
            if (criteria.getBarCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBarCode(), Product_.barCode));
            }
            if (criteria.getGstPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGstPercentage(), Product_.gstPercentage));
            }
            if (criteria.getProductType() != null) {
                specification = specification.and(buildSpecification(criteria.getProductType(), Product_.productType));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), Product_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Product_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), Product_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), Product_.freeField2));
            }
            if (criteria.getTransferDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransferDetailsId(),
                            root -> root.join(Product_.transferDetails, JoinType.LEFT).get(TransferDetails_.id)
                        )
                    );
            }
            if (criteria.getCategoriesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoriesId(),
                            root -> root.join(Product_.categories, JoinType.LEFT).get(Categories_.id)
                        )
                    );
            }
            if (criteria.getUnitId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUnitId(), root -> root.join(Product_.unit, JoinType.LEFT).get(Unit_.id))
                    );
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(Product_.securityUser, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
