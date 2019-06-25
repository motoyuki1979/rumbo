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

import com.wa.rumbo.R;
import com.wa.rumbo.activities.MainActivity;
import com.wa.rumbo.adapters.MyPageFollow_Adapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyPageFollow_Fragment extends Fragment {

    @BindView(R.id.rv_follow_list)
    RecyclerView rv_follow_list;

    MyPageFollow_Adapter myPageFollowAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page_follow, container, false);
        MainActivity.homeTabsLL.setVisibility(View.GONE);

        ButterKnife.bind(this, view);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_follow_list.setLayoutManager(layoutManager);

        myPageFollowAdapter = new MyPageFollow_Adapter(getActivity());
        rv_follow_list.setAdapter(myPageFollowAdapter);


        return view;

    }

}
