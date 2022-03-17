package com.techvg.inventory.management.service.mapper;

import com.techvg.inventory.management.domain.TransferRecieved;
import com.techvg.inventory.management.service.dto.TransferRecievedDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransferRecieved} and its DTO {@link TransferRecievedDTO}.
 */
@Mapper(componentModel = "spring", uses = { SecurityUserMapper.class, TransferMapper.class })
public interface TransferRecievedMapper extends EntityMapper<TransferRecievedDTO, TransferRecieved> {
    @Mapping(target = "securityUser", source = "securityUser", qualifiedByName = "login")
    @Mapping(target = "transfer", source = "transfer", qualifiedByName = "id")
    TransferRecievedDTO toDto(TransferRecieved s);
}
