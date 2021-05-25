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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.wa.rumbo.Api;
import com.wa.rumbo.R;
import com.wa.rumbo.RetrofitInstance;
import com.wa.rumbo.activities.MainActivity;
import com.wa.rumbo.adapters.NewArrivalAdapter;
import com.wa.rumbo.callbacks.GetBlockedUserCallback;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.interfaces.Register_Interfac;
import com.wa.rumbo.model.GetAllPost;
import com.wa.rumbo.model.GetAllPost_Data;
import com.wa.rumbo.model.GetBlockedListModel;

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
    List<GetAllPost_Data> filterPostList = new ArrayList<>();
    Dialog mDialog;
    Animation clickAnimation;
    public ArrayList<String> blockedUserList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_arrival, container, false);
        ButterKnife.bind(this, view);
        clickAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.grow);
        ((MainActivity) getActivity()).getSelectedTab(1);
        ((MainActivity) getActivity()).getBottomSelectedTabs(2);
        ((MainActivity) getActivity()).btnBooking.setVisibility(View.VISIBLE);

        //  ((MainActivity) getActivity()).getBottomSelectedTabs(0);

        //getBottomSelectedTabs(int type)

        commonData = new CommonData(getActivity());
        mDialog = UsefullData.getProgressDialog(getActivity());

        //timeline_RL

        //MainActivity.timeline_RL.setBackgroundResource(R.drawable.tab_select_bg);
        getBlockList();

        getAllPostsAPI();

        return view;
    }

    public void getBlockList() {
        blockedUserList.clear();
        new Api(getActivity()).geBlockedUserApi(getActivity(), new GetBlockedUserCallback() {
            @Override
            public void onSuccess(GetBlockedListModel model) {
                for (int i = 0; i < model.getObject().size(); i++) {
                    blockedUserList.add(model.getObject().get(i).getBlockedUserId());
                }
                getAllPostsAPI();

            }

            @Override
            public void onFailure() {
                getAllPostsAPI();

            }
        });
    }

    public void getAllPostsAPI() {
        MainActivity.homeTabsLL.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Arrival_recyclerView.setLayoutManager(layoutManager);
        UsefullData.setLocale(getActivity());

        mDialog.show();

        Log.e("url123", register_interfac.toString());
        Log.e("All post => ", commonData.getString(USER_ID) + "\naut => " + commonData.getString(TOKEN));

        Call call = register_interfac.allPostGet(commonData.getString(USER_ID), commonData.getString(TOKEN));

        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {

                Log.e("new arrival resp ==> ", response.raw() + "");

                if (response.isSuccessful() && response.body() != null) {
                    mDialog.dismiss();

                    Log.e("Success_post", new Gson().toJson(response.body()));

                    String resp = new Gson().toJson(response.body());

                    getAllPosts = new Gson().fromJson(resp, GetAllPost.class);

                    getAllPost_data = getAllPosts.getObject();
                    filterPostList.clear();
                    for (int i = 0; i < getAllPost_data.size(); i++) {
                        if (!blockedUserList.contains(getAllPost_data.get(i).getUserId())) {
                            filterPostList.add(getAllPost_data.get(i));
                        }
                    }

                    arrivalAdapter = new NewArrivalAdapter(getActivity(), getActivity(), filterPostList, new NewArrivalAdapter.OnBlockListner() {
                        @Override
                        public void onUserBlocked() {
                            getBlockList();
                        }
                    });
                    Arrival_recyclerView.setAdapter(arrivalAdapter);

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                mDialog.dismiss();
                Log.e("onFailure >>>> ", "" + t.getMessage());

            }
        });
    }
}




