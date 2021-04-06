package com.wa.rumbo.fragments;

import android.app.DatePickerDialog;
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


public class MYPost_Fragment extends Fragment {

    NewArrivalFragment newArrivalFragment;


    @BindView(R.id.rv_mypost)
    RecyclerView rv_mypost;

  /*  @BindView(R.id.tv_calendar)
    TextView tv_calendar;

    @BindView(R.id.tv_household)
    TextView tv_household;

    @BindView(R.id.tv_mypost)
    TextView tv_mypost;

    @BindView(R.id.tv_prof_settings)
    TextView tv_prof_settings;

    @BindView(R.id.lin_number_of_follower)
    LinearLayout lin_number_of_follower;*/

    NewArrivalAdapter newArrivalAdapter;
    DatePickerDialog datePickerDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypost, container, false);
        //MainActivity.homeTabsLL.setVisibility(View.GONE);

        ButterKnife.bind(this, view);

        UsefullData.setLocale(getActivity());

        newArrivalFragment = new NewArrivalFragment();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_mypost.setLayoutManager(layoutManager);

        if (newArrivalFragment.getAllPost_data != null) {
            newArrivalAdapter = new NewArrivalAdapter(getActivity(),getActivity(), newArrivalFragment.getAllPost_data);
            rv_mypost.setAdapter(newArrivalAdapter);
        }

        return view;

    }

}

