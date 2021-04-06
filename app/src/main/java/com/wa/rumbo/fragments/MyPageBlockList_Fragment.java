package com.wa.rumbo.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wa.rumbo.R;
import com.wa.rumbo.adapters.Block_List_Adapter;
import com.wa.rumbo.common.UsefullData;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageBlockList_Fragment extends Fragment {

    @BindView(R.id.rv_blocked_list)
    RecyclerView rv_blocked_list;

    Block_List_Adapter block_list_adapter;


    public MyPageBlockList_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_page_block_list, container, false);

        ButterKnife.bind(this, view);
        UsefullData.setLocale(getActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_blocked_list.setLayoutManager(layoutManager);

        block_list_adapter = new Block_List_Adapter(getActivity());
        rv_blocked_list.setAdapter(block_list_adapter);

        return  view;
    }

}
