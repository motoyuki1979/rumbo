package com.wa.rumbo.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wa.rumbo.R;
import com.wa.rumbo.RetrofitInstance;
import com.wa.rumbo.activities.MainActivity;
import com.wa.rumbo.adapters.NewArrivalAdapter;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.interfaces.Register_Interfac;
import com.wa.rumbo.model.GetAllPost;
import com.wa.rumbo.model.GetAllPost_Data;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.wa.rumbo.common.ConstantValue.TOKEN;
import static com.wa.rumbo.common.ConstantValue.USER_ID;

public class MyPostsFragment extends Fragment {

    @BindView(R.id.new_arrival_rv)
    RecyclerView Arrival_recyclerView;

   @BindView(R.id.tvNoDataFound)
   TextView tvNoDataFound;

    NewArrivalAdapter arrivalAdapter;

    Retrofit retrofit = RetrofitInstance.getClient();
    Register_Interfac register_interfac = retrofit.create(Register_Interfac.class);

    CommonData commonData;
    GetAllPost getAllPosts;
    List<GetAllPost_Data> getAllPost_data = new ArrayList<>();
    Dialog mDialog;
    Animation clickAnimation;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_arrival, container, false);
        ButterKnife.bind(this, view);
        clickAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.grow);
        ((MainActivity) getActivity()).getSelectedTab(0);
        ((MainActivity) getActivity()).btnBooking.setVisibility(View.VISIBLE);

        //  ((MainActivity) getActivity()).getBottomSelectedTabs(0);

//getBottomSelectedTabs(int type)
        commonData = new CommonData(getActivity());
        mDialog = UsefullData.getProgressDialog(getActivity());

        //timeline_RL

        //MainActivity.timeline_RL.setBackgroundResource(R.drawable.tab_select_bg);

        MainActivity.homeTabsLL.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Arrival_recyclerView.setLayoutManager(layoutManager);
        UsefullData.setLocale(getActivity());

        getAllPostsAPI();

        return view;
    }


    public void getAllPostsAPI() {

        mDialog.show();

        Log.e("url123", register_interfac.toString());
        Log.e("All post => ", commonData.getString(USER_ID) + "\naut => " + commonData.getString(TOKEN));

        Call call = register_interfac.allPostGet(commonData.getString(USER_ID), commonData.getString(TOKEN));

        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {

                Log.e("new arrival resp == ", response.raw() + "");

                if (response.isSuccessful() && response.body() != null) {
                    mDialog.dismiss();

                    Log.e("Success_post_mine", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());

                    getAllPosts = new Gson().fromJson(resp, GetAllPost.class);
                    getAllPost_data = getAllPosts.getObject();

                    List<GetAllPost_Data> getMyPost_data = new ArrayList<>();

                    Log.e("Main post list size ==>  ", getAllPost_data.size()+"" );

                    for (int i = 0; i< getAllPost_data.size();i++){
                       if (getAllPost_data.get(i).getUserId().equalsIgnoreCase(commonData.getString(USER_ID))){
                           getMyPost_data.add(getAllPost_data.get(i));
                       }
                   }
                    Log.e("Main My list size ==>  ", getMyPost_data.size()+"" );

                    if (getMyPost_data.size() >0){
                        tvNoDataFound.setVisibility(View.GONE);
                        Arrival_recyclerView.setVisibility(View.VISIBLE);
                        arrivalAdapter = new NewArrivalAdapter(getActivity(), getActivity(), getMyPost_data, new NewArrivalAdapter.OnBlockListner() {
                            @Override
                            public void onUserBlocked() {

                            }
                        });
                        Arrival_recyclerView.setAdapter(arrivalAdapter);
                    }else {
                        tvNoDataFound.setVisibility(View.VISIBLE);
                        Arrival_recyclerView.setVisibility(View.GONE);

                    }


                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                mDialog.dismiss();
                Log.e("onFailure >>>>", "" + t.getMessage());

            }
        });
    }


}