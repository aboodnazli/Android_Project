package com.example.midtermproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class WelcomeActivity extends AppCompatActivity {
    private Button loginButton;
    private Button registerButton;
    private TextView browseAsGuestTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (UserManager.getInstance().isLoggedIn()) {
            startActivity(new Intent(this, ProductsActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_welcome);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        browseAsGuestTextView = findViewById(R.id.browseAsGuest);
        loginButton.setOnClickListener(v -> startActivity(new Intent(WelcomeActivity.this, LoginActivity.class)));
        registerButton.setOnClickListener(v -> startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class)));
        browseAsGuestTextView.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, ProductsActivity.class);
            intent.putExtra("isGuest", true);
            startActivity(intent);
            finish();
        });
    }
}
