package com.techvg.inventory.management.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.inventory.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductTransactionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductTransaction.class);
        ProductTransaction productTransaction1 = new ProductTransaction();
        productTransaction1.setId(1L);
        ProductTransaction productTransaction2 = new ProductTransaction();
        productTransaction2.setId(productTransaction1.getId());
        assertThat(productTransaction1).isEqualTo(productTransaction2);
        productTransaction2.setId(2L);
        assertThat(productTransaction1).isNotEqualTo(productTransaction2);
        productTransaction1.setId(null);
        assertThat(productTransaction1).isNotEqualTo(productTransaction2);
    }
}
