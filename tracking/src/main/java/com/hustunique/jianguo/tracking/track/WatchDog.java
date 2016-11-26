package com.hustunique.jianguo.tracking.track;

import android.util.Log;

import com.hustunique.jianguo.tracking.Config;

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

    public void watchOverViewTree(String clz) {
        if (isWatched(clz)) {
            Log.d(TAG, "Current activity is being watched by path: " + config.getPath(clz));
            //TODO: use dynamic proxy to replace original OnClickListener

        }
    }

    private boolean isWatched(String decorClz) {
        return config.getActivityClz(decorClz);
    }
}
