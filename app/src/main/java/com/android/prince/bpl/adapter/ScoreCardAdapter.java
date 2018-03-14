package com.android.prince.bpl.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.prince.bpl.R;

import java.util.ArrayList;

/**
 * Created by prince on 13/3/18.
 */

public class ScoreCardAdapter extends RecyclerView.Adapter<ScoreCardAdapter.ScoreCardCustomViewHolder> {

    private ArrayList<String> run;
    private ArrayList<String> ball;
    private ArrayList<String> sr;
    private ArrayList<String> name;

    public ScoreCardAdapter(ArrayList<String> name,ArrayList<String> run,ArrayList<String> ball,ArrayList<String> sr){
        this.name = name;
        this.ball = ball;
        this.run = run;
        this.sr = sr;
    }


    @Override
    public ScoreCardCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scorecard_list_item,parent,false);

        return new ScoreCardCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScoreCardCustomViewHolder holder, int position) {

        String nameStr = name.get(position);
        String runStr = run.get(position);
        String srStr = sr.get(position);
        String ballStr = ball.get(position);

        holder.name.setText(nameStr);
        holder.r.setText(runStr);
        holder.b.setText(ballStr);
        holder.sr.setText(srStr);
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ScoreCardCustomViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView r;
        private TextView b;
        private TextView sr;

        public ScoreCardCustomViewHolder(View itemView) {

            super(itemView);

            name = (TextView)itemView.findViewById(R.id.NamePlayed);
            r = (TextView)itemView.findViewById(R.id.RPlayed);
            b = (TextView)itemView.findViewById(R.id.BPlayed);
            sr = (TextView)itemView.findViewById(R.id.SrPlayed);

        }
    }
}
