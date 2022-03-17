package com.techvg.inventory.management.service.mapper;

import com.techvg.inventory.management.domain.ProductTransaction;
import com.techvg.inventory.management.service.dto.ProductTransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductTransaction} and its DTO {@link ProductTransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = { SecurityUserMapper.class, WareHouseMapper.class })
public interface ProductTransactionMapper extends EntityMapper<ProductTransactionDTO, ProductTransaction> {
    @Mapping(target = "securityUser", source = "securityUser", qualifiedByName = "login")
    @Mapping(target = "wareHouse", source = "wareHouse", qualifiedByName = "whName")
    ProductTransactionDTO toDto(ProductTransaction s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductTransactionDTO toDtoId(ProductTransaction productTransaction);
}
