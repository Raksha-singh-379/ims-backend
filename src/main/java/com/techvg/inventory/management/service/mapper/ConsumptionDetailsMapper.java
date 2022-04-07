package com.techvg.inventory.management.service.mapper;

import com.techvg.inventory.management.domain.ConsumptionDetails;
import com.techvg.inventory.management.service.dto.ConsumptionDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConsumptionDetails} and its DTO {@link ConsumptionDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { SecurityUserMapper.class, ProjectMapper.class, ProductInventoryMapper.class })
public interface ConsumptionDetailsMapper extends EntityMapper<ConsumptionDetailsDTO, ConsumptionDetails> {
    @Mapping(target = "securityUser", source = "securityUser", qualifiedByName = "login")
    @Mapping(target = "project", source = "project", qualifiedByName = "projectName")
    @Mapping(target = "productInventory", source = "productInventory", qualifiedByName = "id")
    ConsumptionDetailsDTO toDto(ConsumptionDetails s);
}
