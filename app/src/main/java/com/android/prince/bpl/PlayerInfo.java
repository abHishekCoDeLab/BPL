package com.android.prince.bpl;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PlayerInfo extends AppCompatActivity {

    String name;
    String spec;
    String price;
    String image;

    TextView nameTextView;
    TextView priceTextView;
    TextView specTextView;
    TextView currentBid;

    ImageView profileImageView;
    ImageView plus;

    Button sell;
    Button unsold;

    int playerSellPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);

        Intent intent = getIntent();
        name = intent.getStringExtra("NAME");
        spec = intent.getStringExtra("SPEC");
        price = intent.getStringExtra("PRICE");
        image = intent.getStringExtra("IMAGE");

        playerSellPrice = Integer.parseInt(price);

        nameTextView = (TextView)findViewById(R.id.InfoName);
        priceTextView = (TextView)findViewById(R.id.InfoPrice);
        specTextView = (TextView)findViewById(R.id.InfoSpec);
        currentBid = (TextView)findViewById(R.id.InfoCurrentBid);

        profileImageView = (ImageView)findViewById(R.id.PlayerInfoImage);
        plus = (ImageView) findViewById(R.id.InfoBidPlus);

        sell = (Button)findViewById(R.id.InfoSell);
        unsold = (Button)findViewById(R.id.InfoUnsole);

        nameTextView.setText(name);
        priceTextView.setText(String.valueOf(playerSellPrice));
        currentBid.setText(String.valueOf(playerSellPrice));
        specTextView.setText(spec);

        Glide.with(PlayerInfo.this).load(image).into(profileImageView);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerSellPrice = playerSellPrice+100000;
                currentBid.setText(String.valueOf(playerSellPrice));
            }
        });

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PlayerInfo.this,SoldActivity.class);
                intent.putExtra("MONEY",String.valueOf(playerSellPrice));
                intent.putExtra("NAME",name);
                Log.e("MONEY",String.valueOf(playerSellPrice));
                startActivity(intent);
                finish();
            }
        });

        unsold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlayerInfo.this,AuxionList.class));
                finish();
            }
        });



    }
}
