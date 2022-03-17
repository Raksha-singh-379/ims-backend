package com.techvg.inventory.management.service;

import com.techvg.inventory.management.domain.*; // for static metamodels
import com.techvg.inventory.management.domain.GoodsRecived;
import com.techvg.inventory.management.repository.GoodsRecivedRepository;
import com.techvg.inventory.management.service.criteria.GoodsRecivedCriteria;
import com.techvg.inventory.management.service.dto.GoodsRecivedDTO;
import com.techvg.inventory.management.service.mapper.GoodsRecivedMapper;
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
 * Service for executing complex queries for {@link GoodsRecived} entities in the database.
 * The main input is a {@link GoodsRecivedCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GoodsRecivedDTO} or a {@link Page} of {@link GoodsRecivedDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GoodsRecivedQueryService extends QueryService<GoodsRecived> {

    private final Logger log = LoggerFactory.getLogger(GoodsRecivedQueryService.class);

    private final GoodsRecivedRepository goodsRecivedRepository;

    private final GoodsRecivedMapper goodsRecivedMapper;

    public GoodsRecivedQueryService(GoodsRecivedRepository goodsRecivedRepository, GoodsRecivedMapper goodsRecivedMapper) {
        this.goodsRecivedRepository = goodsRecivedRepository;
        this.goodsRecivedMapper = goodsRecivedMapper;
    }

    /**
     * Return a {@link List} of {@link GoodsRecivedDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GoodsRecivedDTO> findByCriteria(GoodsRecivedCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GoodsRecived> specification = createSpecification(criteria);
        return goodsRecivedMapper.toDto(goodsRecivedRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GoodsRecivedDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GoodsRecivedDTO> findByCriteria(GoodsRecivedCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GoodsRecived> specification = createSpecification(criteria);
        return goodsRecivedRepository.findAll(specification, page).map(goodsRecivedMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GoodsRecivedCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<GoodsRecived> specification = createSpecification(criteria);
        return goodsRecivedRepository.count(specification);
    }

    /**
     * Function to convert {@link GoodsRecivedCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<GoodsRecived> createSpecification(GoodsRecivedCriteria criteria) {
        Specification<GoodsRecived> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), GoodsRecived_.id));
            }
            if (criteria.getGrDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGrDate(), GoodsRecived_.grDate));
            }
            if (criteria.getQtyOrdered() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQtyOrdered(), GoodsRecived_.qtyOrdered));
            }
            if (criteria.getQtyRecieved() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQtyRecieved(), GoodsRecived_.qtyRecieved));
            }
            if (criteria.getManufacturingDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getManufacturingDate(), GoodsRecived_.manufacturingDate));
            }
            if (criteria.getExpiryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiryDate(), GoodsRecived_.expiryDate));
            }
            if (criteria.getLotNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLotNo(), GoodsRecived_.lotNo));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), GoodsRecived_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), GoodsRecived_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), GoodsRecived_.freeField3));
            }
            if (criteria.getPurchaseQuotationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPurchaseQuotationId(),
                            root -> root.join(GoodsRecived_.purchaseQuotation, JoinType.LEFT).get(PurchaseQuotation_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
