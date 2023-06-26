package com.example.myapplication;
import static com.example.myapplication.logInActivity.logedInUser;
import android.content.Intent;
import android.content.SyncStatusObserver;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.Random;

public class CreatePollActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-register-3e247-default-rtdb.firebaseio.com/");


    private LinearLayout optionsLayout;
    private Button addOptionButton;
    private Button createPollButton;


    public Integer generatePollId(){
        Random random = new Random();
        int minRange = 100_000;  // Minimum 6-digit number
        int maxRange = 999_999;  // Maximum 6-digit number

        return random.nextInt(maxRange - minRange + 1) + minRange;
    }
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
        int check=0;
        for (int i=0;i<optionCount;i++){
            LinearLayout optionLayout = (LinearLayout) optionsLayout.getChildAt(i);
            EditText optionEditText = (EditText) optionLayout.getChildAt(0);
            String option = optionEditText.getText().toString();
            if (option.isEmpty()){
                check=1;
            }
        }
        if (check==1){
            Toast.makeText(CreatePollActivity.this, "Fill all candidate boxes or Delete them", Toast.LENGTH_SHORT).show();
        }
        else if (optionCount<2) {
            Toast.makeText(CreatePollActivity.this, "Add atleast 2 Candidates", Toast.LENGTH_SHORT).show();
        }
        else if(question.equals("")){
                Toast.makeText(this, "Please enter election name", Toast.LENGTH_SHORT).show();
        } else {
            databaseReference.child("Election").addListenerForSingleValueEvent(new ValueEventListener() {
////////
//                String owner;
                int pollId=generatePollId();
                //////////
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    DatabaseReference userReference = databaseReference.child("Election").child(question);
                    ////////
//                    databaseReference.child("Election").child(owner);
                    userReference.child("pollId").setValue(pollId);
                    userReference.child("endElection").setValue(0);
                    userReference.child("createdBy").setValue(logedInUser);
                    userReference.child("Candidates");
                    /////////
                    for (int i = 0; i < optionCount; i++) {
                        LinearLayout optionLayout = (LinearLayout) optionsLayout.getChildAt(i);
                        EditText optionEditText = (EditText) optionLayout.getChildAt(0);
                        String option = optionEditText.getText().toString();
                        String s = String.format("Candidate %d", i + 1);
                        int vote=0;
                        userReference.child("Candidates").child(s).child("name").setValue(option);
                        System.out.println(option);
                        userReference.child("Candidates").child(s).child("vote").setValue(vote);
                    }
                    Toast.makeText(CreatePollActivity.this, "Poll Created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreatePollActivity.this, PollAdmin.class));
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(CreatePollActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}