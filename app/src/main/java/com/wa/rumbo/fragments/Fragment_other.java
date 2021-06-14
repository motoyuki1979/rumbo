package com.wa.rumbo.fragments;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wa.rumbo.Api;
import com.wa.rumbo.R;
import com.wa.rumbo.RetrofitInstance;
import com.wa.rumbo.activities.MainActivity;
import com.wa.rumbo.activities.SplashActivity;
import com.wa.rumbo.adapters.Fragment_Other_Adapter;
import com.wa.rumbo.adapters.Fragmeny_Other_Second_Adapter;
import com.wa.rumbo.adapters.NewArrivalAdapter;
import com.wa.rumbo.callbacks.GetCalenderBookingCalback;
import com.wa.rumbo.callbacks.GetFollowersCallback;
import com.wa.rumbo.callbacks.GetUserProfileCallback;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.ConstantValue;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.custom_calendar.model.Event;
import com.wa.rumbo.interfaces.Register_Interfac;
import com.wa.rumbo.model.GetAllPost;
import com.wa.rumbo.model.GetAllPost_Data;
import com.wa.rumbo.model.GetBlockedListModel;
import com.wa.rumbo.model.GetCalenderBookingModel;
import com.wa.rumbo.model.GetFollowersModel;
import com.wa.rumbo.model.GetUserProfileModel;
import com.wa.rumbo.model.User_Post_Model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.wa.rumbo.common.ConstantValue.TOKEN;
import static com.wa.rumbo.common.ConstantValue.USER_ID;


public class Fragment_other extends Fragment {

    Date date = null;
    GetCalenderBookingModel.Object getCalenderBookingModel;
    ArrayList<GetCalenderBookingModel.Object> mList = new ArrayList<>();
    ArrayList<GetCalenderBookingModel.Object> mFilterList = new ArrayList<>();
    ArrayList<GetCalenderBookingModel.Object> mFinalFilterList = new ArrayList<>();
    ArrayList<GetCalenderBookingModel.Object> mSortedList = new ArrayList<>();

    @BindView(R.id.tv_prof_settings)
    TextView tv_prof_settings;

    @BindView(R.id.lin_number_of_follower)
    LinearLayout lin_number_of_follower;

    User_Post_Model user_post_model;

    @BindView(R.id.tv_calendar)
    TextView tv_calendar;

    @BindView(R.id.tv_household)
    TextView tv_household;

    @BindView(R.id.tv_mypost)
    TextView tv_mypost;
    @BindView(R.id.followings)
    TextView followings;

    @BindView(R.id.scrollable)
    ScrollView scrollable;
    @BindView(R.id.frameLayout_other)
    FrameLayout frameLayout_other;

    Dialog mDialog;
    //lin_number_of_follower,  tv_saving_n_expenses

    Fragment_Other_Adapter fragment_other_adapter;

    DatePickerDialog datePickerDialog;


    @BindView(R.id.rv2_itemname_other)
    RecyclerView rv2_itemname_other;

    /* @BindView(R.id.tv_saving_n_expenses)
     TextView tv_saving_n_expenses;*/
    @BindView(R.id.tvRegister)
    TextView tvRegister;

    @BindView(R.id.tv_others)
    TextView tv_others;

    @BindView(R.id.tvNoData)
    TextView tvNoData;

    @BindView(R.id.tvLogin)
    TextView tvLogin;


    @BindView(R.id.llFollower)
    LinearLayout llFollower;

    @BindView(R.id.ivPreviousMonth)
    ImageView ivPreviousMonth;

    @BindView(R.id.ivNextMonth)
    ImageView ivNextMonth;

    @BindView(R.id.llFollow)
    LinearLayout llFollow;
    @BindView(R.id.llNoUserLogin)
    LinearLayout llNoUserLogin;
    @BindView(R.id.arrival_adptr_img)
    CircleImageView ivProfile;
    @BindView(R.id.tvFollowerCount)
    TextView tvFollowerCount;
    @BindView(R.id.tvFollowCount)
    TextView tvFollowCount;
    @BindView(R.id.tvCurrentDate)
    TextView tvCurrentDate;

    @BindView(R.id.tvExpense)
    TextView tvExpense;
    @BindView(R.id.tvIncome)
    TextView tvIncome;
    @BindView(R.id.tvTotalAmount)
    TextView tvTotalAmount;

