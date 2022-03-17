package com.techvg.inventory.management.repository;

import com.techvg.inventory.management.domain.PurchaseQuotation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PurchaseQuotation entity.
 */
@Repository
public interface PurchaseQuotationRepository extends JpaRepository<PurchaseQuotation, Long>, JpaSpecificationExecutor<PurchaseQuotation> {
    default Optional<PurchaseQuotation> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PurchaseQuotation> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PurchaseQuotation> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct purchaseQuotation from PurchaseQuotation purchaseQuotation left join fetch purchaseQuotation.securityUser",
        countQuery = "select count(distinct purchaseQuotation) from PurchaseQuotation purchaseQuotation"
    )
    Page<PurchaseQuotation> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct purchaseQuotation from PurchaseQuotation purchaseQuotation left join fetch purchaseQuotation.securityUser")
    List<PurchaseQuotation> findAllWithToOneRelationships();

    @Query(
        "select purchaseQuotation from PurchaseQuotation purchaseQuotation left join fetch purchaseQuotation.securityUser where purchaseQuotation.id =:id"
    )
    Optional<PurchaseQuotation> findOneWithToOneRelationships(@Param("id") Long id);
}
