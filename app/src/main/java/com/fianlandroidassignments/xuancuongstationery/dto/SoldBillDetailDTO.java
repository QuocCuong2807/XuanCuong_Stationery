package com.fianlandroidassignments.xuancuongstationery.dto;

public class SoldBillDetailDTO {
    private int id;
    private SoldBillDTO soldBill;
    private ProductDTO product;

    private int productQuantity;
    private int productPrice;
    private int soldPrice;

    public SoldBillDetailDTO(){

    };

    public SoldBillDetailDTO(int productQuantity, int productPrice, int soldPrice) {
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.soldPrice = soldPrice;
    }

    public SoldBillDetailDTO(int id, SoldBillDTO soldBill, ProductDTO product, int productQuantity, int productPrice, int soldPrice) {
        this.id = id;
        this.soldBill = soldBill;
        this.product = product;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.soldPrice = soldPrice;
    }

    public SoldBillDetailDTO(SoldBillDTO soldBill, ProductDTO product, int productQuantity, int productPrice, int soldPrice) {
        this.soldBill = soldBill;
        this.product = product;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.soldPrice = soldPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SoldBillDTO getSoldBill() {
        return soldBill;
    }

    public void setSoldBill(SoldBillDTO soldBill) {
        this.soldBill = soldBill;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(int soldPrice) {
        this.soldPrice = soldPrice;
    }

    public void plusProductQuantity(int quantity){
        this.productQuantity +=quantity ;
    }

    public void minusProductQuantity(){
        this.productQuantity -=1;
    }
}
