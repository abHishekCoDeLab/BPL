package com.android.prince.bpl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.prince.bpl.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by prince on 14/3/18.
 */

public class RegisterPlayerAdapter extends RecyclerView.Adapter<RegisterPlayerAdapter.RegisterPlayerCustomViewHolder>{

    private ArrayList<String> nameListArray;
    private ArrayList<String> specListArray;
    private ArrayList<String> rollListArray;
    private ArrayList<String> imageListArray;

    Context context ;

    public RegisterPlayerAdapter(Context context, ArrayList<String> name, ArrayList<String> spec, ArrayList<String> roll, ArrayList<String> image){
        nameListArray = name;
        specListArray = spec;
        rollListArray = roll;
        imageListArray = image;
        this.context = context;
    }

    @Override
    public RegisterPlayerCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.register_player_list,parent,false);
        return new RegisterPlayerCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RegisterPlayerCustomViewHolder holder, int position) {
        String nameStr = nameListArray.get(position);
        String specStr = specListArray.get(position);
        String imageStr = imageListArray.get(position);

        holder.name.setText(nameStr);
        holder.roll.setText(rollListArray.get(position));
        holder.spec.setText(specStr);

        Glide.with(context).load(imageStr).into(holder.profileImage);
    }

    @Override
    public int getItemCount() {
        return nameListArray.size();
    }

    public class RegisterPlayerCustomViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView spec;
        private TextView roll;

        private ImageView profileImage;

        public RegisterPlayerCustomViewHolder(View itemView) {

            super(itemView);

            name = (TextView)itemView.findViewById(R.id.RegisterPlayerName);
            spec = (TextView)itemView.findViewById(R.id.RegisterPlayerSpec);
            roll = (TextView)itemView.findViewById(R.id.RegisterPlayerRoll);

            profileImage = (ImageView)itemView.findViewById(R.id.RegisterPlayerImage);
        }
    }
}
