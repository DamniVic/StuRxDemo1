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
public class FiveFragment extends BaseFragment {

    @BindView(R.id.text5)AppCompatTextView mAppCompatTextView;
    @BindView(R.id.swipe5)SwipeRefreshLayout mSwipeRefreshLayout;
    final LooperToken looperToken=new LooperToken(true);
    boolean tokenUpdated;

    public FiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_five, container, false);
        ButterKnife.bind(this,view);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        mSwipeRefreshLayout.setEnabled(false);
        return view;
    }

    @OnClick(R.id.destory5)
    void destory_token()
    {
        looperToken.expired=true;
        Toast.makeText(getActivity(), R.string.token_destroyed, Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.loaddata5)
    void loaddata()
    {
        tokenUpdated=false;
        mSwipeRefreshLayout.setRefreshing(true);
        unsubscribe();
        final LooperServer looperServer=new LooperServer();
        Observable<LooperThing> observable=Observable.just(null)
                .flatMap(new Func1<Object, Observable<LooperThing>>() {
                    @Override
                    public Observable<LooperThing> call(Object o) {
                        return looperToken.Token==null? Observable.<LooperThing>error(new NullPointerException("Token is null")) :looperServer.getLooperData(looperToken);
                    }
                })
                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {
                        Observable<?> mObservable=observable.flatMap(new Func1<Throwable, Observable<?>>() {
                            @Override
                            public Observable<?> call(Throwable throwable) {
                                if(throwable instanceof NullPointerException || throwable instanceof IllegalArgumentException)
                                {
                                    Observable<?> o=looperServer.getLooperToken("useless char")
                                            .doOnNext(new Action1<LooperToken>() {
                                                @Override
                                                public void call(LooperToken looperToken1) {
                                                    tokenUpdated=true;
                                                    looperToken.Token=looperToken1.Token;
                                                    looperToken.expired=looperToken1.expired;
                                                }
                                            });
                                    return o;
                                }
                                return Observable.error(throwable);
                            }
                        });
                        return mObservable;
                    }
                });
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<LooperThing>() {
                    @Override
                    public void call(LooperThing looperThing) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        String token = looperToken.Token;
                        if (tokenUpdated) {
                            token += "(" + getString(R.string.updated) + ")";
                        }
                        mAppCompatTextView.setText(getString(R.string.got_token_and_data, token, looperThing.id, looperThing.name));
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
        return R.string.title_token_advanced;
    }

    @Override
    protected int getConmentRes() {
        return R.layout.dialog_five;
    }
}
