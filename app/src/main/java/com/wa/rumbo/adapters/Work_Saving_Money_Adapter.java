package com.wa.rumbo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wa.rumbo.R;

public class Work_Saving_Money_Adapter extends RecyclerView.Adapter<Work_Saving_Money_Adapter.MyViewHolder> {

    Context context;

    public Work_Saving_Money_Adapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.work_with_saving_money, viewGroup, false);
        return new Work_Saving_Money_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder( View itemView) {
            super(itemView);
        }
    }
}
