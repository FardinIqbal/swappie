package com.example.swappapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    // Firebase auth
    FirebaseAuth firebaseAuth;

    // Views
    private TextView mProfileTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");

        // Initialize firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize Views
        mProfileTv = findViewById(R.id.profileTv);
    }

    private void checkUserStatus() {
        // Get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null) {
            // User is signed in stay here
            mProfileTv.setText(user.getEmail());
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

    // Inflate options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Handle menu item clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // get item id
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            firebaseAuth.signOut();
            checkUserStatus();
        } if (id == R.id.action_post) {
            startActivity(new Intent(ProfileActivity.this, PostActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}