package com.wa.rumbo.fragments;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.wa.rumbo.Api;
import com.wa.rumbo.R;
import com.wa.rumbo.adapters.Fragment_Other_Adapter;
import com.wa.rumbo.callbacks.GetCalenderBookingCalback;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.custom_calendar.CalenderEvent;
import com.wa.rumbo.custom_calendar.listener.CalenderDayClickListener;
import com.wa.rumbo.custom_calendar.model.DayContainerModel;
import com.wa.rumbo.custom_calendar.model.Event;
import com.wa.rumbo.model.GetCalenderBookingModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarFragment extends Fragment {

    private static final String TAG = "CalenderTest";
    GetCalenderBookingModel.Object getCalenderBookingModel;
    ArrayList<GetCalenderBookingModel.Object> mList = new ArrayList<>();
    ArrayList<GetCalenderBookingModel.Object> mFilterList = new ArrayList<>();
    String total_amount = "0";
    List<GetCalenderBookingModel.Object> monthlyList = new ArrayList<>();

    @BindView(R.id.tvExpense)
    TextView tvExpense;
    @BindView(R.id.tvIncome)
    TextView tvIncome;
    @BindView(R.id.tvTotalAmount)
    TextView tvTotalAmount;
    int totalAmount = 0;
    int totalIncome = 0;
    int totalExpence = 0;
    CalenderEvent calenderEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        UsefullData.setLocale(getActivity());
        ButterKnife.bind(this, view);


 calenderEvent = view.findViewById(R.id.calender_event);


        final Calendar calendar = Calendar.getInstance();

        new Api(getActivity()).getCalenderBookingApi(new GetCalenderBookingCalback() {
            @Override
            public void onRespose(GetCalenderBookingModel model) {
                getCalenderBookingModel = new GetCalenderBookingModel.Object();
                int displayingAmount = 0;

                for (int i = 0; i < model.getObject().size(); i++) {

                    mList.add(model.getObject().get(i));

                    String string_date = model.getObject().get(i).getDate();
                    long milliseconds = 787878787;
                    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date d = f.parse(string_date);
                        milliseconds = d.getTime();
                        Log.e("time12 => ", milliseconds + "");
                    } catch (Exception e) {

                    }

                    for (int k = 0; k < model.getObject().size(); k++) {
                        if (model.getObject().get(k).getDate().equalsIgnoreCase(model.getObject().get(i).getDate())) {
                            monthlyList.add(model.getObject().get(k));

                            displayingAmount = displayingAmount + Integer.valueOf(model.getObject().get(k).getAmount());
                        }
                    }
                    Event event = new Event(milliseconds, displayingAmount + "");
                    //  Event event = new Event(milliseconds, model.getObject().get(i).getAmount());
                    calenderEvent.addEvent(event);
                    displayingAmount = 0;

                }

                    if (monthlyList.size() > 0) {
                        for (int j = 0; j < monthlyList.size(); j++) {

                            if (monthlyList.get(j).getPost_category().equalsIgnoreCase("expence")) {
                                totalExpence = totalExpence + Integer.valueOf(monthlyList.get(j).getAmount());
                            } else {
                                totalIncome = totalIncome + Integer.valueOf(monthlyList.get(j).getAmount());
                            }
                            totalAmount = totalAmount + Integer.valueOf(monthlyList.get(j).getAmount());
                        }
                }

                tvExpense.setText(totalExpence + "");
                tvIncome.setText(totalIncome + "");
                tvTotalAmount.setText(totalAmount + "");


            }

        @Override
        public void onFailure () {
            Log.e("Calender Booking api's respose =>  ", "Failure");
        }
    });

        calenderEvent.initCalderItemClickCallback(new

    CalenderDayClickListener() {
        @Override
        public void onGetDay (DayContainerModel dayContainerModel){
            Log.e("123456", dayContainerModel.getDate());

            //3 April 2021
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String date = formatter.format(Date.parse(dayContainerModel.getDate()));
            mFilterList.clear();

            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).getDate().equalsIgnoreCase(date)) {
                    mFilterList.add(mList.get(i));
                }
            }

            Log.e("123456 new List", mFilterList.size() + "");
            Log.e("123456 new ", date);

            EventsDetailsFragment fragment = new EventsDetailsFragment();
            Bundle args = new Bundle();

            args.putString("title", date);
            args.putSerializable("data_list", mFilterList);
            fragment.setArguments(args);

            getFragmentManager().beginTransaction().add(R.id.frameLayout, fragment).addToBackStack(null).commit();

        }
    });

        return view;
}
}