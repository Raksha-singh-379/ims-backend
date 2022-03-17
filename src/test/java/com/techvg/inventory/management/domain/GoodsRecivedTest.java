package com.techvg.inventory.management.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.inventory.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GoodsRecivedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoodsRecived.class);
        GoodsRecived goodsRecived1 = new GoodsRecived();
        goodsRecived1.setId(1L);
        GoodsRecived goodsRecived2 = new GoodsRecived();
        goodsRecived2.setId(goodsRecived1.getId());
        assertThat(goodsRecived1).isEqualTo(goodsRecived2);
        goodsRecived2.setId(2L);
        assertThat(goodsRecived1).isNotEqualTo(goodsRecived2);
        goodsRecived1.setId(null);
        assertThat(goodsRecived1).isNotEqualTo(goodsRecived2);
    }
}
