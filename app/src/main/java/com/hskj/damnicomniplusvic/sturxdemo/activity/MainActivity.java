package com.hskj.damnicomniplusvic.sturxdemo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.hskj.damnicomniplusvic.sturxdemo.R;
import com.hskj.damnicomniplusvic.sturxdemo.fragment.FiveFragment;
import com.hskj.damnicomniplusvic.sturxdemo.fragment.FourFragment;
import com.hskj.damnicomniplusvic.sturxdemo.fragment.OneFragment;
import com.hskj.damnicomniplusvic.sturxdemo.fragment.SixFragment;
import com.hskj.damnicomniplusvic.sturxdemo.fragment.ThreeFragment;
import com.hskj.damnicomniplusvic.sturxdemo.fragment.TwoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tablayout)TabLayout mTabLayout;
    @BindView(R.id.toolbar)Toolbar mToolbar;
    @BindView(R.id.viewpager)ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        setSupportActionBar(mToolbar);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position)
                {
                    case 0:
                        return new OneFragment();
                    case 1:
                        return new TwoFragment();
                    case 2:
                        return new ThreeFragment();
                    case 3:
                        return new FourFragment();
                    case 4:
                        return new FiveFragment();
                    case 5:
                        return new SixFragment();
                    default:
                        return new OneFragment();
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position)
                {
                    case 0:
                        return getResources().getString(R.string.one);
                    case 1:
                        return getResources().getString(R.string.two);
                    case 2:
                        return getResources().getString(R.string.three);
                    case 3:
                        return getResources().getString(R.string.four);
                    case 4:
                        return getResources().getString(R.string.five);
                    case 5:
                        return getResources().getString(R.string.six);
                    default:
                        return getResources().getString(R.string.one);
                }
            }

            @Override
            public int getCount() {
                return 6;
            }
        });
        mTabLayout.setupWithViewPager(mViewPager,true);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
