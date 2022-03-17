package com.techvg.inventory.management.service;

import com.techvg.inventory.management.domain.ProductInventory;
import com.techvg.inventory.management.repository.ProductInventoryRepository;
import com.techvg.inventory.management.service.dto.ProductInventoryDTO;
import com.techvg.inventory.management.service.mapper.ProductInventoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductInventory}.
 */
@Service
@Transactional
public class ProductInventoryService {

    private final Logger log = LoggerFactory.getLogger(ProductInventoryService.class);

    private final ProductInventoryRepository productInventoryRepository;

    private final ProductInventoryMapper productInventoryMapper;

    public ProductInventoryService(ProductInventoryRepository productInventoryRepository, ProductInventoryMapper productInventoryMapper) {
        this.productInventoryRepository = productInventoryRepository;
        this.productInventoryMapper = productInventoryMapper;
    }

    /**
     * Save a productInventory.
     *
     * @param productInventoryDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductInventoryDTO save(ProductInventoryDTO productInventoryDTO) {
        log.debug("Request to save ProductInventory : {}", productInventoryDTO);
        ProductInventory productInventory = productInventoryMapper.toEntity(productInventoryDTO);
        productInventory = productInventoryRepository.save(productInventory);
        return productInventoryMapper.toDto(productInventory);
    }

    /**
     * Partially update a productInventory.
     *
     * @param productInventoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductInventoryDTO> partialUpdate(ProductInventoryDTO productInventoryDTO) {
        log.debug("Request to partially update ProductInventory : {}", productInventoryDTO);

        return productInventoryRepository
            .findById(productInventoryDTO.getId())
            .map(existingProductInventory -> {
                productInventoryMapper.partialUpdate(existingProductInventory, productInventoryDTO);

                return existingProductInventory;
            })
            .map(productInventoryRepository::save)
            .map(productInventoryMapper::toDto);
    }

    /**
     * Get all the productInventories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductInventoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductInventories");
        return productInventoryRepository.findAll(pageable).map(productInventoryMapper::toDto);
    }

    /**
     * Get all the productInventories with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProductInventoryDTO> findAllWithEagerRelationships(Pageable pageable) {
        return productInventoryRepository.findAllWithEagerRelationships(pageable).map(productInventoryMapper::toDto);
    }

    /**
     * Get one productInventory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductInventoryDTO> findOne(Long id) {
        log.debug("Request to get ProductInventory : {}", id);
        return productInventoryRepository.findOneWithEagerRelationships(id).map(productInventoryMapper::toDto);
    }

    /**
     * Delete the productInventory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductInventory : {}", id);
        productInventoryRepository.deleteById(id);
    }
}
