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
public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> productList;
    private List<Product> allProducts;
    private OnProductClickListener onProductClickListener;
    public interface OnProductClickListener {
        void onProductClick(Product product);
    }
    public ProductRecyclerAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = new ArrayList<>(productList);
        this.allProducts = new ArrayList<>(productList);
    }
    public void setOnProductClickListener(OnProductClickListener listener) {
        this.onProductClickListener = listener;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.product_item, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(productList.get(position));
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }
    public void updateList(List<Product> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ProductDiffCallback(this.productList, newList));
        this.productList.clear();
        this.productList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }
    public void filter(String query) {
        List<Product> filteredList = new ArrayList<>();
        if (query.isEmpty()) {
            filteredList.addAll(allProducts);
        } else {
            String lowerQuery = query.toLowerCase();
            for (Product product : allProducts) {
                if (product.getName().toLowerCase().contains(lowerQuery) ||
                        product.getDescription().toLowerCase().contains(lowerQuery)) {
                    filteredList.add(product);
                }
            }
        }
        updateList(filteredList);
    }
    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage, addToCartButton;
        TextView productName, productDescription, productPrice, productRating;
        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productDescription = itemView.findViewById(R.id.productDescription);
            productPrice = itemView.findViewById(R.id.productPrice);
            productRating = itemView.findViewById(R.id.productRating);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
        }
        void bind(Product product) {
            productImage.setImageResource(product.getImageUrl());
            productName.setText(product.getName());
            productDescription.setText(product.getDescription());
            productPrice.setText(product.getPrice() + " ₪");
            productRating.setText(String.valueOf(product.getRating()));
            itemView.setOnClickListener(v -> {
                if (onProductClickListener != null) onProductClickListener.onProductClick(product);
            });
            addToCartButton.setOnClickListener(v -> {
                product.setQuantity(1);
                CartManager.getInstance().addProduct(product);
                Toast.makeText(context, "تمت الإضافة", Toast.LENGTH_SHORT).show();
            });
        }
    }
    private static class ProductDiffCallback extends DiffUtil.Callback {
        private final List<Product> oldList, newList;
        ProductDiffCallback(List<Product> oldList, List<Product> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }
        @Override public int getOldListSize() { return oldList.size(); }
        @Override public int getNewListSize() { return newList.size(); }
        @Override public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
        }
        @Override public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }
    }
}
