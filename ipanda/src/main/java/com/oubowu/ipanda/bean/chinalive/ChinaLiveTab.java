package com.oubowu.ipanda.bean.chinalive;

import java.util.List;

/**
 * Created by Oubowu on 2018/3/5 14:30.
 */
public class ChinaLiveTab {

    public List<TablistBean> tablist;
    public List<TablistBean> alllist;

    public static class TablistBean {
        /**
         * title : 精编直播
         * url : http://www.ipanda.com/kehuduan/liebiao/shanhaiguan/index.json
         * type :
         * order : 1
         */

        public String title;
        public String url;
        public String type;
        public String order;
    }
}
