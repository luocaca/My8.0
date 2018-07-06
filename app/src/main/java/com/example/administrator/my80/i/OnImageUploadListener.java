package com.example.administrator.my80.i;

/**
 * 图片上传接口
 */

public interface OnImageUploadListener {


    /* 上传成功  ---   成功放回 string  json  */

    void onSucceed(String json);

    /* 上传失败 */
    void onFailed(String msg);


}
