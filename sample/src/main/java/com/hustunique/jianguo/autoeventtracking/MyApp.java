package com.hustunique.jianguo.autoeventtracking;

import android.app.Application;

import com.hustunique.jianguo.tracking.Config;
import com.hustunique.jianguo.tracking.TrackingManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JianGuo on 11/25/16.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        List<String> viewList = new ArrayList<>();
        viewList.add("R.id.contentView");
        Config config = new Config.Builder()
                .addPath(new Config.Callback() {
                    @Override
                    public void onEventTracked() {

                    }
                }, "com.hustunique.jianguo.autoeventtracking.MainActivity",
                        viewList)
                .build();
        TrackingManager.track(this, config);
    }
}
