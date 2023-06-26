package com.example.myapplication;
import static com.example.myapplication.WinnerList.ElectionName;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Winner extends AppCompatActivity {
    public static String ElectName=ElectionName;

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

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-register-3e247-default-rtdb.firebaseio.com/").child("Election").child(ElectName).child("Candidates");
//        DatabaseReference userReference = databaseReference.child("Election").child(ElectName);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    long candidates=dataSnapshot.getChildrenCount();
                    String winner="Candidate 1";
                    int voteCount = dataSnapshot.child(winner).child("vote").getValue(Integer.class);
                    for (int i=2;i<=candidates;i++) {
                        String temp=String.format("Candidate %d", i);
                        int tempVote= dataSnapshot.child(temp).child("vote").getValue(Integer.class);
                        if (tempVote>voteCount) {
                            winner = temp;
                            voteCount=tempVote;
                        }
                    }
                    String winnerName =winner;
                    String electionName = ElectName;

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
