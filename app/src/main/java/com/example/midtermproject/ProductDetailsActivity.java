package com.example.midtermproject;



import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView productImageLarge;
    private ImageView favoriteButton;
    private TextView productNameDetail;
    private TextView ratingText;
    private TextView reviewsCount;
    private TextView currentPrice;
    private TextView originalPrice;
    private TextView discountBadge;
    private View availabilityIndicator;
    private TextView availabilityText;
    private TextView productDescription;
    private ImageView decreaseQuantity;
    private TextView quantityText;
    private ImageView increaseQuantity;
    private Button addToCartButton;
    private Button buyNowButton;

    private Product currentProduct;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsingToolbar);
        productImageLarge = findViewById(R.id.productImageLarge);
        favoriteButton = findViewById(R.id.favoriteButton);
        productNameDetail = findViewById(R.id.productNameDetail);
        ratingText = findViewById(R.id.ratingText);
        reviewsCount = findViewById(R.id.reviewsCount);
        currentPrice = findViewById(R.id.currentPrice);
        originalPrice = findViewById(R.id.originalPrice);
        discountBadge = findViewById(R.id.discountBadge);
        availabilityIndicator = findViewById(R.id.availabilityIndicator);
        availabilityText = findViewById(R.id.availabilityText);
        productDescription = findViewById(R.id.productDescription);
        decreaseQuantity = findViewById(R.id.decreaseQuantity);
        quantityText = findViewById(R.id.quantityText);
        increaseQuantity = findViewById(R.id.increaseQuantity);
        addToCartButton = findViewById(R.id.addToCartButton);
        buyNowButton = findViewById(R.id.buyNowButton);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("product")) {
            currentProduct = (Product) intent.getSerializableExtra("product");
            if (currentProduct != null) {
                displayProductDetails();
            }
        }

        decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--;
                    quantityText.setText(String.valueOf(quantity));
                }
            }
        });

        increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                quantityText.setText(String.valueOf(quantity));
            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentProduct != null) {
                    // تعيين الكمية المختارة
                    currentProduct.setQuantity(quantity);
                    CartManager.getInstance().addProduct(currentProduct);
                    Toast.makeText(ProductDetailsActivity.this, "تم إضافة " + quantity + " من " + currentProduct.getName() + " إلى السلة", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentProduct != null) {
                    currentProduct.setQuantity(quantity);
                    CartManager.getInstance().addProduct(currentProduct);
                    Intent intent = new Intent(ProductDetailsActivity.this, ShoppingCartActivity.class);
                    startActivity(intent);
                }
            }
        });


        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentProduct != null) {
                    if (WishlistManager.getInstance().isInWishlist(currentProduct)) {
                        WishlistManager.getInstance().removeProduct(currentProduct);
                        favoriteButton.setImageResource(R.drawable.ic_favorite_border);
                        Toast.makeText(ProductDetailsActivity.this, "أزيل من المفضلة", Toast.LENGTH_SHORT).show();
                    } else {
                        WishlistManager.getInstance().addProduct(currentProduct);
                        favoriteButton.setImageResource(R.drawable.ic_favorite_filled);
                        Toast.makeText(ProductDetailsActivity.this, "أضيف إلى المفضلة", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        if (currentProduct != null) {
            if (WishlistManager.getInstance().isInWishlist(currentProduct)) {
                favoriteButton.setImageResource(R.drawable.ic_favorite_filled);
            } else {
                favoriteButton.setImageResource(R.drawable.ic_favorite_border);
            }
        }


    }

    private void displayProductDetails() {
        productImageLarge.setImageResource(currentProduct.getImageUrl());
        productNameDetail.setText(currentProduct.getName());
        ratingText.setText(String.valueOf(currentProduct.getRating()));
        reviewsCount.setText("(125 تقييم)");
        currentPrice.setText(String.format("%.2f ₪", currentProduct.getPrice()));

        double originalPriceValue = currentProduct.getPrice() * 1.25;
        originalPrice.setText(String.format("%.2f ₪", originalPriceValue));
        originalPrice.setPaintFlags(originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        discountBadge.setText("خصم 25%");

        if (currentProduct.getPrice() > 0) {
            availabilityText.setText("متوفر في المخزن");
            availabilityIndicator.setBackgroundResource(R.drawable.availability_indicator_green);
        } else {
            availabilityText.setText("غير متوفر");
            availabilityIndicator.setBackgroundResource(R.drawable.availability_indicator_red);
        }

        productDescription.setText(currentProduct.getDescription() + "\n\n" +
                "هذا نص تجريبي لوصف المنتج. يمكن أن يحتوي على تفاصيل مهمة حول المنتج، مميزاته، طريقة الاستخدام، والمواصفات التقنية. هذا النص قابل للتمرير إذا كان طويلاً.");

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsingToolbar);
        collapsingToolbar.setTitle(currentProduct.getName());
    }
}