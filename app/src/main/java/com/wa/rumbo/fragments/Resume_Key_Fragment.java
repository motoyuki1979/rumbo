package com.wa.rumbo.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wa.rumbo.R;
import com.wa.rumbo.common.UsefullData;


public class Resume_Key_Fragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view= inflater.inflate(R.layout.fragment_resume_key, container, false);
        UsefullData.setLocale(getActivity());

      return  view;
    }

}



