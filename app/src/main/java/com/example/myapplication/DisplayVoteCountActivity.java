package com.example.myapplication;//package com.example.myapplication;
//import static com.example.myapplication.logInActivity.logedInUser;
//import android.os.Bundle;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DisplayVoteCountActivity extends AppCompatActivity {
//    private ListView candidatesListView;
//    private List<String> candidateNames;
//    public  String En;
//    private DatabaseReference databaseReference;
//    ArrayList<String> datalist=new ArrayList<>();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_display_vote_count);
//
//        // Initialize Firebase
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReferenceFromUrl("https://login-register-3e247-default-rtdb.firebaseio.com/");
//
////        for (logedInUser.equals(databaseReference.)){
////
////        }
//        // Initialize UI elements
//        candidatesListView = findViewById(R.id.candidates_listview);
//        candidateNames = new ArrayList<>();
//        databaseReference.child("Election").addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot ElectionSnapshot) {
//                datalist.clear();
//                for (DataSnapshot eSnapShot:ElectionSnapshot.getChildren()){
//                    String val=eSnapShot.getKey().toString();
//                    datalist.add(val);
//                }
////                for (long i=0;i<ElectionSnapshot.child("Candidates").getChildrenCount();i++){
////                    String s = String.format("Candidate %d", i + 1);
//                    for(String j:datalist) {
//
//                        String k = ElectionSnapshot.child(j).child("createdBy").getValue().toString();
//                        if (logedInUser.equals(k)){
//                            En=k;
//                            databaseReference.child("Election").child(En).child("Candidates").addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    candidateNames.clear();
////                for (DataSnapshot candidateSnapshot : dataSnapshot.getChildren()) {
//                                    for (long  i=0 ; i<dataSnapshot.getChildrenCount();i++) {
//                                        String s = String.format("Candidate %d", i + 1);
//                                        String candidateName = dataSnapshot.child(s).child("name").getValue(String.class);
//                                        String votes = dataSnapshot.child(s).child("vote").getValue().toString();
//                                        String candidateInfo = candidateName + " - Votes: " + votes;
//                                        candidateNames.add(candidateInfo);
//                                    }
//                                    displayCandidates();
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//                                    // Handle database error
//                                }
//                            });
//                            break;
//                        }else{
//                            Toast.makeText(DisplayVoteCountActivity.this, "You have not created any pole", Toast.LENGTH_SHORT).show();
//                        }
////                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        // Retrieve candidate names and votes from the database
//
//    }
//
//    private void displayCandidates() {
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, candidateNames);
//        candidatesListView.setAdapter(adapter);
//    }
//}
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.myapplication.AdminPolls.ElectionName;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DisplayVoteCountActivity extends AppCompatActivity {
    private ListView candidatesListView;
    private List<Candidate> candidates;
//    public static String ElectionName;
    private DatabaseReference databaseReference;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_vote_count);

        // Initialize Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReferenceFromUrl("https://login-register-3e247-default-rtdb.firebaseio.com/");

        // Initialize UI elements
        candidatesListView = findViewById(R.id.candidates_listview);
        candidates = new ArrayList<>();

        // Retrieve candidate names and votes from the database
        databaseReference.child("Election").child(ElectionName).child("Candidates").addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                candidates.clear();
                for (DataSnapshot candidateSnapshot : dataSnapshot.getChildren()) {
//                for (long i=0; i< dataSnapshot.getChildrenCount();i++) {
//                    String s1=String.format("Candidates %d",i+1);
                    String candidateName = candidateSnapshot.child("name").getValue(String.class);
                    int votes = candidateSnapshot.child("vote").getValue(Integer.class);
                    Candidate candidate = new Candidate(candidateName, votes);
                    candidates.add(candidate);
                }

                // Sort candidates by vote count in descending order
                Collections.sort(candidates, new Comparator<Candidate>() {
                    @Override
                    public int compare(Candidate c1, Candidate c2) {
                        return c2.getVotes() - c1.getVotes();
                    }
                });
//                System.out.println(candidates);
                displayCandidates();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });

    }
    public void EndVoting(View view){
        databaseReference.child("Election").child(ElectionName).child("endElection").setValue(1);
        Intent intent2 = new Intent(this, DisplayVoteCountActivity.class);
        startActivity(intent2);
    }
    private void displayCandidates() {
        List<String> candidateInfoList = new ArrayList<>();
        for (Candidate candidate : candidates) {
            String candidateInfo = candidate.getName() + " - Votes: " + candidate.getVotes();
            candidateInfoList.add(candidateInfo);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, candidateInfoList);
        candidatesListView.setAdapter(adapter);
    }

    private static class Candidate {
        private String name;
        private int votes;

        public Candidate(String name, int votes) {
            this.name = name;
            this.votes = votes;
        }

        public String getName() {
            return name;
        }

        public int getVotes() {
            return votes;
        }
    }
}
