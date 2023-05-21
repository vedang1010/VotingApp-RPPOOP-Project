package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class signUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceSate){
            super.onCreate(savedInstanceSate);
            setContentView(R.layout.activity_signup);
    }
    public void gotoLogin(View view){
        Intent intent = new Intent(this,logInActivity.class);
        startActivity(intent);
    }
}
