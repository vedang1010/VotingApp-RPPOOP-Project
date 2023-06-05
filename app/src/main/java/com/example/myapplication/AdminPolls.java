package com.example.myapplication;

import static com.example.myapplication.AdminPage.LogUser;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class AdminPolls extends AppCompatActivity {
    private ListView pollsListView;
    private List<String> pollNames;
    public static String ElectionName;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_polls);

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
//                    String ElectName=pollSnapshot.getKey().toString();
                    if (LogUser.equals(pollSnapshot.child("createdBy").getValue())) {
                        String pollName = pollSnapshot.getKey().toString();
                        pollNames.add(pollName);
//                        Toast.makeText(AdminPolls.this, "Added", Toast.LENGTH_SHORT).show();
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
                } else {
                    button = (Button) convertView;
                }

                final String pollName = getItem(position);
                button.setText(pollName);
                button.setOnClickListener(v -> {
                    Button button1 =(Button) v;
                    String buttonTxt= button1.getText().toString();
                    ElectionName=buttonTxt;
                    startActivity(new Intent(AdminPolls.this, DisplayVoteCountActivity.class ));
                    // Handle button click
                    // You can perform any desired action here, such as navigating to a detail activity
                    // or starting the voting process for the selected poll
                });

                return button;
            }
        };

        pollsListView.setAdapter(adapter);
    }
}
