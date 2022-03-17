package com.techvg.inventory.management.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.inventory.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransferRecievedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransferRecieved.class);
        TransferRecieved transferRecieved1 = new TransferRecieved();
        transferRecieved1.setId(1L);
        TransferRecieved transferRecieved2 = new TransferRecieved();
        transferRecieved2.setId(transferRecieved1.getId());
        assertThat(transferRecieved1).isEqualTo(transferRecieved2);
        transferRecieved2.setId(2L);
        assertThat(transferRecieved1).isNotEqualTo(transferRecieved2);
        transferRecieved1.setId(null);
        assertThat(transferRecieved1).isNotEqualTo(transferRecieved2);
    }
}
