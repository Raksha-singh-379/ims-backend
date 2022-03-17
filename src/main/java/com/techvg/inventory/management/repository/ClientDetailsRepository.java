package com.techvg.inventory.management.repository;

import com.techvg.inventory.management.domain.ClientDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClientDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientDetailsRepository extends JpaRepository<ClientDetails, Long>, JpaSpecificationExecutor<ClientDetails> {}
