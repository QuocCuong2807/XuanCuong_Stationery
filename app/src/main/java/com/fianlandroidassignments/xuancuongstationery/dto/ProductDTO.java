package com.fianlandroidassignments.xuancuongstationery.dto;

public class ProductDTO {
    private int product_id;
    private String product_name;
    private byte[] image;
    private int product_quantity;
    private int product_price;
    private int product_status;
    private String product_description;
    private CategoryDTO category;
    private ProviderDTO provider;

    //constructor with id
    public ProductDTO(int product_id, String product_name, byte[] image, int product_quantity,
                      int product_price, int product_status, String product_description,
                      CategoryDTO category, ProviderDTO provider)
    {
        this.product_id = product_id;
        this.product_name = product_name;
        this.image = image;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
        this.product_status = product_status;
        this.product_description = product_description;
        this.category = category;
        this.provider = provider;
    }

    //constructor without id
    public ProductDTO(String product_name, byte[] image, int product_quantity, int product_price,
                      int product_status, String product_description, CategoryDTO category, ProviderDTO provider)
    {
        this.product_name = product_name;
        this.image = image;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
        this.product_status = product_status;
        this.product_description = product_description;
        this.category = category;
        this.provider = provider;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
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

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getProduct_status() {
        return product_status;
    }

    public void setProduct_status(int product_status) {
        this.product_status = product_status;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public ProviderDTO getProvider() {
        return provider;
    }

    public void setProvider(ProviderDTO provider) {
        this.provider = provider;
    }
}