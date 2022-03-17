package com.techvg.inventory.management.repository;

import com.techvg.inventory.management.domain.Transfer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Transfer entity.
 */
@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long>, JpaSpecificationExecutor<Transfer> {
    default Optional<Transfer> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Transfer> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Transfer> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct transfer from Transfer transfer left join fetch transfer.securityUser left join fetch transfer.wareHouse",
        countQuery = "select count(distinct transfer) from Transfer transfer"
    )
    Page<Transfer> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct transfer from Transfer transfer left join fetch transfer.securityUser left join fetch transfer.wareHouse")
    List<Transfer> findAllWithToOneRelationships();

    @Query(
        "select transfer from Transfer transfer left join fetch transfer.securityUser left join fetch transfer.wareHouse where transfer.id =:id"
    )
    Optional<Transfer> findOneWithToOneRelationships(@Param("id") Long id);
}
