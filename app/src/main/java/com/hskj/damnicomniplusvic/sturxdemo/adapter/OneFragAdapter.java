package com.hskj.damnicomniplusvic.sturxdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hskj.damnicomniplusvic.sturxdemo.R;
import com.hskj.damnicomniplusvic.sturxdemo.bean.ImageTextBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DAMNICOMNIPLUSVIC on 2017/2/8.
 */

public class OneFragAdapter extends RecyclerView.Adapter {
    List<ImageTextBean> mImageTextBeanList;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new OneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OneViewHolder oneViewHolder= (OneViewHolder) holder;
        ImageTextBean bean=mImageTextBeanList.get(position);
        Glide.with(holder.itemView.getContext()).load(bean.image_url).into(oneViewHolder.mImageView);
        oneViewHolder.mTextView.setText(bean.description);
    }

    @Override
    public int getItemCount() {
        return mImageTextBeanList==null?0:mImageTextBeanList.size();
    }

    public void setImageTextBeanList(List<ImageTextBean> list)
    {
        mImageTextBeanList=list;
        notifyDataSetChanged();
    }

    static class OneViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.imageIvg)ImageView mImageView;
        @BindView(R.id.descriptionTvg)TextView mTextView;
        public OneViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
