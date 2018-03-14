package com.android.prince.bpl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.prince.bpl.R;

import java.util.ArrayList;

/**
 * Created by prince on 11/3/18.
 */

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.UpcomingCustomViewHolder>{

    private ArrayList<String> teamOne;
    private ArrayList<String> teamTwo;
    private ArrayList<String> date;

    public UpcomingAdapter(Context context, ArrayList<String> one, ArrayList<String> two, ArrayList<String> date){
        teamTwo = two;
        teamOne = one;
        this.date = date;

    }

    @Override
    public UpcomingCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_list_item,parent,false);

        return new UpcomingCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UpcomingCustomViewHolder holder, int position) {

        String team1 = teamOne.get(position);
        String team2 = teamTwo.get(position);
        String matchDate = date.get(position);

        holder.teamOne.setText(team1);
        holder.teamTwo.setText(team2);
        holder.date.setText(matchDate);

    }

    @Override
    public int getItemCount() {
        return teamTwo.size();
    }

    public class UpcomingCustomViewHolder extends RecyclerView.ViewHolder {

        private TextView teamOne;
        private TextView teamTwo;
        private TextView date;

        public UpcomingCustomViewHolder(View itemView) {

            super(itemView);

            teamOne = (TextView) itemView.findViewById(R.id.UpcomingTeamOne);
            teamTwo = (TextView) itemView.findViewById(R.id.UpcomingTeamTwo);
            date = (TextView) itemView.findViewById(R.id.UpcomingDate);

        }
    }
}
