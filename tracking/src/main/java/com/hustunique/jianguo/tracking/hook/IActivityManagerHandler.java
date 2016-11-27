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
