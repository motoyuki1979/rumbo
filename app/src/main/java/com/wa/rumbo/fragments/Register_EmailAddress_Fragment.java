package com.wa.rumbo.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wa.rumbo.R;
import com.wa.rumbo.common.UsefullData;

/**
 * A simple {@link Fragment} subclass.
 */
public class Register_EmailAddress_Fragment extends Fragment {


    public Register_EmailAddress_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_register_email_address, container, false);
        UsefullData.setLocale(getActivity());

        return  view;
    }

}
