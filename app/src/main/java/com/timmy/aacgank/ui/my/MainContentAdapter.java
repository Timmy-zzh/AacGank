package com.timmy.aacgank.ui.my;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.my.MainModel;
import com.timmy.aacgank.bean.my.MainTag;
import com.timmy.aacgank.ui.login.GestureLoginActivity;
import com.timmy.aacgank.ui.multimedia.audio.AudioStudyActivity;
import com.timmy.androidbase.activity.ActivityStudyActivity;
import com.timmy.androidbase.animation.AnimationUseActivity;
import com.timmy.androidbase.behavior.BehaviorActivity;
import com.timmy.androidbase.binder.BinderActivity;
import com.timmy.androidbase.broadcastReceiver.BroadcaseReceiverActivity;
import com.timmy.androidbase.contentProvider.ContentProviderStudyActivity;
import com.timmy.androidbase.database.DatabaseActivity;
import com.timmy.androidbase.motionEvent.MotionEventActivity;
import com.timmy.androidbase.graphicsDraw.GraphicsDrawActivity;
import com.timmy.androidbase.recyclveriew.RecyclerViewActivity;
import com.timmy.androidbase.service.ServiceStudyActivity;
import com.timmy.aacgank.ui.multimedia.openGL.OpenGLStudyActivity;
import com.timmy.aacgank.ui.multimedia.video.CameraStudyActivity;
import com.timmy.aacgank.ui.multimedia.video.VideoRecordActivity;
import com.timmy.aacgank.ui.multimedia.video.VideoStudyActivity1;
import com.timmy.androidbase.uiDraw.UIDrawActivity;
import com.timmy.customeview.cityselect.CitySelectActivity;
import com.timmy.thirdframework.database.DataBaseActivity;
import com.timmy.thirdframework.downrefresh.DownRefreshActivity;
import com.timmy.thirdframework.eventbus.EventBusActivity;
import com.timmy.thirdframework.net.NetHttpActivity;
import com.timmy.thirdframework.permissions.PermissionsActivity;

