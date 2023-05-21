package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.text.BreakIterator;

public class signUpActivity extends AppCompatActivity {

    TextInputEditText textInputEditTextfullname , textInputEditTextusername , textInputEditTextemail , textInputEditTextcollegeid , textInputEditTextpassword , textInputEditTextconfirmpassword;
    Button buttonSignUp;
//    TextView textviewlogin;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceSate){
            super.onCreate(savedInstanceSate);
            setContentView(R.layout.activity_signup);

            textInputEditTextfullname = findViewById(R.id.fullname);
            textInputEditTextfullname = findViewById(R.id.username);
            textInputEditTextfullname = findViewById(R.id.email);
            textInputEditTextfullname = findViewById(R.id.collegeid);
            textInputEditTextfullname = findViewById(R.id.password);
            textInputEditTextfullname = findViewById(R.id.confirmpassword);
            buttonSignUp = findViewById(R.id.buttonSignUp);

            buttonSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String fullname,username,email,password,confirmpassword,collegeid;

                    fullname = String.valueOf(textInputEditTextfullname.getText());
                    username = String.valueOf(textInputEditTextusername.getText());
                    email = String.valueOf(textInputEditTextemail.getText());
                    password = String.valueOf(textInputEditTextpassword.getText());
                    confirmpassword = String.valueOf(textInputEditTextconfirmpassword.getText());

                    //string value of college id
                    collegeid = String.valueOf(textInputEditTextcollegeid.getText());

                    //integer value of college id
                    int intid = Integer.parseInt(collegeid);

                    if(!fullname.equals("") && !username.equals("") && !email.equals("") && !password.equals("") && !confirmpassword.equals("") && !collegeid.equals("")) {

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
                                data[0] = fullname;
                                data[1] = username;
                                data[2] = email;
                                data[3] = collegeid;
                                data[4] = password;
                                data[5] = confirmpassword;
                                PutData putData = new PutData("http://192.168.209.35:81/SignUpRegister/signup.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                        if(result.equals("Sign Up Success")){
                                            Toast.makeText(getApplicationContext(),"all fields are required!!",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(),logInActivity.class);
                                            startActivity(intent);
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                //End Write and Read data with URL
                            }
                        });
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"all fields are required!!",Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }
//    public void gotoLogin(View view){
//        Intent intent = new Intent(this,logInActivity.class);
//        startActivity(intent);
//    }
}
