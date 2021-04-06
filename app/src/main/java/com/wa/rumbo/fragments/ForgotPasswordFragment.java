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

import com.wa.rumbo.R;
import com.wa.rumbo.activities.MainActivity;
import com.wa.rumbo.common.UsefullData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPasswordFragment extends Fragment {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tvSendEmail)
    TextView tvSendEmail;
    @BindView(R.id.etEmail)
    EditText etEmail;
    Animation clickAnimation;
    private static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValid(final String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).getBottomSelectedTabs(2);
        clickAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.grow);
        UsefullData.setLocale(getActivity());

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(clickAnimation);
                getActivity().onBackPressed();
            }
        });

        tvSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
                sendEmailValidation();
            }
        });

        return view;
    }

    private void sendEmailValidation() {
        if (etEmail.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter email address", Toast.LENGTH_SHORT).show();
        } else if (!isValid(etEmail.getText().toString())) {
            Toast.makeText(getActivity(), "Please enter vaild email address", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Under construction", Toast.LENGTH_SHORT).show();
        }
    }
}
