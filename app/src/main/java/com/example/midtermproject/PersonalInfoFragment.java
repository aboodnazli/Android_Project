package com.example.midtermproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

public class PersonalInfoFragment extends Fragment {

    private ImageView userProfileImage;
    private TextInputEditText fullNameEditText;
    private TextInputEditText emailEditText;
    private TextInputEditText phoneEditText;
    private Button cancelButton;
    private Button saveButton;

    public PersonalInfoFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_info_fragment, container, false);

        userProfileImage = view.findViewById(R.id.userProfileImage);
        fullNameEditText = view.findViewById(R.id.fullNameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        cancelButton = view.findViewById(R.id.cancelButton);
        saveButton = view.findViewById(R.id.saveButton);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fullNameEditText.setText("محمد أحمد");
        emailEditText.setText("mohamed.ahmed@example.com");
        phoneEditText.setText("0599123456");

        cancelButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "تم إلغاء التعديلات", Toast.LENGTH_SHORT).show();
            fullNameEditText.setText("محمد أحمد");
            emailEditText.setText("mohamed.ahmed@example.com");
            phoneEditText.setText("0599123456");
        });

        saveButton.setOnClickListener(v -> {
            String fullName = fullNameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String phone = phoneEditText.getText().toString();

            Toast.makeText(getContext(), "تم حفظ المعلومات الشخصية", Toast.LENGTH_SHORT).show();
        });

        userProfileImage.setOnClickListener(v -> {
            Toast.makeText(getContext(), "تغيير صورة الملف الشخصي", Toast.LENGTH_SHORT).show();
        });
    }
}
