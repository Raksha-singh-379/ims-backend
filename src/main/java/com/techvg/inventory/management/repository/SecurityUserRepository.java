package com.techvg.inventory.management.repository;

import com.techvg.inventory.management.domain.SecurityUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SecurityUser entity.
 */
@Repository
public interface SecurityUserRepository
    extends SecurityUserRepositoryWithBagRelationships, JpaRepository<SecurityUser, Long>, JpaSpecificationExecutor<SecurityUser> {
    default Optional<SecurityUser> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<SecurityUser> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<SecurityUser> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
