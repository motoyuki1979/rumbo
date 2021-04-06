package com.wa.rumbo.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.wa.rumbo.Api;
import com.wa.rumbo.R;
import com.wa.rumbo.activities.MainActivity;
import com.wa.rumbo.adapters.MyPageFollow_Adapter;
import com.wa.rumbo.callbacks.GetFollowersCallback;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.model.GetFollowersModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyPageFollow_Fragment extends Fragment {

    @BindView(R.id.rv_follow_list)
    RecyclerView rv_follow_list;

    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.tvNoDataFound1)
    TextView tvNoDataFound1;

    MyPageFollow_Adapter myPageFollowAdapter;

    Animation clickAnimation;
    ArrayList<GetFollowersModel.Object> mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page_follow, container, false);
        MainActivity.homeTabsLL.setVisibility(View.GONE);

        ButterKnife.bind(this, view);
        clickAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.grow);
        UsefullData.setLocale(getActivity());
        mList = new ArrayList<>();

        new Api(getActivity()).getFollowsApi(getActivity(),true, new GetFollowersCallback() {
            @Override
            public void onResponse(GetFollowersModel model) {
                for (int i = 0 ; i < model.getObject().size(); i++ ){
                    mList.add(model.getObject().get(i));
                }

                if (mList.size()>0){
                    rv_follow_list.setVisibility(View.VISIBLE);
                    tvNoDataFound1.setVisibility(View.GONE);


                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    rv_follow_list.setLayoutManager(layoutManager);

                    myPageFollowAdapter = new MyPageFollow_Adapter(getActivity(), mList);
                    rv_follow_list.setAdapter(myPageFollowAdapter);

                }else {
                    rv_follow_list.setVisibility(View.GONE);
                    tvNoDataFound1.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure() {
                rv_follow_list.setVisibility(View.GONE);
                tvNoDataFound1.setVisibility(View.VISIBLE);
            }
        });




        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(clickAnimation);
                getActivity().onBackPressed();
            }
        });

        return view;

    }

}
