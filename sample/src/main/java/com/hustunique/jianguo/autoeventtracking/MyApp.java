package com.hustunique.jianguo.autoeventtracking;

import android.app.Application;
import android.util.Log;
import android.view.View;

import com.hustunique.jianguo.tracking.Config;
import com.hustunique.jianguo.tracking.TrackingManager;

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
            "btn_track")
        .addPath(new Config.Callback() {
                   @Override
                   public void onEventTracked(View v) {
                     Log.d(TAG, "btn_track2 in MainActivity, BIG BROTHER IS WATCHING OVER YOU");
                   }
                 }, "com.hustunique.jianguo.autoeventtracking.MainActivity",
            "btn_track2")
        .addPath(new Config.Callback() {
                   @Override
                   public void onEventTracked(View v) {
                     Log.d(TAG, "btn_track2 in Main2Activity, BIG BROTHER IS WATCHING OVER YOU");
                   }
                 }, "com.hustunique.jianguo.autoeventtracking.Main2Activity",
            "btn_track2")
        .addPath(new Config.Callback() {
                   @Override
                   public void onEventTracked(View v) {
                     Log.d(TAG, "btn_startAct in MainActivity, BIG BROTHER IS WATCHING OVER YOU");
                   }
                 }, "com.hustunique.jianguo.autoeventtracking.MainActivity",
            "btn_startAct")
        .addPath(new Config.Callback() {
                   @Override
                   public void onEventTracked(View v) {
                     Log.d(TAG, "linearLayout3 in MainActivity2, BIG BROTHER IS WATCHING OVER YOU");
                   }
                 }, "com.hustunique.jianguo.autoeventtracking.Main2Activity",
            "linearLayout3")
        .addPath(new Config.Callback() {
                   @Override
                   public void onEventTracked(View v) {
                     Log.d(TAG, "textView in MainActivity2, BIG BROTHER IS WATCHING OVER YOU");
                   }
                 }, "com.hustunique.jianguo.autoeventtracking.Main2Activity",
            "textView")
        .addPath(new Config.Callback() {
                   @Override
                   public void onEventTracked(View v) {
                     Log.d(TAG, "textView in BlankFragment, BIG BROTHER IS WATCHING OVER YOU");
                   }
                 }, "com.hustunique.jianguo.autoeventtracking.Main2Activity",
            "textView_frag")
        .addPath(new Config.Callback() {
          @Override
          public void onEventTracked(View v) {
            Log.d(TAG, "btn_frag2 in Blank2Fragment, BIG BROTHER IS WATCHING OVER YOU");
          }
        }, "com.hustunique.jianguo.autoeventtracking.Main2Activity", "btn_frag2")
        .addPath(new Config.Callback() {
          @Override
          public void onEventTracked(View v) {
            Log.d(TAG, "btn_merge in MainActivity, BIG BROTHER IS WATCHING OVER YOU");
          }
        }, "com.hustunique.jianguo.autoeventtracking.MainActivity", "btn_merge")
        .build();
    TrackingManager.track(this, config);
  }
}
