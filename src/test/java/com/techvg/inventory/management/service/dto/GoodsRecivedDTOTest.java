package com.techvg.inventory.management.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.inventory.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GoodsRecivedDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoodsRecivedDTO.class);
        GoodsRecivedDTO goodsRecivedDTO1 = new GoodsRecivedDTO();
        goodsRecivedDTO1.setId(1L);
        GoodsRecivedDTO goodsRecivedDTO2 = new GoodsRecivedDTO();
        assertThat(goodsRecivedDTO1).isNotEqualTo(goodsRecivedDTO2);
        goodsRecivedDTO2.setId(goodsRecivedDTO1.getId());
        assertThat(goodsRecivedDTO1).isEqualTo(goodsRecivedDTO2);
        goodsRecivedDTO2.setId(2L);
        assertThat(goodsRecivedDTO1).isNotEqualTo(goodsRecivedDTO2);
        goodsRecivedDTO1.setId(null);
        assertThat(goodsRecivedDTO1).isNotEqualTo(goodsRecivedDTO2);
    }
}
