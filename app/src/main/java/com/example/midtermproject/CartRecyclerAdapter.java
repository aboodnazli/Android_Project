package com.example.midtermproject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
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
        this.cartItems = new ArrayList<>(cartItems);
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.bind(cartItems.get(position));
    }
    @Override
    public int getItemCount() {
        return cartItems.size();
    }
    public void updateList(List<Product> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new CartDiffCallback(this.cartItems, newList));
        this.cartItems.clear();
        this.cartItems.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }
    class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView cartItemImage, decreaseQuantityButton, increaseQuantityButton, removeFromCartButton;
        TextView cartItemName, cartItemPrice, cartItemQuantity;
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
            cartItemPrice.setText(product.getPrice() + " ₪");
            cartItemQuantity.setText(String.valueOf(product.getQuantity()));
            decreaseQuantityButton.setOnClickListener(v -> {
                int q = product.getQuantity();
                if (q > 1) {
                    CartManager.getInstance().updateProductQuantity(product, q - 1);
                    if (onCartChangeListener != null) onCartChangeListener.onCartChanged();
                } else {
                    CartManager.getInstance().removeProduct(product);
                    if (onCartChangeListener != null) onCartChangeListener.onCartChanged();
                    Toast.makeText(context, "تم الحذف", Toast.LENGTH_SHORT).show();
                }
            });
            increaseQuantityButton.setOnClickListener(v -> {
                CartManager.getInstance().updateProductQuantity(product, product.getQuantity() + 1);
                if (onCartChangeListener != null) onCartChangeListener.onCartChanged();
            });
            removeFromCartButton.setOnClickListener(v -> {
                CartManager.getInstance().removeProduct(product);
                if (onCartChangeListener != null) onCartChangeListener.onCartChanged();
                Toast.makeText(context, "تم الحذف", Toast.LENGTH_SHORT).show();
            });
        }
    }
    private static class CartDiffCallback extends DiffUtil.Callback {
        private final List<Product> oldList, newList;
        CartDiffCallback(List<Product> oldList, List<Product> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }
        @Override public int getOldListSize() { return oldList.size(); }
        @Override public int getNewListSize() { return newList.size(); }
        @Override public boolean areItemsTheSame(int oldPos, int newPos) {
            return oldList.get(oldPos).getId().equals(newList.get(newPos).getId());
        }
        @Override public boolean areContentsTheSame(int oldPos, int newPos) {
            return oldList.get(oldPos).getQuantity() == newList.get(newPos).getQuantity();
        }
    }
}
