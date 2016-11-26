package com.hustunique.jianguo.tracking.track;

import android.app.Activity;
import android.os.IBinder;
import android.util.ArrayMap;
import android.util.Log;

import com.hustunique.jianguo.tracking.Config;
import com.hustunique.jianguo.tracking.hook.HookHelper;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by JianGuo on 11/25/16.
 * WatchDog
 */

public class WatchDog {
    private static final String TAG = "WatchDog";
    private Config config;
    private ArrayMap<String, IBinder> mBinders;
    private static WatchDog sInstance;
    public static WatchDog getInstance(Config config) {
        if (sInstance == null) {
            sInstance = new WatchDog(config);
        }
        return sInstance;
    }
    private WatchDog(Config config) {
        this.config = config;
        mBinders = new ArrayMap<>();
    }

    public void watchOverViewTree(String clz) {
        if (isWatched(clz)) {
            Log.d(TAG, "Current activity is being watched by path: " + config.getPath(clz));
            //TODO: use dynamic proxy to replace original OnClickListener
            IBinder token = mBinders.get(clz);
            try {
                Activity activity = HookHelper.hookActivity(token);
                Log.i(TAG, "Current watching: " + activity.getClass().getName());
                traverse(activity);
            } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                Log.wtf(TAG, e);
            }
        }
    }

    private void traverse(Activity activity) {
        //TODO: hook window rather than activity!
    }

    private boolean isWatched(String decorClz) {
        return config.getActivityClz(decorClz);
    }

    public void addToTokenList(String clz, IBinder token) {
        mBinders.put(clz, token);
    }


}
