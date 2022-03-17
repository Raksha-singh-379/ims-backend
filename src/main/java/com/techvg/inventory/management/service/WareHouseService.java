package com.techvg.inventory.management.service;

import com.techvg.inventory.management.domain.WareHouse;
import com.techvg.inventory.management.repository.WareHouseRepository;
import com.techvg.inventory.management.service.dto.WareHouseDTO;
import com.techvg.inventory.management.service.mapper.WareHouseMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WareHouse}.
 */
@Service
@Transactional
public class WareHouseService {

    private final Logger log = LoggerFactory.getLogger(WareHouseService.class);

    private final WareHouseRepository wareHouseRepository;

    private final WareHouseMapper wareHouseMapper;

    public WareHouseService(WareHouseRepository wareHouseRepository, WareHouseMapper wareHouseMapper) {
        this.wareHouseRepository = wareHouseRepository;
        this.wareHouseMapper = wareHouseMapper;
    }

    /**
     * Save a wareHouse.
     *
     * @param wareHouseDTO the entity to save.
     * @return the persisted entity.
     */
    public WareHouseDTO save(WareHouseDTO wareHouseDTO) {
        log.debug("Request to save WareHouse : {}", wareHouseDTO);
        WareHouse wareHouse = wareHouseMapper.toEntity(wareHouseDTO);
        wareHouse = wareHouseRepository.save(wareHouse);
        return wareHouseMapper.toDto(wareHouse);
    }

    /**
     * Partially update a wareHouse.
     *
     * @param wareHouseDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WareHouseDTO> partialUpdate(WareHouseDTO wareHouseDTO) {
        log.debug("Request to partially update WareHouse : {}", wareHouseDTO);

        return wareHouseRepository
            .findById(wareHouseDTO.getId())
            .map(existingWareHouse -> {
                wareHouseMapper.partialUpdate(existingWareHouse, wareHouseDTO);

                return existingWareHouse;
            })
            .map(wareHouseRepository::save)
            .map(wareHouseMapper::toDto);
    }

    /**
     * Get all the wareHouses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WareHouseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WareHouses");
        return wareHouseRepository.findAll(pageable).map(wareHouseMapper::toDto);
    }

    /**
     * Get one wareHouse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WareHouseDTO> findOne(Long id) {
        log.debug("Request to get WareHouse : {}", id);
        return wareHouseRepository.findById(id).map(wareHouseMapper::toDto);
    }

    /**
     * Delete the wareHouse by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WareHouse : {}", id);
        wareHouseRepository.deleteById(id);
    }
}
