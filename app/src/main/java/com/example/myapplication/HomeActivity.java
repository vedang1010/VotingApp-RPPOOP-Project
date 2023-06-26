package com.example.myapplication;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.myapplication.logInActivity.logedInUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    public  static  String LU=logedInUser;
    private TextView userTextView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://login-register-3e247-default-rtdb.firebaseio.com/");

        userTextView = findViewById(R.id.userIn);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.child("users").getChildren()){
                    if (LU.equals(ds.getKey())){
                        userTextView.setText(ds.child("fullname").getValue(String.class) + "!");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
//    @Override
//    public void onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

