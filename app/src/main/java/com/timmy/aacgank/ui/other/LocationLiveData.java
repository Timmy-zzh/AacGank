package com.timmy.aacgank.ui.other;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationLiveData extends LiveData<Location> {

    private final LocationManager locationManager;
    private static LocationLiveData instance;

    private LocationLiveData(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    //单例模式
    public static LocationLiveData getInstance(Context context) {
        if (instance == null) {
            synchronized (LocationLiveData.class) {
                if (instance == null) {
                    instance = new LocationLiveData(context);
                }
            }
        }
        return instance;
    }


    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            setValue(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    /**
     * 组件生命周期感知,到当前处理活跃状态时调用
     */
    @SuppressLint("MissingPermission")
    @Override
    protected void onActive() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 5, listener);
    }


    @Override
    protected void onInactive() {
        super.onInactive();
        locationManager.removeUpdates(listener);
    }
}
