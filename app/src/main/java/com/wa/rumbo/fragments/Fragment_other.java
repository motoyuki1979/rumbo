package com.wa.rumbo.fragments;

import android.app.DatePickerDialog;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wa.rumbo.R;
import com.wa.rumbo.activities.MainActivity;
import com.wa.rumbo.adapters.Fragment_Other_Adapter;
import com.wa.rumbo.adapters.Fragmeny_Other_Second_Adapter;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_other extends Fragment {


    Date date = null;


    @BindView(R.id.tv_prof_settings)
    TextView tv_prof_settings;

    @BindView(R.id.lin_number_of_follower)
    LinearLayout lin_number_of_follower;



    @BindView(R.id.tv_calendar)
    TextView tv_calendar;

    @BindView(R.id.tv_household)
    TextView tv_household;

    @BindView(R.id.tv_mypost)
    TextView tv_mypost;


    //lin_number_of_follower,  tv_saving_n_expenses

    Fragment_Other_Adapter fragment_other_adapter;
    Fragmeny_Other_Second_Adapter fragmeny_other_second_adapter;
    DatePickerDialog datePickerDialog;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_other_layout, container, false);
        MainActivity.homeTabsLL.setVisibility(View.GONE);

        ButterKnife.bind(this, view);

        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout_other, new Fragment_Other_Household(), "NewFragmentTag");

        ft.addToBackStack(null);
        ft.commit();





        tv_prof_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, new Profile_and_Settings_Frag(), "NewFragmentTag");

                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lin_number_of_follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout_other, new MyPageFollow_Fragment(), "NewFragmentTag");

                ft.addToBackStack(null);
                ft.commit();



            }
        });



        tv_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_calendar.setTextColor(getResources().getColor(R.color.white));
               // tv_calendar.setBackgroundColor(getResources().getColor(R.color.home_bg));
                tv_calendar.setBackgroundResource(R.drawable.tab_select_bg);

                tv_household.setTextColor(getResources().getColor(R.color.black));
                tv_household.setBackgroundColor(getResources().getColor(R.color.white));

                tv_mypost.setTextColor(getResources().getColor(R.color.black));
                tv_mypost.setBackgroundColor(getResources().getColor(R.color.white));




             /*   CalendarView cv = new CalendarView( getActivity() );
                cv.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                        date = new Date(year, month, dayOfMonth);
                    }
                });
                Intent intent = new Intent(getActivity(), Calendar_Fragment.class);
                intent.putExtra("long date", date.getTime());
                startActivity(intent);*/



                Fragment fragment = new CalendarFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayout_other, fragment).addToBackStack(null);
                ft.commit();

            }
        });

        tv_household.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_calendar.setTextColor(getResources().getColor(R.color.black));
   tv_calendar.setBackgroundColor(getResources().getColor(R.color.white));
             //   tv_calendar.setBackgroundResource(R.drawable.heart_yellow_bg);

                tv_household.setTextColor(getResources().getColor(R.color.white));
               // tv_household.setBackgroundColor(getResources().getColor(R.color.home_bg));
                tv_household.setBackgroundResource(R.drawable.tab_select_bg);

                tv_mypost.setTextColor(getResources().getColor(R.color.black));
                tv_mypost.setBackgroundColor(getResources().getColor(R.color.white));

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout_other, new Fragment_Other_Household(), "HouseHold_Fragment");

                ft.addToBackStack(null);

                ft.commit();





            }
        });

        tv_mypost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_calendar.setTextColor(getResources().getColor(R.color.black));
                tv_calendar.setBackgroundColor(getResources().getColor(R.color.white));

                tv_household.setTextColor(getResources().getColor(R.color.black));
                tv_household.setBackgroundColor(getResources().getColor(R.color.white));

                tv_mypost.setTextColor(getResources().getColor(R.color.white));
                //tv_mypost.setBackgroundColor(getResources().getColor(R.color.home_bg));
                tv_mypost.setBackgroundResource(R.drawable.tab_select_bg);


                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout_other, new NewArrivalFragment(), "NewFragmentTag");

                ft.addToBackStack(null);

                ft.commit();


            }
        });


        /*
        timeline_IV.setImageDrawable(getResources().getDrawable(R.mipmap.star_grey));
        timeline_TV.setTextColor(getResources().getColor(R.color.white));
        timeline_RL.setBackgroundColor(getResources().getColor(R.color.home_bg));

        community_IV.setImageDrawable(getResources().getDrawable(R.mipmap.comment_repaly));
        community_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
        community_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        booking_IV.setImageDrawable(getResources().getDrawable(R.mipmap.quill));
        booking_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
        booking_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
         */

        return  view;



    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }


}
