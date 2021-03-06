package com.wa.rumbo.fragments;

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
import com.google.gson.Gson;
import com.wa.rumbo.R;
import com.wa.rumbo.RetrofitInstance;
import com.wa.rumbo.activities.MainActivity;
import com.wa.rumbo.adapters.NewArrivalAdapter;
import com.wa.rumbo.common.CommonData;
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

public class NewArrivalFragment extends Fragment {

    @BindView(R.id.new_arrival_rv)
    RecyclerView Arrival_recyclerView;

    NewArrivalAdapter arrivalAdapter;

    Retrofit retrofit = RetrofitInstance.getClient();
    Register_Interfac register_interfac = retrofit.create(Register_Interfac.class);

    CommonData commonData;
    GetAllPost getAllPosts;
    List<GetAllPost_Data> getAllPost_data = new ArrayList<>();



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_arrival, container, false);
        ButterKnife.bind(this, view);

        ((MainActivity)getActivity()).getSelectedTab(1);

        ((MainActivity) getActivity()).getBottomSelectedTabs(0);

//getBottomSelectedTabs(int type)
        commonData = new CommonData(getActivity());


        //timeline_RL

        //MainActivity.timeline_RL.setBackgroundResource(R.drawable.tab_select_bg);

        MainActivity.homeTabsLL.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Arrival_recyclerView.setLayoutManager(layoutManager);

        getAllPostsAPI();

        return view;
    }


    public void getAllPostsAPI() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();

        Call call = register_interfac.allPostGet(commonData.getString(USER_ID), commonData.getString(TOKEN));

        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {

                Log.e("new arrival resp == ", response.raw() + "");

                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();

                    Log.e("Success_post", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());

                    getAllPosts = new Gson().fromJson(resp, GetAllPost.class);
                    getAllPost_data = getAllPosts.getObject();

                    arrivalAdapter = new NewArrivalAdapter(getActivity(), getAllPost_data);
                    Arrival_recyclerView.setAdapter(arrivalAdapter);

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDialog.dismiss();
                Log.e("onFailure >>>>", "" + t.getMessage());

            }
        });
    }





    }




