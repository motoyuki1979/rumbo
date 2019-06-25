package com.wa.rumbo.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wa.rumbo.R;
import com.wa.rumbo.adapters.Fragment_Other_Adapter;
import com.wa.rumbo.adapters.Fragmeny_Other_Second_Adapter;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.model.GetAllPost;
import com.wa.rumbo.model.GetAllPost_Data;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_Other_Household extends Fragment {

    @BindView(R.id.rv1_itemname_other)
    RecyclerView rv1_itemname_other;

    @BindView(R.id.rv2_itemname_other)
    RecyclerView rv2_itemname_other;

    @BindView(R.id.tv_saving_n_expenses)
    TextView tv_saving_n_expenses;

    //tv_saving_n_expenses

    Fragment_Other_Adapter fragment_other_adapter;
    Fragmeny_Other_Second_Adapter fragmeny_other_second_adapter;

    CommonData commonData;
    GetAllPost getAllPosts;
    List<GetAllPost_Data> getAllPost_data= new ArrayList<>();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_other_household, container, false);

        commonData = new CommonData(getActivity());

      //  MainActivity.homeTabsLL.setVisibility(View.GONE);

        ButterKnife.bind(this, view);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv1_itemname_other.setLayoutManager(layoutManager);

        fragment_other_adapter = new Fragment_Other_Adapter(getActivity());
        rv1_itemname_other.setAdapter(fragment_other_adapter);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        rv2_itemname_other.setLayoutManager(layoutManager2);

        fragmeny_other_second_adapter= new Fragmeny_Other_Second_Adapter(getActivity());
        rv2_itemname_other.setAdapter(fragmeny_other_second_adapter);


        tv_saving_n_expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, new Savings_and_Expenses_Fragment(), "NewFragmentTag");

                ft.addToBackStack(null);
                ft.commit();
            }
        });





        return view;
    }
}
