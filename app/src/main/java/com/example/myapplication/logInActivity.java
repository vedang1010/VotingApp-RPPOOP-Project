package com.example.myapplication;

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

public class logInActivity extends AppCompatActivity {
    String user,pass;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-register-3e247-default-rtdb.firebaseio.com/");
    public static String logedInUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username = findViewById(R.id.username2);
        final EditText password = findViewById(R.id.password2);
        final Button loginBtn = findViewById(R.id.buttonSignUp);
//        final EditText collegeid = findViewById(R.id.collegeid);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usernameTxt = username.getText().toString();
                final String passwordTxt = password.getText().toString();
                user=usernameTxt;
                pass=passwordTxt;
                if (usernameTxt.isEmpty() || passwordTxt.isEmpty()) {
                    Toast.makeText(logInActivity.this, "Please Enter your username or password", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(usernameTxt)) {
                                final String getPassword = snapshot.child(usernameTxt).child("password").getValue(String.class);

                                if (getPassword.equals(passwordTxt)) {
                                    logedInUser= usernameTxt;
                                    Toast.makeText(logInActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(logInActivity.this,HomeActivity.class ));
                                    finish();
                                } else {
                                    Toast.makeText(logInActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Toast.makeText(logInActivity.this, "Wrong Username", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(logInActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
//    public void
}
//    public void gotoHome(View view){
//        Intent intent = new Intent(this,HomeActivity.class);
//        startActivity(intent);
//    }
//}
