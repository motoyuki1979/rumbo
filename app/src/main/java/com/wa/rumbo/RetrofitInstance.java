package com.wa.rumbo;

import com.wa.rumbo.common.ConstantValue;
import com.wa.rumbo.interfaces.Register_Interfac;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
  //  public static final String BASE_URL = "http://112.196.16.90:8080/Rumbo/";

    public static Retrofit getClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantValue.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Creating object for our interface
        Register_Interfac api = retrofit.create(Register_Interfac.class);
        return retrofit; // return the APIInterface object
    }

}
