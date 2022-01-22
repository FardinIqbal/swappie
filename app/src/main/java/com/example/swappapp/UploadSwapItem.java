package com.example.swappapp;


import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;

public class UploadSwapItem extends AppCompatActivity {

    ActionBar actionBar;
    DatabaseReference userDbRef;

    EditText titleEt, descriptionEt;
    Button uploadBtn;
    ProgressDialog pd;
    FirebaseAuth firebaseAuth;
    String name, email, uid, dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_swap_item);

        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("add new post");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        pd = new ProgressDialog(this);
        actionBar.setSubtitle(email);

        userDbRef = FirebaseDatabase.getInstance().getReference("Users");
        Query query = userDbRef.orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    name = ""+ds.child("name").getValue();
                    email = ""+ds.child("email").getValue();
                    dp = ""+ds.child("image").getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        titleEt = findViewById(R.id.pTitleEt);
        descriptionEt = findViewById(R.id.pDescriptionEt);
        uploadBtn = findViewById(R.id.pUploadBtn);

        uploadBtn.setOnClickListener(v -> {
            String title = titleEt.getText().toString().trim();
            String description = descriptionEt.getText().toString().trim();

            if(TextUtils.isEmpty(title)) {
                Toast.makeText(UploadSwapItem.this, "Enter Title", Toast.LENGTH_SHORT).show();
                return;
            }

            if(TextUtils.isEmpty(description)) {
                Toast.makeText(UploadSwapItem.this, "Enter Description", Toast.LENGTH_SHORT).show();
                return;
            }

            uploadData(title, description);
        });
    }

    private void uploadData(String title, String description) {
        pd.setMessage("Publishing Post");
        pd.show();

        String timeStamp = String.valueOf(System.currentTimeMillis());
            HashMap<Object, String> hashMap = new HashMap<>();
            hashMap.put("uid", uid);
            hashMap.put("uName", name);
            hashMap.put("uEmail", email);
            hashMap.put("uDp", dp);
            hashMap.put("pId", timeStamp);
            hashMap.put("pTitle", title);
            hashMap.put("pDescr", description);
            hashMap.put("pTime", timeStamp);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
            ref.child(timeStamp).setValue(hashMap)
                    .addOnSuccessListener(aVoid -> {
                        pd.dismiss();
                        Toast.makeText(UploadSwapItem.this, "Post Published", Toast.LENGTH_SHORT).show();
                        titleEt.setText("");
                        descriptionEt.setText("");
                    })
                    .addOnFailureListener(e -> {
                        pd.dismiss();
                        Toast.makeText(UploadSwapItem.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
    }
    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(UploadSwapItem.this, MainActivity.class));
            finish();
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_add_post).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_logout) {
            firebaseAuth.signOut();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }
}