package com.fianlandroidassignments.xuancuongstationery.dto;

public class RevenueCategoryDTO {
    private int category_id;
    private String category_name;
    private long total_revenue;

    public RevenueCategoryDTO(int category_id, String category_name, long total_revenue) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.total_revenue = total_revenue;
    }

    public RevenueCategoryDTO(String category_name, long total_revenue) {
        this.category_name = category_name;
        this.total_revenue = total_revenue;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public long getTotal_revenue() {
        return total_revenue;
    }

    public void setTotal_revenue(long total_revenue) {
        this.total_revenue = total_revenue;
    }
}
