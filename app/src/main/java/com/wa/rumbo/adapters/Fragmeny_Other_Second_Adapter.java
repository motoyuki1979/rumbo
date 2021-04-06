package com.wa.rumbo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wa.rumbo.R;

public  class Fragmeny_Other_Second_Adapter extends RecyclerView.Adapter<Fragmeny_Other_Second_Adapter.MyViewHolder> {

    Context context;

    public Fragmeny_Other_Second_Adapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_other2_adapter, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MyViewHolder myViewHolder, int i) {




    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_other2_adapter;
        public MyViewHolder( View itemView) {
            super(itemView);
            tv_other2_adapter= itemView.findViewById(R.id.tv_other2_adapter);


        }
    }
}
