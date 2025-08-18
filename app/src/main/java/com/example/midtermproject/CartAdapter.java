package com.example.midtermproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CartAdapter extends ArrayAdapter<Product> {

    private Context context;
    private List<Product> cartItems;
    private OnCartChangeListener onCartChangeListener; // Custom listener

    public interface OnCartChangeListener {
        void onCartChanged();
    }

    public void setOnCartChangeListener(OnCartChangeListener listener) {
        this.onCartChangeListener = listener;
    }

    public CartAdapter(@NonNull Context context, List<Product> cartItems) {
        super(context, 0, cartItems);
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        }

        Product currentProduct = cartItems.get(position);

        ImageView cartItemImage = listItem.findViewById(R.id.cartItemImage);
        TextView cartItemName = listItem.findViewById(R.id.cartItemName);
        TextView cartItemPrice = listItem.findViewById(R.id.cartItemPrice);
        TextView cartItemQuantity = listItem.findViewById(R.id.cartItemQuantity);
        ImageView decreaseQuantityButton = listItem.findViewById(R.id.decreaseQuantityButton);
        ImageView increaseQuantityButton = listItem.findViewById(R.id.increaseQuantityButton);
        ImageView removeFromCartButton = listItem.findViewById(R.id.removeFromCartButton);

        cartItemImage.setImageResource(currentProduct.getImageUrl());
        cartItemName.setText(currentProduct.getName());
        cartItemPrice.setText(String.format("%.2f ₪", currentProduct.getPrice()));
        cartItemQuantity.setText(String.valueOf(currentProduct.getQuantity()));

        decreaseQuantityButton.setOnClickListener(v -> {
            int quantity = currentProduct.getQuantity();
            if (quantity > 1) {
                quantity--;
                CartManager.getInstance().updateProductQuantity(currentProduct, quantity);
                cartItemQuantity.setText(String.valueOf(quantity));
                if (onCartChangeListener != null) {
                    onCartChangeListener.onCartChanged();
                }
            } else {
                CartManager.getInstance().removeProduct(currentProduct);
                cartItems.remove(currentProduct);
                notifyDataSetChanged();
                if (onCartChangeListener != null) {
                    onCartChangeListener.onCartChanged();
                }
                Toast.makeText(context, currentProduct.getName() + " تم حذفه من السلة", Toast.LENGTH_SHORT).show();
            }
        });

        increaseQuantityButton.setOnClickListener(v -> {
            int quantity = currentProduct.getQuantity();
            quantity++;
            CartManager.getInstance().updateProductQuantity(currentProduct, quantity);
            cartItemQuantity.setText(String.valueOf(quantity));
            if (onCartChangeListener != null) {
                onCartChangeListener.onCartChanged();
            }
        });

        removeFromCartButton.setOnClickListener(v -> {
            CartManager.getInstance().removeProduct(currentProduct);
            cartItems.remove(currentProduct);
            notifyDataSetChanged();
            if (onCartChangeListener != null) {
                onCartChangeListener.onCartChanged();
            }
            Toast.makeText(context, currentProduct.getName() + " تم حذفه من السلة", Toast.LENGTH_SHORT).show();
        });

        return listItem;
    }
}
