package com.hskj.damnicomniplusvic.sturxdemo.netutils;

import com.hskj.damnicomniplusvic.sturxdemo.bean.ImageTextBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by DAMNICOMNIPLUSVIC on 2017/2/8.
 */

public interface ImageServer {
    @GET("search")
    Observable<List<ImageTextBean>> loaddata1(@Query("q") String query);
}
