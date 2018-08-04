package com.timmy.aacgank.ui.simple;

import android.support.v7.widget.LinearLayoutManager;

import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.ViewRecyclerViewBinding;
import com.timmy.baselib.base.fragment.TBaseContentFragment;

import java.util.Arrays;

public class SimpleFragment extends TBaseContentFragment<ViewRecyclerViewBinding> {

    @Override
    protected int getLayoutRes() {
        return R.layout.view_recycler_view;
    }

    @Override
    protected void initBase() {
        super.initBase();
        String[] stringArray = getResources().getStringArray(R.array.date);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.recyclerView.setAdapter(new SimpleAdapter(this.getContext(), Arrays.asList(stringArray)));
    }
}
