package com.example.administrator.my80.mvp.m.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/28.
 */

public class UserInfo implements Serializable {


    /**
     * id : 2876f7e0f51c4153aadc603b661fedfa
     * remarks :
     * createDate : 2017-03-28 14:09:18
     * cityCode : 120102
     * prCode : 12
     * ciCode : 1201
     * coCode : 120102
     * twCode :
     * coCity : {"id":"362","name":"河东","fullName":"天津市河东区","cityCode":"120102","parentCode":"1201","level":3}
     * userName : u_17074990702
     * realName :
     * publicName : 姓名测试
     * publicPhone : 17074990702
     * plainPassword : 123456aa
     * sex : 1
     * phone : 17074990702
     * email :
     * address :
     * variety :
     * companyName : 明白
     * status : enabled
     * isAgent : false
     * isClerk : false
     * isInvoices : false
     * permissions : PUBLISH_PURCHASE,ADD_TO_CART,PUBLISH_SEEDLING,QUOTE,QUOTE_BEFORE
     * storeId : ebe98b46013c40c78b003921c54c5e0c
     * headImage : http://image.hmeg.cn/upload/image/201703/84da1c9e8de04505b5e102a4f5987bb8.png
     * isPartners : false
     * cashOnDelivery : false
     * receiptMsg : true
     * isQuote : true
     * showQuote : true
     * showQuoteCount : true
     * supplierIsAgree : false
     * isActivated : true
     * isPartner : false
     * recUserId :
     * agentTypeName :
     * isDirectAgent : false
     * displayName : 明白
     * adminDisplayName : 明白
     * displayPhone : 17074990702
     * permissionsName : 发布采购信息，添加购物车，发布苗木资源，采购报价，报价信息先发后审，
     */

    public String id;
    public String remarks;
    public String createDate;
    public String cityCode;
    public String prCode;
    public String ciCode;
    public String coCode;
    public String twCode;
    public CoCityBean coCity;
    public String userName;
    public String realName;
    public String publicName;
    public String publicPhone;
    public String plainPassword;
    public String sex;
    public String phone;
    public String email;
    public String address;
    public String variety;
    public String companyName;
    public String status;
    public boolean isAgent;
    public boolean isClerk;
    public boolean isInvoices;
    public String permissions;
    public String storeId = "";
    public String headImage;
    public boolean isPartners;
    public boolean cashOnDelivery;
    public boolean receiptMsg;
    public boolean isQuote; // 是否有报价权限
    public boolean showQuote;
    public boolean showQuoteCount;
    public boolean supplierIsAgree;
    public boolean isActivated;
    public boolean isPartner;
    public String recUserId;
    public String agentTypeName;
    public boolean isDirectAgent;
    public String displayName;
    public String adminDisplayName;
    public String displayPhone;
    public String permissionsName;

    public static class CoCityBean {
        /**
         * id : 362
         * name : 河东
         * fullName : 天津市河东区
         * cityCode : 120102
         * parentCode : 1201
         * level : 3
         */

        public String id;
        public String name;
        public String fullName;
        public String cityCode;
        public String parentCode;
        public int level;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id='" + id + '\'' +
                ", remarks='" + remarks + '\'' +
                ", createDate='" + createDate + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", prCode='" + prCode + '\'' +
                ", ciCode='" + ciCode + '\'' +
                ", coCode='" + coCode + '\'' +
                ", twCode='" + twCode + '\'' +
                ", coCity=" + coCity +
                ", userName='" + userName + '\'' +
                ", realName='" + realName + '\'' +
                ", publicName='" + publicName + '\'' +
                ", publicPhone='" + publicPhone + '\'' +
                ", plainPassword='" + plainPassword + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", variety='" + variety + '\'' +
                ", companyName='" + companyName + '\'' +
                ", status='" + status + '\'' +
                ", isAgent=" + isAgent +
                ", isClerk=" + isClerk +
                ", isInvoices=" + isInvoices +
                ", permissions='" + permissions + '\'' +
                ", storeId='" + storeId + '\'' +
                ", headImage='" + headImage + '\'' +
                ", isPartners=" + isPartners +
                ", cashOnDelivery=" + cashOnDelivery +
                ", receiptMsg=" + receiptMsg +
                ", isQuote=" + isQuote +
                ", showQuote=" + showQuote +
                ", showQuoteCount=" + showQuoteCount +
                ", supplierIsAgree=" + supplierIsAgree +
                ", isActivated=" + isActivated +
                ", isPartner=" + isPartner +
                ", recUserId='" + recUserId + '\'' +
                ", agentTypeName='" + agentTypeName + '\'' +
                ", isDirectAgent=" + isDirectAgent +
                ", displayName='" + displayName + '\'' +
                ", adminDisplayName='" + adminDisplayName + '\'' +
                ", displayPhone='" + displayPhone + '\'' +
                ", permissionsName='" + permissionsName + '\'' +
                '}';
    }
}
