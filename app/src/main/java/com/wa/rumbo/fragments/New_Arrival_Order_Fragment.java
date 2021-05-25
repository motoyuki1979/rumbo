package com.wa.rumbo.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wa.rumbo.R;
import com.wa.rumbo.adapters.NewArrivalAdapter;
import com.wa.rumbo.common.UsefullData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class New_Arrival_Order_Fragment extends Fragment {


    @BindView(R.id.rv_new_arrival)
    RecyclerView rv_new_arrival;

    NewArrivalAdapter newArrivalAdapter;
    NewArrivalFragment newArrivalFragment= new NewArrivalFragment();


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_new_arrival_order, container, false);
        //MainActivity.homeTabsLL.setVisibility(View.GONE);

        ButterKnife.bind(this, view);

        UsefullData.setLocale(getActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_new_arrival.setLayoutManager(layoutManager);

        newArrivalAdapter = new NewArrivalAdapter(getActivity(), getActivity(), newArrivalFragment.getAllPost_data, new NewArrivalAdapter.OnBlockListner() {
            @Override
            public void onUserBlocked() {

            }
        });
        rv_new_arrival.setAdapter(newArrivalAdapter);

        return  view;
    }
}
