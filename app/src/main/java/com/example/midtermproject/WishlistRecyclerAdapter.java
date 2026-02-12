package com.example.midtermproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WishlistRecyclerAdapter extends RecyclerView.Adapter<WishlistRecyclerAdapter.WishlistViewHolder> {

    private Context context;
    private List<Product> wishlistItems;
    private OnWishlistChangeListener onWishlistChangeListener;

    public interface OnWishlistChangeListener {
        void onWishlistChanged();
    }

    public void setOnWishlistChangeListener(OnWishlistChangeListener listener) {
        this.onWishlistChangeListener = listener;
    }

    public WishlistRecyclerAdapter(Context context, List<Product> wishlistItems) {
        this.context = context;
        this.wishlistItems = wishlistItems;
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wishlist_item, parent, false);
        return new WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistViewHolder holder, int position) {
        Product product = wishlistItems.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return wishlistItems.size();
    }

    public void updateList(List<Product> newList) {
        this.wishlistItems = newList;
        notifyDataSetChanged();
    }

    class WishlistViewHolder extends RecyclerView.ViewHolder {
        ImageView wishlistItemImage;
        TextView wishlistItemName;
        TextView wishlistItemPrice;
        Button addToCartFromWishlistButton;
        ImageView removeFromWishlistButton;

        WishlistViewHolder(@NonNull View itemView) {
            super(itemView);
            wishlistItemImage = itemView.findViewById(R.id.wishlistItemImage);
            wishlistItemName = itemView.findViewById(R.id.wishlistItemName);
            wishlistItemPrice = itemView.findViewById(R.id.wishlistItemPrice);
            addToCartFromWishlistButton = itemView.findViewById(R.id.addToCartFromWishlistButton);
            removeFromWishlistButton = itemView.findViewById(R.id.removeFromWishlistButton);
        }

        void bind(Product product) {
            wishlistItemImage.setImageResource(product.getImageUrl());
            wishlistItemName.setText(product.getName());
            wishlistItemPrice.setText(String.format("%.2f ₪", product.getPrice()));

            addToCartFromWishlistButton.setOnClickListener(v -> {
                CartManager.getInstance().addProduct(product);
                Toast.makeText(context, product.getName() + " أضيف إلى السلة", Toast.LENGTH_SHORT).show();
            });

            removeFromWishlistButton.setOnClickListener(v -> {
                WishlistManager.getInstance().removeProduct(product);
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    wishlistItems.remove(position);
                    notifyItemRemoved(position);
                    if (onWishlistChangeListener != null) {
                        onWishlistChangeListener.onWishlistChanged();
                    }
                    Toast.makeText(context, product.getName() + " أزيل من المفضلة", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
