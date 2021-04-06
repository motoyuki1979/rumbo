package com.wa.rumbo.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wa.rumbo.R;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.fragments.Fragment_ChatWrite;
import com.wa.rumbo.fragments.Other_User_Profile_Fragment;
import com.wa.rumbo.model.Notice3_model;
import com.wa.rumbo.model.NotificationRespList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Notice3_Adapter extends RecyclerView.Adapter<Notice3_Adapter.MyViewHolder> {

   // List<String> list_notice= new ArrayList<>();
    Context context;
    List<Notice3_model> list_notice;
    String date,time, title;
    CommonData commonData;
    List<NotificationRespList> getAllNotificationData;

    public Notice3_Adapter(Context context,   List<NotificationRespList> getAllNotificationData) {
        this.context = context;
        this.getAllNotificationData=getAllNotificationData;
        commonData = new CommonData(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_notice, viewGroup, false);
        return new Notice3_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int position) {

        //Notice3_model notice3_model= new Notice3_model(title, date,time);
       // myViewHolder.tv_time.setText(getAllNotificationData.get(position).getDatetime());


        myViewHolder.tv_top.setText(getAllNotificationData.get(position).getMessage());

        myViewHolder.tv_time.setText(UsefullData.getDateTimeFromMills(getAllNotificationData.get(position).getDatetime()));

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getAllNotificationData.get(position).getType().equals("followed")){


                    String TYPE = getAllNotificationData.get(position).getType();
                    String POST_ID = getAllNotificationData.get(position).getPost_id();
                    String FROM_USER_ID= getAllNotificationData.get(position).getFromUserId();
                    Activity activity = (Activity) v.getContext();
                    Fragment myFragment = new Other_User_Profile_Fragment();
                    FragmentManager fragmentManager = activity.getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    Bundle bundle = new Bundle();
                    bundle.putString("post_id", POST_ID); //key and value
                    bundle.putString("from_user_id", FROM_USER_ID); //key and value
                    bundle.putString("comment_id", getAllNotificationData.get(position).getCommentId()); //key and value

                    myFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.frameLayout, myFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();





                }

                else if (getAllNotificationData.get(position).getType().equals("comment")) {


                    String TYPE = getAllNotificationData.get(position).getType();
                    String POST_ID = getAllNotificationData.get(position).getPost_id();
                    Activity activity = (Activity) v.getContext();
                    Fragment myFragment = new Fragment_ChatWrite();
                    FragmentManager fragmentManager = activity.getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    Bundle bundle = new Bundle();
                    bundle.putString("post_id", getAllNotificationData.get(position).getPost_id()); //key and value
                    bundle.putString("comment_id", getAllNotificationData.get(position).getCommentId()); //key and value

                    myFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.frameLayout, myFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }

              /*
                Activity activity = (Activity) v.getContext();
                Fragment myFragment = new Fragment_ChatWrite();
                FragmentManager fragmentManager = activity.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("data", new Gson().toJson(getAllPost_dataList.get(i))); //key and value

                myFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frameLayout, myFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
               */

            }
        });
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
        return getAllNotificationData.size();
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
