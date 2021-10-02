package com.greymatter.sesmo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.greymatter.sesmo.Model.EarthQuake;
import com.greymatter.sesmo.R;

import java.util.ArrayList;

public class EarthQuakeAdapter extends RecyclerView.Adapter<EarthQuakeAdapter.ViewHolder>{
    ArrayList<EarthQuake> itemslist;
    Context context;

    public EarthQuakeAdapter(ArrayList<EarthQuake> itemslist, Context context) {
        this.itemslist = itemslist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.erathquake_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EarthQuake model = itemslist.get(position);

        holder.rating.setText(model.getRating());
        holder.radius.setText(model.getRadius());
        holder.time.setText(model.getTime());
        holder.area.setText(model.getLocation());


    }

    @Override
    public int getItemCount() {
        return itemslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView rating,time,radius,area;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rating = itemView.findViewById(R.id.rating);
            radius = itemView.findViewById(R.id.radius);
            time = itemView.findViewById(R.id.time);
            area = itemView.findViewById(R.id.area);
        }
    }
}
