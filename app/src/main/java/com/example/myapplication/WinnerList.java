package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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

public class WinnerList extends AppCompatActivity {
    private ListView pollsListView;
    private List<String> pollNames;
    public static String ElectionName;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showpolls);

        // Initialize Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-register-3e247-default-rtdb.firebaseio.com/");

        // Initialize UI elements
        pollsListView = findViewById(R.id.polls_listview);
        pollNames = new ArrayList<>();

        // Retrieve poll names from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pollNames.clear();
                for (DataSnapshot pollSnapshot : dataSnapshot.child("Election").getChildren()) {
                    int end_election = pollSnapshot.child("endElection").getValue(Integer.class);
                    if (end_election == 1) {
                        String pollName = pollSnapshot.getKey().toString();
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
                    button.setTextColor(Color.BLACK);
                    button.setHintTextColor(Color.BLACK);
                } else {
                    button = (Button) convertView;
                }

                final String pollName = getItem(position);
                button.setText(pollName);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button button = (Button) v;
                        ElectionName = button.getText().toString();
                        System.out.println(ElectionName);

//                        DatabaseReference databaseReference1 = databaseReference.child("Election").child(ElectionName).child("Candidates");
                        Intent intent =new Intent(WinnerList.this,Winner.class);
                        startActivity(intent);
//                        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                startActivity(new Intent(WinnerList.this, Winner.class));
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//                                // Handle database error
//                            }
//                        });

                        // Handle button click
                        // You can perform any desired action here, such as navigating to a detail activity
                        // or starting the voting process for the selected poll
                    }
                });

                return button;
            }
        };

        pollsListView.setAdapter(adapter);
    }
}
