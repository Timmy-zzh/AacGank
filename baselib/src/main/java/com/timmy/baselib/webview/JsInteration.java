package com.timmy.baselib.webview;

import android.content.Context;
import android.webkit.JavascriptInterface;



/**
 * Created by itservice on 2017/12/1.
 */

public class JsInteration {

    private Context mContext;

    public JsInteration(Context context) {
        this.mContext = context;
    }

    /**
     * 专题H5页面,商品点击后调整到商品详情页
     * Json数据获取商品id
     */
    @JavascriptInterface
    public void activeProject(String idJson) {
//        Logger.d("题H5页面:" + idJson);
//        ID idObj = JsonUtil.fromJson(idJson, ID.class);
//        GoodsDetailActivity.startActionNewTask(mContext, idObj.id);
    }
}
