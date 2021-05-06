package com.wa.rumbo.adapters;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wa.rumbo.R;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.model.GetCalenderBookingModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class EventsDetailsAdapter extends RecyclerView.Adapter<EventsDetailsAdapter.ViewHolder> {
    private Activity mActivity;
    ArrayList<GetCalenderBookingModel.Object> mList;

    public EventsDetailsAdapter(Activity mActivity, ArrayList<GetCalenderBookingModel.Object> mList) {
        this.mActivity = mActivity;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.event_row_item, viewGroup, false);
        return new EventsDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvItemAmount.setText(UsefullData.getCommaPrice(mActivity, mList.get(i).getAmount()));
        viewHolder.tvItemName.setText(mList.get(i).getCategoryTitle() + " . " + mList.get(i).getTitle());
        decodeBase64AndSetImage(mList.get(i).getCategoryImage(), viewHolder.ivEvent);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivEvent;
        TextView tvItemName;
        TextView tvItemAmount;

        public ViewHolder(View itemView) {
            super(itemView);

            ivEvent = itemView.findViewById(R.id.ivEvent);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemAmount = itemView.findViewById(R.id.tvItemAmount);
        }
    }

    private void decodeBase64AndSetImage(String completeImageData, ImageView imageView) {

        if (completeImageData != null) {
            Uri imageUri = Uri.parse(completeImageData);
            imageView.setImageURI(imageUri);
        }

    }
}
