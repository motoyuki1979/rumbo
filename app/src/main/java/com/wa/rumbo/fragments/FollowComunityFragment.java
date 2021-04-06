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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wa.rumbo.Api;
import com.wa.rumbo.R;
import com.wa.rumbo.RetrofitInstance;
import com.wa.rumbo.activities.MainActivity;
import com.wa.rumbo.adapters.Community_Adapter;
import com.wa.rumbo.adapters.GetComunityCommentsAdapter;
import com.wa.rumbo.adapters.NewArrivalAdapter;
import com.wa.rumbo.callbacks.AddComunityCommentCallback;
import com.wa.rumbo.callbacks.GetComunityCommentsCallback;
import com.wa.rumbo.callbacks.GetFollowersCallback;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.ConstantValue;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.interfaces.Register_Interfac;
import com.wa.rumbo.model.Category_Data;
import com.wa.rumbo.model.CommentPostModel;
import com.wa.rumbo.model.Community_Model;
import com.wa.rumbo.model.GetComunityComents;
import com.wa.rumbo.model.GetFollowersModel;
import com.wa.rumbo.utils;

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

public class FollowComunityFragment extends Fragment {

    // List<Community_Model> list_community;
    List<GetComunityComents.Object> list_community;
    List<GetComunityComents.Object> filterListData = new ArrayList<>();

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
    @BindView(R.id.tvSend)
    TextView tvSend;
    @BindView(R.id.edt_comment_write)
    TextView etComment;
    @BindView(R.id.tv_ac_review)
    TextView tvAcReview;
    @BindView(R.id.tvNoDataFound)
    TextView tvNoDataFound;
    List<Category_Data> categoryList;
    Register_Interfac register_interfac;
    Retrofit retrofit;
    CommonData commonData;
    // CategoriesAdapter adapter;
    GetComunityCommentsAdapter adapter;
    Dialog mDialog;
    GetComunityComents getComunityComents;

