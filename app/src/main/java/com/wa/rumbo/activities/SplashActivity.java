package com.wa.rumbo.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.wa.rumbo.R;
import com.wa.rumbo.RetrofitInstance;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.ConstantValue;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.interfaces.Register_Interfac;
import com.wa.rumbo.model.Register_Model;
import com.wa.rumbo.model.Register_Model_Request;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class SplashActivity extends AppCompatActivity implements ConstantValue {
    Context context;

    private static int SPLASH_TIME_OUT = 3000;
    CommonData commonData;
    private int PERMISSION_REQUEST_CODE = 101;

    // String ts = Context.TELEPHONY_SERVICE;
    TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.e("base url ", ConstantValue.BASE_URL + " --");

        commonData = new CommonData(this);
        //register_model = new Register_Model();
        UsefullData.setLocale(SplashActivity.this);
//        deviceId();
//        getDeviceId(context);
        //  languageSet();



        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashActivity.this,
                new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String newToken = instanceIdResult.getToken();
                        Log.e("newToken", newToken);
                        commonData.save(FIREBASE_TOKEN, newToken);

                        Log.e("commonDat", "<1><><>  " + commonData.getString(FIREBASE_TOKEN));

                    }
                });

        //a9xproltesea

        if (checkPermission()) {
            waitThread();
        } else {
            requestPermission();
        }

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    waitThread();
                    if (locationAccepted && cameraAccepted) {
                    }

                    // Snackbar.make(getActivity(), "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    else {

                        // Snackbar.make(getActivity(), "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                              /*  showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });*/
                                return;
                            }
                        }

                    }
                }
        }
    }

    private void waitThread() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (commonData.isExist(USER_ID)) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT |
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT |
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }


}