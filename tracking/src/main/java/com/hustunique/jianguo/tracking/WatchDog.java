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
        Intent intent = application.getPackageManager().getLaunchIntentForPackage(application.getPackageName());
        if (intent == null) {
            throw new IllegalStateException("No default launcher activity found in application");
        }
        String launcherClz = intent.getComponent().getClassName();
        // TODO: Add observable pattern to launcherActivity
        Log.d(TAG, "Starting watching " + launcherClz);
        HookHelper.hookActivityManager(config);
    }
}
