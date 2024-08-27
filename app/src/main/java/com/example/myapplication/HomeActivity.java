package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.myapplication.logInActivity.logedInUser;

public class HomeActivity extends AppCompatActivity {
    private TextView userTextView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        userTextView = findViewById(R.id.userIn);
        Button logoutBtn = findViewById(R.id.logoutButton);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-register-3e247-default-rtdb.firebaseio.com/");

        // Fetch and display the current user's full name
        loadUserData();

        // Set up the logout button
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    private void loadUserData() {
        if (logedInUser != null) {
            databaseReference.child("users").child(logedInUser).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String fullName = snapshot.child("fullname").getValue(String.class);
                        userTextView.setText("" + fullName + "!");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle possible errors.
                }
            });
        }
    }

    private void logoutUser() {
        // Clear the global loggedInUser variable
        logedInUser = null;

        // Navigate back to the login activity
        Intent intent = new Intent(HomeActivity.this, logInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Close the current activity
    }

    public void gotoAdmin(View view) {
        Intent intent = new Intent(this, AdminPage.class);
        startActivity(intent);
    }

    public void gotoShowPolls(View view) {
        Intent intent2 = new Intent(this, ShowPollsActivity.class);
        startActivity(intent2);
    }

    public void gotoProfile(View view) {
        Intent intent3 = new Intent(this, Profile.class);
        startActivity(intent3);
    }

    public void gotoWinners(View view) {
        Intent intent4 = new Intent(this, WinnerList.class);
        startActivity(intent4);
    }
}
