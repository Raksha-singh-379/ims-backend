package com.techvg.inventory.management.service;

import com.techvg.inventory.management.domain.GoodsRecived;
import com.techvg.inventory.management.repository.GoodsRecivedRepository;
import com.techvg.inventory.management.service.dto.GoodsRecivedDTO;
import com.techvg.inventory.management.service.mapper.GoodsRecivedMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GoodsRecived}.
 */
@Service
@Transactional
public class GoodsRecivedService {

    private final Logger log = LoggerFactory.getLogger(GoodsRecivedService.class);

    private final GoodsRecivedRepository goodsRecivedRepository;

    private final GoodsRecivedMapper goodsRecivedMapper;

    public GoodsRecivedService(GoodsRecivedRepository goodsRecivedRepository, GoodsRecivedMapper goodsRecivedMapper) {
        this.goodsRecivedRepository = goodsRecivedRepository;
        this.goodsRecivedMapper = goodsRecivedMapper;
    }

    /**
     * Save a goodsRecived.
     *
     * @param goodsRecivedDTO the entity to save.
     * @return the persisted entity.
     */
    public GoodsRecivedDTO save(GoodsRecivedDTO goodsRecivedDTO) {
        log.debug("Request to save GoodsRecived : {}", goodsRecivedDTO);
        GoodsRecived goodsRecived = goodsRecivedMapper.toEntity(goodsRecivedDTO);
        goodsRecived = goodsRecivedRepository.save(goodsRecived);
        return goodsRecivedMapper.toDto(goodsRecived);
    }

    /**
     * Partially update a goodsRecived.
     *
     * @param goodsRecivedDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GoodsRecivedDTO> partialUpdate(GoodsRecivedDTO goodsRecivedDTO) {
        log.debug("Request to partially update GoodsRecived : {}", goodsRecivedDTO);

        return goodsRecivedRepository
            .findById(goodsRecivedDTO.getId())
            .map(existingGoodsRecived -> {
                goodsRecivedMapper.partialUpdate(existingGoodsRecived, goodsRecivedDTO);

                return existingGoodsRecived;
            })
            .map(goodsRecivedRepository::save)
            .map(goodsRecivedMapper::toDto);
    }

    /**
     * Get all the goodsReciveds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GoodsRecivedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GoodsReciveds");
        return goodsRecivedRepository.findAll(pageable).map(goodsRecivedMapper::toDto);
    }

    /**
     * Get one goodsRecived by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GoodsRecivedDTO> findOne(Long id) {
        log.debug("Request to get GoodsRecived : {}", id);
        return goodsRecivedRepository.findById(id).map(goodsRecivedMapper::toDto);
    }

    /**
     * Delete the goodsRecived by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GoodsRecived : {}", id);
        goodsRecivedRepository.deleteById(id);
    }
}
