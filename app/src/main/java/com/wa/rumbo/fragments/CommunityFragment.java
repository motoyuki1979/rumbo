package com.wa.rumbo.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wa.rumbo.R;
import com.wa.rumbo.RetrofitInstance;
import com.wa.rumbo.activities.MainActivity;
import com.wa.rumbo.adapters.CategoriesAdapter;
import com.wa.rumbo.adapters.Community_Adapter;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.interfaces.Category_Interf;
import com.wa.rumbo.interfaces.FragmentReplac;
import com.wa.rumbo.interfaces.Register_Interfac;
import com.wa.rumbo.model.CategoryResponse;
import com.wa.rumbo.model.Category_Data;
import com.wa.rumbo.model.Community_Model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.wa.rumbo.common.ConstantValue.TOKEN;
import static com.wa.rumbo.common.ConstantValue.USER_ID;

public class CommunityFragment extends Fragment {

    List<Community_Model> list_community;

    Community_Adapter community_adapter;
    Community_Model community_model;

    @BindView(R.id.rv_community)
    RecyclerView rv_community;

    @BindView(R.id.tv_bulletin_board)
    TextView tv_bulletin_board;

    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tv_chat)
    TextView tvChat;
    @BindView(R.id.tv_ac_review)
    TextView tvAcReview;
    List<Category_Data> categoryList;
    Register_Interfac register_interfac;
    Retrofit retrofit;
    CommonData commonData;
    CategoriesAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_community2, container, false);
        MainActivity.homeTabsLL.setVisibility(View.GONE);
        ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).getBottomSelectedTabs(1);
        list_community = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        rv_community.setLayoutManager(layoutManager);

        //   MainActivity.community_RL.setBackgroundResource(R.drawable.tab_select_bg);

        tvChat.setTextColor(getResources().getColor(R.color.white));
        tvChat.setBackground(getResources().getDrawable(R.drawable.tab_select_bg));

        tvRecommend.setTextColor(getResources().getColor(R.color.tab_text_color));
        tvRecommend.setBackgroundColor(getResources().getColor(R.color.tab_unselected));

        tvAcReview.setTextColor(getResources().getColor(R.color.tab_text_color));
        tvAcReview.setBackgroundColor(getResources().getColor(R.color.tab_unselected));

        /*community_adapter = new Community_Adapter(getActivity(), new FragmentReplac() {
            @Override
            public void clicked() {
                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, new Work_Saving_Money_Fragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        rv_community.setAdapter(community_adapter);*/


        retrofit = RetrofitInstance.getClient();
        register_interfac = retrofit.create(Register_Interfac.class);
        commonData = new CommonData(getActivity());

        getCategoryListAPI();

        ////////////////////////////////////

        tv_bulletin_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context;
                Activity activity;

                //two parameters for full width dialog box and single for marginable automatically
                final Dialog dlg = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);

                dlg.setContentView(R.layout.dialog_community);

                Window window = dlg.getWindow();
                window.setGravity(Gravity.CENTER);

                window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                WindowManager.LayoutParams wlp = window.getAttributes();

                wlp.gravity = Gravity.BOTTOM;
                wlp.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);
                dlg.setCancelable(false);
                dlg.show();

                ImageView img_down_dialog = dlg.findViewById(R.id.img_down_dialog);
                final TextView tv_bullet_board = dlg.findViewById(R.id.tv_bullet_board);
                final TextView tv_new_arrival_order = dlg.findViewById(R.id.tv_new_arrival_order);
                final TextView tv_popularity_order = dlg.findViewById(R.id.tv_popularity_order);


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

               /* tvRecommend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                tvChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                tvAcReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });*/

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
    @OnClick(R.id.tv_recommend)
    public void setNotificationTab() {
        tvRecommend.setTextColor(getResources().getColor(R.color.white));
        tvRecommend.setBackground(getResources().getDrawable(R.drawable.tab_select_bg));

        tvChat.setTextColor(getResources().getColor(R.color.tab_text_color));
        tvChat.setBackgroundColor(getResources().getColor(R.color.tab_unselected));

        tvAcReview.setTextColor(getResources().getColor(R.color.tab_text_color));
        tvAcReview.setBackgroundColor(getResources().getColor(R.color.tab_unselected));

        Fragment fragment = new CommunityFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment).addToBackStack(null);
        ft.commit();


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick(R.id.tv_chat)
    public void setNewArrivalTab() {

        tvChat.setTextColor(getResources().getColor(R.color.white));
        tvChat.setBackground(getResources().getDrawable(R.drawable.tab_select_bg));

        tvRecommend.setTextColor(getResources().getColor(R.color.tab_text_color));
        tvRecommend.setBackgroundColor(getResources().getColor(R.color.tab_unselected));

        tvAcReview.setTextColor(getResources().getColor(R.color.tab_text_color));
        tvAcReview.setBackgroundColor(getResources().getColor(R.color.tab_unselected));
        Fragment fragment = new CommunityFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment).addToBackStack(null);
        ft.commit();


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick(R.id.tv_ac_review)
    public void setFollowTab() {

        tvAcReview.setTextColor(getResources().getColor(R.color.white));
        tvAcReview.setBackground(getResources().getDrawable(R.drawable.tab_select_bg));

        tvRecommend.setTextColor(getResources().getColor(R.color.tab_text_color));
        tvRecommend.setBackgroundColor(getResources().getColor(R.color.tab_unselected));

        tvChat.setTextColor(getResources().getColor(R.color.tab_text_color));
        tvChat.setBackgroundColor(getResources().getColor(R.color.tab_unselected));


        Fragment fragment = new CommunityFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment).addToBackStack(null);
        ft.commit();


    }


    public void getCategoryListAPI() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();

        Call call = register_interfac.category_list(commonData.getString(USER_ID), commonData.getString(TOKEN));

        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {

                progressDialog.dismiss();
                Log.e("new arrival resp == ", response.raw() + "");
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("Success_post", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());

                    CategoryResponse categoryResponse = new Gson().fromJson(resp, CategoryResponse.class);
                    Type listType = new TypeToken<List<Category_Data>>() {
                    }.getType();

                    categoryList = categoryResponse.getObject(); //new Gson().fromJson(categoryResponse.toString(), listType);

                    adapter = new CategoriesAdapter(getActivity(), categoryList, new Category_Interf() {
                        @Override
                        public void cat_data(String catgry_id, String catgry_name) {

                        }
                    });
                    rv_community.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDialog.dismiss();
                Log.e("onFailure >>>>", "" + t.getMessage());

            }
        });

        Log.e("API", "========== ========");
    }

       /* Call call = register_interfac.posts();

        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {

                Log.e("data","========== "+response);
               Log.e("data","========== "+response.body());
            }

            @Override
            public void onFailure(Call call, Throwable t) {
               // progressDialog.dismiss();
                Log.e("onFailure >>>>", "" + t.getMessage());

            }
        });
    }*/


}
