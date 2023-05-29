package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import static com.example.myapplication.HomeActivity.LU;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminPage extends AppCompatActivity {
 public  static String LogUser=LU;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
    }
    public void gotoCreatePolls(View view){
        Intent i1=new Intent(AdminPage.this,CreatePollActivity.class);
        startActivity(i1);
    }
    public void gotoViewCount(View view){
        Intent i2=new Intent(AdminPage.this,AdminPolls.class);
        startActivity(i2);
    }
    public void gotoHome(View view){
        Intent i3=new Intent(AdminPage.this,HomeActivity.class);
        startActivity(i3);
    }
}