package com.example.administrator.my80.mvp.m.entity.query;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/7 0007.
 */

public class QueryPage implements Serializable{

    public String searchSpec = "";
    public String specMinValue = "";
    public String specMaxValue = "";


    //高度
    public String minHeight = "";
    public String maxHeight = "";
    //冠幅
    public String minCrown = "";
    public String maxCrown = "";
    //杆径
    public String minRod = "";
    public String maxRod = "";


    public String searchKey = "";
    public String supportTradeType = "";
    public String firstSeedlingTypeId = "";
    public String secondSeedlingTypeId = "";
    public String cityCode = "";
    public String plantTypes = "";
    public String orderBy = "";

    public int pageSize = 20;
    public int pageIndex = 0;


    public String latitude = "0.0";
    public String longitude = "0.0";


    @Override
    public String toString() {
        return "QueryPage{" +
                "searchSpec='" + searchSpec + '\'' +
                ", specMinValue='" + specMinValue + '\'' +
                ", specMaxValue='" + specMaxValue + '\'' +
                ", minHeight='" + minHeight + '\'' +
                ", maxHeight='" + maxHeight + '\'' +
                ", minCrown='" + minCrown + '\'' +
                ", maxCrown='" + maxCrown + '\'' +
                ", minRod='" + minRod + '\'' +
                ", maxRod='" + maxRod + '\'' +
                ", searchKey='" + searchKey + '\'' +
                ", supportTradeType='" + supportTradeType + '\'' +
                ", firstSeedlingTypeId='" + firstSeedlingTypeId + '\'' +
                ", secondSeedlingTypeId='" + secondSeedlingTypeId + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", plantTypes='" + plantTypes + '\'' +
                ", orderBy='" + orderBy + '\'' +
                ", pageSize=" + pageSize +
                ", pageIndex=" + pageIndex +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
