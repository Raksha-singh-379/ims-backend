package com.techvg.inventory.management.domain.enumeration;

/**
 * The OrderType enumeration.
 */
public enum OrderType {
    PURCHASE_ORDER("PurchaseOrder"),
    PRODUCT_QUATATION("ProductQuatation"),
    TAX_INVOICE("TaxInvoice");

    private final String value;

    OrderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
