package com.techvg.inventory.management.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.inventory.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WareHouseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WareHouseDTO.class);
        WareHouseDTO wareHouseDTO1 = new WareHouseDTO();
        wareHouseDTO1.setId(1L);
        WareHouseDTO wareHouseDTO2 = new WareHouseDTO();
        assertThat(wareHouseDTO1).isNotEqualTo(wareHouseDTO2);
        wareHouseDTO2.setId(wareHouseDTO1.getId());
        assertThat(wareHouseDTO1).isEqualTo(wareHouseDTO2);
        wareHouseDTO2.setId(2L);
        assertThat(wareHouseDTO1).isNotEqualTo(wareHouseDTO2);
        wareHouseDTO1.setId(null);
        assertThat(wareHouseDTO1).isNotEqualTo(wareHouseDTO2);
    }
}
