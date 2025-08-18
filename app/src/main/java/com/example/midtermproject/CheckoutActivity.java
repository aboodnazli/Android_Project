package com.example.midtermproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView checkoutItemsListView;
    private TextView checkoutSubtotalTextView;
    private TextView checkoutShippingTextView;
    private TextView checkoutTotalTextView;
    private TextInputEditText fullNameEditText;
    private TextInputEditText addressEditText;
    private TextInputEditText cityEditText;
    private TextInputEditText zipCodeEditText;
    private TextInputEditText phoneEditText;
    private RadioGroup paymentMethodRadioGroup;
    private RadioButton cashOnDeliveryRadio;
    private RadioButton creditCardRadio;
    private Button confirmOrderButton;

    private CartAdapter cartAdapter;
    private List<Product> cartItems;

    private final double SHIPPING_COST = 15.00; // تكلفة الشحن

    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        toolbar = findViewById(R.id.toolbar);
        checkoutItemsListView = findViewById(R.id.checkoutItemsListView);
        checkoutSubtotalTextView = findViewById(R.id.checkoutSubtotalTextView);
        checkoutShippingTextView = findViewById(R.id.checkoutShippingTextView);
        checkoutTotalTextView = findViewById(R.id.checkoutTotalTextView);
        fullNameEditText = findViewById(R.id.fullNameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        cityEditText = findViewById(R.id.cityEditText);
        zipCodeEditText = findViewById(R.id.zipCodeEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        paymentMethodRadioGroup = findViewById(R.id.paymentMethodRadioGroup);
        cashOnDeliveryRadio = findViewById(R.id.cashOnDeliveryRadio);
        creditCardRadio = findViewById(R.id.creditCardRadio);
        confirmOrderButton = findViewById(R.id.confirmOrderButton);

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

        cartItems = CartManager.getInstance().getCartItems();
        cartAdapter = new CartAdapter(this, cartItems);
        checkoutItemsListView.setAdapter(cartAdapter);

        updateOrderSummary();

        fullNameEditText.setText("اسم المستخدم");
        addressEditText.setText("عنوان الشحن");
        cityEditText.setText("المدينة");
        zipCodeEditText.setText("12345");
        phoneEditText.setText("0599123456");

        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    Toast.makeText(CheckoutActivity.this, "تم تأكيد الطلب بنجاح!", Toast.LENGTH_SHORT).show();
                    CartManager.getInstance().clearCart();
                    Intent intent = new Intent(CheckoutActivity.this, ProductsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CheckoutActivity.this, "الرجاء إدخال جميع تفاصيل الشحن", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateOrderSummary() {
        double subtotal = CartManager.getInstance().getCartTotal();
        double total = subtotal + SHIPPING_COST;

        checkoutSubtotalTextView.setText(String.format("%.2f ₪", subtotal));
        checkoutShippingTextView.setText(String.format("%.2f ₪", SHIPPING_COST));
        checkoutTotalTextView.setText(String.format("%.2f ₪", total));

        setListViewHeightBasedOnItems(checkoutItemsListView);
    }

    private boolean validateInputs() {
        return !fullNameEditText.getText().toString().trim().isEmpty() &&
                !addressEditText.getText().toString().trim().isEmpty() &&
                !cityEditText.getText().toString().trim().isEmpty() &&
                !zipCodeEditText.getText().toString().trim().isEmpty() &&
                !phoneEditText.getText().toString().trim().isEmpty() &&
                paymentMethodRadioGroup.getCheckedRadioButtonId() != -1; // التأكد من اختيار طريقة دفع
    }


    private static void setListViewHeightBasedOnItems(ListView listView) {
        CartAdapter listAdapter = (CartAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
