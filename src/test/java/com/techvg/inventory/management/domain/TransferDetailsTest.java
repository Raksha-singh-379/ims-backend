package com.techvg.inventory.management.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.inventory.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransferDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransferDetails.class);
        TransferDetails transferDetails1 = new TransferDetails();
        transferDetails1.setId(1L);
        TransferDetails transferDetails2 = new TransferDetails();
        transferDetails2.setId(transferDetails1.getId());
        assertThat(transferDetails1).isEqualTo(transferDetails2);
        transferDetails2.setId(2L);
        assertThat(transferDetails1).isNotEqualTo(transferDetails2);
        transferDetails1.setId(null);
        assertThat(transferDetails1).isNotEqualTo(transferDetails2);
    }
}
