package com.hustunique.jianguo.tracking.hook;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.hustunique.jianguo.tracking.track.WatchDog;

import java.lang.reflect.Field;

/**
 * Created by JianGuo on 11/26/16.
 * {@link android.os.Handler.Callback} used in hooking mH in {@link ActivityThread}
 */

public class HookHandlerCallback implements Handler.Callback {
    private static final String TAG = "HookHandlerCallback";
    private Handler mBase;
    private int launchCode = 100;
    private WatchDog watchDog;
    HookHandlerCallback(Handler base, int launchCode, WatchDog watchDog) {
        mBase = base;
        this.launchCode = launchCode;
        this.watchDog = watchDog;
    }


    @Override
    public boolean handleMessage(Message msg) {
        String actClz = null;
        if (msg.what == launchCode) {
            actClz = handleLaunchActivity(msg);
        }
        mBase.handleMessage(msg);
        watchDog.watchOverViewTree(actClz);
        return true;
    }

    private String handleLaunchActivity(Message msg) {
        Object obj = msg.obj;
        try {
            Field tokenField = obj.getClass().getDeclaredField("token");
            Field intentField = obj.getClass().getDeclaredField("intent");
            intentField.setAccessible(true);
            tokenField.setAccessible(true);
            Intent intent = (Intent) intentField.get(obj);
            IBinder token = (IBinder) tokenField.get(obj);
            watchDog.addToTokenList(intent.getComponent().getClassName(), token);
            return intent.getComponent().getClassName();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Log.wtf(TAG, e);
            return null;
        }

    }



}
