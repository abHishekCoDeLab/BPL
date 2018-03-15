package com.android.prince.bpl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {


    Button leaderBoard;
    Button squads;
    Button matchInfo;
    Button registerPlayer;
    Button auxion;
    Button about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        leaderBoard = (Button)findViewById(R.id.LeaderBoardButton);
        squads = (Button)findViewById(R.id.SquadsButton);
        matchInfo = (Button)findViewById(R.id.MatchInfoButton);
        registerPlayer = (Button)findViewById(R.id.RegisteredButton);
        auxion = (Button)findViewById(R.id.AdminHomePage);
        about = (Button)findViewById(R.id.AboutUs);

        squads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this,TeamList.class));
            }
        });

        matchInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this,MatchDetail.class));
            }
        });

        registerPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this,RegisterPlayer.class));
            }
        });

        leaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  startActivity(new Intent(HomePage.this,LeaderBoard.class));
            }
        });

        auxion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this,AuxionList.class));
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this,About.class));
            }
        });

    }
}