    @BindView(R.id.keyboard_LL)
    public RelativeLayout rlKeyboard;
    Boolean isComment = false;
    Animation clickAnimation;
    ArrayList<String> followList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_community2, container, false);
        MainActivity.homeTabsLL.setVisibility(View.GONE);
        ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).getBottomSelectedTabs(1);
        clickAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.grow);
        if (getArguments() != null && getArguments().getString("isFromBottomTab") != null && getArguments().getString("isFromBottomTab").equalsIgnoreCase("true")) {
            isComment = true;
        } else {
            isComment = false;
        }

        if (isComment) {
            rlKeyboard.setVisibility(View.VISIBLE);
        } else {
            rlKeyboard.setVisibility(View.GONE);
        }
        UsefullData.setLocale(getActivity());
        mDialog = UsefullData.getProgressDialog(getActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_community.setLayoutManager(layoutManager);

        ((MainActivity) getActivity()).btnBooking.setVisibility(View.GONE);
        list_community = new ArrayList<>();

        //   MainActivity.community_RL.setBackgroundResource(R.drawable.tab_select_bg);

        tvAcReview.setTextColor(getResources().getColor(R.color.white));
        tvAcReview.setBackground(getResources().getDrawable(R.drawable.tab_select_bg));

        tvRecommend.setTextColor(getResources().getColor(R.color.tab_text_color));
        tvRecommend.setBackgroundColor(getResources().getColor(R.color.tab_unselected));

        tvChat.setTextColor(getResources().getColor(R.color.tab_text_color));
        tvChat.setBackgroundColor(getResources().getColor(R.color.tab_unselected));

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

        if (commonData.getString(ConstantValue.USER_ID) != null) {
            Log.e("userr CommunityFragment", commonData.getString(ConstantValue.USER_ID));
        }
        // getCategoryListAPI();


        ////////////////////////////////////

        tv_bulletin_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context;
                Activity activity;
                v.startAnimation(clickAnimation);
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
                        v.startAnimation(clickAnimation);
                        dlg.dismiss();
                    }
                });

                tv_bullet_board.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        //timeline_IV.setImageDrawable(getResources().getDrawable(R.mipmap.star_color));
                        v.startAnimation(clickAnimation);
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
                        v.startAnimation(clickAnimation);
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
                        v.startAnimation(clickAnimation);
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


        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commonData.getString(USER_ID) != null && !commonData.getString(USER_ID).equals("")) {
                    if (etComment.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_write_a_comment), Toast.LENGTH_SHORT).show();
                    } else {
                        new Api(getActivity()).addCommentInCategory(getActivity(), etComment.getText().toString(), new AddComunityCommentCallback() {
                            @Override
                            public void onResponse(CommentPostModel model) {
                                etComment.setText("");
                                new Api(getActivity()).getComunityComment(new GetComunityCommentsCallback() {
                                    @Override
                                    public void onResponse(GetComunityComents model) {
                                        list_community.clear();
                                        for (int i = 0; i < model.getObject().size(); i++) {
                                            list_community.add(model.getObject().get(i));
                                        }

                                        adapter = new GetComunityCommentsAdapter(getActivity(), getActivity(), list_community);
                                        rv_community.setAdapter(adapter);
                                    }
                                });
                            }
                        });


                    }
                }else {
                    utils.showRegisterDialog(getActivity());
                }
            }
        });


        new Api(getActivity()).getFollowsApi(getActivity(), true, new GetFollowersCallback() {
            @Override
            public void onResponse(GetFollowersModel model) {
                for (int i = 0; i< model.getObject().size(); i++){
                    followList.add(model.getObject().get(i).getFollowerId());
                }
                new Api(getActivity()).getComunityComment(new GetComunityCommentsCallback() {
                    @Override
                    public void onResponse(GetComunityComents model) {
                        list_community.clear();
                        list_community = model.getObject(); //new Gson().fromJson(categoryResponse.toString(), listType);
                        Log.e("Main list size ==>  ", list_community.size()+"" );

                        for (int i=0; i< list_community.size(); i++){
                            if (followList.contains(list_community.get(i).getCommentUserId())){
                                filterListData.add(list_community.get(i));
                            }
                        }
                        Log.e("Main Follow list size ==>  ", filterListData.size()+"" );

                        if(filterListData.size()>0) {
                            rv_community.setVisibility(View.VISIBLE);
                            tvNoDataFound.setVisibility(View.GONE);

                            adapter = new GetComunityCommentsAdapter(getActivity(),getActivity(), filterListData);
                            rv_community.setAdapter(adapter);
                        }else {
                            rv_community.setVisibility(View.GONE);
                            tvNoDataFound.setVisibility(View.VISIBLE);
                        }

                    }
                });
            }

            @Override
            public void onFailure() {
                rv_community.setVisibility(View.GONE);
                tvNoDataFound.setVisibility(View.VISIBLE);
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

        Fragment fragment = new MineComunityFragment();
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


        Fragment fragment = new FollowComunityFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment).addToBackStack(null);
        ft.commit();


    }


    public void getCategoryListAPI() {

       /* final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();*/
        mDialog.show();

        Log.e("userId : ", commonData.getString(USER_ID) + " token: " + commonData.getString(TOKEN));

        Call call = register_interfac.category_list(commonData.getString(USER_ID), commonData.getString(TOKEN));

        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {

                mDialog.dismiss();
                Log.e("new Comunity comments resp == ", response.raw() + "");
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("Success_post", new Gson().toJson(response.body()));

                    String resp = new Gson().toJson(response.body());


                    GetComunityComents categoryResponse = new Gson().fromJson(resp, GetComunityComents.class);
                    Type listType = new TypeToken<List<GetComunityComents>>() {
                    }.getType();

                    list_community.clear();
                    list_community = categoryResponse.getObject(); //new Gson().fromJson(categoryResponse.toString(), listType);

                    adapter = new GetComunityCommentsAdapter(getActivity(),getActivity(), list_community);
                    rv_community.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                mDialog.dismiss();
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

