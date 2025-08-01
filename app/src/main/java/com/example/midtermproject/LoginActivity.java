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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        guestLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ProductsActivity.class);
                intent.putExtra("isGuest", true);
                startActivity(intent);
                finish();
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        forgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "نسيت كلمة المرور؟ هذه الميزة قيد التطوير.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void attemptLogin() {
        // Reset errors
        emailEditText.setError(null);
        passwordEditText.setError(null);

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("كلمة المرور مطلوبة");
            focusView = passwordEditText;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            passwordEditText.setError("كلمة المرور قصيرة جداً");
            focusView = passwordEditText;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("البريد الإلكتروني أو اسم المستخدم مطلوب");
            focusView = emailEditText;
            cancel = true;
        } else if (!isValidEmail(email)) {
            emailEditText.setError("صيغة البريد الإلكتروني غير صحيحة");
            focusView = emailEditText;
            cancel = true;
        }

        if (cancel) {
            if (focusView != null) {
                focusView.requestFocus();
            }
        } else {
            Toast.makeText(this, "تم تسجيل الدخول بنجاح!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(LoginActivity.this, ProductsActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean isValidEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }
}