package com.timmy.aacgank.ui.my;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.my.MainModel;
import com.timmy.aacgank.bean.my.MainTag;
import com.timmy.aacgank.ui.person.activity.ActivityLifeActivity;
import com.timmy.aacgank.ui.person.audio.AudioStudyActivity;
import com.timmy.aacgank.ui.person.behavior.BehaviorActivity;
import com.timmy.aacgank.ui.person.service.ServiceStudyActivity;
import com.timmy.baselib.utils.LogUtils;
import com.timmy.baselib.utils.ToastUtils;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.base.TBaseAdapter;
import com.timmy.tdialog.list.TListDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainContentAdapter extends RecyclerView.Adapter<MainContentAdapter.TabHolder> {

    private List<MainModel> dataList;
    private Context context;

    public MainContentAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
    }

    public void setData(List<MainModel> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public TabHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_main_page, null);
        return new TabHolder(view);
    }

    @Override
    public void onBindViewHolder(TabHolder holder, final int position) {
        final MainModel model = dataList.get(position);
        holder.mContent.setText(model.getDesc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.widget.Toast.makeText(context, dataList.get(position).getDesc(), android.widget.Toast.LENGTH_SHORT).show();
                switch (model.getTag()) {//Android
                    case MainTag.ANDROID.TAG_FOUR_COMPONENT://四大组件
                        fourComponent();
                        break;
                    case MainTag.ANDROID.TAG_BEHAVIOR:
                        gotoNextActivity(BehaviorActivity.class);
                        break;
                }

                switch (model.getTag()) {//音视频开发
                    case MainTag.VIDEO.TAG_AUDIO:
                        gotoNextActivity(AudioStudyActivity.class);
                        break;
                }


            }
        });
    }

    private void fourComponent() {
        new TListDialog.Builder(((FragmentActivity) context).getSupportFragmentManager())
                .setAdapter(new TBaseAdapter<String>(R.layout.item_simple_text,
                        Arrays.asList(new String[]{"Activity", "Service", "ContentProvide", "BroadcastReceiver"})) {
                    @Override
                    protected void onBind(BindViewHolder holder, int position, String item) {
                        holder.setText(R.id.tv, item);
                    }
                })
                .setOnAdapterItemClickListener(new TBaseAdapter.OnAdapterItemClickListener<String>() {
                    @Override
                    public void onItemClick(BindViewHolder holder, int position, String item, TDialog tDialog) {
                        tDialog.dismiss();
                        switch (item) {
                            case "Activity":
                                context.startActivity(new Intent(context, ActivityLifeActivity.class));
                                break;
                            case "Service":
                                context.startActivity(new Intent(context, ServiceStudyActivity.class));
                                break;
                            case "ContentProvide":
                                context.startActivity(new Intent(context, ServiceStudyActivity.class));
                                break;
                            case "BroadcastReceiver":
                                context.startActivity(new Intent(context, ServiceStudyActivity.class));
                                break;
                        }
                    }
                })
                .setScreenWidthAspect((Activity) context, 0.8f)
                .setDimAmount(0.5f)
                .create()
                .show();
    }

    //                switch (model.getTag()) {
