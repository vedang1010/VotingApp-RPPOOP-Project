package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CreatePollActivity extends AppCompatActivity {

    private LinearLayout optionsLayout;
    private Button addOptionButton;
    private Button createPollButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poll);

        optionsLayout = findViewById(R.id.optionsLayout);
        addOptionButton = findViewById(R.id.addOptionButton);
        createPollButton = findViewById(R.id.createPollButton);

        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOption();
            }
        });

        createPollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPoll();
            }
        });
    }

    private void addOption() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout optionLayout = new LinearLayout(this);
        optionLayout.setLayoutParams(layoutParams);
        optionLayout.setOrientation(LinearLayout.HORIZONTAL);

        EditText optionEditText = new EditText(this);
        optionEditText.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        optionEditText.setHint("Enter candidate name");

        Button deleteButton = new Button(this);
        deleteButton.setText("Delete");
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionsLayout.removeView(optionLayout);
            }
        });

        optionLayout.addView(optionEditText);
        optionLayout.addView(deleteButton);

        optionsLayout.addView(optionLayout);
    }

    private void createPoll() {
        String question = ((EditText) findViewById(R.id.questionEditText)).getText().toString();
        int optionCount = optionsLayout.getChildCount();

        // Store the question and options in your database or perform any desired action
        // For simplicity, let's just print the question and options to the console
        System.out.println("Question: " + question);
        System.out.println("Options:");
        for (int i = 0; i < optionCount; i++) {
            LinearLayout optionLayout = (LinearLayout) optionsLayout.getChildAt(i);
            EditText optionEditText = (EditText) optionLayout.getChildAt(0);
            String option = optionEditText.getText().toString();
            System.out.println("- " + option);
        }
    }
}
