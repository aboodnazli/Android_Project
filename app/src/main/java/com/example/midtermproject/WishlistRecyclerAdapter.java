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
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
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
        this.wishlistItems = new ArrayList<>(wishlistItems);
    }
    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WishlistViewHolder(LayoutInflater.from(context).inflate(R.layout.wishlist_item, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull WishlistViewHolder holder, int position) {
        holder.bind(wishlistItems.get(position));
    }
    @Override
    public int getItemCount() {
        return wishlistItems.size();
    }
    public void updateList(List<Product> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new WishlistDiffCallback(this.wishlistItems, newList));
        this.wishlistItems.clear();
        this.wishlistItems.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }
    class WishlistViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage, removeButton;
        Button addToCartButton;
        TextView productName, productPrice;
        WishlistViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.wishlistItemImage);
            productName = itemView.findViewById(R.id.wishlistItemName);
            productPrice = itemView.findViewById(R.id.wishlistItemPrice);
            removeButton = itemView.findViewById(R.id.removeFromWishlistButton);
            addToCartButton = itemView.findViewById(R.id.addToCartFromWishlistButton);
        }
        void bind(Product product) {
            productImage.setImageResource(product.getImageUrl());
            productName.setText(product.getName());
            productPrice.setText(product.getPrice() + " ₪");
            removeButton.setOnClickListener(v -> {
                WishlistManager.getInstance().removeProduct(product);
                if (onWishlistChangeListener != null) onWishlistChangeListener.onWishlistChanged();
                Toast.makeText(context, "تم الحذف", Toast.LENGTH_SHORT).show();
            });
            addToCartButton.setOnClickListener(v -> {
                product.setQuantity(1);
                CartManager.getInstance().addProduct(product);
                Toast.makeText(context, "تمت الإضافة للسلة", Toast.LENGTH_SHORT).show();
            });
        }
    }
    private static class WishlistDiffCallback extends DiffUtil.Callback {
        private final List<Product> oldList, newList;
        WishlistDiffCallback(List<Product> oldList, List<Product> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }
        @Override public int getOldListSize() { return oldList.size(); }
        @Override public int getNewListSize() { return newList.size(); }
        @Override public boolean areItemsTheSame(int oldPos, int newPos) {
            return oldList.get(oldPos).getId().equals(newList.get(newPos).getId());
        }
        @Override public boolean areContentsTheSame(int oldPos, int newPos) {
            return oldList.get(oldPos).equals(newList.get(newPos));
        }
    }
}
