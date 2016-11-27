package com.hustunique.jianguo.tracking.hook;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by JianGuo on 11/27/16.
 * Dynamic Proxy for {@link android.view.View.OnClickListener}
 */

public class IClickHandler implements InvocationHandler {
    private static final String TAG = "IClickHandler";
    Object mBase;

    public IClickHandler(Object base) {
        mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("onClick".equals(method.getName())) {
            //TODO: add callback in this handler
            Log.i(TAG, "BIG BROTHER IS WATCHING OVER YOU");
        }
        return method.invoke(mBase, args);
    }
}
