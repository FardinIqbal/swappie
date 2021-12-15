package com.example.swappapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button logout;
    private EditText item;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);
        item = findViewById(R.id.item);
        add = findViewById(R.id.add);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_item_name = item.getText().toString();
                if (txt_item_name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter the name of an item", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase.getInstance().getReference().child("All_Items").push().child("Item Name").setValue(txt_item_name);
                }
            }
        });
    }
}