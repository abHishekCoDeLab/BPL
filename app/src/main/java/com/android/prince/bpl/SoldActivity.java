package com.android.prince.bpl;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.prince.bpl.adapter.TeamAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SoldActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private ArrayList<String> teamName = new ArrayList<>();
    private ArrayList<String> moneyLeft = new ArrayList<>();;

    Button forward;
    TextView money;
    Spinner spinner;

    String sellPrice;

    String teamSelected ;
    String moneyLeftStr ;

    String name = "";

    DatabaseReference databaseReferenceTeamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sold);

        Intent intent = getIntent();
        sellPrice = intent.getStringExtra("MONEY");
        name  = intent.getStringExtra("NAME");

        Log.e("Sell price",sellPrice);

        databaseReferenceTeamName = FirebaseDatabase.getInstance().getReference("TEAMNAME");
        new LoadTeamNameandRemainMoney().execute(databaseReferenceTeamName);

        spinner = (Spinner)findViewById(R.id.TeamSpinner);
        forward = (Button)findViewById(R.id.Forward);
        money = (TextView) findViewById(R.id.MoneyLeft);


        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teamSelected .equalsIgnoreCase("")){
                    Toast.makeText(SoldActivity.this,"Please,Select the team Name to sold the player ...!!!",Toast.LENGTH_LONG).show();
                }else {
                    long j = Integer.parseInt(sellPrice);
                    long k = Integer.parseInt(moneyLeftStr);
                    long i = (k-j);

                    if(j>k){
                        Toast.makeText(SoldActivity.this,"Sorry !!! This team cross the limit of money ",Toast.LENGTH_LONG).show();
                    }else {
                        FirebaseDatabase.getInstance().getReference("TEAMNAME").child(teamSelected).setValue(String.valueOf(i));
                        FirebaseDatabase.getInstance().getReference("SQUADS").child("TEAMNAME").child(teamSelected).child(name).setValue(true);
                        startActivity(new Intent(SoldActivity.this, AuxionList.class));
                        finish();
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        teamSelected = teamName.get(i);
        moneyLeftStr = moneyLeft.get(i);
        money.setText("Money Left : "+moneyLeftStr);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class LoadTeamNameandRemainMoney extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {
            final DatabaseReference databaseReference = databaseReferences[0];

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        teamName.add(child.getKey());
                        moneyLeft.add(child.getValue().toString());
                    }

                    ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SoldActivity.this,android.R.layout.simple_expandable_list_item_1,teamName);
                    myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(myAdapter);

                    spinner.setOnItemSelectedListener(SoldActivity.this);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            return null;
        }
    }
}
