package com.hskj.damnicomniplusvic.sturxdemo.netutils;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DAMNICOMNIPLUSVIC on 2017/2/8.
 */

public class NetConfig {
    private static ImageServer mImageserver;
    private static GirlImageServer mGirlImageServer;
    private static LooperServer SLooperServer;

    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static ImageServer getmImageserver()
    {
        if(mImageserver==null)
        {
            Retrofit retrofit=new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://www.zhuangbi.info/")
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            mImageserver=retrofit.create(ImageServer.class);
        }
        return mImageserver;
    }

    public static GirlImageServer getmGirlImageServer()
    {
        if(mGirlImageServer==null)
        {
            Retrofit retrofit=new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://gank.io/api/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mGirlImageServer=retrofit.create(GirlImageServer.class);
        }
        return mGirlImageServer;
    }

    public static LooperServer getLooperServer()
    {
        if(SLooperServer==null)
        {
            SLooperServer=new LooperServer();
        }
        return SLooperServer;
    }
}
