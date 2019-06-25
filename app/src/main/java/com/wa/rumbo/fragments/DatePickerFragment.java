package com.wa.rumbo.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import com.wa.rumbo.interfaces.DateInterf;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    DateInterf dateInterf;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // DatePickerDialog THEME_HOLO_LIGHT
        DatePickerDialog dpd3 = new DatePickerDialog(getActivity(),
                AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);

        // Return the DatePickerDialog
        return dpd3;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the chosen date

        Log.e("DDDDD", year + "/" + month +"/"+ day);
        dateInterf.dated(year + "/" + month +"/"+ day);

    }


}

