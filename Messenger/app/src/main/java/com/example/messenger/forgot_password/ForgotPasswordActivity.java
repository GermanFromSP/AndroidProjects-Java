package com.example.messenger.forgot_password;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.messenger.login.MainActivity;
import com.example.messenger.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText editTextForgotEmail;
    private Button buttonResetPassword;
    private ForgotPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        editTextForgotEmail = findViewById(R.id.editTextForgotEmail);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);
        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
        observeViewModel();

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextForgotEmail.getText().toString().trim();
                viewModel.sendLinkForNewPassword(email);
            }
        });
    }

    private void observeViewModel() {
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null) {
                    Toast.makeText(ForgotPasswordActivity.this,
                            error, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getIsDone().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isDone) {
                if (isDone) {
                    Toast.makeText(ForgotPasswordActivity.this,
                            getString(R.string.forgot_password_toast_message),
                            Toast.LENGTH_SHORT).show();
                    Intent intent = MainActivity.newIntent(ForgotPasswordActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ForgotPasswordActivity.class);
    }
}