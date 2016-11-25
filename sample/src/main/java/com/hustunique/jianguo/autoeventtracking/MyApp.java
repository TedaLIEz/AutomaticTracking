package com.hustunique.jianguo.autoeventtracking;

import android.app.Application;

import com.hustunique.jianguo.tracking.Config;
import com.hustunique.jianguo.tracking.TrackingManager;

/**
 * Created by JianGuo on 11/25/16.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Config config = new Config.Builder().addPath("com.hustunique.jianguo.autoeventtracking.MainActivity", "R.id.contentView").build();
        TrackingManager.track(this, config);
    }
}
