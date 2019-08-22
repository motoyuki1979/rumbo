package com.wa.rumbo.fragments;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
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
import com.wa.rumbo.adapters.Notice3_Adapter;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.interfaces.Register_Interfac;
import com.wa.rumbo.model.Notice3_model;
import com.wa.rumbo.model.NotificationRespList;
import com.wa.rumbo.model.NotificationResponse;

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

public class Fragment_3Notice extends Fragment {

    @BindView(R.id.rv_3notice)
    RecyclerView rv_3notice;

    String date,time, title;
    Notice3_model notice3_model;
    Notice3_Adapter notice3_adapter;
    List<Notice3_model> list_note = new ArrayList<>();

    CommonData commonData;
    Retrofit retrofit = RetrofitInstance.getClient();
    Register_Interfac register_interfac = retrofit.create(Register_Interfac.class);
    NotificationResponse notificationResponse;
    List<NotificationRespList> getNotificationData = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_3notice, container, false);
        ButterKnife.bind(this, view);

        MainActivity.homeTabsLL.setVisibility(View.VISIBLE);
        ((MainActivity)getActivity()).getSelectedTab(0);
        ((MainActivity) getActivity()).getBottomSelectedTabs(0);

        commonData = new CommonData(getActivity());



        rv_3notice.setLayoutManager(new LinearLayoutManager(getActivity()));

        getNotificationApi();



       /* for (int i=0;i<7;i++) {
            notice3_model= new Notice3_model("Alpha","10/12/2018","24:00");
            list_note.add(notice3_model);
        }*/

        return view;
    }

    public void getNotificationApi()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();

        Log.e("notification_resp",commonData.getString(USER_ID));
        Log.e("notification_resp",commonData.getString(TOKEN));


        Call call = register_interfac.getNotifications(commonData.getString(USER_ID), commonData.getString(TOKEN));

        call.enqueue(new Callback(){

            @Override
            public void onResponse(Call call, Response response) {
                Log.e("notification_resp", response.raw() + "");

                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();

                    Log.e("notification_resp_succ", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());

                    Log.e("notification_resp1",resp);

                    //getAllPosts = new Gson().fromJson(resp, GetAllPost.class);
                    notificationResponse=new Gson().fromJson(resp,NotificationResponse.class);

                    getNotificationData= notificationResponse.getNotificationRespLists();
                    // getAllPost_data = getAllPosts.getObject();

                    notice3_adapter = new Notice3_Adapter(getActivity(),getNotificationData);
                    rv_3notice.setAdapter(notice3_adapter);

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
