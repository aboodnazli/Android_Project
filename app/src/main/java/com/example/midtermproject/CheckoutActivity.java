package com.example.midtermproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;
public class CheckoutActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView checkoutItemsRecyclerView;
    private TextView checkoutSubtotalTextView, checkoutShippingTextView, checkoutTotalTextView;
    private TextInputEditText fullNameEditText, addressEditText, cityEditText, zipCodeEditText, phoneEditText;
    private RadioGroup paymentMethodRadioGroup;
    private Button confirmOrderButton;
    private CartRecyclerAdapter cartAdapter;
    private List<Product> cartItems;
    private final double SHIPPING_COST = 15.00;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        toolbar = findViewById(R.id.toolbar);
        checkoutItemsRecyclerView = findViewById(R.id.checkoutItemsListView);
        checkoutSubtotalTextView = findViewById(R.id.checkoutSubtotalTextView);
        checkoutShippingTextView = findViewById(R.id.checkoutShippingTextView);
        checkoutTotalTextView = findViewById(R.id.checkoutTotalTextView);
        fullNameEditText = findViewById(R.id.fullNameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        cityEditText = findViewById(R.id.cityEditText);
        zipCodeEditText = findViewById(R.id.zipCodeEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        paymentMethodRadioGroup = findViewById(R.id.paymentMethodRadioGroup);
        confirmOrderButton = findViewById(R.id.confirmOrderButton);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        cartItems = CartManager.getInstance().getCartItems();
        checkoutItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartRecyclerAdapter(this, cartItems);
        checkoutItemsRecyclerView.setAdapter(cartAdapter);
        updateOrderSummary();
        UserManager userManager = UserManager.getInstance();
        UserManager.User user = userManager.getUser();
        if (user != null) {
            fullNameEditText.setText(user.fullName);
            phoneEditText.setText(user.phone);
        }
        confirmOrderButton.setOnClickListener(v -> {
            if (validateInputs()) {
                Toast.makeText(this, "تم تأكيد الطلب بنجاح!", Toast.LENGTH_SHORT).show();
                CartManager.getInstance().clearCart();
                Intent intent = new Intent(this, ProductsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "الرجاء إدخال جميع تفاصيل الشحن", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateOrderSummary() {
        double subtotal = CartManager.getInstance().getCartTotal();
        double total = subtotal + SHIPPING_COST;
        checkoutSubtotalTextView.setText(subtotal + " ₪");
        checkoutShippingTextView.setText(SHIPPING_COST + " ₪");
        checkoutTotalTextView.setText(total + " ₪");
    }
    private boolean validateInputs() {
        return !fullNameEditText.getText().toString().trim().isEmpty() &&
                !addressEditText.getText().toString().trim().isEmpty() &&
                !cityEditText.getText().toString().trim().isEmpty() &&
                !zipCodeEditText.getText().toString().trim().isEmpty() &&
                !phoneEditText.getText().toString().trim().isEmpty() &&
                paymentMethodRadioGroup.getCheckedRadioButtonId() != -1;
    }
}
