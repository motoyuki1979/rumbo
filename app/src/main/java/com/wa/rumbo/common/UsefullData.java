package com.wa.rumbo.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsefullData {
    Activity _context;

    public UsefullData(Activity context) {
        _context = context;
    }

    public static boolean isEmailValid(String email) // To check Email Validation
    {
        Pattern pattern;
        Matcher matcher;

        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }

    /*  CHECK APP IN BACKGROUND OR NOT  */
    public boolean isAppIsInBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }

        return false;
    }

    // =================== INTERNET ===================//
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        } else
            return true;
    }

    // ==================== HIDE KEYBOARD ==================//
    public void hideKeyBoared() {
//        InputMethodManager imm = (InputMethodManager) _context.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        View view2 = _context.getCurrentFocus();
        if (view2 == null) {
            view2 = new View(_context);
        }
        InputMethodManager imm1 = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm1.isAcceptingText()) {
            imm1.hideSoftInputFromWindow(view2.getWindowToken(), 0);
        } else {
        }

    }

    public static String getDateTimeFromMills(String time) {
        if (time.isEmpty()) {
            return time;
        } else {
            try {
                long timestamp = Long.parseLong(time);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                return simpleDateFormat.format(new Date(timestamp));
            }catch (Exception e){
                e.printStackTrace();
                return time;
            }
        }
    }


}
