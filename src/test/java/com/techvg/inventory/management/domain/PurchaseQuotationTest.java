package com.techvg.inventory.management.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.inventory.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PurchaseQuotationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseQuotation.class);
        PurchaseQuotation purchaseQuotation1 = new PurchaseQuotation();
        purchaseQuotation1.setId(1L);
        PurchaseQuotation purchaseQuotation2 = new PurchaseQuotation();
        purchaseQuotation2.setId(purchaseQuotation1.getId());
        assertThat(purchaseQuotation1).isEqualTo(purchaseQuotation2);
        purchaseQuotation2.setId(2L);
        assertThat(purchaseQuotation1).isNotEqualTo(purchaseQuotation2);
        purchaseQuotation1.setId(null);
        assertThat(purchaseQuotation1).isNotEqualTo(purchaseQuotation2);
    }
}
