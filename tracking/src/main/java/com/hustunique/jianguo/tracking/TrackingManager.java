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

package com.hustunique.jianguo.tracking;

import android.app.Application;
import android.support.annotation.NonNull;

import com.hustunique.jianguo.tracking.hook.HookHelper;
import com.hustunique.jianguo.tracking.track.WatchDog;

/**
 * Created by JianGuo on 11/24/16.
 * Manager for tracking click event.
 */

public class TrackingManager {

  /**
   * Track application
   * @param application the application, init in {@link Application#onCreate()}
   * @param config the config, see {@link Config}
   */
  public static void track(Application application, @NonNull Config config) {
    WatchDog watchDog = new WatchDog(application, config);
    try {
      HookHelper.hookActivityThread(watchDog);
    } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
