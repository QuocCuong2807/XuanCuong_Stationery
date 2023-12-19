package com.fianlandroidassignments.xuancuongstationery.dto;

public enum ProductStatus {
    AVAILABLE(0),
    UN_AVAILABLE(1);

    private final int value;

    ProductStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}

