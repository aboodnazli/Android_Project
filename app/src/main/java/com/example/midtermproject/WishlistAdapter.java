package com.example.midtermproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class WishlistAdapter extends ArrayAdapter<Product> {

    private Context context;
    private List<Product> wishlistItems;

    public WishlistAdapter(@NonNull Context context, @NonNull List<Product> objects) {
        super(context, 0, objects);
        this.context = context;
        this.wishlistItems = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.wishlist_item, parent, false);
        }

        Product currentProduct = wishlistItems.get(position);

        ImageView wishlistItemImage = listItem.findViewById(R.id.wishlistItemImage);
        TextView wishlistItemName = listItem.findViewById(R.id.wishlistItemName);
        TextView wishlistItemPrice = listItem.findViewById(R.id.wishlistItemPrice);
        Button addToCartFromWishlistButton = listItem.findViewById(R.id.addToCartFromWishlistButton);
        ImageView removeFromWishlistButton = listItem.findViewById(R.id.removeFromWishlistButton);

        wishlistItemImage.setImageResource(currentProduct.getImageUrl());
        wishlistItemName.setText(currentProduct.getName());
        wishlistItemPrice.setText(String.format("%.2f ₪", currentProduct.getPrice()));

        addToCartFromWishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartManager.getInstance().addProduct(currentProduct);
                Toast.makeText(context, currentProduct.getName() + " أضيف إلى السلة", Toast.LENGTH_SHORT).show();
            }
        });

        removeFromWishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WishlistManager.getInstance().removeProduct(currentProduct);

                wishlistItems.remove(currentProduct);
                notifyDataSetChanged();
                Toast.makeText(context, currentProduct.getName() + " أزيل من المفضلة", Toast.LENGTH_SHORT).show();
            }
        });

        return listItem;
    }
}
