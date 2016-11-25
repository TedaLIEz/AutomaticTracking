package com.hustunique.jianguo.tracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by JianGuo on 11/25/16.
 * Tracking config
 */

public class Config {
    private List<Path> trackList;
    private static final String TAG = "TrackingConfig";

    private Config() {
    }

    private Config(List<Path> trackList) {
        this.trackList = trackList;
    }

    public List<Path> getTrackList() {
        return trackList;
    }

    public static class Builder {
        private List<Path> trackList;

        public Builder() {
            trackList = new ArrayList<>();
        }

        public Builder addPath(String decorName, String... args) {
            Path path = new Path();
            path.decorClz = decorName;
            Collections.addAll(path.pathIdList, args);
            trackList.add(path);
            return this;
        }

        public Config build() {
            return new Config(trackList);
        }
    }

    public static class Path {
        public String decorClz;
        public List<String> pathIdList;

        Path() {
            pathIdList = new LinkedList<>();
        }
    }
}
