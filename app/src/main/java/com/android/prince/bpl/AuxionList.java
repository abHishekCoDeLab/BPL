package com.android.prince.bpl;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.prince.bpl.adapter.AuxionAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AuxionList extends AppCompatActivity{

    RecyclerView recyclerView;

    private ArrayList<String> nameListArray = new ArrayList<>();
    private ArrayList<String> specListArray = new ArrayList<>();
    private ArrayList<String> priceListArray = new ArrayList<>();
    private ArrayList<String> imageListArray = new ArrayList<>();

    private DatabaseReference auxionListRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auxion_list);

        recyclerView = (RecyclerView)findViewById(R.id.AuxionListView);

        auxionListRef = FirebaseDatabase.getInstance().getReference("AUXION").child("TEAMLIST");
        new LoadAuxionListName().execute(auxionListRef);

        recyclerView.setLayoutManager(new LinearLayoutManager(AuxionList.this));

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(AuxionList.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(AuxionList.this,PlayerInfo.class);

                        intent.putExtra("NAME",nameListArray.get(position));
                        intent.putExtra("SPEC",specListArray.get(position));
                        intent.putExtra("PRICE",priceListArray.get(position));
                        intent.putExtra("IMAGE",imageListArray.get(position));

                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );



    }

    public class LoadAuxionListName extends AsyncTask<DatabaseReference,Void,Void>{

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

                        }else if(child.getKey().equalsIgnoreCase("price")){
                            priceListArray.add(child.getValue().toString());
                        }else if(child.getKey().equalsIgnoreCase("Spec")){
                            specListArray.add(child.getValue().toString());
                        }else if(child.getKey().equalsIgnoreCase("Image")){
                            imageListArray.add(child.getValue().toString());
                        }
                    }

                    if(specListArray != null) {
                        AuxionAdapter auxionAdapter = new AuxionAdapter(AuxionList.this, nameListArray, specListArray, priceListArray,imageListArray);
                        recyclerView.setAdapter(auxionAdapter);
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
