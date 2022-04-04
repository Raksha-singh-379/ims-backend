package com.techvg.inventory.management.service.mapper;

import com.techvg.inventory.management.domain.PurchaseQuotation;
import com.techvg.inventory.management.service.dto.PurchaseQuotationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PurchaseQuotation} and its DTO {@link PurchaseQuotationDTO}.
 */
@Mapper(componentModel = "spring", uses = { SecurityUserMapper.class, ProjectMapper.class, ClientDetailsMapper.class })
public interface PurchaseQuotationMapper extends EntityMapper<PurchaseQuotationDTO, PurchaseQuotation> {
    @Mapping(target = "securityUser", source = "securityUser", qualifiedByName = "login")
    @Mapping(target = "project", source = "project", qualifiedByName = "projectName")
    @Mapping(target = "clientDetails", source = "clientDetails", qualifiedByName = "id")
    PurchaseQuotationDTO toDto(PurchaseQuotation s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PurchaseQuotationDTO toDtoId(PurchaseQuotation purchaseQuotation);
}
