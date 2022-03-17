package com.techvg.inventory.management.service.mapper;

import com.techvg.inventory.management.domain.ClientDetails;
import com.techvg.inventory.management.service.dto.ClientDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClientDetails} and its DTO {@link ClientDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClientDetailsMapper extends EntityMapper<ClientDetailsDTO, ClientDetails> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDetailsDTO toDtoId(ClientDetails clientDetails);
}
