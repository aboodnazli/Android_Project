package com.example.midtermproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private Switch notificationsSwitch;
    private Spinner languageSpinner;
    private Button logoutButton;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);

        notificationsSwitch = view.findViewById(R.id.notificationsSwitch);
        languageSpinner = view.findViewById(R.id.languageSpinner);
        logoutButton = view.findViewById(R.id.logoutButton);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notificationsSwitch.setChecked(true);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.language_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);
        languageSpinner.setSelection(0); // Select Arabic by default

        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            Toast.makeText(getContext(), "الإشعارات: " + (isChecked ? "مفعلة" : "معطلة"), Toast.LENGTH_SHORT).show();
        });

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = parent.getItemAtPosition(position).toString();

                Toast.makeText(getContext(), "اللغة المختارة: " + selectedLanguage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        logoutButton.setOnClickListener(v -> {
            // تسجيل الخروج الفعلي
            UserManager.getInstance().logout();

            Toast.makeText(getContext(), "تم تسجيل الخروج بنجاح", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // مسح سجل التنقل
            startActivity(intent);
            getActivity().finish();
        });
    }
}
