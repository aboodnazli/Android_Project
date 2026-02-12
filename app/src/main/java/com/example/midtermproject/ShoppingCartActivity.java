package com.example.midtermproject;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class ShoppingCartActivity extends AppCompatActivity implements CartRecyclerAdapter.OnCartChangeListener {
    private RecyclerView cartRecyclerView;
    private TextView subtotalTextView;
    private Button proceedToCheckoutButton;
    private CartRecyclerAdapter cartAdapter;
    private CartManager cartManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("سلة التسوق");
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        subtotalTextView = findViewById(R.id.subtotalTextView);
        proceedToCheckoutButton = findViewById(R.id.proceedToCheckoutButton);
        cartManager = CartManager.getInstance();
        List<Product> cartItems = cartManager.getCartItems();
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartRecyclerAdapter(this, cartItems);
        cartAdapter.setOnCartChangeListener(this);
        cartRecyclerView.setAdapter(cartAdapter);
        updateSubtotal();
        proceedToCheckoutButton.setOnClickListener(v -> {
            if (cartManager.getCartItems().isEmpty()) {
                Toast.makeText(this, "السلة فارغة!", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, CheckoutActivity.class));
            }
        });
    }
    @Override
    public void onCartChanged() {
        updateSubtotal();
        cartAdapter.updateList(cartManager.getCartItems());
    }
    private void updateSubtotal() {
        subtotalTextView.setText(cartManager.getCartTotal() + " ₪");
    }
    @Override
    protected void onResume() {
        super.onResume();
        cartAdapter.updateList(cartManager.getCartItems());
        updateSubtotal();
    }
}
