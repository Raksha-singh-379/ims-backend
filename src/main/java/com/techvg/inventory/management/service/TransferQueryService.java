package com.techvg.inventory.management.service;

import com.techvg.inventory.management.domain.*; // for static metamodels
import com.techvg.inventory.management.domain.Transfer;
import com.techvg.inventory.management.repository.TransferRepository;
import com.techvg.inventory.management.service.criteria.TransferCriteria;
import com.techvg.inventory.management.service.dto.TransferDTO;
import com.techvg.inventory.management.service.mapper.TransferMapper;
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
 * Service for executing complex queries for {@link Transfer} entities in the database.
 * The main input is a {@link TransferCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransferDTO} or a {@link Page} of {@link TransferDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransferQueryService extends QueryService<Transfer> {

    private final Logger log = LoggerFactory.getLogger(TransferQueryService.class);

    private final TransferRepository transferRepository;

    private final TransferMapper transferMapper;

    public TransferQueryService(TransferRepository transferRepository, TransferMapper transferMapper) {
        this.transferRepository = transferRepository;
        this.transferMapper = transferMapper;
    }

    /**
     * Return a {@link List} of {@link TransferDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransferDTO> findByCriteria(TransferCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Transfer> specification = createSpecification(criteria);
        return transferMapper.toDto(transferRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransferDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransferDTO> findByCriteria(TransferCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Transfer> specification = createSpecification(criteria);
        return transferRepository.findAll(specification, page).map(transferMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransferCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Transfer> specification = createSpecification(criteria);
        return transferRepository.count(specification);
    }

    /**
     * Function to convert {@link TransferCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Transfer> createSpecification(TransferCriteria criteria) {
        Specification<Transfer> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Transfer_.id));
            }
            if (criteria.getTranferDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTranferDate(), Transfer_.tranferDate));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), Transfer_.comment));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Transfer_.status));
            }
            if (criteria.getSourceWareHouse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSourceWareHouse(), Transfer_.sourceWareHouse));
            }
            if (criteria.getDestinationWareHouse() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDestinationWareHouse(), Transfer_.destinationWareHouse));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), Transfer_.freeField1));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), Transfer_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Transfer_.lastModifiedBy));
            }
            if (criteria.getTransferDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransferDetailsId(),
                            root -> root.join(Transfer_.transferDetails, JoinType.LEFT).get(TransferDetails_.id)
                        )
                    );
            }
            if (criteria.getTransferRecievedId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransferRecievedId(),
                            root -> root.join(Transfer_.transferRecieveds, JoinType.LEFT).get(TransferRecieved_.id)
                        )
                    );
            }
            if (criteria.getTransferDetailsApprovalsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransferDetailsApprovalsId(),
                            root -> root.join(Transfer_.transferDetailsApprovals, JoinType.LEFT).get(TransferDetailsApprovals_.id)
                        )
                    );
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(Transfer_.securityUser, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getWareHouseId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getWareHouseId(),
                            root -> root.join(Transfer_.wareHouse, JoinType.LEFT).get(WareHouse_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
