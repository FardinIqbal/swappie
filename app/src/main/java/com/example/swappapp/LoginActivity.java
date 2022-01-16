package com.example.swappapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    // Views
    private EditText mEmailEt, mPasswordEt;
    private TextView doNotHaveAnAccountTv;
    private Button mLoginBtn;

    // Instantiate FirebaseAuth
    private FirebaseAuth mAuth;

    // Progress dialog
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");

        // Enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // Initialize views
        mEmailEt = findViewById(R.id.emailET);
        mPasswordEt = findViewById(R.id.passwordET);
        doNotHaveAnAccountTv = findViewById(R.id.do_not_have_an_accountTv);
        mLoginBtn = findViewById(R.id.loginBtn);

        mAuth = FirebaseAuth.getInstance();

        // handle login button click
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get input data
                String txt_email = mEmailEt.getText().toString();
                String txt_password = mPasswordEt.getText().toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(txt_email).matches()) {
                    // Invalid email format error
                    mEmailEt.setError("Invalid Email");
                    mEmailEt.setFocusable(true);
                } else {
                    // Email is valid so login user
                    loginUser(txt_email, txt_password);
                }
            }
        });

        // handle doNotHaveAnAccount TexView click
        doNotHaveAnAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        // Initiate progress dialog
        pd = new ProgressDialog(this);
        pd.setMessage("Logging In...");
    }

    private void loginUser(String email, String password) {
        // Show Progress dialogue
        pd.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Dismiss progress dialog
                            pd.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            // Get user email and user ID
                            FirebaseUser user = mAuth.getCurrentUser();
                            String email = user.getEmail();
                            String userID = user.getUid();

                            // Store user info in firebase real time database using HashMap
                            HashMap<Object, String> hashMap = new HashMap<>();
                            // Put info in hashmap
                            hashMap.put("Email", email);
                            hashMap.put("userID", userID);
                            hashMap.put("name", "");
                            hashMap.put("image", "");
                            // Firebase database instance
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            // Store user data in a path called "Users"
                            DatabaseReference reference = database.getReference("Users");
                            // Put hashmap data inside the database
                            reference.child(userID).setValue(hashMap);
                            // User logged in so start profile activity
                            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                            finish();
                        } else {
                            // Dismiss progress dialog
                            pd.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Dismiss progress dialog
                pd.dismiss();
                // Login failed so get and show error messages
                Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}