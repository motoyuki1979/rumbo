package com.wa.rumbo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wa.rumbo.R;
import com.wa.rumbo.model.GetCalenderBookingModel;

import java.util.ArrayList;

public class Fragment_Other_Adapter extends RecyclerView.Adapter<Fragment_Other_Adapter.MyViewHolder> {

    Context context;
    ArrayList<GetCalenderBookingModel.Object> mFilterList;
    public Fragment_Other_Adapter(Context context,ArrayList<GetCalenderBookingModel.Object> mFilterList) {
        this.context = context;
        this.mFilterList = mFilterList;
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_other1_adapter, viewGroup, false);
        return new Fragment_Other_Adapter.MyViewHolder(view);
    }

    public void onBindViewHolder( MyViewHolder myViewHolder, int position) {
        myViewHolder.tvCategory.setText(mFilterList.get(position).getCategoryTitle());
        myViewHolder.tvPrice.setText(mFilterList.get(position).getAmount());

    }

    @Override
    public int getItemCount() {
        return mFilterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategory;
        TextView tvPrice;
        public MyViewHolder( View itemView) {
            super(itemView);

            tvCategory= itemView.findViewById(R.id.tvCategory);
            tvPrice= itemView.findViewById(R.id.tvPrice);
        }
    }
}
