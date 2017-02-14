package com.hskj.damnicomniplusvic.sturxdemo.utils;

import android.app.Application;

/**
 * Created by DAMNICOMNIPLUSVIC on 2017/2/10.
 */

public class AppRoot extends Application{

    private static AppRoot INSTANCE;

    public static AppRoot getINSTANCE()
    {
        return INSTANCE;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE=this;
    }
}
