package com.techvg.inventory.management.repository;

import com.techvg.inventory.management.domain.TransferRecieved;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TransferRecieved entity.
 */
@Repository
public interface TransferRecievedRepository extends JpaRepository<TransferRecieved, Long>, JpaSpecificationExecutor<TransferRecieved> {
    default Optional<TransferRecieved> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TransferRecieved> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TransferRecieved> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct transferRecieved from TransferRecieved transferRecieved left join fetch transferRecieved.securityUser",
        countQuery = "select count(distinct transferRecieved) from TransferRecieved transferRecieved"
    )
    Page<TransferRecieved> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct transferRecieved from TransferRecieved transferRecieved left join fetch transferRecieved.securityUser")
    List<TransferRecieved> findAllWithToOneRelationships();

    @Query(
        "select transferRecieved from TransferRecieved transferRecieved left join fetch transferRecieved.securityUser where transferRecieved.id =:id"
    )
    Optional<TransferRecieved> findOneWithToOneRelationships(@Param("id") Long id);
}
