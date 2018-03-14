package com.android.prince.bpl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.prince.bpl.R;

import java.util.ArrayList;

/**
 * Created by prince on 11/3/18.
 */

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.RecentCustomViewHolder>{

    private ArrayList<String> teamOne;
    private ArrayList<String> teamTwo;
    private ArrayList<String> won;

    public RecentAdapter(Context context, ArrayList<String> one, ArrayList<String> two, ArrayList<String> win){
        teamTwo = two;
        teamOne = one;
        won = win;

    }

    @Override
    public RecentCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_list_item,parent,false);

        return new RecentCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecentCustomViewHolder holder, int position) {

        String team1 = teamOne.get(position);
        String team2 = teamTwo.get(position);
        String win = won.get(position);

        holder.teamOne.setText(team1);
        holder.teamTwo.setText(team2);
        holder.won.setText(win);

    }

    @Override
    public int getItemCount() {
        return teamTwo.size();
    }

    public class RecentCustomViewHolder extends RecyclerView.ViewHolder {

        private TextView teamOne;
        private TextView teamTwo;
        private TextView won;

        public RecentCustomViewHolder(View itemView) {

            super(itemView);

            teamOne = (TextView) itemView.findViewById(R.id.RecentTeamOne);
            teamTwo = (TextView) itemView.findViewById(R.id.RecentTeamTwo);
            won = (TextView) itemView.findViewById(R.id.RecentTeamWon);

        }
    }
}
