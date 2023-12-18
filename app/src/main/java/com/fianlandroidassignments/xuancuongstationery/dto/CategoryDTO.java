package com.fianlandroidassignments.xuancuongstationery.dto;

public class CategoryDTO {
    private int category_id;
    private String category_name;
    private byte [] image;
    private int product_quantity;

    public CategoryDTO(int category_id, String category_name, byte[] image, int product_quantity) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.image = image;
        this.product_quantity = product_quantity;
    }

    public CategoryDTO(String category_name, byte[] image) {
        this.category_name = category_name;
        this.image = image;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }
}
