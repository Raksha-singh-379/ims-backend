package com.techvg.inventory.management.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.inventory.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WareHouseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WareHouse.class);
        WareHouse wareHouse1 = new WareHouse();
        wareHouse1.setId(1L);
        WareHouse wareHouse2 = new WareHouse();
        wareHouse2.setId(wareHouse1.getId());
        assertThat(wareHouse1).isEqualTo(wareHouse2);
        wareHouse2.setId(2L);
        assertThat(wareHouse1).isNotEqualTo(wareHouse2);
        wareHouse1.setId(null);
        assertThat(wareHouse1).isNotEqualTo(wareHouse2);
    }
}
