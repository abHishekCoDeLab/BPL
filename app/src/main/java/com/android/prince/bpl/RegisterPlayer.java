package com.android.prince.bpl;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.android.prince.bpl.adapter.AuxionAdapter;
import com.android.prince.bpl.adapter.RegisterPlayerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegisterPlayer extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ArrayList<String> nameListArray = new ArrayList<>();
    private ArrayList<String> specListArray = new ArrayList<>();
    private ArrayList<String> rollListArray = new ArrayList<>();
    private ArrayList<String> imageListArray = new ArrayList<>();
    private ArrayList<String> nameListArray1 = new ArrayList<>();

    private DatabaseReference registerPlayerListRef;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_player);

        progressBar = (ProgressBar)findViewById(R.id.ProgressRegister);

        recyclerView = (RecyclerView)findViewById(R.id.RegisterPlayerListView);

        registerPlayerListRef = FirebaseDatabase.getInstance().getReference("AUXION").child("TEAMLIST");
        new LoadRegisterPlayerName().execute( registerPlayerListRef);

        recyclerView.setLayoutManager(new LinearLayoutManager(RegisterPlayer.this));


    }

    public class LoadRegisterPlayerName extends AsyncTask<DatabaseReference,Void,Void>{

        DatabaseReference databaseReferenceForDetailFix = FirebaseDatabase.getInstance().getReference("AUXION").child("TEAMLIST");

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {
            final DatabaseReference databaseReference = databaseReferences[0];

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        nameListArray.add(child.getKey());
                    }

                    for(int i=0;i<nameListArray.size();i++){
                        DatabaseReference databaseReferenceForDetail = databaseReferenceForDetailFix.child(nameListArray.get(i));
                        new LoadDetail().execute(databaseReferenceForDetail);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });


            return null;
        }
    }


    public class LoadDetail extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference ref = databaseReferences[0];
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if(child.getKey().equalsIgnoreCase("name")){
                            nameListArray1.add(child.getValue().toString());
                        }else if(child.getKey().equalsIgnoreCase("roll")){
                            rollListArray.add(child.getValue().toString());
                        }else if(child.getKey().equalsIgnoreCase("Spec")){
                            specListArray.add(child.getValue().toString());
                        }else if(child.getKey().equalsIgnoreCase("Image")){
                            imageListArray.add(child.getValue().toString());
                        }
                    }

                    if(specListArray != null) {
                        progressBar.setVisibility(View.GONE);
                        RegisterPlayerAdapter registerPlayerAdapter = new RegisterPlayerAdapter(RegisterPlayer.this, nameListArray1, specListArray, rollListArray,imageListArray);
                        recyclerView.setAdapter(registerPlayerAdapter);
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
