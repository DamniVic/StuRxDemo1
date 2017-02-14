package com.hskj.damnicomniplusvic.sturxdemo.fragment;


import android.app.AlertDialog;
import android.support.v4.app.Fragment;

import com.hskj.damnicomniplusvic.sturxdemo.R;

import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by DAMNICOMNIPLUSVIC on 2017/2/8.
 */

public abstract class BaseFragment extends Fragment {
    protected Subscription mSubscription;
    @OnClick(R.id.tipBt)
    void clickTipBt()
    {
        new AlertDialog.Builder(getActivity())
                .setTitle(getTitleRes())
                .setView(getActivity().getLayoutInflater().inflate(getConmentRes(),null))
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unsubscribe();
    }
    protected void unsubscribe()
    {
        if(mSubscription!=null&&!mSubscription.isUnsubscribed())
        {
            mSubscription.unsubscribe();
        }
    }

    protected abstract int getTitleRes();

    protected abstract int getConmentRes();
}
