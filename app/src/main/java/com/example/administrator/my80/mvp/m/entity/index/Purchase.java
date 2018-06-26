package com.example.administrator.my80.mvp.m.entity.index;

import java.util.List;

/**
 * Created by Administrator on 2017/9/6 0006.
 */

public class Purchase {


    public boolean showQuote;
    public String servicePoint = "0.0";

    public long lastTime;
    /**
     * id : 21fdc65b66d7408ca0f546518efb9081
     * createBy : 1
     * createDate : 2017-03-09 15:58:54
     * cityCode : 3502
     * cityName : 厦门
     * prCode : 35
     * ciCode : 3502
     * coCode :
     * twCode :
     * projectName : 代购模式测试项目
     * projectType : protocol
     * consumerId : a6e6c779fefb4a41b0188dfc5ec76ad5
     * consumerUserId : 5315ff5c-e118-4a5d-abb7-338c90d8ce73
     * consumerName : 新模式测试客户
     * name : 测试代购项目
     * num : P0000318
     * projectId : 2c08b5b58e5d45a0b299bdeb44b72f37
     * receiptDate : 2017-03-31
     * publishDate : 2017-03-09 16:26:12
     * closeDate : 2017-07-20 15:57
     * needInvoice : false
     * customerId : 1
     * status : published
     * source : admin
     * quoteDesc : <p>
     * 报价截止时间：XXXX年XX月XX日
     * </p>
     * <p>
     * 用苗地：XXXX&nbsp;<br />
     * 报价要求：<span style="color:#E53333;">XXX</span>（包含以下费用：1、上车费用。2、税金。）&nbsp;<br />
     * 发票要求：XXXX&nbsp;<br />
     * <span style="color:#E53333;">质量要求：XXXX（接近房地产货）</span><br />
     * <span style="color:#E53333;">测量要求：XXXXX。</span>
     * </p>
     * <p>
     * <span style="color:#E53333;line-height:1.5;">种植要求：XXXX</span>
     * </p>
     * <p>
     * 报价限制：请供应商报价时对应品种必须上传真实有效的图片，（Ps:有照片者优先考虑）
     * </p>
     * isCooper : false
     * type : quoting
     * dispatchPhone : 18250876026
     * dispatchName : 叶珊珊
     * isPrivate : true
     * authcPhone : 18616394226
     * buyer : {"id":"-1","prCode":"","ciCode":"","coCode":"","twCode":"","phone":"4006-579-888","companyName":"厦门花木易购电子商务有限公司","isInvoices":false,"agentTypeName":"","isDirectAgent":false,"displayName":"厦门花木易购电子商务有限公司","adminDisplayName":"厦门花木易购电子商务有限公司","displayPhone":"4006-579-888"}
     * statusName : 已发布
     * quoteCountJson : 15
     * itemCountJson : 4
     * lastDays : 68
     * itemNameList : ["白蜡","红花锦鸡儿","红钻","白花鸡蛋花"]
     * purchaseFormId : 21fdc65b66d7408ca0f546518efb9081
     * typeName : 采购中
     * blurProjectName : 代购模式测试项目
     * blurName : 测试****
     */

    public String id;
    public String createBy;
    public String createDate;
    public String cityCode;
    public String cityName;
    public String prCode;
    public String ciCode;
    public String coCode;
    public String twCode;
    public String projectName;
    public String projectType;
    public String consumerId;
    public String consumerUserId;
    public String consumerName;
    public String name;
    public String num;
    public String projectId;
    public String receiptDate;
    public String publishDate;
    public String closeDate;
    public boolean needInvoice;
    public String customerId;
    public String status;
    public String source;
    public String quoteDesc;
    public boolean isCooper;
    public String type;
    public String dispatchPhone;
    public String dispatchName;
    public boolean isPrivate;
    public String authcPhone;
    public String statusName;
    public int quoteCountJson = 0;
    public String itemCountJson = "0";
    public String lastDays;
    public String purchaseFormId;
    public String typeName;
    public String blurProjectName;
    public String blurName;

    public String consumerFullName = "-";//用苗单位
    public boolean showConsumerName = false;// 是否显示用苗单位

    public List<String> itemNameList;
    public AttrDataBean attrData = new AttrDataBean();


//    "attrData": {"closeDateStr": "2017年06月09日 14点10分"},

    public static class AttrDataBean {
        public String closeDateStr = "";
        public Boolean isSupplier = false;
        public double servicePrice;

    }
}