    CommonData commonData;
    GetAllPost getAllPosts;
    List<GetAllPost_Data> getAllPost_data = new ArrayList<>();
    Animation clickAnimation;
    Retrofit retrofit = RetrofitInstance.getClient();
    Register_Interfac register_interfac = retrofit.create(Register_Interfac.class);
    String from_user_id;
    ArrayList<String> catIdList = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        UsefullData.setLocale(getActivity());

        ((MainActivity) getActivity()).getBottomSelectedTabs(3);
        ((MainActivity) getActivity()).btnBooking.setVisibility(View.GONE);
        commonData = new CommonData(getActivity());

        View view = inflater.inflate(R.layout.fragment_other_layout, container, false);
        MainActivity.homeTabsLL.setVisibility(View.GONE);

        ButterKnife.bind(this, view);
        clickAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.grow);
        frameLayout_other.setVisibility(View.GONE);
        mDialog = UsefullData.getProgressDialog(getActivity());
        Bundle extras = getArguments();

        if (extras != null) {
            from_user_id = extras.getString("from_user_id");
        }

        settingDate(tvCurrentDate, ivPreviousMonth, ivNextMonth);

        if (commonData.getString(ConstantValue.USER_ID) != null && !commonData.getString(ConstantValue.USER_ID).equals("")) {
            tv_prof_settings.setEnabled(true);
            llFollow.setEnabled(true);
            llFollower.setEnabled(true);
            tv_household.setEnabled(true);
            tv_calendar.setEnabled(true);
            tv_mypost.setEnabled(true);
            llNoUserLogin.setVisibility(View.GONE);
            scrollable.setVisibility(View.VISIBLE);

            new Api(getActivity()).getUserProfile(commonData.getString(USER_ID), true, new GetUserProfileCallback() {
                @Override
                public void onResponse(GetUserProfileModel model) {
                    followings.setText(!(model.getUserDetails().get(0).getUserName().equalsIgnoreCase("")) ? model.getUserDetails().get(0).getUserName() : getActivity().getResources().getString(R.string.username));
                    tv_others.setText(!(model.getUserDetails().get(0).getIntroduction().equalsIgnoreCase("")) ? model.getUserDetails().get(0).getIntroduction() : getActivity().getResources().getString(R.string.no_profile_yet));

                    UsefullData.decodeBase64AndSetCircleImage(getActivity(), model.getUserDetails().get(0).getImage(), ivProfile);
                }
            });

            new Api(getActivity()).getFollowsApi(getActivity(), false, new GetFollowersCallback() {

                @Override
                public void onResponse(GetFollowersModel model) {
                    if (model.getObject().size() > 0) {
                        tvFollowCount.setText(model.getObject().size() + "");
                    } else {
                        tvFollowCount.setText("0");
                    }
                }

                @Override
                public void onFailure() {
                    tvFollowCount.setText("0");
                }
            });

            new Api(getActivity()).getFollowersApi(getActivity(), true, new GetFollowersCallback() {

                @Override
                public void onResponse(GetFollowersModel model) {
                    if (model.getObject().size() > 0) {
                        tvFollowerCount.setText(model.getObject().size() + "");
                    } else {
                        tvFollowerCount.setText("0");
                    }
                }

                @Override
                public void onFailure() {
                    tvFollowerCount.setText("0");
                }
            });

        } else {
            followings.setText(getActivity().getResources().getString(R.string.no_name));
            tv_prof_settings.setEnabled(false);
            llFollow.setEnabled(false);
            llFollower.setEnabled(false);
            tv_household.setEnabled(false);
            tv_calendar.setEnabled(false);
            tv_mypost.setEnabled(false);
            llNoUserLogin.setVisibility(View.VISIBLE);
            scrollable.setVisibility(View.GONE);
            tv_others.setText(getActivity().getResources().getString(R.string.no_profile_yet));
        }

        tv_prof_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, new Profile_and_Settings_Frag(), "NewFragmentTag");

                ft.addToBackStack(null);
                ft.commit();
            }
        });

        llFollower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, new MyPageFollowerFragment(), "NewFragmentTag");
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        llFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, new MyPageFollow_Fragment(), "NewFragmentTag");
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, new LoginFragment(), "NewFragmentTag");
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, new RegisterFragment(), "NewFragmentTag");
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        tv_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
                scrollable.setVisibility(View.GONE);
                frameLayout_other.setVisibility(View.VISIBLE);

                tv_calendar.setTextColor(getResources().getColor(R.color.white));
                // tv_calendar.setBackgroundColor(getResources().getColor(R.color.home_bg));
                tv_calendar.setBackgroundResource(R.drawable.tab_select_bg);

                tv_household.setTextColor(getResources().getColor(R.color.black));
                tv_household.setBackgroundColor(getResources().getColor(R.color.white));

                tv_mypost.setTextColor(getResources().getColor(R.color.black));
                tv_mypost.setBackgroundColor(getResources().getColor(R.color.white));

                Fragment fragment = new CalendarFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayout_other, fragment).addToBackStack(null);
                ft.commit();

            }
        });

        tv_household.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
                scrollable.setVisibility(View.GONE);
                frameLayout_other.setVisibility(View.VISIBLE);

                tv_calendar.setTextColor(getResources().getColor(R.color.black));
                tv_calendar.setBackgroundColor(getResources().getColor(R.color.white));
                //   tv_calendar.setBackgroundResource(R.drawable.heart_yellow_bg);

                tv_household.setTextColor(getResources().getColor(R.color.white));
                // tv_household.setBackgroundColor(getResources().getColor(R.color.home_bg));
                tv_household.setBackgroundResource(R.drawable.tab_select_bg);

                tv_mypost.setTextColor(getResources().getColor(R.color.black));
                tv_mypost.setBackgroundColor(getResources().getColor(R.color.white));

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout_other, new Fragment_Other_Household(), "HouseHold_Fragment");
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        tv_mypost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
                tv_calendar.setTextColor(getResources().getColor(R.color.black));
                tv_calendar.setBackgroundColor(getResources().getColor(R.color.white));

                tv_household.setTextColor(getResources().getColor(R.color.black));
                tv_household.setBackgroundColor(getResources().getColor(R.color.white));

                tv_mypost.setTextColor(getResources().getColor(R.color.white));
                //tv_mypost.setBackgroundColor(getResources().getColor(R.color.home_bg));
                tv_mypost.setBackgroundResource(R.drawable.tab_select_bg);

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout_other, new NewArrivalFragment(), "NewFragmentTag");
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        rv2_itemname_other.setLayoutManager(layoutManager2);

       /* tv_saving_n_expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, new Savings_and_Expenses_Fragment(), "NewFragmentTag");

                ft.addToBackStack(null);
                ft.commit();
            }
        });*/

        return view;

    }


    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }


    private void settingDate(final TextView tvCurrentDate, ImageView ivPreviousMonth, ImageView ivNextMonth) {
        final Calendar c = Calendar.getInstance();
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        final String[] formattedDate = {df.format(c.getTime())};
        final String[] currentSelectedMonth = {""};
        tvCurrentDate.setText(formattedDate[0]);
        hittingApi(tvCurrentDate.getText().toString());

        ivNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.add(Calendar.MONTH, 1);
                formattedDate[0] = df.format(c.getTime());
                Log.e("NEXT DATE : ", formattedDate[0]);
                tvCurrentDate.setText(formattedDate[0]);
                hittingApi(tvCurrentDate.getText().toString());
            }
        });

        ivPreviousMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.add(Calendar.MONTH, -1);
                formattedDate[0] = df.format(c.getTime());

                Log.e("PREVIOUS DATE : ", formattedDate[0]);
                tvCurrentDate.setText(formattedDate[0]);
                currentSelectedMonth[0] = tvCurrentDate.getText().toString();
                hittingApi(tvCurrentDate.getText().toString());
            }
        });
    }

    void hittingApi(final String date) {
        new Api(getActivity()).getCalenderBookingApi(new GetCalenderBookingCalback() {
            @Override
            public void onRespose(GetCalenderBookingModel model) {
                getCalenderBookingModel = new GetCalenderBookingModel.Object();

                String[] splitterStrinng = date.split("-");  //now str[0] is "hello" and str[1] is "goodmorning,2,1"

                String stYear = splitterStrinng[0];  //hello
                String stMonth = splitterStrinng[1];  //hello
                Log.e("Selectedde monyh =>  ", stMonth);
                mFilterList.clear();

                for (int i = 0; i < model.getObject().size(); i++) {
                    String[] splitterStrinng1 = model.getObject().get(i).getDate().split("-");  //now str[0] is "hello" and str[1] is "goodmorning,2,1"

                    String stYear1 = splitterStrinng1[0];  //hello
                    String stMonth1 = splitterStrinng1[1]; //hello
                    Log.e("Selectedde monyh =>  ", stMonth1);

                    if (stMonth1.equalsIgnoreCase(stMonth)) {
                        mFilterList.add(model.getObject().get(i));
                        catIdList.add(model.getObject().get(i).getCategoryTitle());
                    }
                }
                Log.e("Filter list size => ", mFilterList.size() + "");

                long totalAmount = 0;
                long totalIncome = 0;
                long totalExpence = 0;

                if (mFilterList.size() > 0) {
                    tvNoData.setVisibility(View.GONE);
                    rv2_itemname_other.setVisibility(View.VISIBLE);
                    mFinalFilterList.clear();

                    HashMap<String, List<GetCalenderBookingModel.Object>> hashMap = new HashMap<String, List<GetCalenderBookingModel.Object>>();

                    for (int i = 0; i < mFilterList.size(); i++) {
                        if (!hashMap.containsKey(mFilterList.get(i).getCategoryTitle())) {
                            List<GetCalenderBookingModel.Object> list = new ArrayList<GetCalenderBookingModel.Object>();
                            list.add(mFilterList.get(i));

                            hashMap.put(mFilterList.get(i).getCategoryTitle(), list);
                        } else {
                            hashMap.get(mFilterList.get(i).getCategoryTitle()).add(mFilterList.get(i));
                        }
                    }


                    Log.e("sortedList=>  ", hashMap.toString());

                    for (HashMap.Entry<String, List<GetCalenderBookingModel.Object>> entry : hashMap.entrySet()) {
                        String key = entry.getKey();
                        List<GetCalenderBookingModel.Object> value = entry.getValue();

                        Log.e("key=>  ", key);
                        Log.e("sortedList=>  ", value.toString());


                        Long actualPrice = 0L;
                        int catId = 0;

                        for (int j = 0; j < value.size(); j++) {
                            actualPrice = actualPrice + Long.valueOf(value.get(j).getAmount());
                            catId = value.get(j).getCategoryId();
                        }
                        GetCalenderBookingModel.Object newObj = new GetCalenderBookingModel.Object();
                        newObj.setAmount(actualPrice + "");
                        newObj.setCategoryTitle(key);
                        newObj.setCategoryId(catId);
                        mFinalFilterList.add(newObj);

                    }

                    Collections.sort(mFinalFilterList, new Comparator<GetCalenderBookingModel.Object>(){
                        public int compare(GetCalenderBookingModel.Object obj1, GetCalenderBookingModel.Object obj2) {

                            return String.valueOf(obj1.getCategoryId()).compareToIgnoreCase(String.valueOf(obj2.getCategoryId())); // To compare string values
                        }
                    });

                } else {
                    tvNoData.setVisibility(View.VISIBLE);
                    rv2_itemname_other.setVisibility(View.GONE);
                }

                Log.e("filter list size", mFilterList.size() + "");
                Log.e("final list size", mFinalFilterList.size() + "");

                fragment_other_adapter = new Fragment_Other_Adapter(getActivity(), getActivity(), mFinalFilterList);
                rv2_itemname_other.setAdapter(fragment_other_adapter);
                for (int j = 0; j < mFilterList.size(); j++) {

                    if (mFilterList.get(j).getPost_category().equalsIgnoreCase("expence")) {
                        totalExpence = totalExpence + Long.valueOf(mFilterList.get(j).getAmount());
                    } else {
                        totalIncome = totalIncome + Long.valueOf(mFilterList.get(j).getAmount());
                    }
                    //totalAmount = totalAmount + Integer.valueOf(mFilterList.get(j).getAmount());
                }

                totalAmount = totalIncome - totalExpence;
                tvExpense.setText(UsefullData.getCommaPrice(getActivity(), totalExpence + ""));
                tvIncome.setText(UsefullData.getCommaPrice(getActivity(), totalIncome + ""));

                if (String.valueOf(totalAmount).contains("-")) {
                    totalAmount = Long.valueOf(String.valueOf(totalAmount).replace("-", ""));
                    tvTotalAmount.setTextColor(getActivity().getResources().getColor(R.color.red_color));
                } else {
                    tvTotalAmount.setTextColor(getActivity().getResources().getColor(R.color.tab_selected));
                }

                tvTotalAmount.setText(UsefullData.getCommaPrice(getActivity(), totalAmount + ""));
            }

            @Override
            public void onFailure() {
                tvNoData.setVisibility(View.VISIBLE);
                rv2_itemname_other.setVisibility(View.GONE);
                Log.e("Calender Booking api's respose =>  ", "Failure");
            }
        });
    }
}

