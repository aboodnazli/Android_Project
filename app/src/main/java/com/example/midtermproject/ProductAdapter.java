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

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {

    public ProductAdapter(@NonNull Context context, ArrayList<Product> products) {
        super(context, 0, products);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.product_item, parent, false);
        }

        Product currentProduct = getItem(position);

        ImageView productImage = listItemView.findViewById(R.id.productImage);
        TextView productName = listItemView.findViewById(R.id.productName);
        TextView productDescription = listItemView.findViewById(R.id.productDescription);
        TextView productPrice = listItemView.findViewById(R.id.productPrice);
        TextView productRating = listItemView.findViewById(R.id.productRating);
        ImageView addToCartButton = listItemView.findViewById(R.id.addToCartButton);

        if (currentProduct != null) {
            productImage.setImageResource(currentProduct.getImageUrl());
            productName.setText(currentProduct.getName());
            productDescription.setText(currentProduct.getDescription());
            productPrice.setText(String.format("%.2f ₪", currentProduct.getPrice()));
            productRating.setText(String.valueOf(currentProduct.getRating()));

            addToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "تم إضافة " + currentProduct.getName() + " إلى السلة", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return listItemView;
    }
}
