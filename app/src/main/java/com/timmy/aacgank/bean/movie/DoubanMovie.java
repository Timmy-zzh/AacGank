package com.timmy.aacgank.bean.movie;

import java.util.List;

/**
 * 豆瓣电影条目Bean
 * <p>
 * {
 * "rating":{
 * "max": 10,
 * "average": 8.9,
 * "stars": "45",
 * "min": 0
 * },
 * "genres":[
 * "剧情",
 * "喜剧"
 * ],
 * "title": "我不是药神",
 * "casts":[
 * {
 * "alt": "https://movie.douban.com/celebrity/1274297/",
 * "avatars":{"small": "https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p43738.jpg", "large": "https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p43738.jpg",…},
 * "name": "徐峥",
 * "id": "1274297"
 * },
 * {"alt": "https://movie.douban.com/celebrity/1313837/",
 * "avatars":{"small": "https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1496577458.38.jpg",…},
 * {"alt": "https://movie.douban.com/celebrity/1276085/",
 * "avatars":{"small": "https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1514533436.1.jpg",…}
 * ],
 * "collect_count": 664740,
 * "original_title": "我不是药神",
 * "subtype": "movie",
 * "directors":[
 * {
 * "alt": "https://movie.douban.com/celebrity/1349765/",
 * "avatars":{"small": "https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1529658740.26.jpg", "large": "https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1529658740.26.jpg",…},
 * "name": "文牧野",
 * "id": "1349765"
 * }
 * ],
 * "year": "2018",
 * "images":{
 * "small": "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2519070834.jpg",
 * "large": "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2519070834.jpg",
 * "medium": "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2519070834.jpg"
 * },
 * "alt": "https://movie.douban.com/subject/26752088/",
 * "id": "26752088"
 */
public class DoubanMovie {

    public Rating rating;//rating	评分，见附录
    public List<String> genres; //影片类型，最多提供3个
    public String title;//中文名
    public List<Cast> casts;//主演，最多可获得4个，
    public int collect_count;//看过人数,
    public String original_title;//	原名
    public String subtype;//	条目分类, movie或者tv
    public List<Cast> directors;//	导演，数据结构为影人的简化描述
    public String year;//	年代
    public Avatar images;//电影海报图，分别提供288px x 465px(大)，96px x 155px(中) 64px x 103px(小)尺寸
    public String alt;//	条目页URL
    public String id;//	条目id

    /**
     * min	最低评分	int	Y	Y	Y	-
     * max	最高评分	int	Y	Y	Y	-
     * average	评分	float(1)	Y	Y	Y	-
     * stars	评星数	int
     */
    public class Rating {
        public int max;
        public float average;
        public int min;
        public String stars;
    }

    /**
     * 主演
     * id	影人条目id	str	Y	Y	Y	-
     * name	中文名	str	Y	Y	Y	-
     * alt	影人条目URL	str	Y	Y	Y	-
     * avatars	影人头像，分别提供420px x 600px(大)，140px x 200px(中) 70px x 100px(小)尺寸
     */
    public class Cast {
        public String alt;
        public String id;
        public String name;
        public Avatar avatars;
    }
}
