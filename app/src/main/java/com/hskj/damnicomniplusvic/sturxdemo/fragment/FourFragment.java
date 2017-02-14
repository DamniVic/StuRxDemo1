package com.hskj.damnicomniplusvic.sturxdemo.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hskj.damnicomniplusvic.sturxdemo.R;
import com.hskj.damnicomniplusvic.sturxdemo.bean.LooperThing;
import com.hskj.damnicomniplusvic.sturxdemo.bean.LooperToken;
import com.hskj.damnicomniplusvic.sturxdemo.netutils.LooperServer;
import com.hskj.damnicomniplusvic.sturxdemo.netutils.NetConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class FourFragment extends BaseFragment {

    @BindView(R.id.swipe4)SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.text4)AppCompatTextView mAppCompatTextView;

    public FourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_four, container, false);
        ButterKnife.bind(this,view);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        mSwipeRefreshLayout.setEnabled(false);
        return view;
    }

    @OnClick(R.id.loaddata4)
    void loaddata()
    {
        unsubscribe();
        mSwipeRefreshLayout.setRefreshing(true);
        final LooperServer looperServer= NetConfig.getLooperServer();
        Observable<LooperThing> observable=looperServer.getLooperToken("useless char")
                .flatMap(new Func1<LooperToken, Observable<LooperThing>>() {
                    @Override
                    public Observable<LooperThing> call(LooperToken looperToken) {
                        return looperServer.getLooperData(looperToken);
                    }
                });
        mSubscription=observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<LooperThing>() {
                    @Override
                    public void call(LooperThing looperThing) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAppCompatTextView.setText(getString(R.string.got_data, looperThing.id, looperThing.name));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(),R.string.loading_failed,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected int getTitleRes() {
        return R.string.title_token;
    }

    @Override
    protected int getConmentRes() {
        return R.layout.dialog_four;
    }
}
