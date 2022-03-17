package com.techvg.inventory.management.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.inventory.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConsumptionDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsumptionDetailsDTO.class);
        ConsumptionDetailsDTO consumptionDetailsDTO1 = new ConsumptionDetailsDTO();
        consumptionDetailsDTO1.setId(1L);
        ConsumptionDetailsDTO consumptionDetailsDTO2 = new ConsumptionDetailsDTO();
        assertThat(consumptionDetailsDTO1).isNotEqualTo(consumptionDetailsDTO2);
        consumptionDetailsDTO2.setId(consumptionDetailsDTO1.getId());
        assertThat(consumptionDetailsDTO1).isEqualTo(consumptionDetailsDTO2);
        consumptionDetailsDTO2.setId(2L);
        assertThat(consumptionDetailsDTO1).isNotEqualTo(consumptionDetailsDTO2);
        consumptionDetailsDTO1.setId(null);
        assertThat(consumptionDetailsDTO1).isNotEqualTo(consumptionDetailsDTO2);
    }
}
