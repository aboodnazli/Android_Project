package com.example.midtermproject;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;
public class CartManager {
    private static CartManager instance;
    private ProductDao productDao;
    private CartManager(Context context) {
        productDao = AppDatabase.getInstance(context).productDao();
    }
    public static synchronized CartManager getInstance(Context context) {
        if (instance == null) instance = new CartManager(context);
        return instance;
    }
    public static synchronized CartManager getInstance() {
        return instance;
    }
    public void addProduct(Product product) {
        List<Product> items = productDao.getAllProducts();
        for (Product item : items) {
            if (item.getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + product.getQuantity());
                productDao.updateProduct(item);
                return;
            }
        }
        productDao.insertProduct(product);
    }
    public void removeProduct(Product product) {
        productDao.deleteProduct(product);
    }
    public void updateProductQuantity(Product product, int quantity) {
        if (quantity <= 0) {
            productDao.deleteProduct(product);
        } else {
            product.setQuantity(quantity);
            productDao.updateProduct(product);
        }
    }
    public List<Product> getCartItems() {
        return productDao.getAllProducts();
    }
    public double getCartTotal() {
        double total = 0;
        for (Product item : productDao.getAllProducts()) total += item.getPrice() * item.getQuantity();
        return total;
    }
    public void clearCart() {
        productDao.deleteAll();
    }
    public int getCartItemCount() {
        return productDao.getAllProducts().size();
    }
}
