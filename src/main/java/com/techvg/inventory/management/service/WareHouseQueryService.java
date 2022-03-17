package com.techvg.inventory.management.service;

import com.techvg.inventory.management.domain.*; // for static metamodels
import com.techvg.inventory.management.domain.WareHouse;
import com.techvg.inventory.management.repository.WareHouseRepository;
import com.techvg.inventory.management.service.criteria.WareHouseCriteria;
import com.techvg.inventory.management.service.dto.WareHouseDTO;
import com.techvg.inventory.management.service.mapper.WareHouseMapper;
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
 * Service for executing complex queries for {@link WareHouse} entities in the database.
 * The main input is a {@link WareHouseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WareHouseDTO} or a {@link Page} of {@link WareHouseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WareHouseQueryService extends QueryService<WareHouse> {

    private final Logger log = LoggerFactory.getLogger(WareHouseQueryService.class);

    private final WareHouseRepository wareHouseRepository;

    private final WareHouseMapper wareHouseMapper;

    public WareHouseQueryService(WareHouseRepository wareHouseRepository, WareHouseMapper wareHouseMapper) {
        this.wareHouseRepository = wareHouseRepository;
        this.wareHouseMapper = wareHouseMapper;
    }

    /**
     * Return a {@link List} of {@link WareHouseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WareHouseDTO> findByCriteria(WareHouseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WareHouse> specification = createSpecification(criteria);
        return wareHouseMapper.toDto(wareHouseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WareHouseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WareHouseDTO> findByCriteria(WareHouseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WareHouse> specification = createSpecification(criteria);
        return wareHouseRepository.findAll(specification, page).map(wareHouseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WareHouseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WareHouse> specification = createSpecification(criteria);
        return wareHouseRepository.count(specification);
    }

    /**
     * Function to convert {@link WareHouseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WareHouse> createSpecification(WareHouseCriteria criteria) {
        Specification<WareHouse> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WareHouse_.id));
            }
            if (criteria.getWhName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWhName(), WareHouse_.whName));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), WareHouse_.address));
            }
            if (criteria.getPincode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPincode(), WareHouse_.pincode));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), WareHouse_.city));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), WareHouse_.state));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), WareHouse_.country));
            }
            if (criteria.getgSTDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getgSTDetails(), WareHouse_.gSTDetails));
            }
            if (criteria.getManagerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManagerName(), WareHouse_.managerName));
            }
            if (criteria.getManagerEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManagerEmail(), WareHouse_.managerEmail));
            }
            if (criteria.getManagerContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManagerContact(), WareHouse_.managerContact));
            }
            if (criteria.getContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContact(), WareHouse_.contact));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), WareHouse_.isDeleted));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), WareHouse_.isActive));
            }
            if (criteria.getWareHouseId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWareHouseId(), WareHouse_.wareHouseId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), WareHouse_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), WareHouse_.lastModifiedBy));
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(WareHouse_.securityUsers, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
