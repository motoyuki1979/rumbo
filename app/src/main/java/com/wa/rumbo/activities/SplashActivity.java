package com.wa.rumbo.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.wa.rumbo.R;
import com.wa.rumbo.RetrofitInstance;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.ConstantValue;
import com.wa.rumbo.interfaces.Register_Interfac;
import com.wa.rumbo.model.Register_Model;
import com.wa.rumbo.model.Register_Model_Request;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashActivity extends AppCompatActivity implements ConstantValue {
    String deviceID;
    Context context;


    private static int SPLASH_TIME_OUT = 3000;
    CommonData commonData;
    Register_Model register_model;

    // String ts = Context.TELEPHONY_SERVICE;
    TelephonyManager telephonyManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        commonData = new CommonData(this);
        //register_model = new Register_Model();

//        deviceId();
//        getDeviceId(context);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashActivity.this,
                new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String newToken = instanceIdResult.getToken();
                        Log.e("newToken", newToken);
                        commonData.save(FIREBASE_TOKEN, newToken);

                        Log.e("commonDat", "<1><><>  "+ commonData.getString(DEVICE_TOKEN));
                        register_user();
                    }
                });

        deviceID = Build.DEVICE + "-" + Build.ID;
        Log.e("====>", "" + deviceID);
        //a9xproltesea
        waitThread();

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

//    ProgressDialog progressDialog;

    public void register_user() {
        final Register_Model_Request register_model_request = new Register_Model_Request();

//        progressDialog = new ProgressDialog(SplashActivity.this);
//        progressDialog.setCancelable(false); // set cancelable to false
//        progressDialog.setMessage("Please Wait"); // set message
//        progressDialog.show();


        Retrofit retrofit = RetrofitInstance.getClient();
        Register_Interfac register_interfac = retrofit.create(Register_Interfac.class);

        Log.e("commonDat", "<><><>  "+ commonData.getString(FIREBASE_TOKEN));
        Call call = register_interfac.registeration(deviceID, commonData.getString(FIREBASE_TOKEN));

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

             /*
               if (response != null && response.isSuccessful && response.body() != null) {
                    Log.e("SUCCESS:::","Response "+ response.body()!!.main.temp)

                    temperature.setText(""+ response.body()!!.main.temp)


                }
              */

//                Register_Model register_model = response.raw();
                Log.e("RESPONSE >>>>", response.raw() + "");

                if (response.isSuccessful() && response.body() != null) {
                    register_model_request.setDevice_token(DEVICE_TOKEN);
                    //  Log.i("DEEE",DEVICE_TOKEN);
                    register_model_request.setDevice_id(deviceID);

                    Log.i("DEEE", register_model_request.getDevice_id());
                    commonData.save("id_device", register_model_request.getDevice_id());


                    Log.e("Success", new Gson().toJson(response.body()));
                    //convert & save to string
                    String resp = new Gson().toJson(response.body());
                    //convert to model
                    register_model = new Gson().fromJson(resp, Register_Model.class);

                    commonData.save("user_id", register_model.getObject().getUserId());
                    Log.i("userr", register_model.getObject().getUserId());
                    Log.i("userr", register_model.getObject().getToken());

                    commonData.save(USER_NAME, register_model.getObject().getUserName());
                    commonData.save(ADDRESS, register_model.getObject().getAddress());
                    commonData.save(TOKEN, register_model.getObject().getToken());


                }

                //    Toast.makeText(SplashActivity.this, register_model.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("success", "register");

            }

            @Override
            public void onFailure(Call call, Throwable t) {


                // Toast.makeText(get,"error",Toast.LENGTH_SHORT);
                //Toast.makeText(this,"error",Toast.LENGTH_SHORT);


                Log.e("fail", "register");

            }
        });





                /*
                 Api.getClient().registration(name.getText().toString().trim(),
                email.getText().toString().trim(),
                password.getText().toString().trim(),
                "email", new Callback<SignUpResponse>() {
                 */

    }

/*
    private void deviceId() {
        telephonyManager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
                        return;
                    }
                    String imeiNumber = telephonyManager.getDeviceId();

                    Log.e("teleee", imeiNumber);
                    Toast.makeText(SplashActivity.this, imeiNumber, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SplashActivity.this, "Without permission we check", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public String getDeviceId(Context context) {
        // returns 64-bit unique string
        String iid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.i("deevic", iid);
        return iid;


    }

*/
}
