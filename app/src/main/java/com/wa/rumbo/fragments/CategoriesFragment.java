package com.wa.rumbo.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wa.rumbo.R;
import com.wa.rumbo.RetrofitInstance;
import com.wa.rumbo.adapters.CategoriesAdapter;
import com.wa.rumbo.adapters.NewArrivalAdapter;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.interfaces.Category_Interf;
import com.wa.rumbo.interfaces.Register_Interfac;
import com.wa.rumbo.model.CategoryResponse;
import com.wa.rumbo.model.Category_Data;
import com.wa.rumbo.model.GetAllPost;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.wa.rumbo.common.ConstantValue.TOKEN;
import static com.wa.rumbo.common.ConstantValue.USER_ID;


public class CategoriesFragment extends Fragment {

    private static final String TAG = "CategoriesFragment";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    CategoriesAdapter adapter;
    List<Category_Data> categoryList;
    Register_Interfac register_interfac;
    Retrofit retrofit;
    CommonData commonData;
    Dialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        ButterKnife.bind(this, view);

        retrofit = RetrofitInstance.getClient();
        register_interfac = retrofit.create(Register_Interfac.class);
        commonData = new CommonData(getActivity());
        UsefullData.setLocale(getActivity());
        mDialog = UsefullData.getProgressDialog(getActivity());
        getCategoryListAPI();

        return view;
    }

    public void getCategoryListAPI() {

       /* final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();*/
        mDialog.show();

        Call call = register_interfac.category_list(commonData.getString(USER_ID), commonData.getString(TOKEN));

        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {

                mDialog.dismiss();
                Log.e("new arrival resp == ", response.raw() + "");
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("Success_post", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());

                    CategoryResponse categoryResponse = new Gson().fromJson(resp, CategoryResponse.class);
                    /*Type listType = new TypeToken<List<Category_Data>>() {
                    }.getType();*/

                    categoryList = categoryResponse.getObject(); //new Gson().fromJson(categoryResponse.toString(), listType);

                    adapter = new CategoriesAdapter(getActivity(), categoryList, new Category_Interf() {
                        @Override
                        public void cat_data(String catgry_id, String catgry_name, Uri uri) {

                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                mDialog.dismiss();
                Log.e("onFailure >>>>", "" + t.getMessage());

            }
        });
    }


}
