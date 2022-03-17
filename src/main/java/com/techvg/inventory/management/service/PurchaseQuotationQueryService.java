package com.techvg.inventory.management.service;

import com.techvg.inventory.management.domain.*; // for static metamodels
import com.techvg.inventory.management.domain.PurchaseQuotation;
import com.techvg.inventory.management.repository.PurchaseQuotationRepository;
import com.techvg.inventory.management.service.criteria.PurchaseQuotationCriteria;
import com.techvg.inventory.management.service.dto.PurchaseQuotationDTO;
import com.techvg.inventory.management.service.mapper.PurchaseQuotationMapper;
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
 * Service for executing complex queries for {@link PurchaseQuotation} entities in the database.
 * The main input is a {@link PurchaseQuotationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PurchaseQuotationDTO} or a {@link Page} of {@link PurchaseQuotationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PurchaseQuotationQueryService extends QueryService<PurchaseQuotation> {

    private final Logger log = LoggerFactory.getLogger(PurchaseQuotationQueryService.class);

    private final PurchaseQuotationRepository purchaseQuotationRepository;

    private final PurchaseQuotationMapper purchaseQuotationMapper;

    public PurchaseQuotationQueryService(
        PurchaseQuotationRepository purchaseQuotationRepository,
        PurchaseQuotationMapper purchaseQuotationMapper
    ) {
        this.purchaseQuotationRepository = purchaseQuotationRepository;
        this.purchaseQuotationMapper = purchaseQuotationMapper;
    }

    /**
     * Return a {@link List} of {@link PurchaseQuotationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PurchaseQuotationDTO> findByCriteria(PurchaseQuotationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PurchaseQuotation> specification = createSpecification(criteria);
        return purchaseQuotationMapper.toDto(purchaseQuotationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PurchaseQuotationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PurchaseQuotationDTO> findByCriteria(PurchaseQuotationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PurchaseQuotation> specification = createSpecification(criteria);
        return purchaseQuotationRepository.findAll(specification, page).map(purchaseQuotationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PurchaseQuotationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PurchaseQuotation> specification = createSpecification(criteria);
        return purchaseQuotationRepository.count(specification);
    }

    /**
     * Function to convert {@link PurchaseQuotationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PurchaseQuotation> createSpecification(PurchaseQuotationCriteria criteria) {
        Specification<PurchaseQuotation> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PurchaseQuotation_.id));
            }
            if (criteria.getRefrenceNumber() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getRefrenceNumber(), PurchaseQuotation_.refrenceNumber));
            }
            if (criteria.getTotalPOAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPOAmount(), PurchaseQuotation_.totalPOAmount));
            }
            if (criteria.getTotalGSTAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalGSTAmount(), PurchaseQuotation_.totalGSTAmount));
            }
            if (criteria.getExpectedDeliveryDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getExpectedDeliveryDate(), PurchaseQuotation_.expectedDeliveryDate));
            }
            if (criteria.getPoDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPoDate(), PurchaseQuotation_.poDate));
            }
            if (criteria.getOrderType() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderType(), PurchaseQuotation_.orderType));
            }
            if (criteria.getOrderStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderStatus(), PurchaseQuotation_.orderStatus));
            }
            if (criteria.getTermsAndCondition() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getTermsAndCondition(), PurchaseQuotation_.termsAndCondition));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), PurchaseQuotation_.notes));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), PurchaseQuotation_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), PurchaseQuotation_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), PurchaseQuotation_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), PurchaseQuotation_.freeField2));
            }
            if (criteria.getPurchaseQuotationDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPurchaseQuotationDetailsId(),
                            root -> root.join(PurchaseQuotation_.purchaseQuotationDetails, JoinType.LEFT).get(PurchaseQuotationDetails_.id)
                        )
                    );
            }
            if (criteria.getGoodsRecivedId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getGoodsRecivedId(),
                            root -> root.join(PurchaseQuotation_.goodsReciveds, JoinType.LEFT).get(GoodsRecived_.id)
                        )
                    );
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(PurchaseQuotation_.securityUser, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getProjectId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProjectId(),
                            root -> root.join(PurchaseQuotation_.project, JoinType.LEFT).get(Project_.id)
                        )
                    );
            }
            if (criteria.getClientDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClientDetailsId(),
                            root -> root.join(PurchaseQuotation_.clientDetails, JoinType.LEFT).get(ClientDetails_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
