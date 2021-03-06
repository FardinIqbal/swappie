package com.example.swappapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    // views
    Button mRegisterBtn, mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize views
        mRegisterBtn = findViewById(R.id.register_btn);
        mLoginBtn = findViewById(R.id.login_btn);

        // Handle register button click
        mRegisterBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, RegisterActivity.class)));

        // Handle login button click
        mLoginBtn.setOnClickListener(view -> {
            // Start Login Activity
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });
    }
}