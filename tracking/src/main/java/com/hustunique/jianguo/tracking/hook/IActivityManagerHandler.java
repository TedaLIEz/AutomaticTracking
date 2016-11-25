package com.hustunique.jianguo.tracking.hook;

import android.util.Log;

import com.hustunique.jianguo.tracking.Config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JianGuo on 11/25/16.
 * InvocationHandler for {@link ActivityManagerNative}
 */
//TODO: filter to get the entrance function when activity starts.
class IActivityManagerHandler implements InvocationHandler {
    private static final String TAG = "IActivityManagerHandler";
    private Object mBase;
    private List<String> startList;
    IActivityManagerHandler(Object base, Config config) {
        mBase = base;
        startList = new ArrayList<>();
        setupConfig(config);
    }

    private void setupConfig(Config config) {
        for (Config.Path path : config.getTrackList()) {
            startList.add(path.decorClz);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d(TAG, "method:" + method.getName() + " called with args:" + Arrays.toString(args));
        return method.invoke(mBase, args);
    }
}
