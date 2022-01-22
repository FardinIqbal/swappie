package com.example.swappapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    EditText mEmailEt, mPasswordEt;
    TextView notHaveAccountTv;
    Button mLoginBtn;

    private FirebaseAuth mAuth;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Login");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        mEmailEt = findViewById(R.id.emailET);
        mPasswordEt = findViewById(R.id.passwordET);
        notHaveAccountTv = findViewById(R.id.do_not_have_an_accountTv);
        mLoginBtn = findViewById(R.id.loginBtn);

        mLoginBtn.setOnClickListener(v -> {
            String email = mEmailEt.getText().toString();
            String passw = mPasswordEt.getText().toString().trim();
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                mEmailEt.setError("Invalid e-mail");
                mEmailEt.setFocusable(true);
            }
            else {
                loginUser(email, passw);
            }
        });
        notHaveAccountTv.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        pd = new ProgressDialog(this);
        pd.setMessage("Logging In");
    }

    private void loginUser(String email, String passw) {
        pd.show();
        mAuth.signInWithEmailAndPassword(email, passw)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()) {
                        pd.dismiss();
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        finish();
                    } else {
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                }) .addOnFailureListener(e -> {
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}