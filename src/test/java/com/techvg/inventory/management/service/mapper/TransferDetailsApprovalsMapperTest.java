package com.techvg.inventory.management.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransferDetailsApprovalsMapperTest {

    private TransferDetailsApprovalsMapper transferDetailsApprovalsMapper;

    @BeforeEach
    public void setUp() {
        transferDetailsApprovalsMapper = new TransferDetailsApprovalsMapperImpl();
    }
}
