package com.techvg.inventory.management.service;

import com.techvg.inventory.management.domain.TransferRecieved;
import com.techvg.inventory.management.repository.TransferRecievedRepository;
import com.techvg.inventory.management.service.dto.TransferRecievedDTO;
import com.techvg.inventory.management.service.mapper.TransferRecievedMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TransferRecieved}.
 */
@Service
@Transactional
public class TransferRecievedService {

    private final Logger log = LoggerFactory.getLogger(TransferRecievedService.class);

    private final TransferRecievedRepository transferRecievedRepository;

    private final TransferRecievedMapper transferRecievedMapper;

    public TransferRecievedService(TransferRecievedRepository transferRecievedRepository, TransferRecievedMapper transferRecievedMapper) {
        this.transferRecievedRepository = transferRecievedRepository;
        this.transferRecievedMapper = transferRecievedMapper;
    }

    /**
     * Save a transferRecieved.
     *
     * @param transferRecievedDTO the entity to save.
     * @return the persisted entity.
     */
    public TransferRecievedDTO save(TransferRecievedDTO transferRecievedDTO) {
        log.debug("Request to save TransferRecieved : {}", transferRecievedDTO);
        TransferRecieved transferRecieved = transferRecievedMapper.toEntity(transferRecievedDTO);
        transferRecieved = transferRecievedRepository.save(transferRecieved);
        return transferRecievedMapper.toDto(transferRecieved);
    }

    /**
     * Partially update a transferRecieved.
     *
     * @param transferRecievedDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TransferRecievedDTO> partialUpdate(TransferRecievedDTO transferRecievedDTO) {
        log.debug("Request to partially update TransferRecieved : {}", transferRecievedDTO);

        return transferRecievedRepository
            .findById(transferRecievedDTO.getId())
            .map(existingTransferRecieved -> {
                transferRecievedMapper.partialUpdate(existingTransferRecieved, transferRecievedDTO);

                return existingTransferRecieved;
            })
            .map(transferRecievedRepository::save)
            .map(transferRecievedMapper::toDto);
    }

    /**
     * Get all the transferRecieveds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TransferRecievedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransferRecieveds");
        return transferRecievedRepository.findAll(pageable).map(transferRecievedMapper::toDto);
    }

    /**
     * Get all the transferRecieveds with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TransferRecievedDTO> findAllWithEagerRelationships(Pageable pageable) {
        return transferRecievedRepository.findAllWithEagerRelationships(pageable).map(transferRecievedMapper::toDto);
    }

    /**
     * Get one transferRecieved by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TransferRecievedDTO> findOne(Long id) {
        log.debug("Request to get TransferRecieved : {}", id);
        return transferRecievedRepository.findOneWithEagerRelationships(id).map(transferRecievedMapper::toDto);
    }

    /**
     * Delete the transferRecieved by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TransferRecieved : {}", id);
        transferRecievedRepository.deleteById(id);
    }
}
