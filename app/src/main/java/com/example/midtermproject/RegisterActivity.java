package com.example.midtermproject;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private ImageView backButton;
    private TextInputEditText fullNameEditText;
    private TextInputEditText emailEditText;
    private TextInputEditText phoneEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText confirmPasswordEditText;
    private CheckBox termsCheckBox;
    private Button registerButton;
    private TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        backButton = findViewById(R.id.backButton);
        fullNameEditText = findViewById(R.id.fullNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        termsCheckBox = findViewById(R.id.termsCheckBox);
        registerButton = findViewById(R.id.registerButton);
        loginLink = findViewById(R.id.loginLink);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegistration();
            }
        });


        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void attemptRegistration() {

        fullNameEditText.setError(null);
        emailEditText.setError(null);
        phoneEditText.setError(null);
        passwordEditText.setError(null);
        confirmPasswordEditText.setError(null);


        String fullName = fullNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(fullName)) {
            fullNameEditText.setError("الاسم الكامل مطلوب");
            focusView = fullNameEditText;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("البريد الإلكتروني مطلوب");
            focusView = emailEditText;
            cancel = true;
        } else if (!isValidEmail(email)) {
            emailEditText.setError("صيغة البريد الإلكتروني غير صحيحة");
            focusView = emailEditText;
            cancel = true;
        }

        if (TextUtils.isEmpty(phone)) {
            phoneEditText.setError("رقم الهاتف مطلوب");
            focusView = phoneEditText;
            cancel = true;
        } else if (phone.length() < 7) {
            phoneEditText.setError("رقم الهاتف قصير جداً");
            focusView = phoneEditText;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("كلمة المرور مطلوبة");
            focusView = passwordEditText;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            passwordEditText.setError("كلمة المرور قصيرة جداً (على الأقل 6 أحرف)");
            focusView = passwordEditText;
            cancel = true;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("كلمة المرور غير متطابقة");
            focusView = confirmPasswordEditText;
            cancel = true;
        }

        if (!termsCheckBox.isChecked()) {
            Toast.makeText(this, "يجب الموافقة على الشروط والأحكام", Toast.LENGTH_SHORT).show();
            focusView = termsCheckBox;
            cancel = true;
        }

        if (cancel) {
            if (focusView != null) {
                focusView.requestFocus();
            }
        } else {
            // حفظ بيانات المستخدم الجديد
            UserManager.getInstance().registerUser(fullName, email, phone, password);

            Toast.makeText(this, "تم التسجيل بنجاح! يمكنك الآن تسجيل الدخول.", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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