package com.android.prince.bpl.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.prince.bpl.AuxionList;
import com.android.prince.bpl.R;
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
public class Upcoming extends Fragment {

    RecyclerView recyclerView;

    ProgressBar progressBar;

    private ArrayList<String> teamOne = new ArrayList<>();
    private ArrayList<String> teamTwo = new ArrayList<>();
    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> noOfMatch = new ArrayList<>();

    Context context;

    private DatabaseReference upcomingMatchRef;

    public void getContextMain(Context context){
        this.context = context;
    }

    public Upcoming() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        upcomingMatchRef = FirebaseDatabase.getInstance().getReference("MATCHDETAIL").child("UPCOMING");
        new LoadMatch().execute(upcomingMatchRef);

        progressBar = (ProgressBar)getView().findViewById(R.id.ProgressUpcoming);

        recyclerView = (RecyclerView)getView().findViewById(R.id.UpcomingRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    public class LoadMatch extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

                final DatabaseReference matchDataFixRef = FirebaseDatabase.getInstance().getReference("MATCHDETAIL").child("UPCOMING");
                final DatabaseReference databaseReference = databaseReferences[0];

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            noOfMatch.add(child.getKey());
                        }

                        for(int i=0;i<noOfMatch.size();i++){
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

        public class LoadMatchDetail extends AsyncTask<DatabaseReference,Void,Void>{

            @Override
            protected Void doInBackground(DatabaseReference... databaseReferences) {
                final DatabaseReference ref = databaseReferences[0];
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            if(child.getKey().equalsIgnoreCase("DATE")){
                                date.add(child.getValue().toString());
                            }else if(child.getKey().equalsIgnoreCase("TEAMONE")){
                                teamOne.add(child.getValue().toString());
                            }else if (child.getKey().equalsIgnoreCase("TEAMTWO")){
                                teamTwo.add(child.getValue().toString());
                            }
                        }

                        progressBar.setVisibility(View.GONE);
                        UpcomingAdapter upcomingAdapter = new UpcomingAdapter(context,teamOne,teamTwo,date);
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
}
