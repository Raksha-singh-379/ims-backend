package com.techvg.inventory.management.repository;

import com.techvg.inventory.management.domain.SecurityPermission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SecurityPermission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecurityPermissionRepository
    extends JpaRepository<SecurityPermission, Long>, JpaSpecificationExecutor<SecurityPermission> {}
