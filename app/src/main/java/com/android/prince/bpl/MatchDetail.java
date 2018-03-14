package com.android.prince.bpl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.prince.bpl.fragment.All;
import com.android.prince.bpl.fragment.Recent;
import com.android.prince.bpl.fragment.Upcoming;

public class MatchDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);

        Recent recent = new Recent();
        Upcoming upcoming = new Upcoming();
        recent.getContextMain(MatchDetail.this);
        upcoming.getContextMain(MatchDetail.this);


        getSupportFragmentManager().beginTransaction().add(R.id.MatchDetailFrameLayout,new All()).commit();
    }
}
