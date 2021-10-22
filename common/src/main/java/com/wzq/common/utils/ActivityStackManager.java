package com.wzq.common.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.util.Stack;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/11/18
 * desc   : Activity 栈管理
 */
public final class ActivityStackManager {

    private static volatile ActivityStackManager sInstance;
    private final static String TAG = "ActivityStackManager";

    private final Stack<Activity> mActivitySet = new Stack<>();

    // 当前 Activity 对象标记
    private String mCurrentTag;

    private ActivityStackManager() {
    }

    public static ActivityStackManager getInstance() {
        // 加入双重校验锁
        if (sInstance == null) {
            synchronized (ActivityStackManager.class) {
                if (sInstance == null) {
                    sInstance = new ActivityStackManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取栈顶的 Activity
     */
    public Activity getTopActivity() {
        Log.d(TAG, "getTopActivity: 1111");
        if (mActivitySet.empty())
            return null;
        Log.d(TAG, "getTopActivity: 2222"+mActivitySet.peek().getClass());
        return mActivitySet.peek();
    }


    /**
     * 销毁所有的 Activity
     */
    public void finishAllActivities() {
        finishAllActivities((Class<? extends Activity>) null);
    }

    /**
     * 销毁指定activity
     * @param aclass
     */
    public final void finishActivity(Class<? extends Activity> aclass) {

        for (int i = 0; i < mActivitySet.size(); i++) {
            Activity mActivity = mActivitySet.elementAt(i);
            // 如果不是白名单上面的 Activity 就销毁掉
            if (mActivity.getClass() == aclass) {
                mActivity.finish();
                mActivitySet.remove(mActivity);
            }
        }
    }

    /**
     * 清理其他activity
     * @param aclass
     */
    public final void finishOtherActivity(Class<? extends Activity> aclass) {

        for (int i = 0; i < mActivitySet.size(); i++) {
            Activity mActivity = mActivitySet.elementAt(i);
            // 如果不是白名单上面的 Activity 就销毁掉
            if (mActivity.getClass() != aclass) {
                Log.d(TAG, "finishOrtherActivity: remove " + mActivity.getClass().getName());
                mActivity.finish();
                mActivitySet.remove(mActivity);
            }
        }
    }

    /**
     * 销毁所有的 Activity，除这些 Class 之外的 Activity
     */
    @SafeVarargs
    public final void finishAllActivities(Class<? extends Activity>... classArray) {
        Log.d(TAG, "finishAllActivities:1111 size="+mActivitySet.size());
        for (int i = 0; i < mActivitySet.size(); i++) {
            boolean whiteClazz = false;
            Activity activity = mActivitySet.elementAt(i);
            for (Class<? extends Activity> clazz : classArray) {
                if (activity.getClass() == clazz) {
                    whiteClazz = true;
                }
            }
            // 如果不是白名单上面的 Activity 就销毁掉
            if (!whiteClazz) {
                Log.d(TAG, "finishAllActivities: remove "+activity.getClass().getName());
                activity.finish();
                mActivitySet.remove(activity);
            }
        }
        Log.d(TAG, "finishAllActivities:2222 size="+mActivitySet.size());
    }

    public void onActivityCreated(Activity activity) {
        Log.d(TAG, "finishAllActivities: onActivityCreated "+activity.getClass().getName());
        mActivitySet.push(activity);
    }

    public void onActivityDestroyed(Activity activity) {
        Log.d(TAG, "finishAllActivities: onActivityDestroyed " + activity.getClass().getName());
        if (mActivitySet.empty())
            return ;
        mActivitySet.remove(activity);
    }

    /**
     * 获取一个对象的独立无二的标记
     */
    private static String getObjectTag(Object object) {
        // 对象所在的包名 + 对象的内存地址
        return object.getClass().getName() + Integer.toHexString(object.hashCode());
    }

    /**
     *
     */
    public void appExit(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addCategory(Intent.CATEGORY_HOME);
        getInstance().getTopActivity().startActivity(intent);
        finishAllActivities();
        // API Level至少为8才能使用
        android.os.Process.killProcess(android.os.Process.myPid());
        System.gc();// 通知垃圾回收
        System.exit(0);
    }
}