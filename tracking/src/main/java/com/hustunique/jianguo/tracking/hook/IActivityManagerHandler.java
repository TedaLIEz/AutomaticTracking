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

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by JianGuo on 11/25/16.
 * InvocationHandler for {@link ActivityManagerNative},
 * deprecated, use {@link HookHandlerCallback} instead
 */
@Deprecated
class IActivityManagerHandler implements InvocationHandler {
    private static final String TAG = "IActivityManagerHandler";
    private Object mBase;
    IActivityManagerHandler(Object base) {
        mBase = base;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d(TAG, "method:" + method.getName() + " called with args:" + Arrays.toString(args));
        return method.invoke(mBase, args);
    }
}
