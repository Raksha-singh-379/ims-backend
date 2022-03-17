package com.techvg.inventory.management.service;

import com.techvg.inventory.management.domain.Categories;
import com.techvg.inventory.management.repository.CategoriesRepository;
import com.techvg.inventory.management.service.dto.CategoriesDTO;
import com.techvg.inventory.management.service.mapper.CategoriesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Categories}.
 */
@Service
@Transactional
public class CategoriesService {

    private final Logger log = LoggerFactory.getLogger(CategoriesService.class);

    private final CategoriesRepository categoriesRepository;

    private final CategoriesMapper categoriesMapper;

    public CategoriesService(CategoriesRepository categoriesRepository, CategoriesMapper categoriesMapper) {
        this.categoriesRepository = categoriesRepository;
        this.categoriesMapper = categoriesMapper;
    }

    /**
     * Save a categories.
     *
     * @param categoriesDTO the entity to save.
     * @return the persisted entity.
     */
    public CategoriesDTO save(CategoriesDTO categoriesDTO) {
        log.debug("Request to save Categories : {}", categoriesDTO);
        Categories categories = categoriesMapper.toEntity(categoriesDTO);
        categories = categoriesRepository.save(categories);
        return categoriesMapper.toDto(categories);
    }

    /**
     * Partially update a categories.
     *
     * @param categoriesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CategoriesDTO> partialUpdate(CategoriesDTO categoriesDTO) {
        log.debug("Request to partially update Categories : {}", categoriesDTO);

        return categoriesRepository
            .findById(categoriesDTO.getId())
            .map(existingCategories -> {
                categoriesMapper.partialUpdate(existingCategories, categoriesDTO);

                return existingCategories;
            })
            .map(categoriesRepository::save)
            .map(categoriesMapper::toDto);
    }

    /**
     * Get all the categories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        return categoriesRepository.findAll(pageable).map(categoriesMapper::toDto);
    }

    /**
     * Get one categories by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CategoriesDTO> findOne(Long id) {
        log.debug("Request to get Categories : {}", id);
        return categoriesRepository.findById(id).map(categoriesMapper::toDto);
    }

    /**
     * Delete the categories by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Categories : {}", id);
        categoriesRepository.deleteById(id);
    }
}
