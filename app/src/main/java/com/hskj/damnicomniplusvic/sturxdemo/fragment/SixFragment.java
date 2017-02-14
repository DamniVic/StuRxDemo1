package com.hskj.damnicomniplusvic.sturxdemo.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hskj.damnicomniplusvic.sturxdemo.R;
import com.hskj.damnicomniplusvic.sturxdemo.adapter.OneFragAdapter;
import com.hskj.damnicomniplusvic.sturxdemo.bean.ImageTextBean;
import com.hskj.damnicomniplusvic.sturxdemo.utils.Data;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class SixFragment extends BaseFragment {

    @BindView(R.id.swipe6)SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycle6)RecyclerView mRecyclerView;
    @BindView(R.id.text6)AppCompatTextView mAppCompatTextView;

    private long startingtime;
    public SixFragment() {
        // Required empty public constructor
    }

    OneFragAdapter mOneFragAdapter=new OneFragAdapter();

    @OnClick(R.id.loaddata6)
    void loaddata()
    {
        mSwipeRefreshLayout.setRefreshing(true);
        unsubscribe();
        startingtime=System.currentTimeMillis();
        mSubscription=Data.getInstance()
                .subscribeData(new Observer<List<ImageTextBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), R.string.loading_failed, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<ImageTextBean> list) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        int loadingTime= (int) (System.currentTimeMillis()-startingtime);
                        mAppCompatTextView.setText(getString(R.string.loading_time_and_source, loadingTime, Data.getInstance().getDataSourceText()));
                        mOneFragAdapter.setImageTextBeanList(list);
                    }
                });
    }

    @OnClick(R.id.clearMemoryCacheBt)
    void clearMemoryCacheBt()
    {
        Data.getInstance().clearMemoryCache();
        mOneFragAdapter.setImageTextBeanList(null);
        Toast.makeText(getActivity(), R.string.memory_cache_cleared, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.clearMemoryAndDiskCacheBt)
    void clearMemoryAndDiskCacheBt()
    {
        Data.getInstance().clearMemoryAndDiskCache();
        mOneFragAdapter.setImageTextBeanList(null);
        Toast.makeText(getActivity(),R.string.memory_and_disk_cache_cleared,Toast.LENGTH_SHORT).show();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_six, container, false);
        ButterKnife.bind(this,view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRecyclerView.setAdapter(mOneFragAdapter);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        mSwipeRefreshLayout.setEnabled(false);
        return view;
    }

    @Override
    protected int getTitleRes() {
        return R.string.title_cache;
    }

    @Override
    protected int getConmentRes() {
        return R.layout.dialog_six;
    }
}
