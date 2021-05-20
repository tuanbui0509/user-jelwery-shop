package com.example.de_tai_di_dong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SanPham {
    @SerializedName("Id")
    @Expose
    private int Id=0;
    @SerializedName("Color")
    @Expose
    private String Color="";
    @SerializedName("Description")
    @Expose
    private String Description = "";
    @SerializedName("Discount")
    @Expose
    private float Discount;
    @SerializedName("Display")
    @Expose
    private boolean Display = true;
    @SerializedName("Image1")
    @Expose
    private String Image1="";
    @SerializedName("Image2")
    @Expose
    private String Image2 = "";
    @SerializedName("Image3")
    @Expose
    private String Image3 = "";
    @SerializedName("Information")
    @Expose
    private String Information = "";
    @SerializedName("Name")
    @Expose
    private String Name = "";
    @SerializedName("Size")
    @Expose
    private String Size = "";
    @SerializedName("Price")
    @Expose
    private float Price=0;
    @SerializedName("ProGroupId")
    @Expose
    private int ProGroupId=0;
    @SerializedName("Stock")
    @Expose
    private int Stock=0;
    @SerializedName("Quantity")
    @Expose
    private int Quantity =0;

    public SanPham(int id, String color, String description, float discount, String image1, String image2, String image3, String information, String name, String size, float price, int proGroupId, int stock ,int quantity) {
        Id = id;
        Color = color;
        Description = description;
        Discount = discount;
        Image1 = image1;
        Image2 = image2;
        Image3 = image3;
        Information = information;
        Name = name;
        Size = size;
        Price = price;
        ProGroupId = proGroupId;
        Stock = stock;
        Quantity=quantity;
    }
    public SanPham(){}

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public float getDiscount() {
        return Discount;
    }

    public void setDiscount(float discount) {
        Discount = discount;
    }

    public boolean getDisplay() {
        return Display;
    }

    public void setDisplay(boolean display) {
        Display = display;
    }

    public String getImage1() {
        return Image1;
    }

    public void setImage1(String image1) {
        Image1 = image1;
    }

    public String getImage2() {
        return Image2;
    }

    public void setImage2(String image2) {
        Image2 = image2;
    }

    public String getImage3() {
        return Image3;
    }

    public void setImage3(String image3) {
        Image3 = image3;
    }

    public String getInformation() {
        return Information;
    }

    public void setInformation(String information) {
        Information = information;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getProGroupId() {
        return ProGroupId;
    }

    public void setProGroupId(int proGroupId) {
        ProGroupId = proGroupId;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }
}
