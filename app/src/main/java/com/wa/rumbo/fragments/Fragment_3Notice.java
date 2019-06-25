package com.wa.rumbo.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wa.rumbo.R;
import com.wa.rumbo.adapters.Notice3_Adapter;
import com.wa.rumbo.model.Notice3_model;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_3Notice extends Fragment {

    @BindView(R.id.rv_3notice)
    RecyclerView rv_3notice;

    String date,time, title;
    Notice3_model notice3_model;
    Notice3_Adapter notice3_adapter;
    List<Notice3_model> list_note = new ArrayList<>();

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_3notice, container, false);
        ButterKnife.bind(this, view);

        rv_3notice.setLayoutManager(new LinearLayoutManager(getActivity()));

        notice3_adapter = new Notice3_Adapter(getActivity());
        rv_3notice.setAdapter(notice3_adapter);

        for (int i=0;i<7;i++) {
            notice3_model= new Notice3_model("Alpha","10/12/2018","24:00");
            list_note.add(notice3_model);
        }

        return view;
    }



}
