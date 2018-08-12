package com.timmy.aacgank.ui.cityselect;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.ActivityCitySelectBinding;
import com.timmy.aacgank.ui.MainActivity;
import com.timmy.aacgank.ui.cityselect.helper.PinyinComparator;
import com.timmy.aacgank.ui.cityselect.helper.PinyinUtils;
import com.timmy.aacgank.ui.cityselect.helper.SortModel;
import com.timmy.baselib.base.activity.TBaseContentActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CitySelectActivity extends TBaseContentActivity<ActivityCitySelectBinding> {

    private List<SortModel> mDateList;
    private SortAdapter mAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);

        binding.letterBar.setOnLetterChangeListener(new LetterBar.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(letter.charAt(0));
                if (position != -1) {
                    layoutManager.scrollToPositionWithOffset(position, 0);
                }
            }
        });

        mDateList = filledData(getResources().getStringArray(R.array.date));

        PinyinComparator mComparator = new PinyinComparator();
        // 根据a-z进行排序源数据
        Collections.sort(mDateList, mComparator);

        //RecyclerView设置manager
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        mAdapter = new SortAdapter(this, mDateList);

        TitleItemDecoration mDecoration = new TitleItemDecoration(this, mDateList);
        //如果add两个，那么按照先后顺序，依次渲染。
        binding.recyclerView.addItemDecoration(mDecoration);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        binding.recyclerView.setAdapter(mAdapter);

//        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//               LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//                SortModel item = (SortModel) mAdapter.getItem(firstVisibleItemPosition);
//                binding.letterBar.selectLetter(item.getLetters());
//            }
//        });
    }


    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<>();

        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setLetters(sortString.toUpperCase());
            } else {
                sortModel.setLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

}
