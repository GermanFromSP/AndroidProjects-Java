package com.example.messenger.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.messenger.R;
import com.example.messenger.registration.RegistrationActivity;
import com.example.messenger.userlist.UsersActivity;
import com.example.messenger.forgot_password.ForgotPasswordActivity;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String EXTRA_EMAIL = "email";
    private static final String EXTRA_PASSWORD = "password";
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewForgot;
    private TextView textViewRegistration;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        observeViewModel();
        setOnClickListeners();

        editTextEmail.setText(getIntent().getStringExtra(EXTRA_EMAIL));
        editTextPassword.setText(getIntent().getStringExtra(EXTRA_PASSWORD));


    }

    private void observeViewModel() {
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(MainActivity.this,
                        s, Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Intent intent = UsersActivity.newIntent(MainActivity.this, firebaseUser.getUid());
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private void setOnClickListeners() {
        textViewRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = RegistrationActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
        textViewForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ForgotPasswordActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            getString(R.string.please_fill_in_all_fields),
                            Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.logIn(email, password);


                }
            }
        });
    }

    private void initViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewForgot = findViewById(R.id.textViewForgot);
        textViewRegistration = findViewById(R.id.textViewRegister);
    }

    public static Intent newIntent(Context context, String email, String password) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_EMAIL, email);
        intent.putExtra(EXTRA_PASSWORD, password);
        return intent;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}