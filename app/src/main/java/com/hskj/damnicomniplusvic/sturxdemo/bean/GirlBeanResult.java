package com.hskj.damnicomniplusvic.sturxdemo.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DAMNICOMNIPLUSVIC on 2017/2/9.
 */

public class GirlBeanResult {
    public boolean error;
    public @SerializedName("results")List<GirlBean> mGirlBeanList;
}
