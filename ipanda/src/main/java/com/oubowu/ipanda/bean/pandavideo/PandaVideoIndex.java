package com.oubowu.ipanda.bean.pandavideo;

import java.util.List;

/**
 * Created by Oubowu on 2018/2/27 17:04.
 */
public class PandaVideoIndex {

    public List<BigImgBean> bigImg;
    public List<ListBean> list;

    public static class BigImgBean {
        /**
         * url : http://live.ipanda.com/2018/02/17/VIDEM3HEZgjwBNm3w29rXEIh180217.shtml
         * image : http://p1.img.cctvpic.com/photoAlbum/page/performance/img/2018/2/19/1519029989581_268.jpg
         * title : 《恭贺新春》2018熊猫拜年歌
         * id : TITE1519023576024240
         * type : 2
         * stype :
         * pid : cdec90b06a3a4416986ce06598605510
         * vid :
         * order : 1
         */

        public String url;
        public String image;
        public String title;
        public String id;
        public String type;
        public String stype;
        public String pid;
        public String vid;
        public String order;
    }

    public static class ListBean {
        /**
         * url : http://live.ipanda.com/2018/02/12/VIDEg5P38YXv4zeuL6oi59iw180212.shtml
         * image : http://p1.img.cctvpic.com/photoworkspace/2018/02/12/2018021215083493318.jpg
         * title : 《熊猫TOP榜》
         * brief : 第二季第三十四期：吃播很流行
         * type : 2
         * videoLength : 03:31
         * id : VSET100284428835
         * order : 1
         */

        public String url;
        public String image;
        public String title;
        public String brief;
        public String type;
        public String videoLength;
        public String id;
        public String order;
    }
}
