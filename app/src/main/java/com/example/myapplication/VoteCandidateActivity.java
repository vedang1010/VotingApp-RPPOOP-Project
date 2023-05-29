package com.example.myapplication;

import android.os.Bundle;
import static com.example.myapplication.ShowPollsActivity.ElectionName;

import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VoteCandidateActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private String selectedElection;
    private CheckBox lastCheckedCheckBox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vooting);

        // Get the selected election name from the previous activity or fragment
        selectedElection = ElectionName;

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-register-3e247-default-rtdb.firebaseio.com/");

        // Retrieve the list of candidates for the selected election
        DatabaseReference electionRef = databaseReference.child("Election").child(selectedElection).child("Candidates");
        electionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Iterate through the candidates and create checkboxes dynamically
                LinearLayout candidateLayout = findViewById(R.id.CandidateLayout);
                for (DataSnapshot candidateSnapshot : dataSnapshot.getChildren()) {
                    String candidateName = candidateSnapshot.child("name").getValue(String.class);

                    // Create a new checkbox for the candidate
                    CheckBox checkBox = new CheckBox(VoteCandidateActivity.this);
                    checkBox.setText(candidateName);
                    int padding = getResources().getDimensionPixelSize(R.dimen.checkbox_padding);
                    checkBox.setPadding(17 , 10 , padding, padding);
                    // Add the checkbox to the layout
                    candidateLayout.addView(checkBox);
                    checkBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CheckBox clickedCheckBox = (CheckBox) v;
                            if (clickedCheckBox.isChecked()) {
                                // If the clicked checkbox is checked, uncheck the last checked checkbox
                                if (lastCheckedCheckBox != null && lastCheckedCheckBox != clickedCheckBox) {
                                    lastCheckedCheckBox.setChecked(false);
                                }
                                // Update the last checked checkbox
                                lastCheckedCheckBox = clickedCheckBox;
                            } else {
                                // If the clicked checkbox is unchecked, clear the last checked checkbox
                                lastCheckedCheckBox = null;
                            }
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(VoteCandidateActivity.this, "Database Error!", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle the user's selection of candidates and perform necessary actions
        // You can add a button or implement a different user interaction mechanism for voting
        // and retrieve the selected candidates when the user submits their vote.
    }
}
