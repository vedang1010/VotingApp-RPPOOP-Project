package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import static com.example.myapplication.logInActivity.logedInUser;

public class ShowPollsActivity extends AppCompatActivity {
    private ListView pollsListView;
    private List<String> pollNames;
    public static String ElectionName;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showpolls);

        // Initialize Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReferenceFromUrl("https://login-register-3e247-default-rtdb.firebaseio.com/");

        // Initialize UI elements
        pollsListView = findViewById(R.id.polls_listview);
        pollNames = new ArrayList<>();

        // Retrieve poll names from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pollNames.clear();
                for (DataSnapshot pollSnapshot : dataSnapshot.child("Election").getChildren()) {
                    int val = pollSnapshot.child("endElection").getValue(Integer.class);
                    String pollName = pollSnapshot.getKey().toString();

                    if(val!=1) {
                        pollNames.add(pollName);
                    }
                }
                displayPollNames();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });
    }

    private void displayPollNames() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pollNames) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Button button;
                if (convertView == null) {
                    button = new Button(getContext());
                    button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    button.setPadding(16, 16, 16, 16);
                    button.setTextColor(Color.WHITE); // Set text color
                    button.setTextSize(18); // Set text size
                    button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER); // Center align text
                    button.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Set bold text
                    // Create LayoutParams with margins
                    ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(46, 46, 46, 46); // Set margins: left, top, right, bottom
                    button.setLayoutParams(params);
                } else {
                    button = (Button) convertView;
                }

                final String pollName = getItem(position);
                button.setText(pollName);

                // Determine the background based on whether the user has voted
                DatabaseReference pollRef = databaseReference.child("Election").child(pollName);
                pollRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int val = snapshot.child("endElection").getValue(Integer.class);
                        boolean hasVoted = snapshot.child("Voters").hasChild(logedInUser);

                        if (val != 1) {
                            // Set background based on voting status
                            if (hasVoted) {
                                button.setBackgroundResource(R.drawable.custom_button_main4); // Background for voted polls
                            } else {
                                button.setBackgroundResource(R.drawable.custom_button_main3); // Background for not voted polls
                            }
                        } else {
//                            button.setBackgroundResource(R.drawable.custom_button_ended); // Background for ended polls
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle database error
                    }
                });

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ElectionName = pollName;
                        DatabaseReference databaseReference1 = databaseReference.child("Election").child(ElectionName);
                        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int val = snapshot.child("endElection").getValue(Integer.class);
                                if (val != 1) {
                                    if (snapshot.child("Voters").hasChild(logedInUser)) {
                                        Toast.makeText(ShowPollsActivity.this, "Already Voted!!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        startActivity(new Intent(ShowPollsActivity.this, VoteCandidateActivity.class));
                                    }
                                } else {
                                    Toast.makeText(ShowPollsActivity.this, "Election has ended!!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Handle error
                            }
                        });
                    }
                });

                return button;
            }

        };

        pollsListView.setAdapter(adapter);
    }
}
