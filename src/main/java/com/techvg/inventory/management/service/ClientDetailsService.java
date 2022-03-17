package com.techvg.inventory.management.service;

import com.techvg.inventory.management.domain.ClientDetails;
import com.techvg.inventory.management.repository.ClientDetailsRepository;
import com.techvg.inventory.management.service.dto.ClientDetailsDTO;
import com.techvg.inventory.management.service.mapper.ClientDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClientDetails}.
 */
@Service
@Transactional
public class ClientDetailsService {

    private final Logger log = LoggerFactory.getLogger(ClientDetailsService.class);

    private final ClientDetailsRepository clientDetailsRepository;

    private final ClientDetailsMapper clientDetailsMapper;

    public ClientDetailsService(ClientDetailsRepository clientDetailsRepository, ClientDetailsMapper clientDetailsMapper) {
        this.clientDetailsRepository = clientDetailsRepository;
        this.clientDetailsMapper = clientDetailsMapper;
    }

    /**
     * Save a clientDetails.
     *
     * @param clientDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public ClientDetailsDTO save(ClientDetailsDTO clientDetailsDTO) {
        log.debug("Request to save ClientDetails : {}", clientDetailsDTO);
        ClientDetails clientDetails = clientDetailsMapper.toEntity(clientDetailsDTO);
        clientDetails = clientDetailsRepository.save(clientDetails);
        return clientDetailsMapper.toDto(clientDetails);
    }

    /**
     * Partially update a clientDetails.
     *
     * @param clientDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClientDetailsDTO> partialUpdate(ClientDetailsDTO clientDetailsDTO) {
        log.debug("Request to partially update ClientDetails : {}", clientDetailsDTO);

        return clientDetailsRepository
            .findById(clientDetailsDTO.getId())
            .map(existingClientDetails -> {
                clientDetailsMapper.partialUpdate(existingClientDetails, clientDetailsDTO);

                return existingClientDetails;
            })
            .map(clientDetailsRepository::save)
            .map(clientDetailsMapper::toDto);
    }

    /**
     * Get all the clientDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClientDetails");
        return clientDetailsRepository.findAll(pageable).map(clientDetailsMapper::toDto);
    }

    /**
     * Get one clientDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClientDetailsDTO> findOne(Long id) {
        log.debug("Request to get ClientDetails : {}", id);
        return clientDetailsRepository.findById(id).map(clientDetailsMapper::toDto);
    }

    /**
     * Delete the clientDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClientDetails : {}", id);
        clientDetailsRepository.deleteById(id);
    }
}
