package com.example.administrator.my80.mvp.m.entity.index;

import java.io.Serializable;
import java.util.List;

/**
 * 首页数据
 */

public class Index implements Serializable {

    public List<Title> titleList ;//头部数据-- 包括滚动图片信息

    public List<Purchase> purchaseList ;

    public List<BPage> seedlingList ;

    public List<Article> articleList ;//头条新闻滚动 数据

    public List<HomeStore> storeList ;// 商店数据  列表  底部使用

    public List<Bannar> bannarList ;//顶部banner  数据





}
