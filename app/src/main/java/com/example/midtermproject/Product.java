package com.example.midtermproject;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import java.util.Objects;
@Entity(tableName = "products")
public class Product implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    private String name, description;
    private double price, rating;
    private int imageUrl, quantity;
    public Product(@NonNull String id, String name, String description, double price, double rating, int imageUrl, int quantity) {
        this.id = id; this.name = name; this.description = description;
        this.price = price; this.rating = rating; this.imageUrl = imageUrl; this.quantity = quantity;
    }
    @NonNull public String getId() { return id; }
    public void setId(@NonNull String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    public int getImageUrl() { return imageUrl; }
    public void setImageUrl(int imageUrl) { this.imageUrl = imageUrl; }
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
