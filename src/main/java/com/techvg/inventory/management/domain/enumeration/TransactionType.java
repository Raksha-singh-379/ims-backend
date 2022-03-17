package com.techvg.inventory.management.domain.enumeration;

/**
 * The TransactionType enumeration.
 */
public enum TransactionType {
    OUTWARDS_CONSUMPTION("Outward_Consumption"),
    INWARD_STOCKS("Inward_Stocks"),
    TRANSFER_STOCKS("Transfer_Stocks"),
    OTHER("Other");

    private final String value;

    TransactionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
