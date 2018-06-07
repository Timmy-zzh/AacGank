package com.timmy.aacgank.bean.gank;

import java.util.List;

/**
 * 获取单日数据
 */
public class DailyData {
//       "_id": "5732b15067765974f885c05a",
//               "content": "<h3><img alt=\"\" src=\"http://ww2.sinaimg.cn/large/610dc034jw1f3rbikc83dj20dw0kuadt.jpg\" /></h3>\r\n\r\n<h3>Android</h3>\r\n\r\n<ul>\r\n\t<li><a href=\"http://www.jianshu.com/p/d9143a92ad94\" target=\"_blank\">Fragment\u5b8c\u5168\u89e3\u6790\u4e09\u6b65\u66f2</a>&nbsp;(AndWang)</li>\r\n\t<li><a href=\"http://blog.csdn.net/dd864140130/article/details/51313342\" target=\"_blank\">\u9001\u7ed9\u5c0f\u767d\u7684\u8bbe\u8ba1\u8bf4\u660e\u4e66</a>&nbsp;(Dong dong Liu)</li>\r\n\t<li><a href=\"http://www.jcodecraeer.com/a/anzhuokaifa/2016/0508/4222.html\" target=\"_blank\">Material \u98ce\u683c\u7684\u5e72\u8d27\u5ba2\u6237\u7aef</a>&nbsp;(None)</li>\r\n\t<li><a href=\"https://github.com/jiang111/ScalableTabIndicator\" target=\"_blank\">\u53ef\u5b9a\u5236\u7684Indicator,\u7ed3\u5408ViewPager\u4f7f\u7528</a>&nbsp;(NewTab)</li>\r\n\t<li><a href=\"https://github.com/north2014/T-MVP\" target=\"_blank\">T-MVP\uff1a\u6cdb\u578b\u6df1\u5ea6\u89e3\u8026\u4e0b\u7684MVP\u5927\u7626\u8eab</a>&nbsp;(Bai xiaokang)</li>\r\n\t<li><a href=\"https://github.com/GrenderG/Prefs\" target=\"_blank\">Simple Android SharedPreferences wrapper</a>&nbsp;(\u848b\u670b)</li>\r\n\t<li><a href=\"https://github.com/shiraji/permissions-dispatcher-plugin\" target=\"_blank\">IntelliJ plugin for supporting PermissionsDispatcher</a>&nbsp;(\u848b\u670b)</li>\r\n</ul>\r\n\r\n<h3>iOS</h3>\r\n\r\n<ul>\r\n\t<li><a href=\"http://mp.weixin.qq.com/s?__biz=MzIwMTYzMzcwOQ==&amp;mid=2650948304&amp;idx=1&amp;sn=f76e7b765a7fcabcb71d37052b46e489&amp;scene=0#wechat_redirect\" target=\"_blank\">\u522b\u4eba\u7684&nbsp;App \u4e0d\u597d\u7528\uff1f\u81ea\u5df1\u6539\u4e86\u4fbf\u662f\u3002Moves \u7bc7\uff08\u4e0a\uff09</a>&nbsp;(tripleCC)</li>\r\n\t<li><a href=\"https://github.com/netease-im/NIM_iOS_UIKit\" target=\"_blank\">\u7f51\u6613\u4e91\u4fe1iOS UI\u7ec4\u4ef6\u6e90\u7801\u4ed3\u5e93</a>&nbsp;(__weak_Point)</li>\r\n\t<li><a href=\"https://github.com/openshopio/openshop.io-ios\" target=\"_blank\">OpenShop \u5f00\u6e90</a>&nbsp;(\u4ee3\u7801\u5bb6)</li>\r\n\t<li><a href=\"https://github.com/garnele007/SwiftOCR\" target=\"_blank\">Swift \u5b9e\u73b0\u7684 OCR \u8bc6\u522b\u5e93</a>&nbsp;(\u4ee3\u7801\u5bb6)</li>\r\n\t<li><a href=\"http://yulingtianxia.com/blog/2016/05/06/Let-your-WeChat-for-Mac-never-revoke-messages/\" target=\"_blank\">\u8ba9\u4f60\u7684\u5fae\u4fe1\u4e0d\u518d\u88ab\u4eba\u64a4\u56de\u6d88\u606f</a>&nbsp;(CallMeWhy)</li>\r\n\t<li><a href=\"http://drops.wooyun.org/mobile/15406\" target=\"_blank\">\u5fae\u4fe1\u53cc\u5f00\u8fd8\u662f\u5fae\u4fe1\u5b9a\u65f6\u70b8\u5f39\uff1f</a>&nbsp;(CallMeWhy)</li>\r\n</ul>\r\n\r\n<h3>\u778e\u63a8\u8350</h3>\r\n\r\n<ul>\r\n\t<li><a href=\"http://blog.csdn.net/shenyisyn/article/details/50056319\" target=\"_blank\">\u963b\u788d\u65b0\u624b\u7a0b\u5e8f\u5458\u63d0\u5347\u76848\u4ef6\u5c0f\u4e8b</a>&nbsp;(LHF)</li>\r\n\t<li><a href=\"http://36kr.com/p/5046775.html\" target=\"_blank\">\u7a0b\u5e8f\u5458\u3001\u9ed1\u5ba2\u4e0e\u5f00\u53d1\u8005\u4e4b\u522b</a>&nbsp;(LHF)</li>\r\n</ul>\r\n\r\n<h3>\u62d3\u5c55\u8d44\u6e90</h3>\r\n\r\n<ul>\r\n\t<li><a href=\"http://www.wxtlife.com/2016/04/25/java-jvm-gc/\" target=\"_blank\">\u8be6\u7ec6\u4ecb\u7ecd\u4e86java jvm \u5783\u573e\u56de\u6536\u76f8\u5173\u7684\u77e5\u8bc6\u6c47\u603b</a>&nbsp;(Aaron)</li>\r\n\t<li><a href=\"http://arondight.me/2016/04/17/%E4%BD%BF%E7%94%A8GPG%E7%AD%BE%E5%90%8DGit%E6%8F%90%E4%BA%A4%E5%92%8C%E6%A0%87%E7%AD%BE/\" target=\"_blank\">\u4f7f\u7528GPG\u7b7e\u540dGit\u63d0\u4ea4\u548c\u6807\u7b7e</a>&nbsp;(\u848b\u670b)</li>\r\n</ul>\r\n\r\n<h3>\u4f11\u606f\u89c6\u9891</h3>\r\n\r\n<ul>\r\n\t<li><a href=\"http://weibo.com/p/2304443956b04478364a64185f196f0a89266d\" target=\"_blank\">\u79d2\u62cd\u725b\u4eba\u5927\u5408\u96c6\uff0c[\u7b11cry]\u76ee\u6d4b\u819d\u76d6\u6839\u672c\u4e0d\u591f\u7528\u554a\u3002</a>&nbsp;(LHF)</li>\r\n</ul>\r\n\r\n<p><iframe frameborder=\"0\" height=\"498\" src=\"http://v.qq.com/iframe/player.html?vid=w0198nyi5x5&amp;tiny=0&amp;auto=0\" width=\"640\"></iframe></p>\r\n\r\n<p>\u611f\u8c22\u6240\u6709\u9ed8\u9ed8\u4ed8\u51fa\u7684\u7f16\u8f91\u4eec,\u613f\u5927\u5bb6\u6709\u7f8e\u597d\u4e00\u5929.</p>\r\n",
//               "publishedAt": "2016-05-11T12:11:00.0Z",
//               "title": "\u79d2\u62cd\u725b\u4eba\u5927\u96c6\u5408\uff0c\u770b\u5230\u54ea\u4e2a\u4f60\u8dea\u4e86"

//    public String _id;
//    public String content;
//    public String publishedAt;
//    public String title;

    public List<String> category;
    public GankResult.GankCategory results;

    public static class GankCategory {
        public List<Gank> Android;
        public List<Gank> iOS;
        public List<Gank> 前端;
        public List<Gank> 瞎推荐;
        public List<Gank> App;
        public List<Gank> 休息视频;


        @Override
        public String toString() {
            return "GankCategory{" +
                    "Android=" + Android +
                    ", iOS=" + iOS +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DailyData{" +
                "category=" + category +
                ", results=" + results +
                '}';
    }
}
