package com.hskj.damnicomniplusvic.sturxdemo.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hskj.damnicomniplusvic.sturxdemo.R;
import com.hskj.damnicomniplusvic.sturxdemo.adapter.OneFragAdapter;
import com.hskj.damnicomniplusvic.sturxdemo.bean.ImageTextBean;
import com.hskj.damnicomniplusvic.sturxdemo.netutils.NetConfig;
import com.hskj.damnicomniplusvic.sturxdemo.utils.GirlImageResultToImageBeanList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends BaseFragment {
    @BindView(R.id.page)TextView currentpage;
    @BindView(R.id.prepage)AppCompatButton prepage;
    @BindView(R.id.swipe2)SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycle2)RecyclerView mRecyclerView;
    private int page=0;

    OneFragAdapter mTwoFragAdapter=new OneFragAdapter();

    Observer<List<ImageTextBean>>mObserver=new Observer<List<ImageTextBean>>() {
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
            currentpage.setText(getString(R.string.page_with_number, page));
            mTwoFragAdapter.setImageTextBeanList(imageTextBeen);
        }
    };
    public TwoFragment() {
        // Required empty public constructor
    }
    @OnClick({R.id.prepage,R.id.nextpage})
    void pageChanged(AppCompatButton button)
    {
        if(button.getTag().toString().equals("pre"))
        {
            loaddata(--page);
            if(page==1)
                prepage.setEnabled(false);
        }
        else
        {
            loaddata(++page);
            if(page==2)
                prepage.setEnabled(true);
        }
    }

    private void loaddata(int page)
    {
        mSwipeRefreshLayout.setRefreshing(true);
        unsubscribe();
        mSubscription= NetConfig.getmGirlImageServer()
                .getGirls(50,page)
                .map(GirlImageResultToImageBeanList.getINSTANCE())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribeOn(rx.schedulers.Schedulers.io())
                .subscribe(mObserver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_two, container, false);
        ButterKnife.bind(this,v);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRecyclerView.setAdapter(mTwoFragAdapter);
        mSwipeRefreshLayout.setColorSchemeColors(Color.YELLOW,Color.BLUE,Color.RED,Color.GREEN,Color.DKGRAY);
        mSwipeRefreshLayout.setEnabled(false);
        return v;
    }

    @Override
    protected int getTitleRes() {
        return R.string.title_map;
    }

    @Override
    protected int getConmentRes() {
        return R.layout.dialog_two;
    }
}
