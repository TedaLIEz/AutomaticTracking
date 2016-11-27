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
        viewList.add("activity_main");
        viewList.add("linearLayout1");
        viewList.add("btn_track");
        List<String> view4List = new ArrayList<>();
        view4List.add("activity_main");
        view4List.add("btn_startAct");
        List<String> view2List = new ArrayList<>();
        view2List.add("activity_main");
        view2List.add("linearLayout1");
        view2List.add("linearLayout2");
        view2List.add("btn_track2");
        List<String> view3List = new ArrayList<>();
        view3List.add("activity_main2");
        view3List.add("btn_track2");
        Config config = new Config.Builder()
                .addPath(new Config.Callback() {
                             @Override
                             public void onEventTracked() {

                             }
                         }, "com.hustunique.jianguo.autoeventtracking.MainActivity",
                        viewList)
                .addPath(new Config.Callback() {
                             @Override
                             public void onEventTracked() {

                             }
                         }, "com.hustunique.jianguo.autoeventtracking.MainActivity",
                        view2List)
                .addPath(new Config.Callback() {
                             @Override
                             public void onEventTracked() {

                             }
                         }, "com.hustunique.jianguo.autoeventtracking.Main2Activity",
                        view3List)
                .addPath(new Config.Callback() {
                             @Override
                             public void onEventTracked() {

                             }
                         }, "com.hustunique.jianguo.autoeventtracking.MainActivity",
                        view4List)
                .build();
        TrackingManager.track(this, config);
    }
}
