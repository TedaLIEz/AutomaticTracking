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
import android.app.Instrumentation;
import android.os.Bundle;

/**
 * Created by JianGuo on 4/9/17.
 * Custom {@link Instrumentation} class used for hooking
 */

public class TrackInstrumentation extends Instrumentation {

  private static final String TAG = "TrackInstrumentation";

  private WatchDog mWatchDog;

  public TrackInstrumentation(WatchDog watchDog) {
    mWatchDog = watchDog;
  }

  @Override
  public void callActivityOnCreate(Activity activity, Bundle icicle) {
    super.callActivityOnCreate(activity, icicle);
    mWatchDog.watchOver(activity);
  }
}
