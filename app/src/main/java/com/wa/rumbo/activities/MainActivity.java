package com.wa.rumbo.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.wa.rumbo.R;
import com.wa.rumbo.fragments.BookingFragment;
import com.wa.rumbo.fragments.CommunityFragment;
import com.wa.rumbo.fragments.FollowingFragment;
import com.wa.rumbo.fragments.Fragment_3Notice;
import com.wa.rumbo.fragments.Fragment_other;
import com.wa.rumbo.fragments.NewArrivalFragment;
import com.wa.rumbo.model.Register_Model;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    public static LinearLayout homeTabsLL;
    Register_Model register_model;
    Context context;


    @BindView(R.id.notifications)
    TextView notificationTab;
    @BindView(R.id.new_arrival)
    TextView newArrivalTab;
    @BindView(R.id.followings)
    TextView followTab;

    @BindView(R.id.home_timeline_LL)
    RelativeLayout timeline_RL;
    @BindView(R.id.home_community_LL)
    RelativeLayout community_RL;
    @BindView(R.id.home_booking_LL)
    RelativeLayout booking_RL;
    @BindView(R.id.home_mypage_LL)
    RelativeLayout mypage_RL;

    @BindView(R.id.timeline_iv)
    ImageView timeline_IV;
    @BindView(R.id.timeline_txt)
    TextView timeline_TV;
    @BindView(R.id.community_iv)
    ImageView community_IV;
    @BindView(R.id.community_txt)
    TextView community_TV;
    @BindView(R.id.booking_iv)
    ImageView booking_IV;
    @BindView(R.id.booking_txt)
    TextView booking_TV;
    @BindView(R.id.my_page_iv)
    ImageView my_page_IV;
    @BindView(R.id.my_page_txt)
    TextView my_page_TV;

    @BindView(R.id.adView)
    AdView mAdView;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeTabsLL = (LinearLayout) findViewById(R.id.home_tabs);
        ButterKnife.bind(this);

        Fragment fragment = new NewArrivalFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();

        newArrivalTab.setTextColor(getResources().getColor(R.color.white));
        newArrivalTab.setBackground(getResources().getDrawable(R.drawable.tab_select_bg));

        notificationTab.setTextColor(getResources().getColor(R.color.tab_text_color));
        notificationTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));

        followTab.setTextColor(getResources().getColor(R.color.tab_text_color));
        followTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        //register_user();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onResume() {
        super.onResume();

        newArrivalTab.setTextColor(getResources().getColor(R.color.white));
        newArrivalTab.setBackground(getResources().getDrawable(R.drawable.tab_select_bg));

        notificationTab.setTextColor(getResources().getColor(R.color.tab_text_color));
        notificationTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));

        followTab.setTextColor(getResources().getColor(R.color.tab_text_color));
        followTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick(R.id.notifications)
    public void setNotificationTab() {
        notificationTab.setTextColor(getResources().getColor(R.color.white));
        notificationTab.setBackground(getResources().getDrawable(R.drawable.tab_select_bg));
        newArrivalTab.setTextColor(getResources().getColor(R.color.tab_text_color));
        newArrivalTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));
        followTab.setTextColor(getResources().getColor(R.color.tab_text_color));
        followTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));

        Fragment fragment = new Fragment_3Notice();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment).addToBackStack(null);
        ft.commit();


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick(R.id.new_arrival)
    public void setNewArrivalTab() {
        Fragment fragment = new NewArrivalFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment).addToBackStack(null);
        ft.commit();


        newArrivalTab.setTextColor(getResources().getColor(R.color.white));
        newArrivalTab.setBackground(getResources().getDrawable(R.drawable.tab_select_bg));

        notificationTab.setTextColor(getResources().getColor(R.color.tab_text_color));
        notificationTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));

        followTab.setTextColor(getResources().getColor(R.color.tab_text_color));
        followTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick(R.id.followings)
    public void setFollowTab() {
        Fragment fragment = new FollowingFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment).addToBackStack(null);
        ft.commit();

        followTab.setTextColor(getResources().getColor(R.color.white));
        followTab.setBackground(getResources().getDrawable(R.drawable.tab_select_bg));

        notificationTab.setTextColor(getResources().getColor(R.color.tab_text_color));
        notificationTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));

        newArrivalTab.setTextColor(getResources().getColor(R.color.tab_text_color));
        newArrivalTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick(R.id.home_timeline_LL)
    public void setTimeline_RL() {


        newArrivalTab.setTextColor(getResources().getColor(R.color.white));
        newArrivalTab.setBackground(getResources().getDrawable(R.drawable.tab_select_bg));

        notificationTab.setTextColor(getResources().getColor(R.color.tab_text_color));
        notificationTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));

        followTab.setTextColor(getResources().getColor(R.color.tab_text_color));
        followTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));


        Fragment fragment = new NewArrivalFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment).addToBackStack(null);
        //clearStack();
        ft.commit();
       // fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        timeline_IV.setImageDrawable(getResources().getDrawable(R.mipmap.star_grey));
        timeline_TV.setTextColor(getResources().getColor(R.color.white));
        timeline_RL.setBackgroundColor(getResources().getColor(R.color.home_bg));

        community_IV.setImageDrawable(getResources().getDrawable(R.mipmap.comment_repaly));
        community_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
        community_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        booking_IV.setImageDrawable(getResources().getDrawable(R.mipmap.quill));
        booking_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
        booking_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        my_page_IV.setImageDrawable(getResources().getDrawable(R.mipmap.user));
        my_page_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
        mypage_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @OnClick(R.id.home_community_LL)
    public void setCommunity_RL() {
        Fragment fragment = new CommunityFragment();//new CommunityFragment();
        // Fragment fragment = new Calendar_Fragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment).addToBackStack(null);
        ft.commit();
        //  fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);


        timeline_IV.setImageDrawable(getResources().getDrawable(R.mipmap.star_color));
        timeline_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
        timeline_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        community_IV.setImageDrawable(getResources().getDrawable(R.mipmap.comment_grey));
        community_TV.setTextColor(getResources().getColor(R.color.white));
        community_RL.setBackgroundColor(getResources().getColor(R.color.home_bg));

        booking_IV.setImageDrawable(getResources().getDrawable(R.mipmap.quill));
        booking_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
        booking_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        my_page_IV.setImageDrawable(getResources().getDrawable(R.mipmap.user));
        my_page_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
        mypage_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @OnClick(R.id.home_booking_LL)
    public void setBooking_RL() {
        Fragment fragment = new BookingFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment).addToBackStack(null);
        //clearStack();
        ft.commit();

        timeline_IV.setImageDrawable(getResources().getDrawable(R.mipmap.star_color));
        timeline_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
        timeline_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        community_IV.setImageDrawable(getResources().getDrawable(R.mipmap.comment_repaly));
        community_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
        community_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        booking_IV.setImageDrawable(getResources().getDrawable(R.mipmap.quill_selected));
        booking_TV.setTextColor(getResources().getColor(R.color.white));
        booking_RL.setBackgroundColor(getResources().getColor(R.color.home_bg));

        my_page_IV.setImageDrawable(getResources().getDrawable(R.mipmap.user));
        my_page_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
        mypage_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @OnClick(R.id.home_mypage_LL)
    public void setMypage_RL() {

        Fragment fragment = new Fragment_other();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment).addToBackStack(null);
       // clearStack();
        ft.commit();

        timeline_IV.setImageDrawable(getResources().getDrawable(R.mipmap.star_color));
        timeline_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
        timeline_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));


        community_IV.setImageDrawable(getResources().getDrawable(R.mipmap.comment_repaly));
        community_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
        community_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        booking_IV.setImageDrawable(getResources().getDrawable(R.mipmap.quill));
        booking_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
        booking_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        my_page_IV.setImageDrawable(getResources().getDrawable(R.mipmap.user_selected));
        my_page_TV.setTextColor(getResources().getColor(R.color.white));
        mypage_RL.setBackgroundColor(getResources().getColor(R.color.home_bg));
    }


    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void getSelectedTab(int type) {


        if (type == 0) {
            notificationTab.setTextColor(getResources().getColor(R.color.white));
            notificationTab.setBackground(getResources().getDrawable(R.drawable.tab_select_bg));
            newArrivalTab.setTextColor(getResources().getColor(R.color.tab_text_color));
            newArrivalTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));
            followTab.setTextColor(getResources().getColor(R.color.tab_text_color));
            followTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));


        } else if (type == 1) {
            newArrivalTab.setTextColor(getResources().getColor(R.color.white));
            newArrivalTab.setBackground(getResources().getDrawable(R.drawable.tab_select_bg));

            notificationTab.setTextColor(getResources().getColor(R.color.tab_text_color));
            notificationTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));

            followTab.setTextColor(getResources().getColor(R.color.tab_text_color));
            followTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));
        } else if (type == 2) {
            followTab.setTextColor(getResources().getColor(R.color.white));
            followTab.setBackground(getResources().getDrawable(R.drawable.tab_select_bg));

            notificationTab.setTextColor(getResources().getColor(R.color.tab_text_color));
            notificationTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));

            newArrivalTab.setTextColor(getResources().getColor(R.color.tab_text_color));
            newArrivalTab.setBackgroundColor(getResources().getColor(R.color.tab_unselected));
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void getBottomSelectedTabs(int type) {

        if (type == 0) {

            timeline_IV.setImageDrawable(getResources().getDrawable(R.mipmap.star_grey));
            timeline_TV.setTextColor(getResources().getColor(R.color.white));
            timeline_RL.setBackgroundColor(getResources().getColor(R.color.home_bg));

            community_IV.setImageDrawable(getResources().getDrawable(R.mipmap.comment_repaly));
            community_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
            community_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            booking_IV.setImageDrawable(getResources().getDrawable(R.mipmap.quill));
            booking_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
            booking_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            my_page_IV.setImageDrawable(getResources().getDrawable(R.mipmap.user));
            my_page_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
            mypage_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        } else if (type == 1) {
            timeline_IV.setImageDrawable(getResources().getDrawable(R.mipmap.star_color));
            timeline_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
            timeline_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            community_IV.setImageDrawable(getResources().getDrawable(R.mipmap.comment_grey));
            community_TV.setTextColor(getResources().getColor(R.color.white));
            community_RL.setBackgroundColor(getResources().getColor(R.color.home_bg));

            booking_IV.setImageDrawable(getResources().getDrawable(R.mipmap.quill));
            booking_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
            booking_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            my_page_IV.setImageDrawable(getResources().getDrawable(R.mipmap.user));
            my_page_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
            mypage_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        } else if (type == 2) {
            timeline_IV.setImageDrawable(getResources().getDrawable(R.mipmap.star_color));
            timeline_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
            timeline_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            community_IV.setImageDrawable(getResources().getDrawable(R.mipmap.comment_repaly));
            community_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
            community_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            booking_IV.setImageDrawable(getResources().getDrawable(R.mipmap.quill_selected));
            booking_TV.setTextColor(getResources().getColor(R.color.white));
            booking_RL.setBackgroundColor(getResources().getColor(R.color.home_bg));

            my_page_IV.setImageDrawable(getResources().getDrawable(R.mipmap.user));
            my_page_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
            mypage_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        } else if (type == 3) {
            timeline_IV.setImageDrawable(getResources().getDrawable(R.mipmap.star_color));
            timeline_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
            timeline_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));


            community_IV.setImageDrawable(getResources().getDrawable(R.mipmap.comment_repaly));
            community_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
            community_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            booking_IV.setImageDrawable(getResources().getDrawable(R.mipmap.quill));
            booking_TV.setTextColor(getResources().getColor(R.color.tab_text_color));
            booking_RL.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            my_page_IV.setImageDrawable(getResources().getDrawable(R.mipmap.user_selected));
            my_page_TV.setTextColor(getResources().getColor(R.color.white));
            mypage_RL.setBackgroundColor(getResources().getColor(R.color.home_bg));

        }

    }

    public void clearStack() {
        //Here we are clearing back stack fragment entries
        int backStackEntry = getFragmentManager().getBackStackEntryCount();
        if (backStackEntry > 0) {
            for (int i = 0; i < backStackEntry; i++) {
                getFragmentManager().popBackStackImmediate();
            }
        }


    }
}