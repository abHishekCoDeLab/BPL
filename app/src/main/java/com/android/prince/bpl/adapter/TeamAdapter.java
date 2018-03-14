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
 * Created by prince on 10/3/18.
 */

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamCustomeAdapter>{

    private ArrayList<String> nameListArray;

    public TeamAdapter(Context context, ArrayList<String> name){
        nameListArray = name;
    }

    @Override
    public TeamCustomeAdapter onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_list_item,parent,false);

        return new TeamCustomeAdapter(view);
    }

    @Override
    public void onBindViewHolder(TeamCustomeAdapter holder, int position) {

        String nameStr = nameListArray.get(position);
        holder.name.setText(nameStr);
    }

    @Override
    public int getItemCount() {
        return nameListArray.size();
    }

    public class TeamCustomeAdapter extends RecyclerView.ViewHolder{

        private TextView name;

        public TeamCustomeAdapter(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.TeamNameList);
        }
    }


}
