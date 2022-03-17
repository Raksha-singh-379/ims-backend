package com.techvg.inventory.management.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.inventory.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClientDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientDetailsDTO.class);
        ClientDetailsDTO clientDetailsDTO1 = new ClientDetailsDTO();
        clientDetailsDTO1.setId(1L);
        ClientDetailsDTO clientDetailsDTO2 = new ClientDetailsDTO();
        assertThat(clientDetailsDTO1).isNotEqualTo(clientDetailsDTO2);
        clientDetailsDTO2.setId(clientDetailsDTO1.getId());
        assertThat(clientDetailsDTO1).isEqualTo(clientDetailsDTO2);
        clientDetailsDTO2.setId(2L);
        assertThat(clientDetailsDTO1).isNotEqualTo(clientDetailsDTO2);
        clientDetailsDTO1.setId(null);
        assertThat(clientDetailsDTO1).isNotEqualTo(clientDetailsDTO2);
    }
}
