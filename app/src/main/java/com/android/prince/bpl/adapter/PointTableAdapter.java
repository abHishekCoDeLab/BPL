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
 * Created by prince on 14/3/18.
 */

public class PointTableAdapter extends RecyclerView.Adapter<PointTableAdapter.TableCustomViewHolder>{

    private ArrayList<String> teamName;
    private ArrayList<String> teamPoint;

    public PointTableAdapter(Context context, ArrayList<String> one, ArrayList<String> two){
        teamName = one;
        teamPoint = two;
    }

    @Override
    public TableCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.point_table_list,parent,false);

        return new TableCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TableCustomViewHolder holder, int position) {

        String str = String.valueOf(position+1);

        holder.teamNameStr.setText(teamName.get(position));
        holder.teamPointStr.setText(teamPoint.get(position));
        holder.sno.setText(str);

    }

    @Override
    public int getItemCount() {
        return teamName.size();
    }

    public class TableCustomViewHolder extends RecyclerView.ViewHolder {

        private TextView teamNameStr;
        private TextView teamPointStr;
        private TextView sno;

        public TableCustomViewHolder(View itemView) {

            super(itemView);

            teamNameStr = (TextView) itemView.findViewById(R.id.NameTeamPoint);
            teamPointStr = (TextView) itemView.findViewById(R.id.TeamPoint);
            sno = (TextView) itemView.findViewById(R.id.SNo);

        }
    }
}
