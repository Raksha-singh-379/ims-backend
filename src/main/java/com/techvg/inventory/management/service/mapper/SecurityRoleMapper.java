package com.techvg.inventory.management.service.mapper;

import com.techvg.inventory.management.domain.SecurityRole;
import com.techvg.inventory.management.service.dto.SecurityRoleDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SecurityRole} and its DTO {@link SecurityRoleDTO}.
 */
@Mapper(componentModel = "spring", uses = { SecurityPermissionMapper.class })
public interface SecurityRoleMapper extends EntityMapper<SecurityRoleDTO, SecurityRole> {
    @Mapping(target = "securityPermissions", source = "securityPermissions", qualifiedByName = "nameSet")
    SecurityRoleDTO toDto(SecurityRole s);

    @Mapping(target = "removeSecurityPermission", ignore = true)
    SecurityRole toEntity(SecurityRoleDTO securityRoleDTO);

    @Named("nameSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Set<SecurityRoleDTO> toDtoNameSet(Set<SecurityRole> securityRole);
}
