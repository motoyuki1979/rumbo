package com.wa.rumbo.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.wa.rumbo.R;
import com.wa.rumbo.activities.MainActivity;
import com.wa.rumbo.adapters.Community_Adapter;
import com.wa.rumbo.interfaces.FragmentReplac;
import com.wa.rumbo.model.Community_Model;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommunityFragment extends Fragment {

    List<Community_Model> list_community;

    Community_Adapter community_adapter;
    Community_Model community_model;

    @BindView(R.id.rv_community)
    RecyclerView rv_community;

    @BindView(R.id.tv_bulletin_board)
    TextView tv_bulletin_board;

    @BindView(R.id.notifications)
    TextView notificationTab;
    @BindView(R.id.new_arrival)
    TextView newArrivalTab;
    @BindView(R.id.followings)
    TextView followTab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_community2, container, false);
        MainActivity.homeTabsLL.setVisibility(View.GONE);
        ButterKnife.bind(this, view);

        list_community= new ArrayList<>();

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        rv_community.setLayoutManager(layoutManager);

        community_adapter = new Community_Adapter(getActivity(), new FragmentReplac() {
            @Override
            public void clicked() {
                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, new Work_Saving_Money_Fragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        rv_community.setAdapter(community_adapter);

        /*
         // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        int numberOfColumns = 6;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new MyRecyclerViewAdapter(this, data);
        adapter.setClickListener(this);
         */



        for (int i=0;i<7;i++) {

            //community_model= new Community_Model("Alpha","10/12/2018","24:00");
            list_community.add(community_model);
        }


////////////////////////////////////

        tv_bulletin_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context;
                Activity activity;

                //two parameters for full width dialog box and single for marginable automatically
                final Dialog dlg =new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);

                dlg.setContentView(R.layout.dialog_community);

                Window window = dlg.getWindow();
                window.setGravity(Gravity.CENTER);

                window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                WindowManager.LayoutParams wlp = window.getAttributes();

                wlp.gravity = Gravity.BOTTOM;
                wlp.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);
                dlg.setCancelable(false);
                dlg.show();;


                ImageView img_down_dialog= dlg.findViewById(R.id.img_down_dialog);
                 final TextView tv_bullet_board= dlg.findViewById(R.id.tv_bullet_board) ;
                 final TextView tv_new_arrival_order= dlg.findViewById(R.id.tv_new_arrival_order) ;
                 final TextView tv_popularity_order= dlg.findViewById(R.id.tv_popularity_order) ;



                 img_down_dialog.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         dlg.dismiss();
                     }
                 });

                tv_bullet_board.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        //timeline_IV.setImageDrawable(getResources().getDrawable(R.mipmap.star_color));
                        tv_bullet_board.setTextColor(getResources().getColor(R.color.white));
                        tv_bullet_board.setBackground(getResources().getDrawable(R.drawable.tab_select_bg));

                        tv_new_arrival_order.setTextColor(getResources().getColor(R.color.black));
                        tv_new_arrival_order.setBackground(getResources().getDrawable(R.drawable.heart_white_bg));

                        tv_popularity_order.setTextColor(getResources().getColor(R.color.black));
                        tv_popularity_order.setBackground(getResources().getDrawable(R.drawable.heart_white_bg));


                        FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameLayout, new CommunityFragment());
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });


                tv_new_arrival_order.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        tv_bullet_board.setTextColor(getResources().getColor(R.color.black));
                        tv_bullet_board.setBackground(getResources().getDrawable(R.drawable.heart_white_bg));

                        tv_new_arrival_order.setTextColor(getResources().getColor(R.color.white));
                        tv_new_arrival_order.setBackground(getResources().getDrawable(R.drawable.tab_select_bg));

                        tv_popularity_order.setTextColor(getResources().getColor(R.color.black));
                        tv_popularity_order.setBackground(getResources().getDrawable(R.drawable.heart_white_bg));


                        FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameLayout, new New_Arrival_Order_Fragment());
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    }
                });

                tv_popularity_order.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        tv_bullet_board.setTextColor(getResources().getColor(R.color.black));
                        tv_bullet_board.setBackground(getResources().getDrawable(R.drawable.heart_white_bg));

                        tv_new_arrival_order.setTextColor(getResources().getColor(R.color.black));
                        tv_new_arrival_order.setBackground(getResources().getDrawable(R.drawable.heart_white_bg));

                        tv_popularity_order.setTextColor(getResources().getColor(R.color.white));
                        tv_popularity_order.setBackground(getResources().getDrawable(R.drawable.tab_select_bg));
                    }
                });

                 /*
                  View dialogView = inflater.inflate(R.layout.dialog_community, null);
                dialogBuilder.setView(dialogView);
                  */
                ///////////////////////////
              //////////////////////////////////////

             /*   final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);

                // Setting dialogview
                Window window = dialog.getWindow();
//                window.setGravity(Gravity.CENTER);

                window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);

                WindowManager.LayoutParams wlp = window.getAttributes();

                wlp.gravity = Gravity.CENTER;
                dialog.setTitle(null);
                dialog.setContentView(R.layout.dialog_community);
                dialog.setCancelable(true);

                dialog.show();*/

              /*  AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
// ...Irrelevant code for customizing the buttons and title
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_community, null);
                dialogBuilder.setView(dialogView);

               *//* EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
                editText.setText("test label");*//*
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();*/


             /*   Dialog dlg =new Dialog(getActivity());

                Window window = dlg.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();

                wlp.gravity = Gravity.BOTTOM;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);*/


                // final Dialog dialog = new Dialog(CustomDialog.this);
                //                // Include dialog.xml file
                //                dialog.setContentView(R.layout.dialog);
                //                // Set dialog title
                //                dialog.setTitle("Custom Dialog");
            }
        });




        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick(R.id.notifications)
    public void setNotificationTab() {
        notificationTab.setTextColor(getResources().getColor(R.color.white));
        notificationTab.setBackground(getResources().getDrawable(R.drawable.tab_select_bg));

        Fragment fragment = new CommunityFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment).addToBackStack(null);
        ft.commit();

        newArrivalTab.setTextColor(getResources().getColor(R.color.tab_text_color));
        newArrivalTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));

        followTab.setTextColor(getResources().getColor(R.color.tab_text_color));
        followTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick(R.id.new_arrival)
    public void setNewArrivalTab() {
        Fragment fragment = new CommunityFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment).addToBackStack(null);
        ft.commit();


        newArrivalTab.setTextColor(getResources().getColor(R.color.white));
        newArrivalTab.setBackground(getResources().getDrawable(R.drawable.tab_select_bg));

        notificationTab.setTextColor(getResources().getColor(R.color.tab_text_color));
        notificationTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));

        followTab.setTextColor(getResources().getColor(R.color.tab_text_color));
        followTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick(R.id.followings)
    public void setFollowTab() {
        Fragment fragment = new CommunityFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment).addToBackStack(null);
        ft.commit();

        followTab.setTextColor(getResources().getColor(R.color.white));
        followTab.setBackground(getResources().getDrawable(R.drawable.tab_select_bg));

        notificationTab.setTextColor(getResources().getColor(R.color.tab_text_color));
        notificationTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));

        newArrivalTab.setTextColor(getResources().getColor(R.color.tab_text_color));
        newArrivalTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));
    }



}
