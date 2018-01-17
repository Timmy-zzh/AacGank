package com.timmy.aacgank.bean.gank;

import java.util.List;

/**
 *
 */
public class GankResult  {

    public List<String> category;
    public GankCategory results;

    public static class GankCategory {
        public List<Gank> Android;
        public List<Gank> iOS;
    }
}
