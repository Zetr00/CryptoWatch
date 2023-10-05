package com.example.cryptowatch;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestBuilder {
    private static String URL = "https://api.coingecko.com/api/v3/";
    private static Retrofit retrofit = null;

    static Retrofit buildRequest(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    private static String URL1 = "http://172.20.10.4:7115/api/";
    private static Retrofit retrofit1 = null;

    static Retrofit buildRequest1(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        retrofit1 = new Retrofit.Builder()
                .baseUrl(URL1)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit1;
    }
}
