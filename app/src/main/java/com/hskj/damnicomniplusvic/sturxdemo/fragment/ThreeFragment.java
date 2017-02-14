package com.hskj.damnicomniplusvic.sturxdemo.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hskj.damnicomniplusvic.sturxdemo.R;
import com.hskj.damnicomniplusvic.sturxdemo.adapter.OneFragAdapter;
import com.hskj.damnicomniplusvic.sturxdemo.bean.ImageTextBean;
import com.hskj.damnicomniplusvic.sturxdemo.netutils.NetConfig;
import com.hskj.damnicomniplusvic.sturxdemo.utils.GirlImageResultToImageBeanList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreeFragment extends BaseFragment {

    @BindView(R.id.recycle3)RecyclerView mRecyclerView;
    @BindView(R.id.swipe3)SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.appCompatEditText)AppCompatEditText edittext;

    OneFragAdapter mFragAdapter=new OneFragAdapter();

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
    public ThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_three, container, false);
        ButterKnife.bind(this,view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRecyclerView.setAdapter(mFragAdapter);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        mSwipeRefreshLayout.setEnabled(false);
        return  view;
    }

    @OnClick(R.id.loaddata3)
    void loaddata()
    {
        unsubscribe();
        mSwipeRefreshLayout.setRefreshing(true);
        mSubscription= Observable.zip(
                NetConfig.getmGirlImageServer()
                        .getGirls(200, 1)
                        .map(GirlImageResultToImageBeanList.getINSTANCE()),
                NetConfig.getmImageserver()
                        .loaddata1(edittext.getText().toString()),
                new Func2<List<ImageTextBean>, List<ImageTextBean>, List<ImageTextBean>>() {
                    @Override
                    public List<ImageTextBean> call(List<ImageTextBean> imageTextBeen, List<ImageTextBean> imageTextBeen2) {
                        List<ImageTextBean> list=new ArrayList<ImageTextBean>();
                        for(int i=0;i<imageTextBeen.size()/2;i++)
                        {
                            if(i<imageTextBeen2.size()) {
                                list.add(imageTextBeen.get(i*2));
                                list.add(imageTextBeen.get(i*2+1));
                                list.add(imageTextBeen2.get(i));
                            }else
                            {
                                list.add(imageTextBeen.get(i*2));
                                list.add(imageTextBeen.get(i*2+1));
                            }
                        }
                        return list;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    @Override
    protected int getTitleRes() {
        return R.string.title_zip;
    }

    @Override
    protected int getConmentRes() {
        return R.layout.dialog_three;
    }
}
