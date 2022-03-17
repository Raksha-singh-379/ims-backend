package com.techvg.inventory.management.domain.enumeration;

/**
 * The ProductType enumeration.
 */
public enum ProductType {
    RAWMATERIAL("RawMaterial"),
    PRODUCT("Product"),
    BOTH("Both"),
    OTHER("Other");

    private final String value;

    ProductType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
