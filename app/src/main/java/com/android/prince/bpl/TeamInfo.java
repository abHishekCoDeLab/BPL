package com.android.prince.bpl;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.prince.bpl.adapter.TeamAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeamInfo extends AppCompatActivity {

    RecyclerView recyclerView;

    TextView name;

    private ArrayList<String> nameListArray = new ArrayList<>();

    String teamName;

    private DatabaseReference teamInfoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_info);

        Intent intent = getIntent();
        teamName = intent.getStringExtra("NAME");

        teamInfoRef = FirebaseDatabase.getInstance().getReference("SQUADS").child("TEAMNAME").child(teamName);

        recyclerView = (RecyclerView)findViewById(R.id.TeamInfoRecylerView);
        name = (TextView)findViewById(R.id.TeamInfoName);

        name.setText(teamName);

        recyclerView.setLayoutManager(new LinearLayoutManager(TeamInfo.this));

        new LoadTeamInfo().execute(teamInfoRef);

    }

    public class LoadTeamInfo extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference databaseReference = databaseReferences[0];

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        nameListArray.add(child.getKey());
                    }

                    if(nameListArray != null){
                        TeamAdapter teamAdapter = new TeamAdapter(TeamInfo.this,nameListArray);
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
