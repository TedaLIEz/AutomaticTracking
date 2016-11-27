package com.hustunique.jianguo.tracking.hook;

import android.util.Log;
import android.view.View;

import com.hustunique.jianguo.tracking.Config;

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
    Config.Callback callback;
    public IClickHandler(Object base, Config.Callback callback) {
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
