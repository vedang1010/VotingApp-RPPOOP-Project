package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import static com.example.myapplication.logInActivity.logedInUser;

public class AdminPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
    }

    public void gotoCreatePolls(View view){
        Intent i1 = new Intent(AdminPage.this, CreatePollActivity.class);
        startActivity(i1);
    }

    public void gotoViewCount(View view){
        Intent i2 = new Intent(AdminPage.this, AdminPolls.class);
        startActivity(i2);
    }

    public void gotoHome(View view){
        Intent i3 = new Intent(AdminPage.this, HomeActivity.class);
        startActivity(i3);
    }

    private void logoutUser() {
        // Clear the global loggedInUser variable
        logedInUser = null;

        // Navigate back to the login activity
        Intent intent = new Intent(AdminPage.this, logInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Close the current activity
    }
}
