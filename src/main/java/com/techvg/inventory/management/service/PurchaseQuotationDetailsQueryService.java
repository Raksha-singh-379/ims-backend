package com.techvg.inventory.management.service;

import com.techvg.inventory.management.domain.*; // for static metamodels
import com.techvg.inventory.management.domain.PurchaseQuotationDetails;
import com.techvg.inventory.management.repository.PurchaseQuotationDetailsRepository;
import com.techvg.inventory.management.service.criteria.PurchaseQuotationDetailsCriteria;
import com.techvg.inventory.management.service.dto.PurchaseQuotationDetailsDTO;
import com.techvg.inventory.management.service.mapper.PurchaseQuotationDetailsMapper;
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
 * Service for executing complex queries for {@link PurchaseQuotationDetails} entities in the database.
 * The main input is a {@link PurchaseQuotationDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PurchaseQuotationDetailsDTO} or a {@link Page} of {@link PurchaseQuotationDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PurchaseQuotationDetailsQueryService extends QueryService<PurchaseQuotationDetails> {

    private final Logger log = LoggerFactory.getLogger(PurchaseQuotationDetailsQueryService.class);

    private final PurchaseQuotationDetailsRepository purchaseQuotationDetailsRepository;

    private final PurchaseQuotationDetailsMapper purchaseQuotationDetailsMapper;

    public PurchaseQuotationDetailsQueryService(
        PurchaseQuotationDetailsRepository purchaseQuotationDetailsRepository,
        PurchaseQuotationDetailsMapper purchaseQuotationDetailsMapper
    ) {
        this.purchaseQuotationDetailsRepository = purchaseQuotationDetailsRepository;
        this.purchaseQuotationDetailsMapper = purchaseQuotationDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link PurchaseQuotationDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PurchaseQuotationDetailsDTO> findByCriteria(PurchaseQuotationDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PurchaseQuotationDetails> specification = createSpecification(criteria);
        return purchaseQuotationDetailsMapper.toDto(purchaseQuotationDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PurchaseQuotationDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PurchaseQuotationDetailsDTO> findByCriteria(PurchaseQuotationDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PurchaseQuotationDetails> specification = createSpecification(criteria);
        return purchaseQuotationDetailsRepository.findAll(specification, page).map(purchaseQuotationDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PurchaseQuotationDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PurchaseQuotationDetails> specification = createSpecification(criteria);
        return purchaseQuotationDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link PurchaseQuotationDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PurchaseQuotationDetails> createSpecification(PurchaseQuotationDetailsCriteria criteria) {
        Specification<PurchaseQuotationDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PurchaseQuotationDetails_.id));
            }
            if (criteria.getQtyordered() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQtyordered(), PurchaseQuotationDetails_.qtyordered));
            }
            if (criteria.getGstTaxPercentage() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getGstTaxPercentage(), PurchaseQuotationDetails_.gstTaxPercentage));
            }
            if (criteria.getPricePerUnit() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPricePerUnit(), PurchaseQuotationDetails_.pricePerUnit));
            }
            if (criteria.getTotalPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPrice(), PurchaseQuotationDetails_.totalPrice));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount(), PurchaseQuotationDetails_.discount));
            }
            if (criteria.getLastModified() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModified(), PurchaseQuotationDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), PurchaseQuotationDetails_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), PurchaseQuotationDetails_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), PurchaseQuotationDetails_.freeField2));
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(PurchaseQuotationDetails_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
            if (criteria.getPurchaseQuotationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPurchaseQuotationId(),
                            root -> root.join(PurchaseQuotationDetails_.purchaseQuotation, JoinType.LEFT).get(PurchaseQuotation_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
