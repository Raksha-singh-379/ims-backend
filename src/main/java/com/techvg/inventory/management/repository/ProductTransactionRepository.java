package com.techvg.inventory.management.repository;

import com.techvg.inventory.management.domain.ProductTransaction;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductTransaction entity.
 */
@Repository
public interface ProductTransactionRepository
    extends JpaRepository<ProductTransaction, Long>, JpaSpecificationExecutor<ProductTransaction> {
    default Optional<ProductTransaction> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ProductTransaction> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ProductTransaction> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct productTransaction from ProductTransaction productTransaction left join fetch productTransaction.securityUser left join fetch productTransaction.wareHouse",
        countQuery = "select count(distinct productTransaction) from ProductTransaction productTransaction"
    )
    Page<ProductTransaction> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct productTransaction from ProductTransaction productTransaction left join fetch productTransaction.securityUser left join fetch productTransaction.wareHouse"
    )
    List<ProductTransaction> findAllWithToOneRelationships();

    @Query(
        "select productTransaction from ProductTransaction productTransaction left join fetch productTransaction.securityUser left join fetch productTransaction.wareHouse where productTransaction.id =:id"
    )
    Optional<ProductTransaction> findOneWithToOneRelationships(@Param("id") Long id);
}
