package com.example.administrator.my80.mvp.m.entity.shop;

/**
 * Created by Administrator on 2017/9/7 0007.
 */

public class Page<T> {


    public int pageNo;
    public int pageSize;
    public int total;
    public int firstResult;
    public int maxResults;
    public T data ;
}
