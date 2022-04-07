package com.techvg.inventory.management.service.mapper;

import com.techvg.inventory.management.domain.PurchaseQuotationDetails;
import com.techvg.inventory.management.service.dto.PurchaseQuotationDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PurchaseQuotationDetails} and its DTO {@link PurchaseQuotationDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class, PurchaseQuotationMapper.class })
public interface PurchaseQuotationDetailsMapper extends EntityMapper<PurchaseQuotationDetailsDTO, PurchaseQuotationDetails> {
    @Mapping(target = "product", source = "product", qualifiedByName = "id")
    @Mapping(target = "purchaseQuotation", source = "purchaseQuotation", qualifiedByName = "id")
    PurchaseQuotationDetailsDTO toDto(PurchaseQuotationDetails s);
}
