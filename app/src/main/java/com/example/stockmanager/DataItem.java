package com.example.stockmanager;

public class DataItem {
    private String Barcode;
    private String Pname;
    private String Supplier;
    private String Category;
    private String Quantity;
    private String OriginalPrice;
    private String SellingPrice;
    private String Date;

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public String getSupplier() {
        return Supplier;
    }

    public void setSupplier(String supplier) {
        Supplier = supplier;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getOriginalPrice() {
        return OriginalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        OriginalPrice = originalPrice;
    }

    public String getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "Barcode='" + Barcode + '\'' +
                ", Pname='" + Pname + '\'' +
                ", Supplier='" + Supplier + '\'' +
                ", Category='" + Category + '\'' +
                ", Quantity='" + Quantity + '\'' +
                ", OriginalPrice='" + OriginalPrice + '\'' +
                ", SellingPrice='" + SellingPrice + '\'' +
                ", Date='" + Date + '\'' +
                '}';
    }
}