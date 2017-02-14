package com.hskj.damnicomniplusvic.sturxdemo.utils;

import com.hskj.damnicomniplusvic.sturxdemo.bean.GirlBean;
import com.hskj.damnicomniplusvic.sturxdemo.bean.GirlBeanResult;
import com.hskj.damnicomniplusvic.sturxdemo.bean.ImageTextBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by DAMNICOMNIPLUSVIC on 2017/2/9.
 */

public class GirlImageResultToImageBeanList implements Func1<GirlBeanResult, List<ImageTextBean>> {
    private static GirlImageResultToImageBeanList INSTANCE=new GirlImageResultToImageBeanList();
    private GirlImageResultToImageBeanList(){}
    public static GirlImageResultToImageBeanList getINSTANCE()
    {
        return INSTANCE;
    }
    @Override
    public List<ImageTextBean> call(GirlBeanResult girlBeanResult) {
        List<GirlBean> girls = girlBeanResult.mGirlBeanList;
        List<ImageTextBean> imageTextBeanList=new ArrayList<>(girls.size());
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        for(GirlBean bean:girls)
        {
            ImageTextBean bean1=new ImageTextBean();
            try {
                Date date = inputFormat.parse(bean.createdAt);
                bean1.description = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                bean1.description = "unknown date";
            }
            bean1.image_url=bean.url;
            imageTextBeanList.add(bean1);
        }
        return imageTextBeanList;
    }
}
