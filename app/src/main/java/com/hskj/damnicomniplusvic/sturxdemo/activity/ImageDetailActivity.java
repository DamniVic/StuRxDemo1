package com.hskj.damnicomniplusvic.sturxdemo.activity;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.hskj.damnicomniplusvic.sturxdemo.R;
import com.hskj.damnicomniplusvic.sturxdemo.costumview.SingleTouchView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageDetailActivity extends AppCompatActivity implements View.OnTouchListener {

    private static String TAG="ImageDetailActivity";
    @BindView(R.id.image_detail_image)SingleTouchView mAppCompatImageView;
    private float oldX,oldY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        String imageurl=intent.getStringExtra("imageurl");
        Glide.with(ImageDetailActivity.this).load(imageurl).into(mAppCompatImageView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Matrix oldMatrix=mAppCompatImageView.getImageMatrix();
        Log.i(TAG, "onResume: "+oldMatrix);
        mAppCompatImageView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_DOWN:
//                float[] values=new float[9];
//                mAppCompatImageView.getImageMatrix().getValues(values);
//                int width=mAppCompatImageView.getDrawable().getMinimumWidth();
//                int height=mAppCompatImageView.getDrawable().getMinimumHeight();
//                oldX=event.getX();
//                oldY=event.getY();
//                Log.i(TAG, "onTouch: width:"+width+" height:"+height);
//                Log.i(TAG, "onTouch: oldX:"+oldX+" oldY："+oldY);
//                Log.i(TAG, "onTouch: matrix:"+values);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }
}
