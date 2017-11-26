package com.timmy.aacgank.ui.home;

import com.timmy.aacgank.R;
import com.timmy.aacgank.ui.base.BaseFragment;

/**
 * Created by admin on 2017/11/26.
 */

public class TextFrgment extends BaseFragment {

    public static TextFrgment newInstance() {
        TextFrgment fragment = new TextFrgment();
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_text;
    }
}