//                    case MainTag.TAG_XIUXIU:
//                       gotoNextActivity(context, XiuViewActivity.class);
//                        break;
//                    case MainTag.TAG_QQ_ZONE_STRETCH:
//                       gotoNextActivity(context, StretchListActivity.class);
//                        break;
//                    case MainTag.TAG_RECYCLER_VIEW:
//                       gotoNextActivity(context, RecyclerViewActivity.class);
//                        break;
//                    case MainTag.TAG_COLLAPSING_TOOLBAR_LAYOUT:
//                       gotoNextActivity(context, CollapsingToolbarLayoutActivity.class);
//                        break;
//                    case MainTag.TAG_SLIDESLIP:
//                       gotoNextActivity(context, DrawerLayoutActivity.class);
//                        break;
//                    case MainTag.TAG_SNACKBAR:
//                       gotoNextActivity(context, SnackBarActivity.class);
//                        break;
//                    case MainTag.TAG_TOAST:
//                       gotoNextActivity(context, ToastActivity.class);
//                        break;
//                    case MainTag.TAG_TEXT_INPUT_LAYOUT:
//                       gotoNextActivity(context, TextInputLayoutActivity.class);
//                        break;
//                    case MainTag.TAG_TOOLBAR:
//                       gotoNextActivity(context, ToolbarActivity.class);
//                        break;
//                    case MainTag.TAG_SEARCH_VIEW:
//                       gotoNextActivity(context, SearchViewActivity.class);
//                        break;
//                    case MainTag.TAG_LINEAR_LAYOUT_COMPAT:
//                       gotoNextActivity(context, LinearLayoutCompatActivity.class);
//                        break;
//                    case MainTag.TAG_TAB_LAYOUT:
//                       gotoNextActivity(context, TabLayoutActivity.class);
//                        break;
//                    case MainTag.TAG_PALETTE:
//                       gotoNextActivity(context, PaletteActivity.class);
//                        break;
//                    case MainTag.TAG_CARD_VIEW:
//                       gotoNextActivity(context, CardViewActivity.class);
//                        break;
//                    case MainTag.TAG_COORDINATOR_LAYOUT:
//                       gotoNextActivity(context, CoordinatorLayoutActivity2.class);
//                        break;
//                    case MainTag.TAG_ANIMATION_VIEW:
//                       gotoNextActivity(context, ViewAnimationActivity.class);
//                        break;
//                    case MainTag.TAG_MOTION_EVENT:
//                       gotoNextActivity(context, MotionEventActivity.class);
//                        break;
//                    case MainTag.TAG_PATH:
//                       gotoNextActivity(context, PathUseActicity.class);
//                        break;
//                    case MainTag.TAG_SHADER:
//                       gotoNextActivity(context, ShaderUseActicity.class);
//                        break;
//                    case MainTag.TAG_DIALOG:
//                       gotoNextActivity(context, DialogActivity.class);
//                        break;
//
//
//                    ///////////////////////////自定义控件
//                    case MainTag.CUSTOMEVIEW.TAG_CLOCK_VIEW:
//                       gotoNextActivity(context, ClockViewActivity.class);
//                        break;
//                    case MainTag.CUSTOMEVIEW.TAG_HOT_TAG:
//                       gotoNextActivity(context, HotTagActivity.class);
//                        break;
//                    case MainTag.CUSTOMEVIEW.TAG_LETTER_NAVIGATION:
//                       gotoNextActivity(context, LetterNavigationActivity.class);
//                        break;
//                    case MainTag.CUSTOMEVIEW.TAG_NOTE_PAD:
//                       gotoNextActivity(context, NotePadActivity.class);
//                        break;
//                    case MainTag.CUSTOMEVIEW.TAG_MY_VIEWPAGER:
//                       gotoNextActivity(context, MyViewPagerActivity.class);
//                        break;
//                    case MainTag.CUSTOMEVIEW.TAG_MY_INDICATOR:
//                       gotoNextActivity(context, MyIndicatorActivity.class);
//                        break;
//                    case MainTag.CUSTOMEVIEW.TAG_GUAGUA_WINNING:
//                       gotoNextActivity(context, GuaGuaWinningActivity.class);
//                        break;
//                    case MainTag.CUSTOMEVIEW.TAG_PHOTO_VIEW:
//                       gotoNextActivity(context, MyPhotoViewActivity.class);
//                        break;
//                    case MainTag.CUSTOMEVIEW.TAG_LOADING_LAYOUT:
//                       gotoNextActivity(context, LoadingLayoutActivity.class);
//                        break;
//                    case MainTag.CUSTOMEVIEW.TAG_RADAR_VIEW:
//                       gotoNextActivity(context, RadarViewActivity.class);
//                        break;
//                    case MainTag.CUSTOMEVIEW.TAG_IMOOC_RIPPLE:
//                       gotoNextActivity(context, IMoocWaterRippleActivity.class);
//                        break;
//                    case MainTag.CUSTOMEVIEW.TAG_COUNT_DOWN_TIME:
//                       gotoNextActivity(context, CountDownTimeActivity.class);
//                        break;
//                    case MainTag.CUSTOMEVIEW.TAG_CIRCLE_MENU:
//                       gotoNextActivity(context, CircleMenuLayoutActivity.class);
//                        break;
//
//                    ///////////////////////////项目总结
//                    case MainTag.PROJECT.TAG_ACTIVITY_LAUNCH:
//                       gotoNextActivity(context, WelcomeActivity.class);
//                        break;
//                    case MainTag.PROJECT.TAG_ACTIVITY_SPLASH:
//                       gotoNextActivity(context, SplashActivity.class);
//                        break;
//                    case MainTag.PROJECT.TAG_SVG:
//                       gotoNextActivity(context, SVGActivity.class);
//                        break;
//                    case MainTag.PROJECT.TAG_INFLATE:
//                       gotoNextActivity(context, InflateActivity.class);
//                        break;
//                    case MainTag.PROJECT.TAG_SERVICE:
//                       gotoNextActivity(context, ServiceActivity.class);
//                        break;
//                    case MainTag.PROJECT.TAG_AIDL_BINDER:
//                       gotoNextActivity(context, AIDLActivity.class);
//                        break;
//                    case MainTag.PROJECT.TAG_TWOCODE_DOWNLOAD:
//                       gotoNextActivity(context, TCDownLoadActivity.class);
//                        break;
//                    case MainTag.PROJECT.TAG_SOFT_KEYBOARD:
//                       gotoNextActivity(context, SoftKeyboardActivity.class);
//                        break;
//                    case MainTag.PROJECT.TAG_DATA_PERSIST:
//                       gotoNextActivity(context, DataPersistActivity.class);
//                        break;
//                    case MainTag.PROJECT.TAG_COUNT_DOWN:
//                       gotoNextActivity(context, CountDownActivity.class);
//                        break;
//                    case MainTag.PROJECT.TAG_SCREEN_ADAPTER:
//                       gotoNextActivity(context, ScreenAdapterActivity.class);
//                        break;
//
//
//                    ///////////////////////////框架学习
//                    case MainTag.FRAMEWORK.TAG_ANNOTATIONS:
//                       gotoNextActivity(context, AnnotationsActivity.class);
//                        break;
//                    case MainTag.FRAMEWORK.TAG_COMPILE_ANNOTATIONS:
//                       gotoNextActivity(context, CompileAnnotationActivity.class);
//                        break;
//                    case MainTag.FRAMEWORK.TAG_NETWORK_REQUEST:
//                       gotoNextActivity(context, NetWorkRequestActivity.class);
//                        break;
//                    case MainTag.FRAMEWORK.TAG_CUSTOME_RETROFIT2:
//                       gotoNextActivity(context, CustomRetrofit2Activity.class);
//                        break;
//                    case MainTag.FRAMEWORK.TAG_TENCENT_TINKER:
//                       gotoNextActivity(context, TinkerActivity.class);
//                        break;
//                    case MainTag.FRAMEWORK.TAG_DATABASE:
//                       gotoNextActivity(context, DataBaseActivity.class);
//                        break;
//                    case MainTag.FRAMEWORK.TAG_IMAGE_LOADER:
//                       gotoNextActivity(context, ImageLoaderActivity.class);
//                        break;
//                    case MainTag.FRAMEWORK.TAG_EVENT_BUS:
//                       gotoNextActivity(context, EventBusActivity.class);
//                        break;
//                    case MainTag.FRAMEWORK.TAG_MVP:
//                       gotoNextActivity(context, MVPActivity.class);
//                        break;
//                    case MainTag.FRAMEWORK.TAG_ALIBABA_V_LAYOUT:
//                       gotoNextActivity(context, VLayoutActivity.class);
//                        break;
//                    case MainTag.FRAMEWORK.TAG_PULL_DOWN_REFRESH://下拉刷新
//                       gotoNextActivity(context, DownRefreshActivity.class);
//                        break;
//
//                    default:
//                        break;
//                }
//            }
//        });
//    }
//
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class TabHolder extends RecyclerView.ViewHolder {

        TextView mContent;

        private TabHolder(View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.tv_desc);
        }
    }

    public void gotoNextActivity(Class<?> nextActivity) {
        Intent intent = new Intent(context, nextActivity);
        context.startActivity(intent);
    }
}
