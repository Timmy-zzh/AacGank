package com.timmy.aacgank.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.FragmentTextBinding;
import com.timmy.aacgank.ui.other.HandlerActivity;
import com.timmy.baselib.fragment.TBaseFragment;

/**
 * Created by admin on 2017/11/26.
 */

public class TextFrgment extends TBaseFragment<FragmentTextBinding> {

    public static TextFrgment newInstance() {
        TextFrgment fragment = new TextFrgment();
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_text;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerUse(null);
            }
        });
    }

    public void handlerUse(View view) {
        startActivity(new Intent(getContext(), HandlerActivity.class));
    }
}
