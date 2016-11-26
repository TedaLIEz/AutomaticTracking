package com.hustunique.jianguo.tracking;

import android.app.Application;
import android.support.annotation.NonNull;

import com.hustunique.jianguo.tracking.hook.HookHelper;

/**
 * Created by JianGuo on 11/24/16.
 * Manager for tracking click event.
 */

public class TrackingManager {
    public static void track(Application application, @NonNull Config config) {
        try {
            HookHelper.hookActivityThread();
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
