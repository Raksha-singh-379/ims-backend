package com.techvg.inventory.management.service.mapper;

import com.techvg.inventory.management.domain.PurchaseQuotationDetails;
import com.techvg.inventory.management.service.dto.PurchaseQuotationDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PurchaseQuotationDetails} and its DTO {@link PurchaseQuotationDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { PurchaseQuotationMapper.class })
public interface PurchaseQuotationDetailsMapper extends EntityMapper<PurchaseQuotationDetailsDTO, PurchaseQuotationDetails> {
    @Mapping(target = "purchaseQuotation", source = "purchaseQuotation", qualifiedByName = "id")
    PurchaseQuotationDetailsDTO toDto(PurchaseQuotationDetails s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PurchaseQuotationDetailsDTO toDtoId(PurchaseQuotationDetails purchaseQuotationDetails);
}
