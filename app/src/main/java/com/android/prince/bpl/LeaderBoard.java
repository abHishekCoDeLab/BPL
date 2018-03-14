package com.android.prince.bpl;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.prince.bpl.adapter.PointTableAdapter;
import com.android.prince.bpl.adapter.TeamAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LeaderBoard extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ArrayList<String> nameListArray = new ArrayList<>();
    private ArrayList<String> pointListArray = new ArrayList<>();

    private ArrayList<String> nameListArrayS = new ArrayList<>();
    private ArrayList<String> pointListArrayS = new ArrayList<>();

    private DatabaseReference tableRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        tableRef = FirebaseDatabase.getInstance().getReference("POINTTABLE");

        recyclerView = (RecyclerView)findViewById(R.id.TeamPointList);

        recyclerView.setLayoutManager(new LinearLayoutManager(LeaderBoard.this));

        new LoadPoints().execute(tableRef);

    }

    public class LoadPoints extends AsyncTask<DatabaseReference,Void,Void> {

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference databaseReference = databaseReferences[0];

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        nameListArray.add(child.getKey());
                        pointListArray.add(child.getValue().toString());
                    }

                    PointTableAdapter pointTableAdapter = new PointTableAdapter(LeaderBoard.this,nameListArray,pointListArray);
                    if(pointTableAdapter != null) {
                        recyclerView.setAdapter(pointTableAdapter);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
            return null;
        }
    }
}
