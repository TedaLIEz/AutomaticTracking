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
import android.view.View;

/**
 * Created by JianGuo on 3/1/17.
 * POJO used in {@link Config}
 */
// TODO: 3/14/17 Reflection here is NG
public class TrackingPath {
  final Class<? extends Activity> actClz;
  final String pathId;
  final Class<? extends View> viewClz;

  /**
   * Construct the tracking path
   * @param clz the activity class your view show in
   * @param viewClz the view class
   * @param pathId the view's id
   */
  public TrackingPath(Class<? extends Activity> clz, Class<? extends View> viewClz, String pathId) {
    actClz = clz;
    this.viewClz = viewClz;
    this.pathId = pathId;
  }

  /**
   * Construct the tracking path
   * @param clz the activity class name your view show in
   * @param viewClz the view class name
   * @param pathId the view's id
   */
  public TrackingPath(String clz, String viewClz, String pathId)
      throws ClassNotFoundException, ClassCastException {
    this(Class.<Activity>forName(clz).asSubclass(Activity.class),
        Class.<View>forName(viewClz).asSubclass(View.class), pathId);
  }


  public String getPathId() {
    return pathId;
  }

  public Class<? extends View> getViewClz() {
    return viewClz;
  }

  @Override
  public String toString() {
    return "TrackingPath{"
        + "actClz="
        + actClz
        + ", pathId='"
        + pathId
        + '\''
        + ", viewClz="
        + viewClz
        + '}';
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    TrackingPath that = (TrackingPath) o;

    return actClz.equals(that.actClz)
        && pathId.equals(that.pathId)
        && viewClz.equals(that.viewClz);

  }

  @Override
  public int hashCode() {
    int result = actClz.hashCode();
    result = 31 * result + pathId.hashCode();
    result = 31 * result + viewClz.hashCode();
    return result;
  }
}
