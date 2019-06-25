package com.wa.rumbo.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wa.rumbo.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Savings_and_Expenses_Fragment extends Fragment {

    @BindView(R.id.tv_send)
    TextView tv_send;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_savings_and_expenses, container, false);


        ButterKnife.bind(this, view);


    tv_send.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    });


        return view;


    }

    // TODO: Rename method, update argument and hook method into UI event



}
