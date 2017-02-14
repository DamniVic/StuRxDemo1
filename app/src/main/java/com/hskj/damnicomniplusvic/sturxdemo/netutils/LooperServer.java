package com.hskj.damnicomniplusvic.sturxdemo.netutils;

import android.support.annotation.Nullable;

import com.hskj.damnicomniplusvic.sturxdemo.bean.LooperThing;
import com.hskj.damnicomniplusvic.sturxdemo.bean.LooperToken;

import java.util.Random;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by DAMNICOMNIPLUSVIC on 2017/2/10.
 */

public class LooperServer {
    Random mRandom=new Random();
    public Observable<LooperToken> getLooperToken(@Nullable String LooperAuth)
    {
        Observable<LooperToken> observable=Observable.just(LooperAuth)
                .map(new Func1<String, LooperToken>() {
                    @Override
                    public LooperToken call(String s) {
                        int LooperNetWorkCost=mRandom.nextInt(500)+500;
                        try {
                            Thread.sleep(LooperNetWorkCost);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        LooperToken looperToken=new LooperToken();
                        looperToken.Token=createToken();
                        return looperToken;
                    }
                });
        return observable;
    }

    public Observable<LooperThing> getLooperData(LooperToken looperToken)
    {
        Observable<LooperThing> observable=Observable.just(looperToken)
                .map(new Func1<LooperToken, LooperThing>() {
                    @Override
                    public LooperThing call(LooperToken looperToken) {
                        int LooperNetWorkCost =mRandom.nextInt(500)+500;
                        try {
                            Thread.sleep(LooperNetWorkCost);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(looperToken.expired)
                        {
                            throw new IllegalArgumentException("Token expired");
                        }
                        LooperThing looperThing=new LooperThing();
                        looperThing.id= (int) (System.currentTimeMillis()%1000);
                        looperThing.name="Looper_User_"+looperThing.id;
                        return looperThing;
                    }
                });
        return observable;
    }

    private static String createToken() {
        return "fake_token_" + System.currentTimeMillis() % 10000;
    }
}
