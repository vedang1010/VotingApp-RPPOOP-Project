package com.example.myapplication;
import static com.example.myapplication.WinnerList.ElectionName;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Winner extends AppCompatActivity {
//    public String ElectName=ElectionName;

    private TextView winnerNameTextView;
    private TextView electionNameTextView;
    private TextView voteCountTextView;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        winnerNameTextView = findViewById(R.id.winnerNameTextView2);
        electionNameTextView = findViewById(R.id.winnerNameTextView3);
        voteCountTextView = findViewById(R.id.voteCountTextView);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-register-3e247-default-rtdb.firebaseio.com/").child("Election").child(ElectionName).child("Candidates");
//        DatabaseReference userReference = databaseReference.child("Election").child(ElectName);
        System.out.println("database done");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot ) {
//                candidates.clear();
//                if (dataSnapshot.exists()) {
                    long candidates=dataSnapshot.getChildrenCount();
                    int count= (int)candidates;
                    String winner = null;
                    int voteCount = 0;
                    for (int i=0;i<count;i++) {
                        String temp=String.format("Candidate %d", i+1);
                        int tempVote= dataSnapshot.child(temp).child("vote").getValue(Integer.class);
                        if (tempVote>voteCount) {
                            winner = dataSnapshot.child(temp).child("name").getValue().toString();
                            voteCount=tempVote;
                        System.out.println("updated");
                        }
                    String winnerName =winner;
                    String electionName = ElectionName;

                    winnerNameTextView.setText(winnerName);
                    electionNameTextView.setText(electionName);
                    voteCountTextView.setText(String.valueOf(voteCount));
                }
            }

            @Override

            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
