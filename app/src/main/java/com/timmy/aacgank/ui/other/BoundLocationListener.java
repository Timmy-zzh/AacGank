package com.timmy.aacgank.ui.other;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

public class BoundLocationListener implements LifecycleObserver {

    private final Context mContext;
    private LocationManager mLocationManager;
    private final LocationListener mListener;

    public BoundLocationListener(LifecycleOwner lifecycleOwner,
                                 LocationListener listener, Context context) {
        mContext = context;
        mListener = listener;
        //想要观察 Activity 的声明周期，必须将其添加到观察者中。添加下面的代码
        //才能是 BoundLocationListener 实例监听到生命周期
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    //可以使用  @OnLifecycleEvent 注解来监听 Activity 生命周期的变化
    // 可以使用下面的注解来添加 addLocationListener() 方法
    @SuppressLint("MissingPermission")
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void addLocationListener() {
        mLocationManager =
                (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mListener);
        Log.d("BoundLocationMgr", "Listener added");

        // Force an update with the last location, if available.
        Location lastLocation = mLocationManager.getLastKnownLocation(
                LocationManager.GPS_PROVIDER);
        if (lastLocation != null) {
            mListener.onLocationChanged(lastLocation);
        }
    }

    // 可以使用下面的注解来移除 removeLocationListener() 方法
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void removeLocationListener() {
        if (mLocationManager == null) {
            return;
        }
        mLocationManager.removeUpdates(mListener);
        mLocationManager = null;
        Log.d("BoundLocationMgr", "Listener removed");
    }
}
