package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class signUpActivity extends AppCompatActivity {
    DatabaseReference databaseReference;

    EditText textInputEditTextfullname, textInputEditTextusername, textInputEditTextemail, textInputEditTextcollegeid, textInputEditTextpassword, textInputEditTextconfirmpassword;
    Button buttonSignUp;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-register-3e247-default-rtdb.firebaseio.com/");

        textInputEditTextfullname = findViewById(R.id.fullname);
        textInputEditTextusername = findViewById(R.id.username);
        textInputEditTextemail = findViewById(R.id.email);
        textInputEditTextcollegeid = findViewById(R.id.collegeid);
        textInputEditTextpassword = findViewById(R.id.password);
        textInputEditTextconfirmpassword = findViewById(R.id.confirmpassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = textInputEditTextfullname.getText().toString();
                String username = textInputEditTextusername.getText().toString();
                String email = textInputEditTextemail.getText().toString();
                String collegeid = textInputEditTextcollegeid.getText().toString();
                String password = textInputEditTextpassword.getText().toString();
                String confirmpassword = textInputEditTextconfirmpassword.getText().toString();


                if (fullname.isEmpty() || username.isEmpty() || email.isEmpty() || collegeid.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()) {
                    Toast.makeText(signUpActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmpassword)) {
                    Toast.makeText(signUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(username)) {
                                Toast.makeText(signUpActivity.this, "User is already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                DatabaseReference userReference = databaseReference.child("users").child(username);
                                userReference.child("fullname").setValue(fullname);
                                userReference.child("email").setValue(email);
                                userReference.child("collegeid").setValue(collegeid);
                                userReference.child("password").setValue(password);

                                Toast.makeText(signUpActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(signUpActivity.this, logInActivity.class));

                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(signUpActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}