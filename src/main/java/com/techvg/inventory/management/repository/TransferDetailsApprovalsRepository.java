package com.techvg.inventory.management.repository;

import com.techvg.inventory.management.domain.TransferDetailsApprovals;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TransferDetailsApprovals entity.
 */
@Repository
public interface TransferDetailsApprovalsRepository
    extends JpaRepository<TransferDetailsApprovals, Long>, JpaSpecificationExecutor<TransferDetailsApprovals> {
    default Optional<TransferDetailsApprovals> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TransferDetailsApprovals> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TransferDetailsApprovals> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct transferDetailsApprovals from TransferDetailsApprovals transferDetailsApprovals left join fetch transferDetailsApprovals.securityUser",
        countQuery = "select count(distinct transferDetailsApprovals) from TransferDetailsApprovals transferDetailsApprovals"
    )
    Page<TransferDetailsApprovals> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct transferDetailsApprovals from TransferDetailsApprovals transferDetailsApprovals left join fetch transferDetailsApprovals.securityUser"
    )
    List<TransferDetailsApprovals> findAllWithToOneRelationships();

    @Query(
        "select transferDetailsApprovals from TransferDetailsApprovals transferDetailsApprovals left join fetch transferDetailsApprovals.securityUser where transferDetailsApprovals.id =:id"
    )
    Optional<TransferDetailsApprovals> findOneWithToOneRelationships(@Param("id") Long id);
}
