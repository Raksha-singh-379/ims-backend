package com.techvg.inventory.management.repository;

import com.techvg.inventory.management.domain.WareHouse;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the WareHouse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WareHouseRepository extends JpaRepository<WareHouse, Long>, JpaSpecificationExecutor<WareHouse> {}
