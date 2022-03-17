package com.techvg.inventory.management.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientDetailsMapperTest {

    private ClientDetailsMapper clientDetailsMapper;

    @BeforeEach
    public void setUp() {
        clientDetailsMapper = new ClientDetailsMapperImpl();
    }
}
