package com.techvg.inventory.management.service.mapper;

import com.techvg.inventory.management.domain.TransferDetails;
import com.techvg.inventory.management.service.dto.TransferDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransferDetails} and its DTO {@link TransferDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class, TransferMapper.class })
public interface TransferDetailsMapper extends EntityMapper<TransferDetailsDTO, TransferDetails> {
    @Mapping(target = "product", source = "product", qualifiedByName = "id")
    @Mapping(target = "transfer", source = "transfer", qualifiedByName = "id")
    TransferDetailsDTO toDto(TransferDetails s);
}
