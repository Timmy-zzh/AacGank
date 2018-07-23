package com.timmy.aacgank.ui.my;

import android.Manifest;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.FragmentMyBinding;
import com.timmy.aacgank.ui.cityselect.CitySelectActivity;
import com.timmy.baselib.base.fragment.TBaseBindingFragment;

import io.reactivex.functions.Consumer;


public class MyFragment extends TBaseBindingFragment<FragmentMyBinding> {

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_my;
    }

    @Override
    protected void onRefresh() {
    }

    @Override
    protected void initBase() {
        showContentLayout();
        final RxPermissions rxPermissions = new RxPermissions(this);

//        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(Boolean aBoolean) throws Exception {
//                        if (aBoolean) {
//                            requestLocation();
//                        }
//                    }
//                });


        binding.tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CitySelectActivity.class));
            }
        });

        getLifecycle().getCurrentState();
    }

    private void requestLocation() {
        binding.tvLocation.setText("check");
//        //请求定位权限
        LiveData<Location> locationLiveData = LocationLiveData.getInstance(getActivity().getApplication());
        locationLiveData.observe(this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                if (location != null)
                    binding.tvLocation.setText("Latitude:" + location.getLatitude() + ",Longitude" + location.getLongitude());
            }
        });

//        new BoundLocationListener(this, new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                binding.tvLocation.setText("Latitude:" + location.getLatitude() + ",Longitude" + location.getLongitude());
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        },getActivity());
    }
}
