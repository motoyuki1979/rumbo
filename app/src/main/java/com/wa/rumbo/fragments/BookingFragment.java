package com.wa.rumbo.fragments;

//https://stackoverflow.com/questions/54208999/get-data-from-retrofit-call-and-send-it-to-another-activity

import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wa.rumbo.R;
import com.wa.rumbo.RetrofitInstance;
import com.wa.rumbo.activities.MainActivity;
import com.wa.rumbo.adapters.BookingAdapter;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.ConstantValue;
import com.wa.rumbo.interfaces.Category_Interf;
import com.wa.rumbo.interfaces.Register_Interfac;
import com.wa.rumbo.model.CategoryResponse;
import com.wa.rumbo.model.Category_Data;
import com.wa.rumbo.model.Status_Model;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BookingFragment extends Fragment implements ConstantValue {

    CommonData commonData;
    CategoryResponse categoryResponse;


    List<Category_Data> categoryList;
    String dateData = "";
    String category_name, category_id;

    @BindView(R.id.rv_kakebo)
    RecyclerView rv_kakebo;

    @BindView(R.id.tv_datePicker)
    TextView tv_datePicker;

    @BindView(R.id.edt_expense_content)
    EditText edt_expense_content;

    @BindView(R.id.edt_comment)
    EditText edt_comment;

    @BindView(R.id.edt_expenditure)
    EditText edt_expenditure;

    @BindView(R.id.btn_send)
    Button btn_send;

    BookingAdapter kakebo_adapter;
    Retrofit retrofit = RetrofitInstance.getClient();
    Register_Interfac register_interfac = retrofit.create(Register_Interfac.class);
    @BindView(R.id.tv_expence)
    TextView tvExpence;
    @BindView(R.id.tv_income)
    TextView tvIncome;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        // MainActivity.booking_RL.setBackgroundResource(R.drawable.tab_select_bg);
        ((MainActivity) getActivity()).getBottomSelectedTabs(2);

        commonData = new CommonData(getActivity());
        commonData.getString(TOKEN);
        commonData.getString(USER_ID);

        MainActivity.homeTabsLL.setVisibility(View.GONE);
        ButterKnife.bind(this, view);

        getCurrentDate();

        getCategoryList();

        //  datePicker.setText();
        tv_datePicker.setTextColor(Color.GREEN);
        tv_datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePicker view;
                DialogFragment dFragment = new DatePickerFragment();
                dFragment.show(getFragmentManager(), "Date Picker");*/
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_datepicker);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                dialog.getWindow().setAttributes(lp);

                final DatePicker datePicker1 = (DatePicker) dialog.findViewById(R.id.simpleDatePicker);
                Button dialogButton = (Button) dialog.findViewById(R.id.msg_popup_btn);

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        int month = datePicker1.getMonth() + 1;
                        dateData = datePicker1.getDayOfMonth() + "-" + month + "-" + datePicker1.getYear();
                        tv_datePicker.setText(dateData);


//                        Log.e("DATE PICKER", datePicker.getDayOfMonth() + "-" + datePicker.getMonth() + "-" + datePicker.getYear());
                    }
                });
                //tv_datePicker.setText(dateData);
                dialog.show();

            }
        });

        tvExpence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvExpence.setBackgroundResource(R.drawable.tab_select_bg);
                tvExpence.setTextColor(getResources().getColor(R.color.white));


                tvIncome.setBackgroundResource(R.color.colorPrimaryDark);
                tvIncome.setTextColor(getResources().getColor(R.color.tab_text_color));


            }
        });

        tvIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvIncome.setBackgroundResource(R.drawable.tab_select_bg);
                tvIncome.setTextColor(getResources().getColor(R.color.white));

                tvExpence.setBackgroundResource(R.color.colorPrimaryDark);
                tvExpence.setTextColor(getResources().getColor(R.color.tab_text_color));


            }
        });
        tv_datePicker.setTextColor(getResources().getColor(R.color.black));


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_expense_content.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter the title", Toast.LENGTH_LONG).show();
                } else if (edt_expenditure.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter the amount", Toast.LENGTH_LONG).show();
                } else if (edt_comment.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter your tweet", Toast.LENGTH_SHORT).show();
                } else {


                    String date = tv_datePicker.getText().toString();
                    String expense_content = edt_expense_content.getText().toString();
                    String comment = edt_comment.getText().toString();
                    String expenditure = edt_expenditure.getText().toString();


                    GetPost getPost = new GetPost();
                    getPost.setCategory_id(category_id);
                    getPost.setCategory_name(category_name);
                    getPost.setDate(date);
                    getPost.setTitle(expense_content);
                    getPost.setTodays_tweets(comment);
                    getPost.setExpenditure(expenditure);

                    if (category_id == null) {
                        Toast.makeText(getActivity(), "Please Select a Category", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    Call call = register_interfac.addPost(commonData.getString(USER_ID), commonData.getString(TOKEN), getPost);

                    call.enqueue(new Callback() {

                        GetPost getPost = new GetPost();

                        @Override
                        public void onResponse(Call call, Response response) {

                            Log.e("RESPONSE_POST >>>>", response.raw() + "");

                            if (response.isSuccessful() && response.body() != null) {
                                Log.e("Success_post", new Gson().toJson(response.body()));
                                String resp = new Gson().toJson(response.body());
                                //convert to model
                                Status_Model status_model = new Gson().fromJson(resp, Status_Model.class);
                                if (status_model.getSuccess().equals("true")) {
                                    Toast.makeText(getActivity(), status_model.getMessage(), Toast.LENGTH_SHORT).show();
                                    ((MainActivity) getActivity()).setTimeline_RL();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {

                            Log.e("fail_to_post", "post");

                        }
                    });

                }
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        rv_kakebo.setLayoutManager(layoutManager);

        return view;
    }


    public void getCategoryList() {

        Call call = register_interfac.category_list(commonData.getString(USER_ID), commonData.getString(TOKEN));

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("RESPONSE2 >>>>", response.raw() + "");

                if (response.isSuccessful() && response.body() != null) {


                    Log.e("Success_third", new Gson().toJson(response.body()));

                    String resp = new Gson().toJson(response.body());

                    //convert to model
                    categoryResponse = new Gson().fromJson(resp, CategoryResponse.class);
                    categoryList = categoryResponse.getObject();

                    kakebo_adapter = new BookingAdapter(getActivity(), categoryList, new Category_Interf() {
                        @Override
                        public void cat_data(String catgry_id, String catgry_name) {

                            category_name = catgry_name;
                            category_id = catgry_id;
                            Log.i("cattt1", catgry_name);
                        }
                    });
                    rv_kakebo.setAdapter(kakebo_adapter);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

                Log.e("fail_third", "category");

            }
        });
    }

    public void getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        tv_datePicker.setText(formattedDate);
    }





  /*  public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        DateInterf dateInterf;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // DatePickerDialog THEME_HOLO_LIGHT
            DatePickerDialog dpd3 = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);

            // Return the DatePickerDialog
            return dpd3;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the chosen date

            Log.e("DOOO", year + "/" + month +"/"+ day);
//            dateInterf.dated(year + "/" + month +"/"+ day);


        }


    }
*/

    public class GetPost {

        String date;
        String title;
        String expenditure;
        String todays_tweets;
        String category_id;
        String category_name;

        public void setDate(String date) {
            this.date = date;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setExpenditure(String expenditure) {
            this.expenditure = expenditure;
        }

        public void setTodays_tweets(String todays_tweets) {
            this.todays_tweets = todays_tweets;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }
    }

}
