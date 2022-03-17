package com.techvg.inventory.management.service.mapper;

import com.techvg.inventory.management.domain.WareHouse;
import com.techvg.inventory.management.service.dto.WareHouseDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WareHouse} and its DTO {@link WareHouseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WareHouseMapper extends EntityMapper<WareHouseDTO, WareHouse> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WareHouseDTO toDtoId(WareHouse wareHouse);

    @Named("whName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "whName", source = "whName")
    WareHouseDTO toDtoWhName(WareHouse wareHouse);

    @Named("whNameSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "whName", source = "whName")
    Set<WareHouseDTO> toDtoWhNameSet(Set<WareHouse> wareHouse);
}
