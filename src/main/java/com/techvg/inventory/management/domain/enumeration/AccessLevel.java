package com.techvg.inventory.management.domain.enumeration;

/**
 * The AccessLevel enumeration.
 */
public enum AccessLevel {
    ROOT("Root"),
    LAB("Lab"),
    SECTION("Section");

    private final String value;

    AccessLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
