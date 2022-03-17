package com.techvg.inventory.management.repository;

import com.techvg.inventory.management.domain.ConsumptionDetails;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ConsumptionDetails entity.
 */
@Repository
public interface ConsumptionDetailsRepository
    extends JpaRepository<ConsumptionDetails, Long>, JpaSpecificationExecutor<ConsumptionDetails> {
    default Optional<ConsumptionDetails> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ConsumptionDetails> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ConsumptionDetails> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct consumptionDetails from ConsumptionDetails consumptionDetails left join fetch consumptionDetails.securityUser left join fetch consumptionDetails.project",
        countQuery = "select count(distinct consumptionDetails) from ConsumptionDetails consumptionDetails"
    )
    Page<ConsumptionDetails> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct consumptionDetails from ConsumptionDetails consumptionDetails left join fetch consumptionDetails.securityUser left join fetch consumptionDetails.project"
    )
    List<ConsumptionDetails> findAllWithToOneRelationships();

    @Query(
        "select consumptionDetails from ConsumptionDetails consumptionDetails left join fetch consumptionDetails.securityUser left join fetch consumptionDetails.project where consumptionDetails.id =:id"
    )
    Optional<ConsumptionDetails> findOneWithToOneRelationships(@Param("id") Long id);
}
