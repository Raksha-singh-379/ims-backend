package com.techvg.inventory.management.repository;

import com.techvg.inventory.management.domain.SecurityUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface SecurityUserRepositoryWithBagRelationships {
    Optional<SecurityUser> fetchBagRelationships(Optional<SecurityUser> securityUser);

    List<SecurityUser> fetchBagRelationships(List<SecurityUser> securityUsers);

    Page<SecurityUser> fetchBagRelationships(Page<SecurityUser> securityUsers);
}
