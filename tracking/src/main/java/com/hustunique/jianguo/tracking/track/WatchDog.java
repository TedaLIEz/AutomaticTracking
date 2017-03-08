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
import android.os.IBinder;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.hustunique.jianguo.tracking.Config;
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
  private ArrayMap<IBinder, String> mBinders;
  private Set<IBinder> mTokens;
  private Application application;
  private IBinder currToken = null;

  public WatchDog(Application application, Config config) {
    this.config = config;
    this.application = application;
    mBinders = new ArrayMap<>();
    mTokens = new HashSet<>();
  }

  /**
   * Start tracking
   */
  public void watchOver() {

    if (!mTokens.contains(currToken)) {
      mTokens.add(currToken);
      try {
        Activity activity = HookHelper.hookActivity(currToken);
        Log.i(TAG, "Current watching: " + activity.getClass().getName());
        watchViewTree(activity);
      } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException
          | IllegalAccessException | InvocationTargetException e) {
        Log.wtf(TAG, e);
      }
    }
  }

  private void watchViewTree(Activity activity) {
    //TODO: hook window rather than activity!
    if (isWatched(activity.getClass().getName())) {
      Log.d(TAG, "Current activity is being tracked now");
      final ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView()
          .findViewById(android.R.id.content);
      final String clzName = activity.getClass().getName();
      rootView.getViewTreeObserver()
          .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
              // TODO: 11/28/16 Currently we have to watch view tree every time layout changes,
              // which may not be very effective
              watch(rootView, clzName);
            }
          });

    }
  }

  private void watch(ViewGroup rootView, String clz) {
    List<String> pathList = config.getPathList(clz);
    int childCount = rootView.getChildCount();
    for (int i = 0; i < childCount; i++) {
      View childView = rootView.getChildAt(i);
      for (String id : pathList) {
        rTraverse(childView, id);
      }
    }
  }

  private void rTraverse(View view, String id) {
    if (view == null) {
      return;
    }
    if (view.getId() == toId(id)) {
      Log.d(TAG, "find target view " + view.toString());
      try {
        HookHelper.hookListener(view, config.findCallback(mBinders.get(currToken), id));
      } catch (NoSuchMethodException | InvocationTargetException
          | IllegalAccessException | ClassNotFoundException | NoSuchFieldException e) {
        Log.wtf(TAG, e);
      }
    } else if (view instanceof ViewGroup) {
      final ViewGroup viewRoot = (ViewGroup) view;
      int childCount = viewRoot.getChildCount();
      for (int i = 0; i < childCount; i++) {
        View childView = viewRoot.getChildAt(i);
        rTraverse(childView, id);
      }
    }
  }

  private int toId(String id) {

    return application.getResources().getIdentifier(id, "id", application.getPackageName());
  }

  private String idToStr(int id) {
    return application.getResources().getResourceName(id);
  }


  private boolean isWatched(String clz) {
    return config.activityInTrack(clz);
  }

  /**
   * add binder token to list
   * @param compClzName the componentName
   * @param token the Binder which used as token in system
   */
  public void addToTokenList(String compClzName, IBinder token) {
    mBinders.put(token, compClzName);
  }


  public void setToken(IBinder token) {
    currToken = token;
  }
}
