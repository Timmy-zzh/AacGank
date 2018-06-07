package com.timmy.aacgank.ui.gank.adapter;


import com.timmy.adapterlib.BaseDataBindingAdapter;

/**
 * Created by Timmy on 2018/1/23.
 */

public class AdapterFactory {

    public static <T extends BaseDataBindingAdapter> T getAdapter(int position) {
        switch (position) {
            case 0:
                return (T) new WelfareAdapter();
            case 1:
                return (T) new AndroidAdapter();
        }
        return (T) new WelfareAdapter();
    }


}
