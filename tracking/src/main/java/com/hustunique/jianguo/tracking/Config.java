package com.hustunique.jianguo.tracking;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JianGuo on 11/25/16.
 * Tracking config, it will support json format in the future like:
 * <p>
 * [
 *  {
 *      "activity" : "com.hustunique.jianguo.sample.MainActivity",
 *      "ids" : "btn_track"
 *  },
 *  {
 *      "activity" : "com.hustunique.jianguo.sample.MainActivity",
 *      "ids" : "btn_track2"
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

    public List<String> getPathList(String name) {
        List<String> rst = new ArrayList<>();
        for (Path path : config.keySet()) {
            if (path.actClz.equals(name)) {
                rst.add(path.pathId);
            }
        }
        return rst;
    }

    public Callback findCallback(String clz, String id) {
        for (Path path : config.keySet()) {
            if (path.actClz.equals(clz) && path.pathId.equals(id)) {
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
        public Builder addPath(Callback callback, String actClz, String id) {
            Path path = new Path(actClz, id);
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

    private static class Path {
        final String actClz;
        final String pathId;
        Path(String actClz, String pathId) {
            this.actClz = actClz;
            this.pathId = pathId;
        }

        @Override
        public String toString() {
            return "Path{" +
                    "actClz='" + actClz + '\'' +
                    ", pathId=" + pathId +
                    '}';
        }
    }
}
