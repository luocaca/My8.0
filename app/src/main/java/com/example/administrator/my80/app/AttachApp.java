package com.example.administrator.my80.app;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * 自定义 app
 */

public class AttachApp  {



    public String appaction_id ="657314b4cd99fefb25596349e9bb06dd";

    public AttachApp(Application baseApplication)
    {


        Bmob.initialize(baseApplication,appaction_id);


    }

}
