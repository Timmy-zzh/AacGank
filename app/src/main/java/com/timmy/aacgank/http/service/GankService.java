package com.timmy.aacgank.http.service;

import com.timmy.aacgank.bean.base.BaseGankResult;
import com.timmy.aacgank.bean.gank.DailyData;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.baselib.bean.BaseResult;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * http://gank.io/api
 *
 * 搜索 API
 * http://gank.io/api/search/query/listview/category/Android/count/10/page/1
 * 注：
 * category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
 * count 最大 50
 * <p>
 * 获取某几日干货网站数据:
 * http://gank.io/api/history/content/2/1
 * 注： 2 代表 2 个数据，1 代表：取第一页数据
 * <p>
 * <p>
 * 获取特定日期网站数据:
 * http://gank.io/api/history/content/day/2016/05/11
 * <p>
 * <p>
 * 获取发过干货日期接口:
 * http://gank.io/api/day/history 方式 GET
 * <p>
 * <p>
 * 支持提交干货到审核区:
 * https://gank.io/api/add2gank 方式: POST
 * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
 * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
 * 请求个数： 数字，大于0
 * 第几页：数字，大于0
 * 例：
 * http://gank.io/api/data/Android/10/1
 * <p>
 * <p>
 * 每日数据： http://gank.io/api/day/年/月/日
 * http://gank.io/api/day/2015/08/06
 * <p>
 * <p>
 * 随机数据：http://gank.io/api/random/data/分类/个数
 * 数据类型：福利 | Android | iOS | 休息视频 | 拓展资源 | 前端
 * 个数： 数字，大于0
 * 例：
 * http://gank.io/api/random/data/Android/20
 */
public interface GankService {
    /**
     * 根据类型获取数据<福利><Andorid><IOS>
     * http://gank.io/api/data/福利/10/1
     */
    @GET("data/{type}/20/{page}")
    Flowable<BaseGankResult<List<Gank>>> getWelfares(@Path("type") String type,
                                                     @Path("page") int page);

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     * http://gank.io/api/day/2015/08/07
     */
    @GET("day/{year}/{month}/{day}")
    Call<DailyData> getGankDetailData(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    /**
     * 获取特定日期网站数据:
     * http://gank.io/api/history/content/day/2016/05/11
     */
    @GET("history/content/day/{year}/{month}/{day}")
    Flowable<BaseResult<DailyData>> getDailyData(@Path("year") int year, @Path("month") int month, @Path("day") int day);


}
