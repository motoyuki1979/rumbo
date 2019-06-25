package com.wa.rumbo.fragments;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wa.rumbo.R;
import com.wa.rumbo.activities.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Profile_and_Settings_Frag extends Fragment {



    @BindView(R.id.tv_resume_key)
     TextView tv_resume_key;

    @BindView(R.id.tv_reg_email)
    TextView tv_reg_email;

    @BindView(R.id.tv_blocklist)
    TextView tv_blocklist;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


//        MainActivity.homeTabsLL.setVisibility(View.GONE);
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_profile_and_settings, container, false);

        ButterKnife.bind(this, view);

        tv_resume_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, new Resume_Key_Fragment(), "NewFragmentTag");

                ft.addToBackStack(null);
                ft.commit();

            }
        });

        tv_reg_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, new Register_EmailAddress_Fragment(), "NewFragmentTag");

                ft.addToBackStack(null);
                ft.commit();

            }
        });

        tv_blocklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, new MyPageBlockList_Fragment(), "NewFragmentTag");

                ft.addToBackStack(null);
                ft.commit();

            }
        });




       return  view;




    }





}