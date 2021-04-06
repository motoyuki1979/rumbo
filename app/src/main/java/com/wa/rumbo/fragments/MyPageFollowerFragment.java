package com.wa.rumbo.fragments;

import android.app.Fragment;
import android.os.Bundle;
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
import com.wa.rumbo.adapters.MyPageFollower_Adapter;
import com.wa.rumbo.callbacks.GetFollowersCallback;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.model.GetFollowersModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyPageFollowerFragment extends Fragment {

    @BindView(R.id.rv_follow_list)
    RecyclerView rv_follow_list;

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvNoDataFound1)
    TextView tvNoDataFound;

    ArrayList<GetFollowersModel.Object> mList;
    MyPageFollower_Adapter myPageFollowAdapter;
    ArrayList<String> mFollowerList = new ArrayList<>();

    Animation cliclAnimation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page_follower, container, false);
        MainActivity.homeTabsLL.setVisibility(View.GONE);

        ButterKnife.bind(this, view);

        mList = new ArrayList<>();
        UsefullData.setLocale(getActivity());
        cliclAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.grow);

        new Api(getActivity()).getFollowersApi(getActivity(),true, new GetFollowersCallback() {
            @Override
            public void onResponse(GetFollowersModel model) {
                for (int i = 0; i < model.getObject().size(); i++) {
                    mList.add(model.getObject().get(i));
                    mFollowerList.add(model.getObject().get(i).getUserId());
                }

                if (mList.size() > 0) {
                    rv_follow_list.setVisibility(View.VISIBLE);
                    tvNoDataFound.setVisibility(View.GONE);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    rv_follow_list.setLayoutManager(layoutManager);

                    myPageFollowAdapter = new MyPageFollower_Adapter(getActivity(), mList, mFollowerList);
                    rv_follow_list.setAdapter(myPageFollowAdapter);
                } else {
                    rv_follow_list.setVisibility(View.GONE);
                    tvNoDataFound.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure() {
                rv_follow_list.setVisibility(View.GONE);
                tvNoDataFound.setVisibility(View.VISIBLE);
            }
        });


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(cliclAnimation);
                getActivity().onBackPressed();
            }
        });
        return view;

    }

}
