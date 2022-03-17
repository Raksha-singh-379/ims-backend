package com.techvg.inventory.management.service.mapper;

import com.techvg.inventory.management.domain.SecurityPermission;
import com.techvg.inventory.management.service.dto.SecurityPermissionDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SecurityPermission} and its DTO {@link SecurityPermissionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SecurityPermissionMapper extends EntityMapper<SecurityPermissionDTO, SecurityPermission> {
    @Named("nameSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Set<SecurityPermissionDTO> toDtoNameSet(Set<SecurityPermission> securityPermission);
}
