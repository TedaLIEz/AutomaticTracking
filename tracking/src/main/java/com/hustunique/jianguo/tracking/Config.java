package com.hustunique.jianguo.tracking;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by JianGuo on 11/25/16.
 * Tracking config
 */

public class Config {
    List<Pair<Path, Callback>> config;
    private static final String TAG = "TrackingConfig";

    private Config() {
    }

    private Config(List<Pair<Path, Callback>> config) {
        this.config = config;
    }



    public boolean getActivityClz(String clz) {
        for (Pair<Path, Callback> pair : config) {
            if (pair.first.actClz.equals(clz)) {
                return true;
            }
        }
        return false;
    }

    public String getPath(String clz) {
        for (Pair<Path, Callback> pair : config) {
            if (pair.first.actClz.equals(clz)) {
                return pair.first.toString();
            }
        }
        return null;
    }

    public List<List<String>> getPathList(String name) {
        List<List<String>> rst = new ArrayList<>();
        for (Pair<Path, Callback> pair : config) {
            if (pair.first.actClz.equals(name)) {
                rst.add(pair.first.pathIdList);
            }
        }
        return rst;
    }

    public static class Builder {

        List<Pair<Path, Callback>> config;

        public Builder() {
            config = new ArrayList<>();
        }

        public Builder addPath(Callback callback, String decorName, List<String> viewLst) {
            Path path = new Path();
            path.actClz = decorName;
            path.pathIdList = viewLst;
            config.add(Pair.create(path, callback));
            return this;
        }

        public Config build() {
            return new Config(config);
        }
    }

    public interface Callback {
        void onEventTracked();
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
