package com.example.midtermproject;



import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String name;
    private String description;
    private double price;
    private double rating;
    private int imageUrl;

    public Product(String id, String name, String description, double price, double rating, int imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.imageUrl = imageUrl;
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
}
