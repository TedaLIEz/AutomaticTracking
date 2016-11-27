package com.hustunique.jianguo.tracking.track;

import android.app.Activity;
import android.app.Application;
import android.os.IBinder;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.hustunique.jianguo.tracking.Config;
import com.hustunique.jianguo.tracking.hook.HookHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Created by JianGuo on 11/25/16.
 * WatchDog
 */

public class WatchDog {
    private static final String TAG = "WatchDog";
    private Config config;
    private ArrayMap<String, IBinder> mBinders;
    private Set<IBinder> mTokens;
    private Application application;
    private IBinder currToken = null;

    public WatchDog(Application application, Config config) {
        this.config = config;
        this.application = application;
        mBinders = new ArrayMap<>();
        mTokens = new HashSet<>();
    }

    public void watchOver() {

        if (!mTokens.contains(currToken)) {
            mTokens.add(currToken);
            try {
                Activity activity = HookHelper.hookActivity(currToken);
                Log.i(TAG, "Current watching: " + activity.getClass().getName());
                watchViewTree(activity);
            } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                Log.wtf(TAG, e);
            }
        }
        //TODO: use dynamic proxy to replace original OnClickListener
    }

    private void watchViewTree(Activity activity) {
        //TODO: hook window rather than activity!
        if (isWatched(activity.getClass().getName())) {
            Log.d(TAG, "Current activity is being tracked now");
            ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
            traverse(rootView, config.getPathList(activity.getClass().getName()));
        }
    }

    private void traverse(ViewGroup rootView, List<List<String>> pathList) {
        int childCount = rootView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = rootView.getChildAt(i);
            for (List<String> list : pathList) {
                rTraverse(childView, list, 0);
            }
        }
    }

    private void rTraverse(View view, List<String> pathList, int depth) {
        if (view == null || pathList.size() == 0) return;
        if (view.getId() == toId(pathList.get(depth))) {
            depth++;
            if (pathList.size() == depth) {
                Log.d(TAG, "find target view " + view.toString());
                try {
                    HookHelper.hookListener(view);
                } catch (NoSuchMethodException | InvocationTargetException
                        | IllegalAccessException | ClassNotFoundException | NoSuchFieldException e) {
                    Log.wtf(TAG, e);
                }
                // TODO: hook the target onclickListener.
                return;
            }
            if (view instanceof ViewGroup) {
                final ViewGroup viewRoot = (ViewGroup) view;
                int childCount = viewRoot.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childView = viewRoot.getChildAt(i);
                    rTraverse(childView, pathList, depth);
                }
            } else {
                rTraverse(view, pathList, depth);
            }
        }
    }

    private int toId(String id) {
        return application.getResources().getIdentifier(id, "id", application.getPackageName());
    }

    private String idToStr(int id) {
        return application.getResources().getResourceName(id);
    }


    private boolean isWatched(String decorClz) {
        return config.getActivityClz(decorClz);
    }

    public void addToTokenList(String clz, IBinder token) {
        mBinders.put(clz, token);
    }


    public void pushToken(IBinder token) {
        currToken = token;
    }
}
