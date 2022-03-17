package com.techvg.inventory.management.service;

import com.techvg.inventory.management.domain.*; // for static metamodels
import com.techvg.inventory.management.domain.TransferDetailsApprovals;
import com.techvg.inventory.management.repository.TransferDetailsApprovalsRepository;
import com.techvg.inventory.management.service.criteria.TransferDetailsApprovalsCriteria;
import com.techvg.inventory.management.service.dto.TransferDetailsApprovalsDTO;
import com.techvg.inventory.management.service.mapper.TransferDetailsApprovalsMapper;
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
 * Service for executing complex queries for {@link TransferDetailsApprovals} entities in the database.
 * The main input is a {@link TransferDetailsApprovalsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransferDetailsApprovalsDTO} or a {@link Page} of {@link TransferDetailsApprovalsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransferDetailsApprovalsQueryService extends QueryService<TransferDetailsApprovals> {

    private final Logger log = LoggerFactory.getLogger(TransferDetailsApprovalsQueryService.class);

    private final TransferDetailsApprovalsRepository transferDetailsApprovalsRepository;

    private final TransferDetailsApprovalsMapper transferDetailsApprovalsMapper;

    public TransferDetailsApprovalsQueryService(
        TransferDetailsApprovalsRepository transferDetailsApprovalsRepository,
        TransferDetailsApprovalsMapper transferDetailsApprovalsMapper
    ) {
        this.transferDetailsApprovalsRepository = transferDetailsApprovalsRepository;
        this.transferDetailsApprovalsMapper = transferDetailsApprovalsMapper;
    }

    /**
     * Return a {@link List} of {@link TransferDetailsApprovalsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransferDetailsApprovalsDTO> findByCriteria(TransferDetailsApprovalsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TransferDetailsApprovals> specification = createSpecification(criteria);
        return transferDetailsApprovalsMapper.toDto(transferDetailsApprovalsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransferDetailsApprovalsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransferDetailsApprovalsDTO> findByCriteria(TransferDetailsApprovalsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TransferDetailsApprovals> specification = createSpecification(criteria);
        return transferDetailsApprovalsRepository.findAll(specification, page).map(transferDetailsApprovalsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransferDetailsApprovalsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TransferDetailsApprovals> specification = createSpecification(criteria);
        return transferDetailsApprovalsRepository.count(specification);
    }

    /**
     * Function to convert {@link TransferDetailsApprovalsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TransferDetailsApprovals> createSpecification(TransferDetailsApprovalsCriteria criteria) {
        Specification<TransferDetailsApprovals> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TransferDetailsApprovals_.id));
            }
            if (criteria.getApprovalDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getApprovalDate(), TransferDetailsApprovals_.approvalDate));
            }
            if (criteria.getQtyRequested() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getQtyRequested(), TransferDetailsApprovals_.qtyRequested));
            }
            if (criteria.getQtyApproved() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getQtyApproved(), TransferDetailsApprovals_.qtyApproved));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), TransferDetailsApprovals_.comment));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), TransferDetailsApprovals_.freeField1));
            }
            if (criteria.getLastModified() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModified(), TransferDetailsApprovals_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), TransferDetailsApprovals_.lastModifiedBy));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), TransferDetailsApprovals_.isDeleted));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), TransferDetailsApprovals_.isActive));
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(TransferDetailsApprovals_.securityUser, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getTransferId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransferId(),
                            root -> root.join(TransferDetailsApprovals_.transfer, JoinType.LEFT).get(Transfer_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
