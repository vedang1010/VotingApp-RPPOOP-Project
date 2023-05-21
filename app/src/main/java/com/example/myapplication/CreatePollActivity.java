package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CreatePollActivity extends AppCompatActivity {

    private EditText editQuestion;
    private EditText editOption;
    private List<String> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poll);

        editQuestion = findViewById(R.id.edit_question);
        editOption = findViewById(R.id.edit_option);
        options = new ArrayList<>();

        Button buttonAddOption = findViewById(R.id.button_add_option);
        buttonAddOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOption();
            }
        });

        Button buttonCreatePoll = findViewById(R.id.button_create_poll);
        buttonCreatePoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPoll();
            }
        });
    }

    private void addOption() {
        String option = editOption.getText().toString().trim();
        if (!option.isEmpty()) {
            options.add(option);
            editOption.setText("");
            Toast.makeText(this, "Option added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Enter an option", Toast.LENGTH_SHORT).show();
        }
    }

    private void createPoll() {
        String question = editQuestion.getText().toString().trim();
        if (!question.isEmpty()) {
            try {
                // Encode the question and options
                String encodedQuestion = URLEncoder.encode(question, "UTF-8");
                String encodedOptions = URLEncoder.encode(options.toString(), "UTF-8");

                // Construct the URL with the encoded question and options
                String url = "https://localhost/create_poll.php?question=" + encodedQuestion
                        + "&options=" + encodedOptions;

                // Send a network request to the PHP script
                URL apiUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
                connection.setRequestMethod("GET");

                // Read the response from the PHP script
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.readLine();

                // Handle the response as needed
                // For example, you can display a success message or handle errors
                Log.d("CreatePollActivity", "Response: " + response);
                Toast.makeText(this, "Poll created", Toast.LENGTH_SHORT).show();
                finish();

                // Close the connections and streams
                reader.close();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error creating poll", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Enter a question", Toast.LENGTH_SHORT).show();
        }
    }
}
