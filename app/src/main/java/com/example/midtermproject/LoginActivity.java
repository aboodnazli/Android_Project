package com.example.midtermproject;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
public class LoginActivity extends AppCompatActivity {
    private ImageView backButton;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private CheckBox rememberMeCheckBox;
    private TextView forgotPasswordLink;
    private Button loginButton;
    private Button guestLoginButton;
    private TextView registerLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        backButton = findViewById(R.id.backButton);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);
        forgotPasswordLink = findViewById(R.id.forgotPasswordLink);
        loginButton = findViewById(R.id.loginButton);
        guestLoginButton = findViewById(R.id.guestLoginButton);
        registerLink = findViewById(R.id.registerLink);
        backButton.setOnClickListener(v -> onBackPressed());
        loginButton.setOnClickListener(v -> attemptLogin());
        guestLoginButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ProductsActivity.class);
            intent.putExtra("isGuest", true);
            startActivity(intent);
            finish();
        });
        registerLink.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
        forgotPasswordLink.setOnClickListener(v -> Toast.makeText(LoginActivity.this, "نسيت كلمة المرور؟ هذه الميزة قيد التطوير.", Toast.LENGTH_SHORT).show());
    }
    private void attemptLogin() {
        emailEditText.setError(null);
        passwordEditText.setError(null);
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "الرجاء تعبئة جميع الحقول", Toast.LENGTH_SHORT).show();
            return;
        }
        if (UserManager.getInstance().loginUser(email, password)) {
            Toast.makeText(this, "تم تسجيل الدخول بنجاح!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this, ProductsActivity.class));
            finish();
        } else {
            Toast.makeText(this, "البريد الإلكتروني أو كلمة المرور غير صحيحة", Toast.LENGTH_LONG).show();
        }
    }
}
