/*
 *
 * Copyright 2017 TedaLIEz
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.hustunique.jianguo.tracking;

import android.app.Activity;
import android.support.v4.util.ArrayMap;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JianGuo on 11/25/16. Tracking config, it will support json format in the future like:
 * <p> [ { "activity" : "com.hustunique.jianguo.sample.MainActivity", "ids" : "btn_track" }, {
 * "activity" : "com.hustunique.jianguo.sample.MainActivity", "ids" : "btn_track2" } ] </p>
 */
//TODO: refactor config builder, build config from json format
public class Config {

  private ArrayMap<TrackingPath, Callback> config;
  private static final String TAG = "TrackingConfig";

  private Config() {
  }

  private Config(ArrayMap<TrackingPath, Callback> config) {
    this.config = config;
  }


  /**
   * Return <tt>true</tt> activity is tracked in config
   *
   * @param clz the activity class
   * @return <tt>true</tt> if tracked, <tt>false</tt> otherwise
   */
  public boolean activityInTrack(Class<? extends Activity> clz) {
    for (TrackingPath path : config.keySet()) {
      if (path.actClz.equals(clz)) {
        return true;
      }
    }
    return false;
  }


  public List<TrackingPath> getPathList(Class<? extends Activity> clz) {
    List<TrackingPath> rst = new ArrayList<>();
    for (TrackingPath path : config.keySet()) {
      if (path.actClz.equals(clz)) {
        rst.add(path);
      }
    }
    return rst;
  }

  public Callback findCallback(String id) {
    for (TrackingPath path : config.keySet()) {
      if (path.pathId.equals(id)) {
        return config.get(path);
      }
    }
    return null;
  }


  public static class Builder {

    ArrayMap<TrackingPath, Callback> config;

    public Builder() {
      config = new ArrayMap<>();
    }


    public Builder addPath(Callback callback, TrackingPath path) {
      config.put(path, callback);
      return this;
    }

    public Config build() {
      return new Config(config);
    }
  }

  public interface Callback {

    void onEventTracked(View v);
  }


}
