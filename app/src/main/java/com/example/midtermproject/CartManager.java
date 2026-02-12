package com.example.midtermproject;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private static CartManager instance;
    private List<Product> cartItems;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "CartPreferences";
    private static final String CART_KEY = "cart_items";
    private Gson gson;

    private CartManager(Context context) {
        this.gson = new Gson();
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.cartItems = loadCartFromPreferences();
    }

    public static synchronized CartManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartManager(context);
        }
        return instance;
    }

    // للحصول على الـ instance بدون context (للاستخدام في الأنشطة الأخرى)
    public static synchronized CartManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("CartManager must be initialized with context first");
        }
        return instance;
    }

    public void addProduct(Product product) {
        // التحقق من وجود المنتج في السلة
        for (Product item : cartItems) {
            if (item.getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + product.getQuantity());
                saveCartToPreferences();
                return;
            }
        }
        // إذا لم يكن موجوداً، أضفه بالكمية المحددة
        cartItems.add(product);
        saveCartToPreferences();
    }

    public void removeProduct(Product product) {
        cartItems.remove(product);
        saveCartToPreferences();
    }

    public void updateProductQuantity(Product product, int quantity) {
        for (Product item : cartItems) {
            if (item.getId().equals(product.getId())) {
                item.setQuantity(quantity);
                if (item.getQuantity() <= 0) {
                    cartItems.remove(item);
                }
                saveCartToPreferences();
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
        saveCartToPreferences();
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    // حفظ السلة في SharedPreferences
    private void saveCartToPreferences() {
        String json = gson.toJson(cartItems);
        sharedPreferences.edit().putString(CART_KEY, json).apply();
    }

    // تحميل السلة من SharedPreferences
    private List<Product> loadCartFromPreferences() {
        String json = sharedPreferences.getString(CART_KEY, "");
        if (json.isEmpty()) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<Product>>(){}.getType();
        return gson.fromJson(json, type);
    }
}
