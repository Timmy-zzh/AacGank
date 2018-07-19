package com.timmy.aacgank.ui.cityselect;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.ActivityCitySelectBinding;
import com.timmy.aacgank.ui.MainActivity;
import com.timmy.aacgank.ui.cityselect.helper.PinyinComparator;
import com.timmy.aacgank.ui.cityselect.helper.PinyinUtils;
import com.timmy.aacgank.ui.cityselect.helper.SortModel;
import com.timmy.baselib.base.activity.TBaseContentActivity;
import com.timmy.baselib.utils.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

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

//        initDataSync();
        initDataAsync();

//        TaskStatus taskStatus = TaskStatus.getTaskStatus(2);
//        TextView textView = new TextView(this);
//        textView.setText(taskStatus.mText);
//        textView.setTextColor(Color.parseColor(taskStatus.mColor));
//        Enum

    }

    String url = "https://www.baidu.com";
    //同步请求,需在子线程进行处理
    private void initDataSync() {
        //1.创建Client客户端实例
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call newCall = client.newCall(request);
        try {
            Response response = newCall.execute();
            String string = response.body().string();
            LogUtils.d(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //异步请求
    private void initDataAsync() {
        //1.新建客户端client
        OkHttpClient client = new OkHttpClient.Builder().build();
        client = new OkHttpClient();
        //2.新建一个请求 -- 会有多个请求
        final Request request = new Request.Builder()
                .method("GET", null)
                .url(url)
                .build();
        //3.发起请求,异步
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("fail:" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                LogUtils.d("onResponse:" + response.toString());
            }
        });
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
