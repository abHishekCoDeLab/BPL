package com.android.prince.bpl;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.android.prince.bpl.adapter.AuxionAdapter;
import com.android.prince.bpl.adapter.ScoreCardAdapter;
import com.android.prince.bpl.adapter.TeamAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ScoreCard extends AppCompatActivity {

    RecyclerView teamOneRun;
    RecyclerView teamTwoRun;
    RecyclerView teamOneWicket;
    RecyclerView teamTwoWicket;

    TextView teamOneName;
    TextView teamTwoName;
    TextView wonComment;

    private ArrayList<String> teamOneRunArrayB = new ArrayList<>();
    private ArrayList<String> teamOneBallArrayB = new ArrayList<>();
    private ArrayList<String> teamOneSrArrayB = new ArrayList<>();
    private ArrayList<String> teamOneNameArrayB = new ArrayList<>();

    private ArrayList<String> teamOneOverArrayW = new ArrayList<>();
    private ArrayList<String> teamOneNameArrayW = new ArrayList<>();
    private ArrayList<String> teamOneRunArrayW = new ArrayList<>();
    private ArrayList<String> teamOneWicketArrayW = new ArrayList<>();

    private ArrayList<String> teamTwoRunArrayB = new ArrayList<>();
    private ArrayList<String> teamTwoBallArrayB = new ArrayList<>();
    private ArrayList<String> teamTwoSrArrayB = new ArrayList<>();
    private ArrayList<String> teamTwoNameArrayB = new ArrayList<>();

    private ArrayList<String> teamTwoOverArrayW = new ArrayList<>();
    private ArrayList<String> teamTwoNameArrayW = new ArrayList<>();
    private ArrayList<String> teamTwoRunArrayW = new ArrayList<>();
    private ArrayList<String> teamTwoWicketArrayW = new ArrayList<>();

    private String matchSelected;
    private String teamOne;
    private String teamTwo;
    private String Comment;

    private DatabaseReference selectMatchRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);

        Intent intent = getIntent();
        matchSelected = intent.getStringExtra("MATCHNAME");
        teamOne = intent.getStringExtra("TEAMONE");
        teamTwo = intent.getStringExtra("TEAMTWO");
        Comment = intent.getStringExtra("WONCOMMENT");

        selectMatchRef = FirebaseDatabase.getInstance().getReference("MATCHDETAIL").child("RECENT").child(matchSelected).child("SCORECARD");

        new LoadBallPlayerTeamOne().execute(selectMatchRef.child(teamOne).child("BALL"));
        new LoadBallPlayerTeamTwo().execute(selectMatchRef.child(teamTwo).child("BALL"));
        new LoadBatPlayerTeamOne().execute(selectMatchRef.child(teamOne).child("BAT"));
        new LoadBatPlayerTeamTwo().execute(selectMatchRef.child(teamTwo).child("BAT"));

        teamOneRun = (RecyclerView)findViewById(R.id.TeamOnePlayedList);
        teamTwoRun = (RecyclerView)findViewById(R.id.TeamTwoPlayedList);

        teamOneWicket = (RecyclerView)findViewById(R.id.TeamOneBallPlayedList);
        teamTwoWicket = (RecyclerView)findViewById(R.id.TeamTwoBallPlayedList);

        teamOneName = (TextView)findViewById(R.id.TeamOneName);
        teamTwoName = (TextView)findViewById(R.id.TeamTwoName);

        teamOneName.setText(teamOne);
        teamTwoName.setText(teamTwo);

        wonComment = (TextView)findViewById(R.id.TeamWonComment);

        wonComment.setText(Comment);

        teamOneRun.setLayoutManager(new LinearLayoutManager(ScoreCard.this));
        teamTwoRun.setLayoutManager(new LinearLayoutManager(ScoreCard.this));
        teamOneWicket.setLayoutManager(new LinearLayoutManager(ScoreCard.this));
        teamTwoWicket.setLayoutManager(new LinearLayoutManager(ScoreCard.this));


    }

    public class LoadBallPlayerTeamOne extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference loadBallPlayerTeamOne = databaseReferences[0];

            final DatabaseReference databaseReferenceFix = FirebaseDatabase.getInstance().getReference("MATCHDETAIL").child("RECENT").child(matchSelected).child("SCORECARD").child(teamOne).child("BALL");

            loadBallPlayerTeamOne.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        teamOneNameArrayW.add(child.getKey());
                    }

                    for(int i=0;i<teamOneNameArrayW.size();i++){
                        new LoadBallDetailTeamOne().execute(databaseReferenceFix.child(teamOneNameArrayW.get(i)));
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            return null;
        }
    }

    public class LoadBatPlayerTeamOne extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(final DatabaseReference... databaseReferences) {

            final DatabaseReference databaseReferenceFix = FirebaseDatabase.getInstance().getReference("MATCHDETAIL").child("RECENT").child(matchSelected).child("SCORECARD").child(teamOne).child("BAT");

            final DatabaseReference loadBatPlayerTeamOne = databaseReferences[0];

            loadBatPlayerTeamOne.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        teamOneNameArrayB.add(child.getKey());
                    }


                    for(int i=0;i<teamOneNameArrayB.size();i++){
                        new LoadBatDetailTeamOne().execute(databaseReferenceFix.child(teamOneNameArrayB.get(i)));
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            return null;
        }
    }

    public class LoadBallPlayerTeamTwo extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference databaseReferenceFix = FirebaseDatabase.getInstance().getReference("MATCHDETAIL").child("RECENT").child(matchSelected).child("SCORECARD").child(teamTwo).child("BALL");

            final DatabaseReference loadBatPlayerTeamTwo = databaseReferences[0];

            loadBatPlayerTeamTwo.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        teamTwoNameArrayW.add(child.getKey());
                    }

                    for(int i=0;i<teamTwoNameArrayW.size();i++){
                        new LoadBallDetailTeamTwo().execute(databaseReferenceFix.child(teamTwoNameArrayW.get(i)));
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            return null;
        }
    }

    public class LoadBatPlayerTeamTwo extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference databaseReferenceFix = FirebaseDatabase.getInstance().getReference("MATCHDETAIL").child("RECENT").child(matchSelected).child("SCORECARD").child(teamTwo).child("BAT");

            final DatabaseReference loadBatPlayerTeamTwo = databaseReferences[0];

            loadBatPlayerTeamTwo.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        teamTwoNameArrayB.add(child.getKey());
                    }

                    for(int i=0;i<teamTwoNameArrayB.size();i++){
                        new LoadBatDetailTeamTwo().execute(databaseReferenceFix.child(teamTwoNameArrayB.get(i)));
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            return null;
        }
    }


    //  Load Detail
    public class LoadBallDetailTeamOne extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference loadBatPlayerTeamTwo = databaseReferences[0];

            loadBatPlayerTeamTwo.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if(child.getKey().equalsIgnoreCase("over")){
                            teamOneOverArrayW.add(child.getValue().toString());
                        }else if(child.getKey().equalsIgnoreCase("run")){
                            teamOneRunArrayW.add(child.getValue().toString());
                        }else if(child.getKey().equalsIgnoreCase("wicket")){
                            teamOneWicketArrayW.add(child.getValue().toString());
                        }
                    }

                    ScoreCardAdapter scoreCardAdapter1w = new ScoreCardAdapter(teamOneNameArrayW,teamOneOverArrayW,teamOneWicketArrayW,teamOneRunArrayW);
                    teamOneWicket.setAdapter(scoreCardAdapter1w);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            return null;
        }
    }

    public class LoadBatDetailTeamOne extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {


            final DatabaseReference loadBatPlayerTeamTwo = databaseReferences[0];

            loadBatPlayerTeamTwo.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if(child.getKey().equalsIgnoreCase("ball")){
                            teamOneBallArrayB.add(child.getValue().toString());
                        }else if(child.getKey().equalsIgnoreCase("out")){
                            teamOneSrArrayB.add(child.getValue().toString());
                        }else if(child.getKey().equalsIgnoreCase("run")){
                            teamOneRunArrayB.add(child.getValue().toString());
                        }
                    }

                    ScoreCardAdapter scoreCardAdapter1b = new ScoreCardAdapter(teamOneNameArrayB,teamOneRunArrayB,teamOneBallArrayB,teamOneSrArrayB);
                    teamOneRun.setAdapter(scoreCardAdapter1b);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            return null;
        }
    }


    public class LoadBallDetailTeamTwo extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {


            final DatabaseReference loadBatPlayerTeamTwo = databaseReferences[0];

            loadBatPlayerTeamTwo.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if(child.getKey().equalsIgnoreCase("over")){
                            teamTwoOverArrayW.add(child.getValue().toString());
                        }else if(child.getKey().equalsIgnoreCase("run")){
                            teamTwoRunArrayW.add(child.getValue().toString());
                        }else if(child.getKey().equalsIgnoreCase("wicket")){
                            teamTwoWicketArrayW.add(child.getValue().toString());
                        }
                    }


                    ScoreCardAdapter scoreCardAdapter2w = new ScoreCardAdapter(teamTwoNameArrayW,teamTwoOverArrayW,teamTwoWicketArrayW,teamTwoRunArrayW);
                    teamTwoWicket.setAdapter(scoreCardAdapter2w);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            return null;
        }
    }

    public class LoadBatDetailTeamTwo extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference loadBatPlayerTeamTwo = databaseReferences[0];

            loadBatPlayerTeamTwo.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if(child.getKey().equalsIgnoreCase("ball")){
                            teamTwoBallArrayB.add(child.getValue().toString());
                        }else if(child.getKey().equalsIgnoreCase("out")){
                            teamTwoSrArrayB.add(child.getValue().toString());
                        }else if(child.getKey().equalsIgnoreCase("run")){
                            teamTwoRunArrayB.add(child.getValue().toString());
                        }
                    }


                    ScoreCardAdapter scoreCardAdapter2b = new ScoreCardAdapter(teamTwoNameArrayB,teamTwoRunArrayB,teamTwoBallArrayB,teamTwoSrArrayB);
                    teamTwoRun.setAdapter(scoreCardAdapter2b);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            return null;
        }
    }


}
