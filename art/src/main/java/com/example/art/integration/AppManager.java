package com.example.art.integration;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * Created by luocaca on 2017/8/7 0007.
 */

@Singleton
public final class AppManager {
    protected final String TAG = this.getClass().getSimpleName();
    public static final String APPMANAGER_MESSAGE = "appmanager_message";
    public static final String IS_NOT_ADD_ACTIVITY_LIST = "is_not_add_activity_list";
    public static final int START_ACTIVITY = 0;
    public static final int SHOW_SNACKBAR = 1;
    public static final int KILL_ALL = 2;
    public static final int APP_EXIT = 3;
    private Application mApplication;


    //管理所有activity
    public List<Activity> mActivitiesList;
    //当前在前台的activity
    private Activity mCurrentActivity;


    @Inject
    public AppManager(Application application) {
        this.mApplication = application;
        EventBus.getDefault().register(this);
    }


    @Subscriber(tag = APPMANAGER_MESSAGE, mode = ThreadMode.MAIN)
    public void onReceive(Message message) {
        showSnackbar(message.obj.toString(),true);
        Log.e(TAG, "onReceive: android.os.Message"+message.toString());
    }

    @Subscriber(tag = APPMANAGER_MESSAGE, mode = ThreadMode.MAIN)
    public void onReceive(com.example.art.mvp.Message message) {
        showSnackbar(message.toString(),true);
        Log.e(TAG, "onReceive: com.example.art.mvp.Message"+message.toString());
    }

    /**
     * 封装使用 snackbar 显示内容
     */
    public void showSnackbar(String message, boolean isLong) {
        if (getCurrentActivity() == null) {
            Timber.tag(TAG).w("mCurrentActivity == null when showSnackbar(String,boolean)");
            return;
        }
        View view = getCurrentActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, message, isLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 将在前台的activity 保存
     *
     * @param currentActivity
     */
    public void setCurrentActivity(Activity currentActivity) {
        this.mCurrentActivity = currentActivity;
    }


    /**
     * 获取当前在前台的activity
     */
    public Activity getCurrentActivity() {
        return mCurrentActivity != null ?
                mCurrentActivity : mActivitiesList != null && mActivitiesList.size() > 0 ?
                mActivitiesList.get(mActivitiesList.size() - 1) : null;
    }


    /**
     * 返回一个存储所有未销毁的activity 的集合
     *
     * @return
     */
    public List<Activity> getActivitiesList() {
        if (mActivitiesList == null) {
            mActivitiesList = new LinkedList<>();
        }
        return mActivitiesList;
    }


    /**
     * 添加activity 集合
     */
    public void addActivity(Activity activity) {
        if (mActivitiesList == null) {
            mActivitiesList = new LinkedList<>();
        }
        synchronized (AppManager.this) {
            if (!mActivitiesList.contains(activity)) {
                mActivitiesList.add(activity);
            }
        }
    }


    /**
     * 删除集合里指定的activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (mActivitiesList == null) {
            Log.w(TAG, "mActivityList == null when remoreActivity(activity)");
        }

        synchronized (AppManager.this) {
            if (mActivitiesList.contains(activity)) {
                mActivitiesList.remove(activity);
            }
        }

    }

    /**
     * 删除集合里指定位置的activity
     *
     * @param location
     * @return
     */
    public Activity removeActivity(int location) {
        if (mActivitiesList == null) {
            Log.w(TAG, "mActivityList == null when removeActivity(int)");
        }

        synchronized (AppManager.this) {
            if (location > 0 && location < mActivitiesList.size()) {
                return mActivitiesList.remove(location);
            }
        }
        return null;
    }


    /**
     * 关闭指定的activity
     *
     * @param activityClass
     */
    public void killActivity(Class<?> activityClass) {
        if (mActivitiesList == null) {
            Log.w(TAG, "mActivityList == null when killActivity: ");
        }

        synchronized (AppManager.this) {
            /**
             *  if (activity.getClass().equals(activityClass)) {
             activity.finish();
             }
             */
            //stream 表达式  java 8 特征
//            mActivitiesList.stream().filter(act -> act.getClass().equals(activityClass)).forEach(Activity::finish);
            mActivitiesList.stream().filter(activity -> activity.getClass().equals(activityClass)).forEach(Activity::finish);
        }
    }

    /**
     * 指定的activity 实例 是否存活
     *
     * @param activity
     * @return
     */
    public boolean activityInstanceIsLive(Activity activity) {
        if (mActivitiesList == null) {
            Log.w(TAG, "mActivityList == null when activityInstanceIsLive: ");
        }
        return mActivitiesList.contains(activity);

    }


    /**
     * 指定的activity class 是否存活 （一个activity 可能有多个实例）
     *
     * @param activityClass
     * @return
     */
    public boolean activityClassIsLive(Class<?> activityClass) {
        if (mActivitiesList == null) {
            Log.w(TAG, "mActivityList == null when activityClassIsLive: ");
        }


        for (Activity activity : mActivitiesList) {
            if (activity.getClass().equals(activityClass)) {
                return true;
            }
        }
        return false;


//     return    mActivitiesList.stream().filter(activity -> activity.getClass().equals(activityClass)).forEach(activity -> activity.finish());
    }


    /**
     * 关闭所有activity
     */
    public void killAll() {
        Iterator<Activity> iterator = getActivitiesList().iterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();
            iterator.remove();
            next.finish();
        }
//        getActivitiesList()
//                .stream()
//                .forEach(activity ->{
//
//                    activity.finish();
//                } );

    }


    public void appExit() {
        try {
            killAll();
            if (mActivitiesList != null) {
                mActivitiesList = null;
                ActivityManager activityManager =
                        (ActivityManager) mApplication.getSystemService(Context.ACTIVITY_SERVICE);
                activityManager.killBackgroundProcesses(mApplication.getPackageName());
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
