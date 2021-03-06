package com.wa.rumbo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wa.rumbo.R;

public class Fragment_Other_Adapter extends RecyclerView.Adapter<Fragment_Other_Adapter.MyViewHolder> {

    Context context;

    public Fragment_Other_Adapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_other1_adapter, viewGroup, false);
        return new Fragment_Other_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MyViewHolder myViewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_other_adapter;
        public MyViewHolder( View itemView) {
            super(itemView);

            tv_other_adapter= itemView.findViewById(R.id.tv_other_adapter);
        }
    }
}
