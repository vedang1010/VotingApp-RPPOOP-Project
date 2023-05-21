package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ShowPollsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showpolls);


//        @SuppressLint({"MissingInflatedId", "LocalSuppress", "WrongViewCast"}) TextView textView = findViewById(R.id.text_show_polls);
//        textView.setText("Show Poll Activity");

        // Add your code for creating and saving polls
    }
}
