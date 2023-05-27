package com.example.myapplication;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    private TextView nameTextView, idTextView;
    private TextView emailTextView, videoTextView;
    private ImageView userImageView, emailImageView;
    private ImageView videoImageView, twitterImageView;
//    private String email, password;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private static final String USER = "users";

    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        Intent intent = getIntent();
        pass = intent.getStringExtra("password");

        nameTextView = findViewById(R.id.username);
        idTextView = findViewById(R.id.collegeid);
        emailTextView = findViewById(R.id.email);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(USER);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    if (ds.child("password").getValue().equals(pass)){
                        nameTextView.setText(ds.child("fullname").getValue(String.class));
                        emailTextView.setText(ds.child("email").getValue(String.class));
                        idTextView.setText(ds.child("collegeid").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        @SuppressLint("WrongViewCast") TextView textView = findViewById(R.id.pr);
//        textView.setText("Show Polls Activity");

        // Add your code to fetch and display the polls
    }
}
