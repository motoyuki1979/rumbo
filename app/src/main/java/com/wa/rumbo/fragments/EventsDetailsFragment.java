package com.wa.rumbo.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wa.rumbo.R;
import com.wa.rumbo.adapters.EventsDetailsAdapter;
import com.wa.rumbo.model.GetCalenderBookingModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventsDetailsFragment extends Fragment {

    @BindView(R.id.rvList)
    RecyclerView rvList;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tvNoDataFound)
    TextView tvNoDataFound;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    ArrayList<GetCalenderBookingModel.Object> mList = new ArrayList<>();
    EventsDetailsAdapter eventsDetailsAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_details_fragment, container, false);
        ButterKnife.bind(this, view);

        String value = getArguments().getString("title");
        mList = (ArrayList<GetCalenderBookingModel.Object>) getArguments().getSerializable("data_list");
        title.setText(value);
       
        Log.e("Filter data =>  ", mList.toString());


        if (mList != null && mList.size() > 0) {
            tvNoDataFound.setVisibility(View.GONE);
            rvList.setVisibility(View.VISIBLE);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rvList.setLayoutManager(layoutManager);
            eventsDetailsAdapter = new EventsDetailsAdapter(getActivity(), mList);
            rvList.setAdapter(eventsDetailsAdapter);

        } else {
            tvNoDataFound.setVisibility(View.VISIBLE);
            rvList.setVisibility(View.GONE);
        }


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}
