package com.example.messenger.registration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.messenger.R;
import com.example.messenger.login.MainActivity;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {
    private EditText registrationEmail;
    private EditText registrationPassword;
    private EditText registrationName;
    private EditText registrationLastName;
    private EditText registrationAge;
    private Button buttonSignUp;
    private RegistrationViewModel viewModel;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews();

        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        observeViewModel();

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkingForCompleteness()) {
                    Toast.makeText(RegistrationActivity.this,
                            getString(R.string.please_fill_in_all_fields),
                            Toast.LENGTH_SHORT).show();
                } else {
                    email = getTextFromEditText(registrationEmail);
                    password = getTextFromEditText(registrationPassword);
                    String name = getTextFromEditText(registrationName);
                    String lastName = getTextFromEditText(registrationLastName);
                    int age = Integer.parseInt(getTextFromEditText(registrationAge));
                    viewModel.userRegistration(email, password, name, lastName, age);

                }
            }
        });
    }

    private void initViews() {
        registrationEmail = findViewById(R.id.registrationEmail);
        registrationPassword = findViewById(R.id.registrationPassword);
        registrationName = findViewById(R.id.registrationName);
        registrationLastName = findViewById(R.id.registrationLastName);
        registrationAge = findViewById(R.id.registrationAge);
        buttonSignUp = findViewById(R.id.buttonSignUp);
    }

    private Boolean checkingForCompleteness() {
        boolean isCheck = true;
        List<String> list = new ArrayList<>();
        list.add(registrationEmail.getText().toString());
        list.add(registrationPassword.getText().toString());
        list.add(registrationName.getText().toString());
        list.add(registrationLastName.getText().toString());
        list.add(registrationAge.getText().toString());
        for (String editText : list) {
            if (editText.isEmpty()) {
                isCheck = false;
                break;
            }
        }
        return isCheck;
    }

    private void observeViewModel() {
        viewModel.getErrorText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null) {
                    Toast.makeText(RegistrationActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getResultOfRegistration().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser user) {
                if (user != null) {
                    Toast.makeText(RegistrationActivity.this,
                            getString(R.string.registration_completed_successfully),
                            Toast.LENGTH_SHORT).show();
                    Intent intent = MainActivity.newIntent(
                            RegistrationActivity.this,
                            email,
                            password);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    private String getTextFromEditText(EditText editText) {
        return editText.getText().toString();
    }
}