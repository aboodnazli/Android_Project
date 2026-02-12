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

import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;
    private List<Product> allProducts; // قائمة مرجعية للبحث
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
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void updateList(List<Product> newList) {
        this.productList = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    public void filter(String query) {
        List<Product> filteredList = new ArrayList<>();
        if (query.isEmpty()) {
            filteredList.addAll(allProducts);
        } else {
            for (Product product : allProducts) {
                if (product.getName().toLowerCase().contains(query.toLowerCase()) ||
                        product.getDescription().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(product);
                }
            }
        }
        this.productList = filteredList;
        notifyDataSetChanged();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productDescription;
        TextView productPrice;
        TextView productRating;
        ImageView addToCartButton;

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
            productPrice.setText(String.format("%.2f ₪", product.getPrice()));
            productRating.setText(String.valueOf(product.getRating()));

            itemView.setOnClickListener(v -> {
                if (onProductClickListener != null) {
                    onProductClickListener.onProductClick(product);
                }
            });

            addToCartButton.setOnClickListener(v -> {
                product.setQuantity(1); // إضافة قطعة واحدة في الإضافة السريعة
                CartManager.getInstance().addProduct(product);
                Toast.makeText(context, "تم إضافة " + product.getName() + " إلى السلة", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
