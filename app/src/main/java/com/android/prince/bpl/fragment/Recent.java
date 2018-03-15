package com.android.prince.bpl.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.prince.bpl.AuxionList;
import com.android.prince.bpl.PlayerInfo;
import com.android.prince.bpl.R;
import com.android.prince.bpl.RecyclerItemClickListener;
import com.android.prince.bpl.ScoreCard;
import com.android.prince.bpl.adapter.RecentAdapter;
import com.android.prince.bpl.adapter.UpcomingAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Recent extends Fragment {

    RecyclerView recyclerView;

    private ArrayList<String> teamOne = new ArrayList<>();
    private ArrayList<String> teamTwo = new ArrayList<>();
    private ArrayList<String> won = new ArrayList<>();
    private ArrayList<String> noOfMatch = new ArrayList<>();

    ProgressBar progressBar;

    Context context;

    private DatabaseReference recentMatchRef;

    public void getContextMain(Context context){
        this.context = context;
    }

    public Recent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recent, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recentMatchRef = FirebaseDatabase.getInstance().getReference("MATCHDETAIL").child("RECENT");

        progressBar = (ProgressBar)getView().findViewById(R.id.ProgressRecent);

        recyclerView = (RecyclerView)getView().findViewById(R.id.RecentRecyclerView);
        new LoadMatch().execute(recentMatchRef);


        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context,recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Intent intent = new Intent(getContext(),ScoreCard.class);
                        intent.putExtra("MATCHNAME",noOfMatch.get(position));
                        intent.putExtra("TEAMONE",teamOne.get(position));
                        intent.putExtra("TEAMTWO",teamTwo.get(position));
                        intent.putExtra("WONCOMMENT",won.get(position));
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    public class LoadMatch extends AsyncTask<DatabaseReference,Void,Void> {

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference matchDataFixRef = FirebaseDatabase.getInstance().getReference("MATCHDETAIL").child("RECENT");
            final DatabaseReference databaseReference = databaseReferences[0];

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        noOfMatch.add(child.getKey());
                    }

                    for (int i = 0; i < noOfMatch.size(); i++) {
                        DatabaseReference ref = matchDataFixRef.child(noOfMatch.get(i));
                        new LoadMatchDetail().execute(ref);
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
            return null;
        }

    }

    public class LoadMatchDetail extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {
            final DatabaseReference ref = databaseReferences[0];
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if(child.getKey().equalsIgnoreCase("TEAMWON")){
                            won.add(child.getValue().toString());
                        }else if(child.getKey().equalsIgnoreCase("TEAMONE")){
                            teamOne.add(child.getValue().toString());
                        }else if (child.getKey().equalsIgnoreCase("TEAMTWO")){
                            teamTwo.add(child.getValue().toString());
                        }
                    }

                    progressBar.setVisibility(View.GONE);
                    RecentAdapter upcomingAdapter = new RecentAdapter(context,teamOne,teamTwo,won);
                    recyclerView.setAdapter(upcomingAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return null;
        }
    }
}

