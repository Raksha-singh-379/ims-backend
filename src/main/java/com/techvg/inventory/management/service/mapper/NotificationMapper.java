package com.techvg.inventory.management.service.mapper;

import com.techvg.inventory.management.domain.Notification;
import com.techvg.inventory.management.service.dto.NotificationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notification} and its DTO {@link NotificationDTO}.
 */
@Mapper(componentModel = "spring", uses = { SecurityUserMapper.class, WareHouseMapper.class })
public interface NotificationMapper extends EntityMapper<NotificationDTO, Notification> {
    @Mapping(target = "securityUser", source = "securityUser", qualifiedByName = "login")
    @Mapping(target = "wareHouse", source = "wareHouse", qualifiedByName = "whName")
    NotificationDTO toDto(Notification s);
}
