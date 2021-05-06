package com.wa.rumbo.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wa.rumbo.R;
import com.wa.rumbo.RetrofitInstance;
import com.wa.rumbo.activities.MainActivity;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.ConstantValue;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.interfaces.Register_Interfac;
import com.wa.rumbo.model.Register_Model;
import com.wa.rumbo.model.Register_Model_Request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginFragment extends Fragment {
    String deviceID;

    @BindView(R.id.tvForgotPassword)
    TextView tvForgotPassword;
    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    Animation clcikAnimation;
    Dialog mDialog;
    private static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";
    CommonData commonData;
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Register_Model register_model;

    public static boolean isValid(final String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).getBottomSelectedTabs(3);
        UsefullData.setLocale(getActivity());
        commonData = new CommonData(getActivity());
        deviceID = Build.DEVICE + "-" + Build.ID;
        Log.e("====> ", "" + deviceID);
        clcikAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.grow);
        mDialog = UsefullData.getProgressDialog(getActivity());

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clcikAnimation);
                ((MainActivity) getActivity()).getFragmentManager().beginTransaction().replace(R.id.frameLayout, new ForgotPasswordFragment()).commit();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(clcikAnimation);
                loginValidator();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(clcikAnimation);
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    private void loginValidator() {
        if (etEmail.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.enter_your_email), Toast.LENGTH_SHORT).show();
        } else if (!isValid(etEmail.getText().toString())) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.enter_a_valid_email), Toast.LENGTH_SHORT).show();
        } else if (etPassword.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.enter_your_password), Toast.LENGTH_SHORT).show();
        } else if (etPassword.getText().toString().length() < 6 || etPassword.getText().toString().length() > 15) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.password_must_be_under_6_15_digits), Toast.LENGTH_SHORT).show();
        } else {
            login_user(etEmail.getText().toString(), etPassword.getText().toString());
        }
    }


    public void login_user(String email, String password) {
        final Register_Model_Request register_model_request = new Register_Model_Request();
        mDialog.show();
        Retrofit retrofit = RetrofitInstance.getClient();
        Register_Interfac register_interfac = retrofit.create(Register_Interfac.class);

        Log.e("register Api ==>> ", "\nDeviceID: " + deviceID + "\ntoken: " + commonData.getString(ConstantValue.FIREBASE_TOKEN) + "\nemail: " + email + "\npassword: " + password);
        Call call = register_interfac.login(deviceID, commonData.getString(ConstantValue.FIREBASE_TOKEN), email, password);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                mDialog.dismiss();
                Log.e("RESPONSE >>>> ", response.raw() + "");

                if (response.isSuccessful() && response.body() != null) {
                    register_model_request.setDevice_token(ConstantValue.DEVICE_TOKEN);
                    register_model_request.setDevice_id(deviceID);

                    commonData.save("id_device", register_model_request.getDevice_id());

                    Toast.makeText(getActivity(), "Login Successfully", Toast.LENGTH_SHORT).show();
                    Log.e("Success", new Gson().toJson(response.body()));
                    //convert & save to string
                    String resp = new Gson().toJson(response.body());
                    //convert to model
                    register_model = new Gson().fromJson(resp, Register_Model.class);

                    commonData.save(ConstantValue.USER_ID, register_model.getObject().getUserId());
                    commonData.save(ConstantValue.USER_NAME, register_model.getObject().getUserName());
                    commonData.save(ConstantValue.ADDRESS, register_model.getObject().getAddress());
                    commonData.save(ConstantValue.TOKEN, register_model.getObject().getToken());

                    Log.e("userr ", register_model.getObject().getUserId());
                    Log.e("userr ", commonData.getString(ConstantValue.USER_ID));


                    ((MainActivity) getActivity()).getFragmentManager().beginTransaction().replace(R.id.frameLayout, new NewArrivalFragment()).commit();

                }

                Log.e("success", "register");

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("fail", "register");
                mDialog.dismiss();
            }
        });
    }
}
