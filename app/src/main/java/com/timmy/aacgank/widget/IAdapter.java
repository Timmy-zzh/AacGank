package com.timmy.aacgank.widget;

import android.view.View;
import android.view.ViewGroup;

public interface IAdapter {

    View getView(int position, ViewGroup parent);

    void setItemView(View itemView, int position);

    int getCount();

}
