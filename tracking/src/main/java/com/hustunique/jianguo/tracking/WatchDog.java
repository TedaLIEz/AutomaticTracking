package com.hustunique.jianguo.tracking;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.hustunique.jianguo.tracking.hook.HookHelper;

/**
 * Created by JianGuo on 11/25/16.
 * WatchDog
 */

public class WatchDog {
    private static final String TAG = "WatchDog";
    private Config config;

    public WatchDog(Config config) {
        this.config = config;
    }

    public void watchOver(Application application) {
        try {
            HookHelper.hookActivityThread();
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            Log.wtf(TAG, e);
        }
    }
}
