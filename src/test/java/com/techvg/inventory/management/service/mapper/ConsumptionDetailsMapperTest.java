package com.techvg.inventory.management.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsumptionDetailsMapperTest {

    private ConsumptionDetailsMapper consumptionDetailsMapper;

    @BeforeEach
    public void setUp() {
        consumptionDetailsMapper = new ConsumptionDetailsMapperImpl();
    }
}
