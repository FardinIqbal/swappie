package com.example.swappapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    // Firebase auth
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");

        // Initialize firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void checkUserStatus() {
        // Get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null) {
            // User is signed in stay here

        }
        else {
            // User not signed in, go to main activity
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        }
    }

    @Override
    protected void onStart() {
        // check on start of app
        checkUserStatus();
        super.onStart();
    }
}