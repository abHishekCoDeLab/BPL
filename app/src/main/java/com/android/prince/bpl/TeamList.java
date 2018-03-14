package com.android.prince.bpl;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.prince.bpl.adapter.TeamAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeamList extends AppCompatActivity {

    RecyclerView recyclerView;

    private ArrayList<String> nameListArray = new ArrayList<>();

    private DatabaseReference teamNameRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);

        teamNameRef = FirebaseDatabase.getInstance().getReference("SQUADS").child("TEAMNAME");

        new LoadTeamName().execute(teamNameRef);

        recyclerView = (RecyclerView)findViewById(R.id.RecyclerViewTeamList);

        recyclerView.setLayoutManager(new LinearLayoutManager(TeamList.this));

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(TeamList.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Intent intent = new Intent(TeamList.this,TeamInfo.class);
                        intent.putExtra("NAME",nameListArray.get(position));
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }

    public class LoadTeamName extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference databaseReference = databaseReferences[0];

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        nameListArray.add(child.getKey());
                    }

                    TeamAdapter teamAdapter = new TeamAdapter(TeamList.this,nameListArray);
                    if(teamAdapter != null) {
                        recyclerView.setAdapter(teamAdapter);
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
