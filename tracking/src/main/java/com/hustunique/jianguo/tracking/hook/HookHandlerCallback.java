package com.hustunique.jianguo.tracking.hook;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.hustunique.jianguo.tracking.track.WatchDog;

import java.lang.reflect.Field;

/**
 * Created by JianGuo on 11/26/16.
 */

public class HookHandlerCallback implements Handler.Callback {
    private static final String TAG = "HookHandlerCallback";
    Handler mBase;
    int launchCode = 100;
    private WatchDog watchDog;
    public HookHandlerCallback(Handler base, int launchCode, WatchDog watchDog) {
        mBase = base;
        this.launchCode = launchCode;
        this.watchDog = watchDog;
    }


    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == launchCode) {
            handleLaunchActivity(msg);
        }
        mBase.handleMessage(msg);
        return true;
    }

    private void handleLaunchActivity(Message msg) {
        Object obj = msg.obj;

        try {
            Field intent = obj.getClass().getDeclaredField("intent");
            intent.setAccessible(true);
            Intent raw = (Intent) intent.get(obj);
            track(raw);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void track(Intent raw) {
        watchDog.watchOverViewTree(raw.getComponent().getClassName());
        Log.d(TAG, "start Activity " + raw.getComponent().getClassName());
    }


}
