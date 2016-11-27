package com.hustunique.jianguo.tracking;

import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;
import android.telecom.Call;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by JianGuo on 11/25/16.
 * Tracking config, it will support json format in the future like:
 * <p>
 * [
 *  {
 *      "activity" : "com.hustunique.jianguo.sample.MainActivity",
 *      "ids" : ["activity_main", "btn_track"]
 *  },
 *  {
 *      "activity" : "com.hustunique.jianguo.sample.MainActivity",
 *      "ids" : ["activity_main", "btn_track2"]
 *  }
 * ]
 * </p>
 */
//TODO: refactor config builder, build config from json format
public class Config {
    ArrayMap<Path, Callback> config;
    private static final String TAG = "TrackingConfig";

    private Config() {
    }

    private Config(ArrayMap<Path, Callback> config) {
        this.config = config;
    }


    public boolean getActivityClz(String clz) {
        for (Path path : config.keySet()) {
            if (path.actClz.equals(clz)) {
                return true;
            }
        }
        return false;
    }

    public String getPath(String clz) {
        for (Path path : config.keySet()) {
            if (path.actClz.equals(clz)) {
                return path.toString();
            }
        }
        return null;
    }

    public List<List<String>> getPathList(String name) {
        List<List<String>> rst = new ArrayList<>();
        for (Path path : config.keySet()) {
            if (path.actClz.equals(name)) {
                rst.add(path.pathIdList);
            }
        }
        return rst;
    }

    public Callback findCallback(String clz, List<String> pathList) {
        for (Path path : config.keySet()) {
            if (path.actClz.equals(clz) && path.pathIdList.equals(pathList)) {
                return config.get(path);
            }
        }
        return null;
    }

    public static class Builder {
        ArrayMap<Path, Callback> config;
        Callback mDefaultCallback;

        public Builder() {
            config = new ArrayMap<>();
        }


        // TODO: 11/27/16 How to set callback when parsing json format
        public Builder addPath(Callback callback, String decorName, String... ids) {
            Path path = new Path();
            path.actClz = decorName;
            Collections.addAll(path.pathIdList, ids);
            config.put(path, callback == null ? mDefaultCallback : callback);
            return this;
        }

        public Builder setDefaultCallback(@NonNull Callback callback) {
            mDefaultCallback = callback;
            return this;
        }

        public Config build() {
            return new Config(config);
        }
    }

    public interface Callback {
        void onEventTracked(View v);
    }

    public static class Path {
        public String actClz;
        public List<String> pathIdList;

        Path() {
            pathIdList = new LinkedList<>();
        }

        @Override
        public String toString() {
            return "Path{" +
                    "actClz='" + actClz + '\'' +
                    ", pathIdList=" + pathIdList +
                    '}';
        }
    }
}
