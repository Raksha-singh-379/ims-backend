package com.techvg.inventory.management.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseQuotationMapperTest {

    private PurchaseQuotationMapper purchaseQuotationMapper;

    @BeforeEach
    public void setUp() {
        purchaseQuotationMapper = new PurchaseQuotationMapperImpl();
    }
}
