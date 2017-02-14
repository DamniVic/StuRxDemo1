package com.hskj.damnicomniplusvic.sturxdemo.utils;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hskj.damnicomniplusvic.sturxdemo.R;
import com.hskj.damnicomniplusvic.sturxdemo.bean.ImageTextBean;
import com.hskj.damnicomniplusvic.sturxdemo.netutils.NetConfig;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by DAMNICOMNIPLUSVIC on 2017/2/10.
 */

public class Data {
    private static Data instance;
    private static final int DATA_SOURCE_MEMORY = 1;
    private static final int DATA_SOURCE_DISK = 2;
    private static final int DATA_SOURCE_NETWORK = 3;

    @IntDef({DATA_SOURCE_DISK, DATA_SOURCE_MEMORY, DATA_SOURCE_NETWORK})
    @interface DataSource {
    }

    BehaviorSubject<List<ImageTextBean>> mBehaviorSubject;
    private int dataSource;

    private Data() {
    }

    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    private void setDataSource(@DataSource int dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSourceText()
    {
        int dataSourceTextRes;
        switch (dataSource)
        {
            case DATA_SOURCE_MEMORY:
                dataSourceTextRes = R.string.data_source_memory;
                break;
            case DATA_SOURCE_DISK:
                dataSourceTextRes = R.string.data_source_disk;
                break;
            case DATA_SOURCE_NETWORK:
                dataSourceTextRes = R.string.data_source_network;
                break;
            default:
                dataSourceTextRes = R.string.data_source_network;
        }
        return AppRoot.getINSTANCE().getString(dataSourceTextRes);
    }

    public void loadFromNetwork()
    {
        NetConfig.getmGirlImageServer()
                .getGirls(100,1)
                .subscribeOn(Schedulers.io())
                .map(GirlImageResultToImageBeanList.getINSTANCE())
                .doOnNext(new Action1<List<ImageTextBean>>() {
                    @Override
                    public void call(List<ImageTextBean> list) {
                        Database.getINSTANCE().writeItems(list);
                    }
                })
                .subscribe(new Action1<List<ImageTextBean>>() {
                    @Override
                    public void call(List<ImageTextBean> list) {
                        mBehaviorSubject.onNext(list);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    public Subscription subscribeData(@NonNull Observer<List<ImageTextBean>> observer)
    {
        if(mBehaviorSubject==null)
        {
            mBehaviorSubject=BehaviorSubject.create();
            Observable.create(new Observable.OnSubscribe<List<ImageTextBean>>()
            {
                @Override
                public void call(Subscriber<? super List<ImageTextBean>> subscriber) {
                    List<ImageTextBean> items = Database.getINSTANCE().readItems();
                    if (items == null) {
                        setDataSource(DATA_SOURCE_NETWORK);
                        loadFromNetwork();
                        Log.i(TAG, "call: load from network");
                    } else {
                        setDataSource(DATA_SOURCE_DISK);
                        subscriber.onNext(items);
                        Log.i(TAG, "call: load from disk");
                    }
                }
            })
                    .subscribeOn(Schedulers.io())
                    .subscribe(mBehaviorSubject);
        }
        else
        {
            setDataSource(DATA_SOURCE_MEMORY);
            Log.i(TAG, "subscribeData: load from memory");
        }
        return mBehaviorSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void clearMemoryCache() {
        mBehaviorSubject = null;
    }

    public void clearMemoryAndDiskCache() {
        clearMemoryCache();
        Database.getINSTANCE().delete();
    }
}
