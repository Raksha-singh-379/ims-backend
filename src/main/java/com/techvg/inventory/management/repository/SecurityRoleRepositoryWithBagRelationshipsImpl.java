package com.techvg.inventory.management.repository;

import com.techvg.inventory.management.domain.SecurityRole;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class SecurityRoleRepositoryWithBagRelationshipsImpl implements SecurityRoleRepositoryWithBagRelationships {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<SecurityRole> fetchBagRelationships(Optional<SecurityRole> securityRole) {
        return securityRole.map(this::fetchSecurityPermissions);
    }

    @Override
    public Page<SecurityRole> fetchBagRelationships(Page<SecurityRole> securityRoles) {
        return new PageImpl<>(
            fetchBagRelationships(securityRoles.getContent()),
            securityRoles.getPageable(),
            securityRoles.getTotalElements()
        );
    }

    @Override
    public List<SecurityRole> fetchBagRelationships(List<SecurityRole> securityRoles) {
        return Optional.of(securityRoles).map(this::fetchSecurityPermissions).get();
    }

    SecurityRole fetchSecurityPermissions(SecurityRole result) {
        return entityManager
            .createQuery(
                "select securityRole from SecurityRole securityRole left join fetch securityRole.securityPermissions where securityRole is :securityRole",
                SecurityRole.class
            )
            .setParameter("securityRole", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<SecurityRole> fetchSecurityPermissions(List<SecurityRole> securityRoles) {
        return entityManager
            .createQuery(
                "select distinct securityRole from SecurityRole securityRole left join fetch securityRole.securityPermissions where securityRole in :securityRoles",
                SecurityRole.class
            )
            .setParameter("securityRoles", securityRoles)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
