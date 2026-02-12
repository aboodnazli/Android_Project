package com.example.midtermproject;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
public class WishlistManager {
    private static WishlistManager instance;
    private List<Product> wishlistItems;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "WishlistPreferences";
    private static final String WISHLIST_KEY = "wishlist_items";
    private Gson gson;
    private WishlistManager(Context context) {
        this.gson = new Gson();
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.wishlistItems = loadWishlistFromPreferences();
    }
    public static synchronized WishlistManager getInstance(Context context) {
        if (instance == null) instance = new WishlistManager(context);
        return instance;
    }
    public static synchronized WishlistManager getInstance() {
        if (instance == null) throw new IllegalStateException("WishlistManager must be initialized with context first");
        return instance;
    }
    public void addProduct(Product product) {
        for (Product item : wishlistItems) {
            if (item.getId().equals(product.getId())) return;
        }
        wishlistItems.add(product);
        saveWishlistToPreferences();
    }
    public void removeProduct(Product product) {
        wishlistItems.removeIf(item -> item.getId().equals(product.getId()));
        saveWishlistToPreferences();
    }
    public List<Product> getWishlistItems() {
        return new ArrayList<>(wishlistItems);
    }
    public boolean isInWishlist(Product product) {
        for (Product item : wishlistItems) {
            if (item.getId().equals(product.getId())) return true;
        }
        return false;
    }
    public void clearWishlist() {
        wishlistItems.clear();
        saveWishlistToPreferences();
    }
    private void saveWishlistToPreferences() {
        sharedPreferences.edit().putString(WISHLIST_KEY, gson.toJson(wishlistItems)).apply();
    }
    private List<Product> loadWishlistFromPreferences() {
        String json = sharedPreferences.getString(WISHLIST_KEY, "");
        if (json.isEmpty()) return new ArrayList<>();
        Type type = new TypeToken<List<Product>>(){}.getType();
        return gson.fromJson(json, type);
    }
}
