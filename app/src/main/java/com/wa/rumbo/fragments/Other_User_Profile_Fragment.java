package com.wa.rumbo.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wa.rumbo.R;
import com.wa.rumbo.RetrofitInstance;
import com.wa.rumbo.activities.MainActivity;
import com.wa.rumbo.adapters.NewArrivalAdapter;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.interfaces.Register_Interfac;
import com.wa.rumbo.model.GetAllPost_Data;
import com.wa.rumbo.model.User_Post_Model;

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

public class Other_User_Profile_Fragment extends Fragment {


    @BindView(R.id.tv_nick_name_user)
    TextView tvNickNameUser;
    @BindView(R.id.tv_self_intro_comment_user)
    TextView tvSelfIntroCommentUser;
    @BindView(R.id.rv_post_list)
    RecyclerView rvPostList;


    Retrofit retrofit = RetrofitInstance.getClient();
    Register_Interfac register_interfac = retrofit.create(Register_Interfac.class);
    CommonData commonData;
    String from_user_id;

    User_Post_Model user_post_model;

    List<GetAllPost_Data> getAllPost_data = new ArrayList<>();

    NewArrivalAdapter arrivalAdapter;
    @BindView(R.id.img_back)
    ImageView imgBack;
    Dialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_user_profile, null);
        ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).getBottomSelectedTabs(0);
        commonData = new CommonData(getActivity());
        UsefullData.setLocale(getActivity());
        mDialog = UsefullData.getProgressDialog(getActivity());
        Bundle extras = getArguments();

        if (extras != null) {
            from_user_id = extras.getString("from_user_id");
        }

        rvPostList.setLayoutManager(new LinearLayoutManager(getActivity()));

        getUserPostList();

        /*
        arrivalAdapter = new NewArrivalAdapter(getActivity(), getAllPost_data);
                    Arrival_recyclerView.setAdapter(arrivalAdapter);
         */
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    public void getUserPostList() {
      /*  final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message*/
        mDialog.show();

        Call call = register_interfac.getUserPostList(commonData.getString(USER_ID), commonData.getString(TOKEN), from_user_id);

        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {

                Log.e("other_user_prof == ", response.raw() + "");

                if (response.isSuccessful() && response.body() != null) {
                    mDialog.dismiss();

                    Log.e("other_user_prof_ok", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());

                    user_post_model = new Gson().fromJson(resp, User_Post_Model.class);

                    getAllPost_data = user_post_model.getGetAllPostDataList();

                    arrivalAdapter = new NewArrivalAdapter(getActivity(), getActivity(), getAllPost_data, new NewArrivalAdapter.OnBlockListner() {
                        @Override
                        public void onUserBlocked() {

                        }
                    });
                    rvPostList.setAdapter(arrivalAdapter);

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                mDialog.dismiss();
            }
        });
    }
}