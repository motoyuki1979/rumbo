package com.wa.rumbo.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wa.rumbo.Api;
import com.wa.rumbo.R;
import com.wa.rumbo.RetrofitInstance;
import com.wa.rumbo.activities.MainActivity;
import com.wa.rumbo.adapters.NewArrivalAdapter;
import com.wa.rumbo.callbacks.GetFollowersCallback;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.interfaces.Register_Interfac;
import com.wa.rumbo.model.GetAllPost;
import com.wa.rumbo.model.GetAllPost_Data;
import com.wa.rumbo.model.GetFollowersModel;

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

public class FollowingFragment extends Fragment {

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
    List<GetAllPost_Data> getFollowsPost_data = new ArrayList<>();
    List<String> followList = new ArrayList<>();
Dialog mDialog;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_arrival, container, false);
        ButterKnife.bind(this, view);

        ((MainActivity)getActivity()).getSelectedTab(2);
        ((MainActivity) getActivity()).getBottomSelectedTabs(2);
        commonData = new CommonData(getActivity());
        UsefullData.setLocale(getActivity());
        mDialog = UsefullData.getProgressDialog(getActivity());
        //timeline_RL

        //MainActivity.timeline_RL.setBackgroundResource(R.drawable.tab_select_bg);

        MainActivity.homeTabsLL.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Arrival_recyclerView.setLayoutManager(layoutManager);

        new Api(getActivity()).getFollowsApi(getActivity(), true, new GetFollowersCallback() {
            @Override
            public void onResponse(GetFollowersModel model) {
                for (int i = 0; i< model.getObject().size(); i++){
                    followList.add(model.getObject().get(i).getFollowerId());
                }
                getAllPostsAPI();
            }

            @Override
            public void onFailure() {
                Arrival_recyclerView.setVisibility(View.GONE);
                tvNoDataFound.setVisibility(View.VISIBLE);
            }
        });



        return view;
    }


    public void getAllPostsAPI() {

        mDialog.show();

        Log.e("All post => ", commonData.getString(USER_ID) +"\naut => "+commonData.getString(TOKEN));
        Call call = register_interfac.allPostGet(commonData.getString(USER_ID), commonData.getString(TOKEN));

        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {

                Log.e("new arrival resp == ", response.raw() + "");

                if (response.isSuccessful() && response.body() != null) {
                    mDialog.dismiss();

                    Log.e("Success_post", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());

                    getAllPosts = new Gson().fromJson(resp, GetAllPost.class);
                    getAllPost_data = getAllPosts.getObject();

                    for (int i=0; i< getAllPost_data.size(); i++){
                        if (followList.contains(getAllPost_data.get(i).getUserId())){
                            getFollowsPost_data.add(getAllPost_data.get(i));
                        }
                    }
                    Log.e("Main Follow list size ==>  ", getFollowsPost_data.size()+"" );

                    if(getFollowsPost_data.size()>0) {
                        Arrival_recyclerView.setVisibility(View.VISIBLE);
                        tvNoDataFound.setVisibility(View.GONE);

                        arrivalAdapter = new NewArrivalAdapter(getActivity(), getActivity(), getFollowsPost_data);
                        Arrival_recyclerView.setAdapter(arrivalAdapter);
                    }else {
                        Arrival_recyclerView.setVisibility(View.GONE);
                        tvNoDataFound.setVisibility(View.VISIBLE);
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




