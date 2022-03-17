package com.techvg.inventory.management.service;

import com.techvg.inventory.management.domain.*; // for static metamodels
import com.techvg.inventory.management.domain.TransferDetails;
import com.techvg.inventory.management.repository.TransferDetailsRepository;
import com.techvg.inventory.management.service.criteria.TransferDetailsCriteria;
import com.techvg.inventory.management.service.dto.TransferDetailsDTO;
import com.techvg.inventory.management.service.mapper.TransferDetailsMapper;
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
 * Service for executing complex queries for {@link TransferDetails} entities in the database.
 * The main input is a {@link TransferDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransferDetailsDTO} or a {@link Page} of {@link TransferDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransferDetailsQueryService extends QueryService<TransferDetails> {

    private final Logger log = LoggerFactory.getLogger(TransferDetailsQueryService.class);

    private final TransferDetailsRepository transferDetailsRepository;

    private final TransferDetailsMapper transferDetailsMapper;

    public TransferDetailsQueryService(TransferDetailsRepository transferDetailsRepository, TransferDetailsMapper transferDetailsMapper) {
        this.transferDetailsRepository = transferDetailsRepository;
        this.transferDetailsMapper = transferDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link TransferDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransferDetailsDTO> findByCriteria(TransferDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TransferDetails> specification = createSpecification(criteria);
        return transferDetailsMapper.toDto(transferDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransferDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransferDetailsDTO> findByCriteria(TransferDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TransferDetails> specification = createSpecification(criteria);
        return transferDetailsRepository.findAll(specification, page).map(transferDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransferDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TransferDetails> specification = createSpecification(criteria);
        return transferDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link TransferDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TransferDetails> createSpecification(TransferDetailsCriteria criteria) {
        Specification<TransferDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TransferDetails_.id));
            }
            if (criteria.getApprovalDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApprovalDate(), TransferDetails_.approvalDate));
            }
            if (criteria.getQty() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQty(), TransferDetails_.qty));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), TransferDetails_.comment));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), TransferDetails_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), TransferDetails_.freeField2));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), TransferDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), TransferDetails_.lastModifiedBy));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), TransferDetails_.isDeleted));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), TransferDetails_.isActive));
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(TransferDetails_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
            if (criteria.getTransferId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransferId(),
                            root -> root.join(TransferDetails_.transfer, JoinType.LEFT).get(Transfer_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
