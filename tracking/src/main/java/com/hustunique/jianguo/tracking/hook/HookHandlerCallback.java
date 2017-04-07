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

package com.hustunique.jianguo.tracking.hook;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.hustunique.jianguo.tracking.track.WatchDog;

import com.hustunique.jianguo.tracking.util.LogUtil;
import java.lang.reflect.Field;

/**
 * Created by JianGuo on 11/26/16. {@link android.os.Handler.Callback} used in hooking mH in {@link
 * ActivityThread}
 */

class HookHandlerCallback implements Handler.Callback {

  private static final String TAG = "HookHandlerCallback";
  private Handler mBase;
  private int launchCode = 100;
  private int resumeCode = 107;
  private WatchDog watchDog;

  HookHandlerCallback(Handler base, int launchCode, int resumeCode, WatchDog watchDog) {
    mBase = base;
    this.launchCode = launchCode;
    this.watchDog = watchDog;
    this.resumeCode = resumeCode;
  }


  @Override
  public boolean handleMessage(Message msg) {
    if (msg.what == launchCode) {
      handleLaunchActivity(msg);
    } else if (msg.what == resumeCode) {
      handleResumeActivity(msg);
    }
    // TODO: 3/14/17 What if the click listener isn't bound in the life cycle of activity.
    mBase.handleMessage(msg);
    watchDog.watchOver();
    return true;
  }

  private void handleResumeActivity(Message msg) {
    IBinder token = (IBinder) msg.obj;
    watchDog.setToken(token);
  }

  private void handleLaunchActivity(Message msg) {
    Object obj = msg.obj;
    try {
      Field tokenField = obj.getClass().getDeclaredField("token");
      Field intentField = obj.getClass().getDeclaredField("intent");
      intentField.setAccessible(true);
      tokenField.setAccessible(true);
      Intent intent = (Intent) intentField.get(obj);
      IBinder token = (IBinder) tokenField.get(obj);
      // component name is not super class of Activity
      watchDog.addToTokenList(intent.getComponent().getClassName(), token);
      watchDog.setToken(token);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      LogUtil.wtf(TAG, e);
    }

  }


}
