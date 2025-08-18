package com.example.midtermproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity implements CartAdapter.OnCartChangeListener {

    private ListView cartListView;
    private TextView subtotalTextView;
    private Button proceedToCheckoutButton;
    private CartAdapter cartAdapter;
    private CartManager cartManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.shopping_cart_title));
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        cartListView = findViewById(R.id.cartListView);
        subtotalTextView = findViewById(R.id.subtotalTextView);
        proceedToCheckoutButton = findViewById(R.id.proceedToCheckoutButton);

        cartManager = CartManager.getInstance();
        List<Product> cartItems = cartManager.getCartItems();

        cartAdapter = new CartAdapter(this, cartItems);
        cartAdapter.setOnCartChangeListener(this);
        cartListView.setAdapter(cartAdapter);

        updateSubtotal();

        proceedToCheckoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CartManager.getInstance().getCartItems().isEmpty()) {
                    Toast.makeText(ShoppingCartActivity.this, "السلة فارغة!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ShoppingCartActivity.this, CheckoutActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onCartChanged() {
        updateSubtotal();
    }

    private void updateSubtotal() {
        double subtotal = cartManager.getSubtotal();
        subtotalTextView.setText(String.format("%.2f ₪", subtotal));
    }
}
