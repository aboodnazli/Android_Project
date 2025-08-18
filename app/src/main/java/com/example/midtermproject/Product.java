package com.example.midtermproject;



import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String name;
    private String description;
    private double price;
    private double rating;
    private int imageUrl;
    private int quantity;


    public Product(String id, String name, String description, double price, double rating, int imageUrl,int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
