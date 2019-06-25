package com.wa.rumbo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wa.rumbo.R;

public class MyPageFollow_Adapter extends RecyclerView.Adapter<MyPageFollow_Adapter.MYviewHolder> {

    Context context;

    public MyPageFollow_Adapter(Context context) {
        this.context = context;
    }

    @Override
    public MYviewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_follow_list_adapter, viewGroup, false);
        return new MyPageFollow_Adapter.MYviewHolder(view);
    }

    @Override
    public void onBindViewHolder( MYviewHolder mYviewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class MYviewHolder extends RecyclerView.ViewHolder {
        public MYviewHolder( View itemView) {
            super(itemView);
        }
    }
}
