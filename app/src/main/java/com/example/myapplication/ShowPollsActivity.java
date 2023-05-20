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

        @SuppressLint("WrongViewCast") TextView textView = findViewById(R.id.text_show_polls);
        textView.setText("Show Polls Activity");

        // Add your code to fetch and display the polls
    }
}
