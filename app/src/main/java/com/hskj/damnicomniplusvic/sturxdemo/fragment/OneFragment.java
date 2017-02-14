package com.hskj.damnicomniplusvic.sturxdemo.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.hskj.damnicomniplusvic.sturxdemo.R;
import com.hskj.damnicomniplusvic.sturxdemo.adapter.OneFragAdapter;
import com.hskj.damnicomniplusvic.sturxdemo.bean.ImageTextBean;
import com.hskj.damnicomniplusvic.sturxdemo.netutils.NetConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends BaseFragment {
    @BindView(R.id.swipe1)SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycle1)RecyclerView mRecyclerView;
    private String TAG="OneFragment";

    OneFragAdapter mFragAdapter=new OneFragAdapter();

    public OneFragment() {
        // Required empty public constructor
    }

    Observer<List<ImageTextBean>> mObserver=new Observer<List<ImageTextBean>>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(),R.string.loading_failed,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(List<ImageTextBean> imageTextBeen) {
            mSwipeRefreshLayout.setRefreshing(false);
            mFragAdapter.setImageTextBeanList(imageTextBeen);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_one, container, false);
        ButterKnife.bind(this,v);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRecyclerView.setAdapter(mFragAdapter);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.bisque), Color.BLACK, Color.RED, Color.CYAN);
        mSwipeRefreshLayout.setEnabled(false);
        return v;
    }

    @OnCheckedChanged({R.id.one_radio_one,R.id.one_radio_two,R.id.one_radio_three,R.id.one_radio_four})
    void CheckedChanged(RadioButton radioButton,boolean isChecked)
    {
        if(isChecked)
        {
            unsubscribe();
            mFragAdapter.setImageTextBeanList(null);
            mSwipeRefreshLayout.setRefreshing(true);
            String key;
            switch (radioButton.getText().toString())
            {
                case "薯条":
                    key="X";
                    break;
                case "汉堡包":
                    key="B";
                    break;
                case "炸鸡腿":
                    key="在下";
                    break;
                case "三明治":
                    key="装逼";
                    break;
                default:
                    key="可爱";
                    break;
            }
            Log.i(TAG, "CheckedChanged: "+key);
            loaddata(key);
        }
    }

    private void loaddata(String key)
    {
        Observable observable= NetConfig.getmImageserver()
                .loaddata1(key)
                .subscribeOn(Schedulers.io())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread());
        mSubscription=observable.subscribe(mObserver);
    }

    @Override
    protected int getTitleRes() {
        return R.string.one;
    }

    @Override
    protected int getConmentRes() {
        return R.layout.dialog_one;
    }
}
