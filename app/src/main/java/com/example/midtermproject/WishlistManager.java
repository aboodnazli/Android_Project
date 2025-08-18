package com.example.midtermproject;

import java.util.ArrayList;
import java.util.List;

public class WishlistManager {

    private static WishlistManager instance;
    private List<Product> wishlistItems;

    private WishlistManager() {
        wishlistItems = new ArrayList<>();
    }

    public static synchronized WishlistManager getInstance() {
        if (instance == null) {
            instance = new WishlistManager();
        }
        return instance;
    }

    public void addProduct(Product product) {

        if (!wishlistItems.contains(product)) {
            wishlistItems.add(product);
        }
    }

    public void removeProduct(Product product) {
        wishlistItems.remove(product);
    }

    public List<Product> getWishlistItems() {
        return new ArrayList<>(wishlistItems);
    }

    public boolean isInWishlist(Product product) {
        return wishlistItems.contains(product);
    }

    public void clearWishlist() {
        wishlistItems.clear();
    }
}
