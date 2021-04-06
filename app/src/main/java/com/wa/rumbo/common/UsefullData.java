package com.wa.rumbo.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.wa.rumbo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

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
            } catch (Exception e) {
                e.printStackTrace();
                return time;
            }
        }
    }

    public static Dialog getProgressDialog(Context mActivity) {
        Dialog mDialog = new Dialog(mActivity);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCancelable(false);
        mDialog.setContentView(R.layout.custom_progressbar);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return mDialog;
    }

    public static boolean setLocale(Activity activity) {
        String phoneLanguage = "";
        // Toast.makeText(activity, Resources.getSystem().getConfiguration().locale.getLanguage(), Toast.LENGTH_SHORT).show();
        if (Resources.getSystem().getConfiguration().locale.getLanguage().equalsIgnoreCase("ja")) {
            phoneLanguage = "de";
        } else {
            phoneLanguage = "en";
        }
        Locale locale = new Locale(phoneLanguage);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        return true;
    }

    public static void decodeBase64AndSetCircleImage(String completeImageData, CircleImageView imageView) {

        if (completeImageData != null) {
            Uri imageUri = Uri.parse(completeImageData);
            imageView.setImageURI(imageUri);
        }

    }

    public static void decodeBase64AndSetImage(String completeImageData, ImageView imageView) {

        if (completeImageData != null) {
            Uri imageUri = Uri.parse(completeImageData);
            imageView.setImageURI(imageUri);
        }

    }
    /*public  void showDialog(Activity activity){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom);
        dialog.setTitle("Title");

// set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.text1);
        text.setText("Text view 1");

        TextView text = (TextView) dialog.findViewById(R.id.text2);
        text.setText("Text view 2");
        dialog.show();
    }*/
}
