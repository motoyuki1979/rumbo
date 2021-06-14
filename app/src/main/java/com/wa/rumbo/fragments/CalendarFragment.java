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
import android.widget.Toast;

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
    Long totalAmount = 0L;
    Long totalIncome = 0L;
    Long totalExpence = 0L;
    CalenderEvent calenderEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        UsefullData.setLocale(getActivity());
        ButterKnife.bind(this, view);


        calenderEvent = view.findViewById(R.id.calender_event);

       /* calenderEvent = new CalenderEvent(getActivity(), new CalenderEvent.onItemClick() {
            @Override
            public void onNext(int month) {
                Toast.makeText(getActivity(), "inside next", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPrevious(int month) {
                Toast.makeText(getActivity(), "inside previous", Toast.LENGTH_SHORT).show();
            }
        });*/


        final Calendar calendar = Calendar.getInstance();

        getCalenderApi();

        calenderEvent.initCalderItemClickCallback(new CalenderDayClickListener() {
            @Override
            public void onGetDay(DayContainerModel dayContainerModel) {
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


    public void getCalenderApi(){
        new Api(getActivity()).getCalenderBookingApi(new GetCalenderBookingCalback() {
            @Override
            public void onRespose(GetCalenderBookingModel model) {
                getCalenderBookingModel = new GetCalenderBookingModel.Object();
                Long displayingAmount = 0L;

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
                    monthlyList.clear();

                    for (int k = 0; k < model.getObject().size(); k++) {
                        if (model.getObject().get(k).getDate().equalsIgnoreCase(model.getObject().get(i).getDate())) {
                            monthlyList.add(model.getObject().get(k));

                            if (model.getObject().get(k).getPost_category().equalsIgnoreCase("expence")) {
                                displayingAmount = displayingAmount + Long.valueOf(model.getObject().get(k).getAmount());
                            } else {
                                displayingAmount = displayingAmount - Long.valueOf(model.getObject().get(k).getAmount());
                            }

                            // displayingAmount = displayingAmount + Integer.valueOf(model.getObject().get(k).getAmount());
                        }
                    }
                    if (String.valueOf(displayingAmount).contains("-")) {
                        displayingAmount = Long.valueOf(String.valueOf(displayingAmount).replace("-", ""));
                    }
                    Event event = new Event(milliseconds, UsefullData.getCommaPrice(getActivity(), displayingAmount + ""));
                    //  Event event = new Event(milliseconds, model.getObject().get(i).getAmount());
                    calenderEvent.addEvent(event);
                    displayingAmount = 0L;

                }

                final Calendar c = Calendar.getInstance();
                final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
                final String[] formattedDate = {df.format(c.getTime())};
                final String[] currentSelectedMonth = {""};
                String date = formattedDate[0];

                String[] splitterStrinng = date.split("-");  //now str[0] is "hello" and str[1] is "goodmorning,2,1"

                String stYear = splitterStrinng[0];  //hello
                String stMonth = splitterStrinng[1];  //hello
                Log.e("Selectedde monyh =>  ", stMonth);
                mFilterList.clear();

                for (int i = 0; i < model.getObject().size(); i++) {
                    String[] splitterStrinng1 = model.getObject().get(i).getDate().split("-");  //now str[0] is "hello" and str[1] is "goodmorning,2,1"

                    String stYear1 = splitterStrinng1[0];  //hello
                    String stMonth1 = splitterStrinng1[1];  //hello
                    Log.e("Selectedde monyh =>  ", stMonth1);

                    if (stMonth1.equalsIgnoreCase(stMonth)) {
                        mFilterList.add(model.getObject().get(i));
                    }
                }


                if (mFilterList.size() > 0) {
                    for (int j = 0; j < mFilterList.size(); j++) {

                        if (mFilterList.get(j).getPost_category().equalsIgnoreCase("expence")) {
                            totalExpence = totalExpence + Long.valueOf(mFilterList.get(j).getAmount());
                        } else {
                            totalIncome = totalIncome + Long.valueOf(mFilterList.get(j).getAmount());
                        }
                        // totalAmount = totalAmount + Integer.valueOf(mFilterList.get(j).getAmount());
                    }
                    totalAmount = totalIncome - totalExpence;
                }

                tvExpense.setText(UsefullData.getCommaPrice(getActivity(), totalExpence + ""));
                tvIncome.setText(UsefullData.getCommaPrice(getActivity(), totalIncome + ""));

                if (String.valueOf(totalAmount).contains("-")) {
                    totalAmount = Long.valueOf(String.valueOf(totalAmount).replace("-", ""));
                    tvTotalAmount.setTextColor(getActivity().getResources().getColor(R.color.red_color));
                } else {
                    tvTotalAmount.setTextColor(getActivity().getResources().getColor(R.color.tab_selected));
                }
                tvTotalAmount.setText(UsefullData.getCommaPrice(getActivity(), totalAmount + ""));
            }

            @Override
            public void onFailure() {
                Log.e("Calender Booking api's respose =>  ", "Failure");
            }
        });
    }

}