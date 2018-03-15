package com.android.prince.bpl;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.prince.bpl.adapter.AuxionAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class AuxionList extends AppCompatActivity{

    RecyclerView recyclerView;

    MaterialSearchView searchView;

    private ArrayList<String> nameListArray = new ArrayList<>();
    private ArrayList<String> nameListArray1 = new ArrayList<>();
    private ArrayList<String> specListArray = new ArrayList<>();
    private ArrayList<String> priceListArray = new ArrayList<>();
    private ArrayList<String> imageListArray = new ArrayList<>();

    private DatabaseReference auxionListRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auxion_list);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.ToolBarAuxionList);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Auction");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setTitleMarginTop(8);

        searchView = (MaterialSearchView)findViewById(R.id.SearchBarAuxionList);

        recyclerView = (RecyclerView)findViewById(R.id.AuxionListView);

        recyclerView.setLayoutManager(new LinearLayoutManager(AuxionList.this));

        auxionListRef = FirebaseDatabase.getInstance().getReference("AUXION").child("TEAMLIST");
        new LoadAuxionListName().execute(auxionListRef);


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(AuxionList.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(AuxionList.this,PlayerInfo.class);

                        intent.putExtra("NAME",nameListArray.get(position));
                        intent.putExtra("SPEC",specListArray.get(position));
                        intent.putExtra("PRICE",priceListArray.get(position));
                        intent.putExtra("IMAGE",imageListArray.get(position));

                        startActivity(intent);

                        finish();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                if(specListArray != null) {
                    AuxionAdapter auxionAdapter = new AuxionAdapter(AuxionList.this, nameListArray1, specListArray, priceListArray,imageListArray);
                    recyclerView.setAdapter(auxionAdapter);
                }
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty()){
                    List<String> firstFound = new ArrayList<String>();
                    ArrayList<String> arrayListSearchName = new ArrayList<>();
                    ArrayList<String> arrayListSearchSpec = new ArrayList<>();
                    ArrayList<String> arrayListSearchPrice = new ArrayList<>();
                    ArrayList<String> arrayListSearchImage = new ArrayList<>();
                    for(int i=0;i<nameListArray1.size();i++){
                        if(nameListArray1.get(i).contains(newText)){
                            arrayListSearchName.add(nameListArray1.get(i));
                            arrayListSearchImage.add(imageListArray.get(i));
                            arrayListSearchPrice.add(priceListArray.get(i));
                            arrayListSearchSpec.add(specListArray.get(i));
                        }
                    }

                    AuxionAdapter auxionAdapter = new AuxionAdapter(AuxionList.this, arrayListSearchName,arrayListSearchSpec, arrayListSearchPrice,arrayListSearchImage);
                    recyclerView.setAdapter(auxionAdapter);
                }else{
                    if(specListArray != null) {
                        AuxionAdapter auxionAdapter = new AuxionAdapter(AuxionList.this, nameListArray1, specListArray, priceListArray,imageListArray);
                        recyclerView.setAdapter(auxionAdapter);
                    }
                }

                return true;
            }
        });

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

                    Log.e("Size of player name : ",String.valueOf(nameListArray.size()));

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
                        }else if(child.getKey().equalsIgnoreCase("price")){
                            priceListArray.add(child.getValue().toString());
                        }else if(child.getKey().equalsIgnoreCase("Spec")){
                            specListArray.add(child.getValue().toString());
                        }else if(child.getKey().equalsIgnoreCase("Image")){
                            imageListArray.add(child.getValue().toString());
                        }
                    }

                    Log.e("Size of price list  : ",String.valueOf(priceListArray.size()));
                    Log.e("Size of spec list  : ",String.valueOf(specListArray.size()));
                    Log.e("Size of image list  : ",String.valueOf(imageListArray.size()));

                    if(specListArray != null) {
                        AuxionAdapter auxionAdapter = new AuxionAdapter(AuxionList.this, nameListArray1, specListArray, priceListArray,imageListArray);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        searchView.setMenuItem(menuItem);

        return true;
    }
}
