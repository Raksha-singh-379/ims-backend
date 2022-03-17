package com.techvg.inventory.management.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductInventoryMapperTest {

    private ProductInventoryMapper productInventoryMapper;

    @BeforeEach
    public void setUp() {
        productInventoryMapper = new ProductInventoryMapperImpl();
    }
}
