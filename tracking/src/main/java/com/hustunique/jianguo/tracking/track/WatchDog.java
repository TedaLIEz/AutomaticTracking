/*
 *
 * Copyright 2017 TedaLIEz
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.hustunique.jianguo.tracking.track;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.hustunique.jianguo.tracking.Config;
import com.hustunique.jianguo.tracking.TrackingPath;
import com.hustunique.jianguo.tracking.hook.HookHelper;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by JianGuo on 11/25/16. WatchDog
 */
public class WatchDog {

  private static final String TAG = "WatchDog";
  private Config config;
  private Application application;

  public WatchDog(Application application, Config config) {
    this.config = config;
    this.application = application;
  }

  /**
   * Start tracking
   */
  void watchOver(Activity activity) {

    Log.i(TAG, "Current watching: " + activity.getClass().getName());
    watchViewTree(activity);
  }

  private void watchViewTree(Activity activity) {
    //TODO: hook window rather than activity!
    if (isWatched(activity.getClass())) {
      mViewSet.clear();
      Log.d(TAG, "Current activity is being tracked now");
      final ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView()
          .findViewById(android.R.id.content);
      final List<TrackingPath> pathList = config.getPathList(activity.getClass());
      rootView.getViewTreeObserver()
          .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
              // Currently we have to watch view tree every time layout changes,
              // which may not be very effective, and this listener needs to be unregistered
              // when app exits
              watch(rootView, pathList);
            }
          });

    }
  }

  private void watch(ViewGroup rootView, List<TrackingPath> pathList) {
    for (TrackingPath path : pathList) {
      rTraverse(rootView, path);
    }
  }

  private void rTraverse(View view, TrackingPath path) {
    if (view == null) {
      return;
    }
    if (view.getId() == toId(path.getPathId())
        && path.getViewClz().isAssignableFrom(view.getClass())) {
      if (hasBeenTracked(view)) {
        return;
      }
      Log.d(TAG, "find target view " + view.toString());
      try {
        HookHelper.hookListener(view, config.findCallback(path.getPathId()));
        mViewSet.add(view);
      } catch (NoSuchMethodException | InvocationTargetException
          | IllegalAccessException | ClassNotFoundException | NoSuchFieldException e) {
        Log.wtf(TAG, e);
      }
      return;
    }
    if (view instanceof ViewGroup) {
      final ViewGroup viewRoot = (ViewGroup) view;
      // TODO: 4/4/17 Hook OnItemClickListener
      int childCount = viewRoot.getChildCount();
      for (int i = 0; i < childCount; i++) {
        View childView = viewRoot.getChildAt(i);
        rTraverse(childView, path);
      }
    }
  }

  private Set<View> mViewSet = new HashSet<>();

  private boolean hasBeenTracked(View view) {
    return mViewSet.contains(view);
  }

  private int toId(String id) {

    return application.getResources().getIdentifier(id, "id", application.getPackageName());
  }

  private String idToStr(int id) {
    return application.getResources().getResourceName(id);
  }


  private boolean isWatched(Class<? extends Activity> clz) {
    return config.activityInTrack(clz);
  }

}
