package com.techvg.inventory.management.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransferRecievedMapperTest {

    private TransferRecievedMapper transferRecievedMapper;

    @BeforeEach
    public void setUp() {
        transferRecievedMapper = new TransferRecievedMapperImpl();
    }
}
