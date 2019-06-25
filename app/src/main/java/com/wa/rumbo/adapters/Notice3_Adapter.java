package com.wa.rumbo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wa.rumbo.R;
import com.wa.rumbo.model.Notice3_model;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Notice3_Adapter extends RecyclerView.Adapter<Notice3_Adapter.MyViewHolder> {

   // List<String> list_notice= new ArrayList<>();

    Context context;
    List<Notice3_model> list_notice;
    String date,time, title;

    public Notice3_Adapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_notice, viewGroup, false);
        return new Notice3_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MyViewHolder myViewHolder, int position) {

        Notice3_model notice3_model= new Notice3_model(title, date,time);
        //holder.txt_Name.setText(placesList.get(position).get("place_name"));
//        myViewHolder.tv_top.setText("abc"/*list_notice.get(position).getTitle()*/);
//        myViewHolder.tv_date.setText("def"/*list_notice.get(position).getTitle()*/);
//        myViewHolder.tv_time.setText("ghi"/*list_notice.get(position).getTitle()*/);

       /* myViewHolder.tv_top.setText(notice3_model.getTitle());
        myViewHolder.tv_date.setText(notice3_model.getDate());
        myViewHolder.tv_time.setText(notice3_model.getTime());*/


    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_top)
        TextView tv_top;

        @BindView(R.id.tv_date)
        TextView tv_date;

        @BindView(R.id.tv_time)
        TextView tv_time;
        public MyViewHolder( View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);




        }
    }
}
