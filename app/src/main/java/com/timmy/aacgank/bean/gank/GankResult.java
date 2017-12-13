package com.timmy.aacgank.bean.gank;

import com.timmy.aacgank.bean.base.SupperResult;

import java.util.List;

/**
 *
 */
public class GankResult extends SupperResult {

    public List<String> category;
    public GankCategory results;

    public static class GankCategory {
        public List<Gank> Android;
        public List<Gank> iOS;
    }
}
