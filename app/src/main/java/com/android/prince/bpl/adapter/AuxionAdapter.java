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
 * Created by prince on 10/3/18.
 */

public class AuxionAdapter extends RecyclerView.Adapter<AuxionAdapter.AuxionCustomViewHolder> {

    private ArrayList<String> nameListArray;
    private ArrayList<String> specListArray;
    private ArrayList<String> priceListArray;
    private ArrayList<String> imageListArray;

    Context context ;

    public AuxionAdapter(Context context,ArrayList<String> name,ArrayList<String> spec,ArrayList<String> price,ArrayList<String> image){
        nameListArray = name;
        specListArray = spec;
        priceListArray = price;
        imageListArray = image;
        this.context = context;
    }


    @Override
    public AuxionCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.auxion_list_item,parent,false);

        return new AuxionCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AuxionCustomViewHolder holder, int position) {

        String nameStr = nameListArray.get(position);
        String specStr = specListArray.get(position);
        String priceStr = priceListArray.get(position);
        String imageStr = imageListArray.get(position);

            holder.name.setText(nameStr);
            holder.price.setText("Base Price : "+priceStr);
            holder.spec.setText(specStr);

            Glide.with(context).load(imageStr).into(holder.profileImage);

    }

    @Override
    public int getItemCount() {
        return nameListArray.size();
    }

    public class AuxionCustomViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView spec;
        private TextView price;

        private ImageView profileImage;

        public AuxionCustomViewHolder(View itemView) {

            super(itemView);

            name = (TextView)itemView.findViewById(R.id.AuxionListName);
            spec = (TextView)itemView.findViewById(R.id.AuxionListSpec);
            price = (TextView)itemView.findViewById(R.id.AuxionListPrice);

            profileImage = (ImageView)itemView.findViewById(R.id.AuxionListImage);
        }
    }
}
