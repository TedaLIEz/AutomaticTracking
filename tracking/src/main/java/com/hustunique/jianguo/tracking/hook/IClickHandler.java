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

import android.view.View;

import com.hustunique.jianguo.tracking.Config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by JianGuo on 11/27/16. Dynamic Proxy for {@link android.view.View.OnClickListener}
 */

class IClickHandler implements InvocationHandler {

  private static final String TAG = "IClickHandler";
  Object mBase;
  Config.Callback callback;

  IClickHandler(Object base, Config.Callback callback) {
    mBase = base;
    this.callback = callback;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    if ("onClick".equals(method.getName())) {
      View v = (View) args[0];
      callback.onEventTracked(v);
    }
    return method.invoke(mBase, args);
  }
}
