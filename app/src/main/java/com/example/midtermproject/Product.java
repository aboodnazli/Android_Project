package com.example.midtermproject;
import java.io.Serializable;
import java.util.Objects;
public class Product implements Serializable {
    private String id, name, description;
    private double price, rating;
    private int imageUrl, quantity;
    public Product(String id, String name, String description, double price, double rating, int imageUrl, int quantity) {
        this.id = id; this.name = name; this.description = description;
        this.price = price; this.rating = rating; this.imageUrl = imageUrl; this.quantity = quantity;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public double getRating() { return rating; }
    public int getImageUrl() { return imageUrl; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && quantity == product.quantity && Objects.equals(id, product.id);
    }
    @Override
    public int hashCode() { return Objects.hash(id, price, quantity); }
}
