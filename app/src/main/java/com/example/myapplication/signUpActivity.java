package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class signUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceSate){
            super.onCreate(savedInstanceSate);
            setContentView(R.layout.activity_signup);
        //Start ProgressBar first (Set visibility VISIBLE)
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[6];
                field[0] = "fullname";
                field[1] = "username";
                field[2] = "email";
                field[3] = "collegeid";
                field[4] = "password";
                field[5] = "confirmpassword";


                //Creating array for data
                String[] data = new String[6];
                data[0] = "data-1";
                data[1] = "data-2";
                PutData putData = new PutData("https://projects.vishnusivadas.com/AdvancedHttpURLConnection/putDataTest.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        //End ProgressBar (Set visibility to GONE)
                    }
                }
                //End Write and Read data with URL
            }
        });
    }
    public void gotoLogin(View view){
        Intent intent = new Intent(this,logInActivity.class);
        startActivity(intent);
    }
}
