package com.fianlandroidassignments.xuancuongstationery.dto;

public enum ProductStatus {
    AVAILABLE(1),
    UN_AVAILABLE(0);

    private final int value;

    ProductStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}

