package com.wa.rumbo;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wa.rumbo.fragments.RegisterFragment;

import static com.wa.rumbo.R.string.not_now;
import static com.wa.rumbo.R.string.please_register_or_login_first;
import static com.wa.rumbo.R.string.register;

public class utils {

    public static void showRegisterDialog(final Activity mActivity) {
        final Dialog dialog = new Dialog(mActivity);
        dialog.setContentView(R.layout.dialog_comment_delete);
        dialog.getWindow().setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.dialog_bg));

        ImageView ivCross = (ImageView) dialog.findViewById(R.id.ivCross);

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
        TextView tvYes = (TextView) dialog.findViewById(R.id.tvYes);
        TextView tvNo = (TextView) dialog.findViewById(R.id.tvNo);

        tvMessage.setText(mActivity.getResources().getString(please_register_or_login_first));
        tvYes.setText(mActivity.getResources().getString(register));
        tvNo.setText(mActivity.getResources().getString(not_now));
        // if button is clicked, close the custom dialog
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Fragment fragment = new RegisterFragment();
                FragmentManager fm = mActivity.getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayout, fragment);
                ft.commit();
            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
