package com.timmy.aacgank.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseFragment extends Fragment {

    protected final CompositeDisposable mDisposable = new CompositeDisposable();

    /**
     * 每个Fragment自己的布局
     */
    protected abstract int getFragmentLayout();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        mDisposable.clear();
    }
}