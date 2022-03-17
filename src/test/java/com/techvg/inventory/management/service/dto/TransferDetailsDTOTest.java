package com.techvg.inventory.management.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.inventory.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransferDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransferDetailsDTO.class);
        TransferDetailsDTO transferDetailsDTO1 = new TransferDetailsDTO();
        transferDetailsDTO1.setId(1L);
        TransferDetailsDTO transferDetailsDTO2 = new TransferDetailsDTO();
        assertThat(transferDetailsDTO1).isNotEqualTo(transferDetailsDTO2);
        transferDetailsDTO2.setId(transferDetailsDTO1.getId());
        assertThat(transferDetailsDTO1).isEqualTo(transferDetailsDTO2);
        transferDetailsDTO2.setId(2L);
        assertThat(transferDetailsDTO1).isNotEqualTo(transferDetailsDTO2);
        transferDetailsDTO1.setId(null);
        assertThat(transferDetailsDTO1).isNotEqualTo(transferDetailsDTO2);
    }
}
