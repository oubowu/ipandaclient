package com.oubowu.ipanda.bean.pandalive;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Oubowu on 2018/2/5 11:08.
 */
public class RecordTab {


    /**
     * videoset : {"0":{"vsid":"VSET100167216881","relvsid":"VSET100372634974","name":"熊猫频道-精彩一刻","img":"http://p5.img.cctvpic.com/fmspic/vms/image/2013/06/21/VSET_1371809214479325.jpg","enname":"其他","url":"http://tv.cntv.cn/videoset/VSET100167216881","cd":"","zy":"","bj":"","dy":"","js":"","nf":"","yz":"","fl":"","sbsj":"2013-05-01","sbpd":"其他","desc":"精彩一刻栏目关注人气较高的熊猫个体，精选每日直播中最吸引人的画面，呈现熊猫生活中最精彩的状态。","playdesc":"","zcr":"","fcl":""},"count":"6050"}
     * video : [{"vsid":"VSET100167216881","order":"6058","vid":"dbf4a921535349b580875db0abc2d625","t":"《精彩一刻》 20180204 ［来，妈妈爱］之拖把神功","url":"http://tv.cntv.cn/video/VSET100167216881/dbf4a921535349b580875db0abc2d625","ptime":"2018-02-04 09:32:01","img":"http://p3.img.cctvpic.com/fmspic/2018/02/04/dbf4a921535349b580875db0abc2d625-43.jpg?p=2&h=120","len":"00:01:06","em":"CM01"},{"vsid":"VSET100167216881","order":"6055","vid":"2d15fdc968b34103bacb459a84c3a847","t":"《精彩一刻》 20180203 麻麻是难以翻越的大山","url":"http://tv.cntv.cn/video/VSET100167216881/2d15fdc968b34103bacb459a84c3a847","ptime":"2018-02-03 12:56:19","img":"http://p1.img.cctvpic.com/fmspic/2018/02/03/2d15fdc968b34103bacb459a84c3a847-9.jpg?p=2&h=120","len":"00:00:19","em":"CM01"},{"vsid":"VSET100167216881","order":"6053","vid":"45e3870db06d4ff5898db293f6d6c6aa","t":"《精彩一刻》 20180203 悬着的青团终于落下了","url":"http://tv.cntv.cn/video/VSET100167216881/45e3870db06d4ff5898db293f6d6c6aa","ptime":"2018-02-03 12:55:32","img":"http://p1.img.cctvpic.com/fmspic/2018/02/03/45e3870db06d4ff5898db293f6d6c6aa-9.jpg?p=2&h=120","len":"00:00:18","em":"CM01"},{"vsid":"VSET100167216881","order":"6054","vid":"f15fa4fb36424103ba9e1a24c1c14829","t":"《精彩一刻》 20180203 摇摇椅技能解锁","url":"http://tv.cntv.cn/video/VSET100167216881/f15fa4fb36424103ba9e1a24c1c14829","ptime":"2018-02-03 12:54:46","img":"http://p2.img.cctvpic.com/fmspic/2018/02/03/f15fa4fb36424103ba9e1a24c1c14829-33.jpg?p=2&h=120","len":"00:00:47","em":"CM01"},{"vsid":"VSET100167216881","order":"6056","vid":"9301be814b1545e6ad65fef391d64ca7","t":"《精彩一刻》 20180203 这才是真正的吸熊猫","url":"http://tv.cntv.cn/video/VSET100167216881/9301be814b1545e6ad65fef391d64ca7","ptime":"2018-02-03 12:53:50","img":"http://p3.img.cctvpic.com/fmspic/2018/02/03/9301be814b1545e6ad65fef391d64ca7-32.jpg?p=2&h=120","len":"00:00:44","em":"CM01"},{"vsid":"VSET100167216881","order":"6050","vid":"adf7183b947b4f5db0910ac27c65e3bf","t":"《精彩一刻》 20180203 一颗青团的诞生","url":"http://tv.cntv.cn/video/VSET100167216881/adf7183b947b4f5db0910ac27c65e3bf","ptime":"2018-02-03 12:24:24","img":"http://p1.img.cctvpic.com/fmspic/2018/02/03/adf7183b947b4f5db0910ac27c65e3bf-21.jpg?p=2&h=120","len":"00:00:22","em":"CM01"},{"vsid":"VSET100167216881","order":"6049","vid":"552ed6119420491da15d74802eb3e414","t":"《精彩一刻》 20180203 有选择困难症的熊","url":"http://tv.cntv.cn/video/VSET100167216881/552ed6119420491da15d74802eb3e414","ptime":"2018-02-03 12:23:33","img":"http://p1.img.cctvpic.com/fmspic/2018/02/03/552ed6119420491da15d74802eb3e414-20.jpg?p=2&h=120","len":"00:00:21","em":"CM01"}]
     */

    public VideosetBean videoset;
    public List<VideoBean> video;

    public static class VideosetBean {
        /**
         * 0 : {"vsid":"VSET100167216881","relvsid":"VSET100372634974","name":"熊猫频道-精彩一刻","img":"http://p5.img.cctvpic.com/fmspic/vms/image/2013/06/21/VSET_1371809214479325.jpg","enname":"其他","url":"http://tv.cntv.cn/videoset/VSET100167216881","cd":"","zy":"","bj":"","dy":"","js":"","nf":"","yz":"","fl":"","sbsj":"2013-05-01","sbpd":"其他","desc":"精彩一刻栏目关注人气较高的熊猫个体，精选每日直播中最吸引人的画面，呈现熊猫生活中最精彩的状态。","playdesc":"","zcr":"","fcl":""}
         * count : 6050
         */

        @SerializedName("0")
        public _$0Bean _$0;
        public String count;

        public static class _$0Bean {
            /**
             * vsid : VSET100167216881
             * relvsid : VSET100372634974
             * name : 熊猫频道-精彩一刻
             * img : http://p5.img.cctvpic.com/fmspic/vms/image/2013/06/21/VSET_1371809214479325.jpg
             * enname : 其他
             * url : http://tv.cntv.cn/videoset/VSET100167216881
             * cd :
             * zy :
             * bj :
             * dy :
             * js :
             * nf :
             * yz :
             * fl :
             * sbsj : 2013-05-01
             * sbpd : 其他
             * desc : 精彩一刻栏目关注人气较高的熊猫个体，精选每日直播中最吸引人的画面，呈现熊猫生活中最精彩的状态。
             * playdesc :
             * zcr :
             * fcl :
             */

            public String vsid;
            public String relvsid;
            public String name;
            public String img;
            public String enname;
            public String url;
            public String cd;
            public String zy;
            public String bj;
            public String dy;
            public String js;
            public String nf;
            public String yz;
            public String fl;
            public String sbsj;
            public String sbpd;
            public String desc;
            public String playdesc;
            public String zcr;
            public String fcl;
        }
    }

    public static class VideoBean {
        /**
         * vsid : VSET100167216881
         * order : 6058
         * vid : dbf4a921535349b580875db0abc2d625
         * t : 《精彩一刻》 20180204 ［来，妈妈爱］之拖把神功
         * url : http://tv.cntv.cn/video/VSET100167216881/dbf4a921535349b580875db0abc2d625
         * ptime : 2018-02-04 09:32:01
         * img : http://p3.img.cctvpic.com/fmspic/2018/02/04/dbf4a921535349b580875db0abc2d625-43.jpg?p=2&h=120
         * len : 00:01:06
         * em : CM01
         */

        public String vsid;
        public String order;
        public String vid;
        public String t;
        public String url;
        public String ptime;
        public String img;
        public String len;
        public String em;
    }
}
