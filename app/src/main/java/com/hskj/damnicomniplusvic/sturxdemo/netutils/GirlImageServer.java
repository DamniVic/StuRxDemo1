package com.hskj.damnicomniplusvic.sturxdemo.netutils;

import com.hskj.damnicomniplusvic.sturxdemo.bean.GirlBeanResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by DAMNICOMNIPLUSVIC on 2017/2/9.
 */

public interface GirlImageServer {
    @GET("data/福利/{number}/{page}")
    Observable<GirlBeanResult>getGirls(@Path("number") int number,@Path("page")int page);
}
