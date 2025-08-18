package com.example.midtermproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private static CartManager instance;
    private List<Product> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addProduct(Product product) {
        // Check if product already exists in cart
        for (Product item : cartItems) {
            if (item.getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        // If not, add new product with quantity 1
        product.setQuantity(1); // Ensure quantity is set to 1 when first added
        cartItems.add(product);
    }

    public void removeProduct(Product product) {
        cartItems.remove(product);
    }

    public void updateProductQuantity(Product product, int quantity) {
        for (Product item : cartItems) {
            if (item.getId() == product.getId()) {
                item.setQuantity(quantity);
                if (item.getQuantity() <= 0) {
                    cartItems.remove(item);
                }
                return;
            }
        }
    }

    public List<Product> getCartItems() {
        return new ArrayList<>(cartItems);
    }
    public double getCartTotal() {
        double total = 0;
        for (Product item : cartItems) {
            total += item.getPrice();
        }
        return total;
    }

    public double getSubtotal() {
        double subtotal = 0;
        for (Product item : cartItems) {
            subtotal += item.getPrice() * item.getQuantity();
        }
        return subtotal;
    }

    public void clearCart() {
        cartItems.clear();
    }

    public int getCartItemCount() {
        return cartItems.size();
    }
}
