package com.wa.rumbo.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wa.rumbo.Api;
import com.wa.rumbo.R;
import com.wa.rumbo.adapters.Fragment_Other_Adapter;
import com.wa.rumbo.adapters.Fragmeny_Other_Second_Adapter;
import com.wa.rumbo.callbacks.GetCalenderBookingCalback;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.model.GetAllPost;
import com.wa.rumbo.model.GetAllPost_Data;
import com.wa.rumbo.model.GetCalenderBookingModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_Other_Household extends Fragment {

    @BindView(R.id.rv2_itemname_other)
    RecyclerView rv1_itemname_other;


    /*@BindView(R.id.tv_saving_n_expenses)
    TextView tv_saving_n_expenses;*/


    //tv_saving_n_expenses

    Fragment_Other_Adapter fragment_other_adapter;

    CommonData commonData;
    GetAllPost getAllPosts;
    List<GetAllPost_Data> getAllPost_data = new ArrayList<>();

    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.ivPreviousMonth)
    ImageView ivPreviousMonth;

    @BindView(R.id.ivNextMonth)
    ImageView ivNextMonth;
    @BindView(R.id.tvCurrentDate)
    TextView tvCurrentDate;
    GetCalenderBookingModel.Object getCalenderBookingModel;
    ArrayList<GetCalenderBookingModel.Object> mList = new ArrayList<>();
    ArrayList<GetCalenderBookingModel.Object> mFilterList = new ArrayList<>();
    @BindView(R.id.tvExpense)
    TextView tvExpense;
    @BindView(R.id.tvIncome)
    TextView tvIncome;
    @BindView(R.id.tvTotalAmount)
    TextView tvTotalAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_other_household, container, false);

        commonData = new CommonData(getActivity());

        //  MainActivity.homeTabsLL.setVisibility(View.GONE);

        ButterKnife.bind(this, view);

        UsefullData.setLocale(getActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv1_itemname_other.setLayoutManager(layoutManager);

        settingDate(tvCurrentDate, ivPreviousMonth, ivNextMonth);


/*

        tv_saving_n_expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, new Savings_and_Expenses_Fragment(), "NewFragmentTag");

                ft.addToBackStack(null);
                ft.commit();
            }
        });
*/


        return view;
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
                    String stMonth1 = splitterStrinng1[1];  //hello
                    Log.e("Selectedde monyh =>  ", stMonth1);

                    if (stMonth1.equalsIgnoreCase(stMonth)) {
                        mFilterList.add(model.getObject().get(i));
                    }
                }
                Log.e("Filter list size => ", mFilterList.size() + "");


                int totalAmount = 0;
                int totalIncome = 0;
                int totalExpence = 0;


                if (mFilterList.size() > 0) {
                    tvNoData.setVisibility(View.GONE);
                    rv1_itemname_other.setVisibility(View.VISIBLE);
                    fragment_other_adapter = new Fragment_Other_Adapter(getActivity(),getActivity(), mFilterList);
                    rv1_itemname_other.setAdapter(fragment_other_adapter);
                    for (int j = 0; j < mFilterList.size(); j++) {

                        if (mFilterList.get(j).getPost_category().equalsIgnoreCase("expence")) {
                            totalExpence = totalExpence + Integer.valueOf(mFilterList.get(j).getAmount());
                        } else {
                            totalIncome = totalIncome + Integer.valueOf(mFilterList.get(j).getAmount());
                        }
                       // totalAmount = totalAmount + Integer.valueOf(mFilterList.get(j).getAmount());
                    }
                    totalAmount = totalIncome - totalExpence;


                } else {
                    tvNoData.setVisibility(View.VISIBLE);
                    rv1_itemname_other.setVisibility(View.GONE);
                }
                tvExpense.setText(UsefullData.getCommaPrice(getActivity(), totalExpence + ""));
                tvIncome.setText(UsefullData.getCommaPrice(getActivity(),totalIncome + ""));

                if(String.valueOf(totalAmount).contains("-")){
                    totalAmount = Integer.valueOf(String.valueOf(totalAmount).replace("-","") );
                    tvTotalAmount.setTextColor(getActivity().getResources().getColor(R.color.red_color));
                }else{
                    tvTotalAmount.setTextColor(getActivity().getResources().getColor(R.color.tab_selected));
                }
                tvTotalAmount.setText(UsefullData.getCommaPrice(getActivity(), totalAmount + ""));
            }

            @Override
            public void onFailure() {
                tvNoData.setVisibility(View.VISIBLE);
                rv1_itemname_other.setVisibility(View.GONE);
                Log.e("Calender Booking api's respose =>  ", "Failure");
            }
        });

    }
}
