package com.techvg.inventory.management.service;

import com.techvg.inventory.management.domain.TransferDetails;
import com.techvg.inventory.management.repository.TransferDetailsRepository;
import com.techvg.inventory.management.service.dto.TransferDetailsDTO;
import com.techvg.inventory.management.service.mapper.TransferDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TransferDetails}.
 */
@Service
@Transactional
public class TransferDetailsService {

    private final Logger log = LoggerFactory.getLogger(TransferDetailsService.class);

    private final TransferDetailsRepository transferDetailsRepository;

    private final TransferDetailsMapper transferDetailsMapper;

    public TransferDetailsService(TransferDetailsRepository transferDetailsRepository, TransferDetailsMapper transferDetailsMapper) {
        this.transferDetailsRepository = transferDetailsRepository;
        this.transferDetailsMapper = transferDetailsMapper;
    }

    /**
     * Save a transferDetails.
     *
     * @param transferDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public TransferDetailsDTO save(TransferDetailsDTO transferDetailsDTO) {
        log.debug("Request to save TransferDetails : {}", transferDetailsDTO);
        TransferDetails transferDetails = transferDetailsMapper.toEntity(transferDetailsDTO);
        transferDetails = transferDetailsRepository.save(transferDetails);
        return transferDetailsMapper.toDto(transferDetails);
    }

    /**
     * Partially update a transferDetails.
     *
     * @param transferDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TransferDetailsDTO> partialUpdate(TransferDetailsDTO transferDetailsDTO) {
        log.debug("Request to partially update TransferDetails : {}", transferDetailsDTO);

        return transferDetailsRepository
            .findById(transferDetailsDTO.getId())
            .map(existingTransferDetails -> {
                transferDetailsMapper.partialUpdate(existingTransferDetails, transferDetailsDTO);

                return existingTransferDetails;
            })
            .map(transferDetailsRepository::save)
            .map(transferDetailsMapper::toDto);
    }

    /**
     * Get all the transferDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TransferDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransferDetails");
        return transferDetailsRepository.findAll(pageable).map(transferDetailsMapper::toDto);
    }

    /**
     * Get one transferDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TransferDetailsDTO> findOne(Long id) {
        log.debug("Request to get TransferDetails : {}", id);
        return transferDetailsRepository.findById(id).map(transferDetailsMapper::toDto);
    }

    /**
     * Delete the transferDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TransferDetails : {}", id);
        transferDetailsRepository.deleteById(id);
    }
}
