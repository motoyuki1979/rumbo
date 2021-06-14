package com.wa.rumbo.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.Expose;
import com.wa.rumbo.Api;
import com.wa.rumbo.R;
import com.wa.rumbo.callbacks.GetUserProfileCallback;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.model.GetUserProfileModel;
import com.wa.rumbo.model.Register_Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wa.rumbo.common.ConstantValue.USER_ID;

public class ChangeEmailPasswordFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.tvChange)
    TextView tvChange;
    Animation clickAnimation;
    GetUserProfileModel.UserDetails userData;
    private static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    CommonData commonData;

    public static boolean isValid(final String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind(this, view);
        UsefullData.setLocale(getActivity());
        clickAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.grow);
        commonData = new CommonData(getActivity());

        ivBack.setOnClickListener(this);
        tvChange.setOnClickListener(this);

        new Api(getActivity()).getUserProfile(commonData.getString(USER_ID), true, new GetUserProfileCallback() {
            @Override
            public void onResponse(GetUserProfileModel model) {
                etEmail.setText(model.getUserDetails().get(0).getEmail());
                etPassword.setText(model.getUserDetails().get(0).getPassword());

                userData = new GetUserProfileModel.UserDetails();
                userData.setUserId(model.getUserDetails().get(0).getUserId());
                userData.setUserName(model.getUserDetails().get(0).getUserName());
                userData.setEmail(model.getUserDetails().get(0).getEmail());
                userData.setPassword(model.getUserDetails().get(0).getPassword());
                userData.setImage(model.getUserDetails().get(0).getImage());
                userData.setIntroduction(model.getUserDetails().get(0).getIntroduction());
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(clickAnimation);
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;
            case R.id.tvChange:
                if (etEmail.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.enter_your_email), Toast.LENGTH_SHORT).show();
                } else if (!isValid(etEmail.getText().toString())) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.enter_a_valid_email), Toast.LENGTH_SHORT).show();
                } else if (etPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.enter_your_password), Toast.LENGTH_SHORT).show();
                } else if (etPassword.getText().toString().length() < 6 || etPassword.getText().toString().length() > 15) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.password_must_be_under_6_15_digits), Toast.LENGTH_SHORT).show();
                } else {
                    new Api(getActivity()).updateUserProfile(userData.getUserName(), etEmail.getText().toString(), etPassword.getText().toString(), userData.getIntroduction(), userData.getImage(), "true");
                }
                break;
        }
    }
}
