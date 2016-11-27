package com.hustunique.jianguo.autoeventtracking;

import android.app.Application;
import android.util.Log;
import android.view.View;

import com.hustunique.jianguo.tracking.Config;
import com.hustunique.jianguo.tracking.TrackingManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JianGuo on 11/25/16.
 */

public class MyApp extends Application {
    private static final String TAG = "MyApp";
    @Override
    public void onCreate() {
        super.onCreate();
        Config config = new Config.Builder()
                .addPath(new Config.Callback() {
                             @Override
                             public void onEventTracked(View v) {
                                 Log.d(TAG, "btn_track in MainActivity, BIG BROTHER IS WATCHING OVER YOU");
                             }
                         }, "com.hustunique.jianguo.autoeventtracking.MainActivity",
                        "activity_main", "linearLayout1", "btn_track")
                .addPath(new Config.Callback() {
                             @Override
                             public void onEventTracked(View v) {
                                 Log.d(TAG, "btn_track2 in MainActivity, BIG BROTHER IS WATCHING OVER YOU");
                             }
                         }, "com.hustunique.jianguo.autoeventtracking.MainActivity",
                        "activity_main", "linearLayout1", "linearLayout2", "btn_track2")
                .addPath(new Config.Callback() {
                             @Override
                             public void onEventTracked(View v) {
                                 Log.d(TAG, "btn_track2 in Main2Activity, BIG BROTHER IS WATCHING OVER YOU");
                             }
                         }, "com.hustunique.jianguo.autoeventtracking.Main2Activity",
                        "activity_main2", "btn_track2")
                .addPath(new Config.Callback() {
                             @Override
                             public void onEventTracked(View v) {
                                 Log.d(TAG, "btn_startAct in MainActivity, BIG BROTHER IS WATCHING OVER YOU");
                             }
                         }, "com.hustunique.jianguo.autoeventtracking.MainActivity",
                        "activity_main", "btn_startAct")
                .build();
        TrackingManager.track(this, config);
    }
}
