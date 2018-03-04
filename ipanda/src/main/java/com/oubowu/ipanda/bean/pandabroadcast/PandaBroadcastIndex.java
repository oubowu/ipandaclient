package com.oubowu.ipanda.bean.pandabroadcast;

import java.util.List;

/**
 * Created by Oubowu on 2018/3/2 14:37.
 */
public class PandaBroadcastIndex {

    /**
     * bigImg : [{"image":"http://p1.img.cctvpic.com/photoworkspace/2018/02/19/2018021911254788849.jpg","title":"广东大熊猫三胞胎给大家拜大年","url":"http://live.ipanda.com/2018/02/19/VIDE1qpdKZtsMXxGqM2pPjmN180219.shtml","id":"TITE1519145285340611","type":"2","stype":"","pid":"0f31fe00c4b64d8cb8a8f87690363c5f","vid":"","order":"1"}]
     * listurl : http://api.cntv.cn/apicommon/index?path=iphoneInterface/general/getArticleAndVideoListInfo.json&primary_id=PAGE1422435191506336&serviceId=panda
     */

    public String listurl;
    public List<BigImgBean> bigImg;

    public static class BigImgBean {
        /**
         * image : http://p1.img.cctvpic.com/photoworkspace/2018/02/19/2018021911254788849.jpg
         * title : 广东大熊猫三胞胎给大家拜大年
         * url : http://live.ipanda.com/2018/02/19/VIDE1qpdKZtsMXxGqM2pPjmN180219.shtml
         * id : TITE1519145285340611
         * type : 2
         * stype :
         * pid : 0f31fe00c4b64d8cb8a8f87690363c5f
         * vid :
         * order : 1
         */

        public String image;
        public String title;
        public String url;
        public String id;
        public String type;
        public String stype;
        public String pid;
        public String vid;
        public String order;
    }
}
