package com.hustunique.jianguo.tracking.hook;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import com.hustunique.jianguo.tracking.Config;
import com.hustunique.jianguo.tracking.track.WatchDog;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by JianGuo on 11/25/16.
 * Helper class for hook
 */

public class HookHelper {
    public static void hookActivityManager(Config config) {
        try {
            Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
            Field gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault");
            gDefaultField.setAccessible(true);
            Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
            Object gDefault = gDefaultField.get(null);
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                // 4.x以上的gDefault是一个 android.util.Singleton对象; 我们取出这个单例里面的字段
                Class<?> singleton = Class.forName("android.util.Singleton");
                Field mInstanceField = singleton.getDeclaredField("mInstance");
                mInstanceField.setAccessible(true);
                Object rawIActivityManager = mInstanceField.get(gDefault);

                Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                        new Class<?>[]{iActivityManagerInterface}, new IActivityManagerHandler(rawIActivityManager));
                mInstanceField.set(gDefault, proxy);
            } else {
                Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                        new Class<?>[]{iActivityManagerInterface}, new IActivityManagerHandler(gDefault));
                gDefaultField.set(gDefault, proxy);
            }
            // ActivityManagerNative 的gDefault对象里面原始的 IActivityManager对象


        } catch (IllegalAccessException | NoSuchFieldException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Activity hookActivity(IBinder token) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
        currentActivityThreadField.setAccessible(true);
        Object currentActivityThread = currentActivityThreadField.get(null);
        Method getActivityMethod = activityThreadClass.getMethod("getActivity", IBinder.class);
        Activity activity = (Activity) getActivityMethod.invoke(currentActivityThread, token);
        return activity;
    }




    public static void hookActivityThread(WatchDog watchDog) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        int launchCode = getLaunchCode(activityThreadClass);
        Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
        currentActivityThreadField.setAccessible(true);
        Object currentActivityThread = currentActivityThreadField.get(null);
        Field mHField = activityThreadClass.getDeclaredField("mH");
        mHField.setAccessible(true);
        Handler mH = (Handler) mHField.get(currentActivityThread);
        Field mCallbackField = Handler.class.getDeclaredField("mCallback");
        mCallbackField.setAccessible(true);
        HookHandlerCallback callback = new HookHandlerCallback(mH, launchCode, watchDog);
        mCallbackField.set(mH, callback);
    }

    private static int getLaunchCode(Class<?> activityThreadClass) throws NoSuchFieldException, IllegalAccessException {
        Class<?>[] clz = activityThreadClass.getDeclaredClasses();
        if (clz.length == 0) {
            return -1;
        }
        for (Class innerClass : clz) {
            if (innerClass.getSimpleName().equals("H")) {
                Field launchField = innerClass.getField("LAUNCH_ACTIVITY");
                return launchField.getInt(null);
            }
        }
        return 100;
    }


}
