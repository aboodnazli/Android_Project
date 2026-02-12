package com.example.midtermproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.CartViewHolder> {

    private Context context;
    private List<Product> cartItems;
    private OnCartChangeListener onCartChangeListener;

    public interface OnCartChangeListener {
        void onCartChanged();
    }

    public void setOnCartChangeListener(OnCartChangeListener listener) {
        this.onCartChangeListener = listener;
    }

    public CartRecyclerAdapter(Context context, List<Product> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartItems.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void updateList(List<Product> newList) {
        this.cartItems = newList;
        notifyDataSetChanged();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView cartItemImage;
        TextView cartItemName;
        TextView cartItemPrice;
        TextView cartItemQuantity;
        ImageView decreaseQuantityButton;
        ImageView increaseQuantityButton;
        ImageView removeFromCartButton;

        CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartItemImage = itemView.findViewById(R.id.cartItemImage);
            cartItemName = itemView.findViewById(R.id.cartItemName);
            cartItemPrice = itemView.findViewById(R.id.cartItemPrice);
            cartItemQuantity = itemView.findViewById(R.id.cartItemQuantity);
            decreaseQuantityButton = itemView.findViewById(R.id.decreaseQuantityButton);
            increaseQuantityButton = itemView.findViewById(R.id.increaseQuantityButton);
            removeFromCartButton = itemView.findViewById(R.id.removeFromCartButton);
        }

        void bind(Product product) {
            cartItemImage.setImageResource(product.getImageUrl());
            cartItemName.setText(product.getName());
            cartItemPrice.setText(String.format("%.2f ₪", product.getPrice()));
            cartItemQuantity.setText(String.valueOf(product.getQuantity()));

            decreaseQuantityButton.setOnClickListener(v -> {
                int quantity = product.getQuantity();
                if (quantity > 1) {
                    quantity--;
                    CartManager.getInstance().updateProductQuantity(product, quantity);
                    cartItemQuantity.setText(String.valueOf(quantity));
                    if (onCartChangeListener != null) {
                        onCartChangeListener.onCartChanged();
                    }
                } else {
                    CartManager.getInstance().removeProduct(product);
                    cartItems.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    if (onCartChangeListener != null) {
                        onCartChangeListener.onCartChanged();
                    }
                    Toast.makeText(context, product.getName() + " تم حذفه من السلة", Toast.LENGTH_SHORT).show();
                }
            });

            increaseQuantityButton.setOnClickListener(v -> {
                int quantity = product.getQuantity();
                quantity++;
                CartManager.getInstance().updateProductQuantity(product, quantity);
                cartItemQuantity.setText(String.valueOf(quantity));
                if (onCartChangeListener != null) {
                    onCartChangeListener.onCartChanged();
                }
            });

            removeFromCartButton.setOnClickListener(v -> {
                CartManager.getInstance().removeProduct(product);
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    cartItems.remove(position);
                    notifyItemRemoved(position);
                    if (onCartChangeListener != null) {
                        onCartChangeListener.onCartChanged();
                    }
                    Toast.makeText(context, product.getName() + " تم حذفه من السلة", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
