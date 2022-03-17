package com.techvg.inventory.management.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.inventory.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransferRecievedDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransferRecievedDTO.class);
        TransferRecievedDTO transferRecievedDTO1 = new TransferRecievedDTO();
        transferRecievedDTO1.setId(1L);
        TransferRecievedDTO transferRecievedDTO2 = new TransferRecievedDTO();
        assertThat(transferRecievedDTO1).isNotEqualTo(transferRecievedDTO2);
        transferRecievedDTO2.setId(transferRecievedDTO1.getId());
        assertThat(transferRecievedDTO1).isEqualTo(transferRecievedDTO2);
        transferRecievedDTO2.setId(2L);
        assertThat(transferRecievedDTO1).isNotEqualTo(transferRecievedDTO2);
        transferRecievedDTO1.setId(null);
        assertThat(transferRecievedDTO1).isNotEqualTo(transferRecievedDTO2);
    }
}
