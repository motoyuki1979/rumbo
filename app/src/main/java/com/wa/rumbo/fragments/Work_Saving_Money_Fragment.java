package com.wa.rumbo.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wa.rumbo.R;
import com.wa.rumbo.adapters.Work_Saving_Money_Adapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Work_Saving_Money_Fragment extends Fragment {


    @BindView(R.id.rv_work_saving_money)
    RecyclerView rv_work_saving_money;

    Work_Saving_Money_Adapter work_saving_money_adapter;
    @BindView(R.id.img_back)
    ImageView imgBack;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.work_with_saving_money_fragment, container, false);


        ButterKnife.bind(this, view);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_work_saving_money.setLayoutManager(layoutManager);

        work_saving_money_adapter = new Work_Saving_Money_Adapter(getActivity());
        rv_work_saving_money.setAdapter(work_saving_money_adapter);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        return view;
    }


}
