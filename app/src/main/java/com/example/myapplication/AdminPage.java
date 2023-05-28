package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AdminPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
    }
    public void gotoCreatePolls(){
        Intent i1=new Intent(AdminPage.this,CreatePollActivty.class);
        startActivity(i1);
    }
    public void gotoViewCount(){
        Intent i2=new Intent(AdminPage.this,DisplayVoteCountActivity.class);
        startActivity(i2);
    }
    public void gotoHome(){
        Intent i3=new Intent(AdminPage.this,HomeActivity.class);
        startActivity(i3);
    }
}