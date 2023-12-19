package com.fianlandroidassignments.xuancuongstationery.dto;

public class ImportBillDetailDTO {
    private int id;
    private ImportBillDTO importBill;
    private ProductDTO product;
    private int productQuantity;
    private int productPrice;
    private int importPrice;

    //with id
    public ImportBillDetailDTO(int id, ImportBillDTO importBill, ProductDTO product,
                               int productQuantity, int productPrice, int importPrice)
    {
        this.id = id;
        this.importBill = importBill;
        this.product = product;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.importPrice = importPrice;
    }

    //without id
    public ImportBillDetailDTO(ImportBillDTO importBill, ProductDTO product, int productQuantity,
                               int productPrice, int importPrice)
    {
        this.importBill = importBill;
        this.product = product;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.importPrice = importPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImportBillDTO getImportBill() {
        return importBill;
    }

    public void setImportBill(ImportBillDTO importBill) {
        this.importBill = importBill;
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

    public int getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(int importPrice) {
        this.importPrice = importPrice;
    }
}
