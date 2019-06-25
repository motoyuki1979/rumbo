package com.wa.rumbo.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.wa.rumbo.R;
import com.wa.rumbo.custom_calendar.CalenderEvent;
import com.wa.rumbo.custom_calendar.listener.CalenderDayClickListener;
import com.wa.rumbo.custom_calendar.model.DayContainerModel;
import com.wa.rumbo.custom_calendar.model.Event;

import java.util.Calendar;


public class CalendarFragment extends Fragment {


    private static final String TAG = "CalenderTest";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_calendar, container, false);



        CalenderEvent calenderEvent = view.findViewById(R.id.calender_event);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Event event = new Event(calendar.getTimeInMillis(), "5,000");
        calenderEvent.addEvent(event);

        calenderEvent.initCalderItemClickCallback(new CalenderDayClickListener() {
            @Override
            public void onGetDay(DayContainerModel dayContainerModel) {
                Log.d(TAG, dayContainerModel.getDate());
            }
        });









        return  view;
    }





}
