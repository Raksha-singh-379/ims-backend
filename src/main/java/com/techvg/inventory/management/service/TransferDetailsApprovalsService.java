package com.techvg.inventory.management.service;

import com.techvg.inventory.management.domain.TransferDetailsApprovals;
import com.techvg.inventory.management.repository.TransferDetailsApprovalsRepository;
import com.techvg.inventory.management.service.dto.TransferDetailsApprovalsDTO;
import com.techvg.inventory.management.service.mapper.TransferDetailsApprovalsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TransferDetailsApprovals}.
 */
@Service
@Transactional
public class TransferDetailsApprovalsService {

    private final Logger log = LoggerFactory.getLogger(TransferDetailsApprovalsService.class);

    private final TransferDetailsApprovalsRepository transferDetailsApprovalsRepository;

    private final TransferDetailsApprovalsMapper transferDetailsApprovalsMapper;

    public TransferDetailsApprovalsService(
        TransferDetailsApprovalsRepository transferDetailsApprovalsRepository,
        TransferDetailsApprovalsMapper transferDetailsApprovalsMapper
    ) {
        this.transferDetailsApprovalsRepository = transferDetailsApprovalsRepository;
        this.transferDetailsApprovalsMapper = transferDetailsApprovalsMapper;
    }

    /**
     * Save a transferDetailsApprovals.
     *
     * @param transferDetailsApprovalsDTO the entity to save.
     * @return the persisted entity.
     */
    public TransferDetailsApprovalsDTO save(TransferDetailsApprovalsDTO transferDetailsApprovalsDTO) {
        log.debug("Request to save TransferDetailsApprovals : {}", transferDetailsApprovalsDTO);
        TransferDetailsApprovals transferDetailsApprovals = transferDetailsApprovalsMapper.toEntity(transferDetailsApprovalsDTO);
        transferDetailsApprovals = transferDetailsApprovalsRepository.save(transferDetailsApprovals);
        return transferDetailsApprovalsMapper.toDto(transferDetailsApprovals);
    }

    /**
     * Partially update a transferDetailsApprovals.
     *
     * @param transferDetailsApprovalsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TransferDetailsApprovalsDTO> partialUpdate(TransferDetailsApprovalsDTO transferDetailsApprovalsDTO) {
        log.debug("Request to partially update TransferDetailsApprovals : {}", transferDetailsApprovalsDTO);

        return transferDetailsApprovalsRepository
            .findById(transferDetailsApprovalsDTO.getId())
            .map(existingTransferDetailsApprovals -> {
                transferDetailsApprovalsMapper.partialUpdate(existingTransferDetailsApprovals, transferDetailsApprovalsDTO);

                return existingTransferDetailsApprovals;
            })
            .map(transferDetailsApprovalsRepository::save)
            .map(transferDetailsApprovalsMapper::toDto);
    }

    /**
     * Get all the transferDetailsApprovals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TransferDetailsApprovalsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransferDetailsApprovals");
        return transferDetailsApprovalsRepository.findAll(pageable).map(transferDetailsApprovalsMapper::toDto);
    }

    /**
     * Get all the transferDetailsApprovals with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TransferDetailsApprovalsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return transferDetailsApprovalsRepository.findAllWithEagerRelationships(pageable).map(transferDetailsApprovalsMapper::toDto);
    }

    /**
     * Get one transferDetailsApprovals by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TransferDetailsApprovalsDTO> findOne(Long id) {
        log.debug("Request to get TransferDetailsApprovals : {}", id);
        return transferDetailsApprovalsRepository.findOneWithEagerRelationships(id).map(transferDetailsApprovalsMapper::toDto);
    }

    /**
     * Delete the transferDetailsApprovals by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TransferDetailsApprovals : {}", id);
        transferDetailsApprovalsRepository.deleteById(id);
    }
}
