package com.timmy.aacgank.bean.gank;

import com.timmy.aacgank.bean.base.BaseResult;

import java.util.List;

/**
 *
 */
public class GankResult extends BaseResult {

    private List<String> category;

    private GankCategory results;

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public GankCategory getResults() {
        return results;
    }

    public void setResults(GankCategory results) {
        this.results = results;
    }

    public class GankCategory {
        private List<Gank> Android;

        public List<Gank> getiOS() {
            return iOS;
        }

        public void setiOS(List<Gank> iOS) {
            this.iOS = iOS;
        }

        private List<Gank> iOS;

        public List<Gank> getAndroid() {
            return Android;
        }

        public void setAndroid(List<Gank> android) {
            Android = android;
        }

    }
}