import java.util.ArrayList;
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
                //Android基础
                switch (model.getTag()) {
                    //Android
//                    case MainTag.ANDROID.TAG_FOUR_COMPONENT:
//                        //四大组件
//                        fourComponent();
//                        break;
                    case MainTag.ANDROID.TAG_ACTIVITY:
                        gotoNextActivity(ActivityStudyActivity.class);
                        break;
                    case MainTag.ANDROID.TAG_SERVICE:
                        gotoNextActivity(ServiceStudyActivity.class);
                        break;
                    case MainTag.ANDROID.TAG_BINDER:
                        gotoNextActivity(BinderActivity.class);
                        break;
                    case MainTag.ANDROID.TAG_BROADCASTRECEIVER:
                        gotoNextActivity(BroadcaseReceiverActivity.class);
                        break;
                    case MainTag.ANDROID.TAG_CONTENTPROVIDER:
                        gotoNextActivity(ContentProviderStudyActivity.class);
                        break;
                    case MainTag.ANDROID.TAG_BEHAVIOR:
                        //自定义Behavior
                        gotoNextActivity(BehaviorActivity.class);
                        break;
                    case MainTag.ANDROID.TAG_DATA_BASE:
                        //数据存储
                        gotoNextActivity(DatabaseActivity.class);
                        break;
                    case MainTag.ANDROID.TAG_MOTION_EVENT:
                        //事件分发
                        gotoNextActivity(MotionEventActivity.class);
                        break;
                    case MainTag.ANDROID.TAG_UI_DRAW:
                        //UI绘制流程
                        gotoNextActivity(UIDrawActivity.class);
                        break;
                    case MainTag.ANDROID.TAG_GRAPHICS_DRAW:
                        //Paint使用
                        gotoNextActivity(GraphicsDrawActivity.class);
                        break;
                    case MainTag.ANDROID.TAG_ANIMATION:
                        gotoNextActivity(AnimationUseActivity.class);
                        break;
                    case MainTag.ANDROID.TAG_RECYCLER_VIEW:
                        gotoNextActivity(RecyclerViewActivity.class);
                        break;
                    default:
                        break;
                }

                //多媒体开发
                switch (model.getTag()) {
                    //多媒体 音视频开发
                    case MainTag.VIDEO.TAG_AUDIO:
                        //音频数据采集
                        gotoNextActivity(AudioStudyActivity.class);
                        break;
                    case MainTag.VIDEO.TAG_VIDEO_CAMERA:
                        //Camera采集视频数据
                        gotoNextActivity(CameraStudyActivity.class);
                        break;
                    case MainTag.VIDEO.TAG_VIDEO_BASE:
                        //使用MediaExtractor与MediaMuxer解析和封装mp4文件
                        gotoNextActivity(VideoStudyActivity1.class);
                        break;
                    case MainTag.VIDEO.TAG_VIDEO_RECORD:
                        //使用MediaExtractor与MediaMuxer解析和封装mp4文件
                        gotoNextActivity(VideoRecordActivity.class);
                        break;
                    case MainTag.VIDEO.TAG_OPENGL:
                        gotoNextActivity(OpenGLStudyActivity.class);
                        break;
                    default:
                        break;
                }

                //自定义控件
                switch (model.getTag()) {
                    case MainTag.CUSTOMEVIEW.TAG_CITY_SELECT:
                        gotoNextActivity(CitySelectActivity.class);
                        break;
                    case MainTag.CUSTOMEVIEW.TAG_GESTURE_LOCK:
                        gotoNextActivity(GestureLoginActivity.class);
                        break;
                    default:
                        break;
                }

                //框架源码
                switch (model.getTag()) {
                    case MainTag.FRAMEWORK.TAG_DATABASE:
                        gotoNextActivity(DataBaseActivity.class);
                        break;
                    case MainTag.FRAMEWORK.TAG_EVENT_BUS:
                        gotoNextActivity(EventBusActivity.class);
                        break;
                    case MainTag.FRAMEWORK.TAG_BUTTER_KNIFE:
                        break;
                    case MainTag.FRAMEWORK.TAG_PERMISSIONS:
                        gotoNextActivity(PermissionsActivity.class);
                        break;
                    case MainTag.FRAMEWORK.TAG_PULL_DOWN_REFRESH://下拉刷新
                        gotoNextActivity(DownRefreshActivity.class);
                        break;
                    case MainTag.FRAMEWORK.TAG_NETWORK_REQUEST:
                        gotoNextActivity(NetHttpActivity.class);
                        break;
                }
            }
        });
    }

//    private void fourComponent() {
//        new TListDialog.Builder(((FragmentActivity) context).getSupportFragmentManager())
//                .setAdapter(new TBaseAdapter<String>(R.layout.item_simple_text,
//                        Arrays.asList(new String[]{"Activity", "Service", "BroadcastReceiver", "ContentProvide"})) {
//                    @Override
//                    protected void onBind(BindViewHolder holder, int position, String item) {
//                        holder.setText(R.id.tv, item);
//                    }
//                })
//                .setOnAdapterItemClickListener(new TBaseAdapter.OnAdapterItemClickListener<String>() {
//                    @Override
//                    public void onItemClick(BindViewHolder holder, int position, String item, TDialog tDialog) {
//                        tDialog.dismiss();
//                        switch (item) {
//                            case "Activity":
//                                context.startActivity(new Intent(context, ActivityLifeActivity.class));
//                                break;
//                            case "Service":
//                                context.startActivity(new Intent(context, ServiceStudyActivity.class));
//                                break;
//                            case "BroadcastReceiver":
//                                context.startActivity(new Intent(context, BroadcaseReceiverActivity.class));
//                                break;
//                            case "ContentProvide":
//                                context.startActivity(new Intent(context, ContentProviderStudyActivity.class));
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//                })
//                .setScreenWidthAspect((Activity) context, 0.8f)
//                .setDimAmount(0.5f)
//                .create()
//                .show();
//    }

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
