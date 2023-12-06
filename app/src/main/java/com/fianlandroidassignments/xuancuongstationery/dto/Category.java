package com.fianlandroidassignments.xuancuongstationery.dto;

public class Category {
    private int categoryId;
    private String categoryName;
    private int image;

    private int quantity;

    public Category(int categoryId, String categoryName, int image, int quantity) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.image = image;
        this.quantity = quantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
