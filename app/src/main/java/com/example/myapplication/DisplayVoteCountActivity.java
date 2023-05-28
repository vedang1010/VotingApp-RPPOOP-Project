package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayVoteCountActivity extends AppCompatActivity {

    private TextView voteCountTextView;
    private DatabaseReference candidateReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_vote_count);

        // Initialize the TextView
        voteCountTextView = findViewById(R.id.voteCountTextView);

        // Get the candidate ID from the intent or any other source
        String candidateId = "candidate1";

        // Get the reference to the candidate in the database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        candidateReference = databaseReference.child("Candidates").child(candidateId);

        // Set up a ValueEventListener to listen for changes in the vote count
        candidateReference.child("vote").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Retrieve the vote count from the dataSnapshot
                Long voteCount = dataSnapshot.getValue(Long.class);

                // Update the vote count in the TextView
                voteCountTextView.setText("Vote Count: " + voteCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any database error
            }
        });
    }
}
