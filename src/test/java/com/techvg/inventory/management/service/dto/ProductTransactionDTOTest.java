package com.techvg.inventory.management.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.inventory.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductTransactionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductTransactionDTO.class);
        ProductTransactionDTO productTransactionDTO1 = new ProductTransactionDTO();
        productTransactionDTO1.setId(1L);
        ProductTransactionDTO productTransactionDTO2 = new ProductTransactionDTO();
        assertThat(productTransactionDTO1).isNotEqualTo(productTransactionDTO2);
        productTransactionDTO2.setId(productTransactionDTO1.getId());
        assertThat(productTransactionDTO1).isEqualTo(productTransactionDTO2);
        productTransactionDTO2.setId(2L);
        assertThat(productTransactionDTO1).isNotEqualTo(productTransactionDTO2);
        productTransactionDTO1.setId(null);
        assertThat(productTransactionDTO1).isNotEqualTo(productTransactionDTO2);
    }
}
