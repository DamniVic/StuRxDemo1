package com.hskj.damnicomniplusvic.sturxdemo.bean;

/**
 * Created by DAMNICOMNIPLUSVIC on 2017/2/10.
 */

public class LooperToken {
    public String Token;
    public boolean expired;
    public LooperToken()
    {}
    public LooperToken(boolean expired)
    {
        this.expired=expired;
    }
}
