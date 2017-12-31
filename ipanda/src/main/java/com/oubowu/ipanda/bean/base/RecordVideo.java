package com.oubowu.ipanda.bean.base;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Oubowu on 2017/12/21 11:19.
 */
public class RecordVideo {


    /**
     * play_channel : 直播中国
     * f_pgmtime : 2017-12-20 14:13:12
     * tag : 精彩一刻大熊猫熊猫基地国宝动物
     * cdn_info : {"cdn_vip":"cntv.vod.cdn.myqcloud.com","cdn_code":"VOD-MP4-CDN-QQ","cdn_name":"3rd腾讯云"}
     * editer_name : zhoulin
     * version : 0.2
     * is_fn_hot : true
     * title : 《精彩一刻》 20171220 小木马，驾驾驾
     * is_protected : 0
     * hls_url : http://cntv.hls.cdn.myqcloud.com/asp/hls/main/0303000a/3/default/f5f4a7638b2446ff9753006d658bcaae/main.m3u8?maxbr=4096
     * hls_cdn_info : {"cdn_vip":"cntv.hls.cdn.myqcloud.com","cdn_code":"VOD-HLS-CDN-QQ","cdn_name":"3rd腾讯云"}
     * client_sid : c3579a18a45e4022bc5c8c0a1f1995f0
     * is_ipad_support : true
     * video : {"totalLength":"47.00","chapters":[{"image":"http://p5.img.cctvpic.com/fmspic/2017/12/20/f5f4a7638b2446ff9753006d658bcaae-33.jpg","url":"http://cntv.vod.cdn.myqcloud.com/flash/mp4video62/TMS/2017/12/20/f5f4a7638b2446ff9753006d658bcaae_h264418000nero_aac32.mp4","duration":"47"}],"validChapterNum":5,"chapters4":[{"image":"http://p5.img.cctvpic.com/fmspic/2017/12/20/f5f4a7638b2446ff9753006d658bcaae-33.jpg","url":"http://cntv.vod.cdn.myqcloud.com/flash/mp4video62/TMS/2017/12/20/f5f4a7638b2446ff9753006d658bcaae_h2642000000nero_aac16.mp4","duration":"47"}],"lowChapters":[{"image":"http://p5.img.cctvpic.com/fmspic/2017/12/20/f5f4a7638b2446ff9753006d658bcaae-33.jpg","url":"http://cntv.vod.cdn.myqcloud.com/flash/mp4video62/TMS/2017/12/20/f5f4a7638b2446ff9753006d658bcaae_h264200000nero_aac16.mp4","duration":"47"}],"chapters3":[{"image":"http://p5.img.cctvpic.com/fmspic/2017/12/20/f5f4a7638b2446ff9753006d658bcaae-33.jpg","url":"http://cntv.vod.cdn.myqcloud.com/flash/mp4video62/TMS/2017/12/20/f5f4a7638b2446ff9753006d658bcaae_h2641200000nero_aac16.mp4","duration":"47"}],"chapters2":[{"image":"http://p5.img.cctvpic.com/fmspic/2017/12/20/f5f4a7638b2446ff9753006d658bcaae-33.jpg","url":"http://cntv.vod.cdn.myqcloud.com/flash/mp4video62/TMS/2017/12/20/f5f4a7638b2446ff9753006d658bcaae_h264818000nero_aac32.mp4","duration":"47"}],"url":""}
     * is_invalid_copyright : 0
     * produce_id : wxsb01
     * default_stream : HD
     * ack : yes
     * is_fn_multi_stream : false
     * embed :
     * asp_error_code : 0
     * column : 熊猫频道精彩一刻高清
     * lc : {"isp_code":"3","city_code":"","provice_code":"GD","country_code":"CN","ip":"221.176.33.249"}
     * public : 1
     * is_p2p_use : false
     * produce :
     */

    public String play_channel;
    public String f_pgmtime;
    public String tag;
    public CdnInfoBean cdn_info;
    public String editer_name;
    public String version;
    public String is_fn_hot;
    public String title;
    public String is_protected;
    public String hls_url;
    public HlsCdnInfoBean hls_cdn_info;
    public String client_sid;
    public String is_ipad_support;
    public VideoBean video;
    public String is_invalid_copyright;
    public String produce_id;
    public String default_stream;
    public String ack;
    public boolean is_fn_multi_stream;
    public String embed;
    public int asp_error_code;
    public String column;
    public LcBean lc;
    @SerializedName("public")
    public String publicX;
    public boolean is_p2p_use;
    public String produce;

    public static class CdnInfoBean {
        /**
         * cdn_vip : cntv.vod.cdn.myqcloud.com
         * cdn_code : VOD-MP4-CDN-QQ
         * cdn_name : 3rd腾讯云
         */

        public String cdn_vip;
        public String cdn_code;
        public String cdn_name;
    }

    public static class HlsCdnInfoBean {
        /**
         * cdn_vip : cntv.hls.cdn.myqcloud.com
         * cdn_code : VOD-HLS-CDN-QQ
         * cdn_name : 3rd腾讯云
         */

        public String cdn_vip;
        public String cdn_code;
        public String cdn_name;
    }

    public static class VideoBean {
        /**
         * totalLength : 47.00
         * chapters : [{"image":"http://p5.img.cctvpic.com/fmspic/2017/12/20/f5f4a7638b2446ff9753006d658bcaae-33.jpg","url":"http://cntv.vod.cdn.myqcloud.com/flash/mp4video62/TMS/2017/12/20/f5f4a7638b2446ff9753006d658bcaae_h264418000nero_aac32.mp4","duration":"47"}]
         * validChapterNum : 5
         * chapters4 : [{"image":"http://p5.img.cctvpic.com/fmspic/2017/12/20/f5f4a7638b2446ff9753006d658bcaae-33.jpg","url":"http://cntv.vod.cdn.myqcloud.com/flash/mp4video62/TMS/2017/12/20/f5f4a7638b2446ff9753006d658bcaae_h2642000000nero_aac16.mp4","duration":"47"}]
         * lowChapters : [{"image":"http://p5.img.cctvpic.com/fmspic/2017/12/20/f5f4a7638b2446ff9753006d658bcaae-33.jpg","url":"http://cntv.vod.cdn.myqcloud.com/flash/mp4video62/TMS/2017/12/20/f5f4a7638b2446ff9753006d658bcaae_h264200000nero_aac16.mp4","duration":"47"}]
         * chapters3 : [{"image":"http://p5.img.cctvpic.com/fmspic/2017/12/20/f5f4a7638b2446ff9753006d658bcaae-33.jpg","url":"http://cntv.vod.cdn.myqcloud.com/flash/mp4video62/TMS/2017/12/20/f5f4a7638b2446ff9753006d658bcaae_h2641200000nero_aac16.mp4","duration":"47"}]
         * chapters2 : [{"image":"http://p5.img.cctvpic.com/fmspic/2017/12/20/f5f4a7638b2446ff9753006d658bcaae-33.jpg","url":"http://cntv.vod.cdn.myqcloud.com/flash/mp4video62/TMS/2017/12/20/f5f4a7638b2446ff9753006d658bcaae_h264818000nero_aac32.mp4","duration":"47"}]
         * url :
         */

        public String totalLength;
        public int validChapterNum;
        public String url;
        public List<ChaptersBean> chapters;
        public List<Chapters4Bean> chapters4;
        public List<LowChaptersBean> lowChapters;
        public List<Chapters3Bean> chapters3;
        public List<Chapters2Bean> chapters2;

        public static class ChaptersBean {
            /**
             * image : http://p5.img.cctvpic.com/fmspic/2017/12/20/f5f4a7638b2446ff9753006d658bcaae-33.jpg
             * url : http://cntv.vod.cdn.myqcloud.com/flash/mp4video62/TMS/2017/12/20/f5f4a7638b2446ff9753006d658bcaae_h264418000nero_aac32.mp4
             * duration : 47
             */

            public String image;
            public String url;
            public String duration;
        }

        public static class Chapters4Bean {
            /**
             * image : http://p5.img.cctvpic.com/fmspic/2017/12/20/f5f4a7638b2446ff9753006d658bcaae-33.jpg
             * url : http://cntv.vod.cdn.myqcloud.com/flash/mp4video62/TMS/2017/12/20/f5f4a7638b2446ff9753006d658bcaae_h2642000000nero_aac16.mp4
             * duration : 47
             */

            public String image;
            public String url;
            public String duration;
        }

        public static class LowChaptersBean {
            /**
             * image : http://p5.img.cctvpic.com/fmspic/2017/12/20/f5f4a7638b2446ff9753006d658bcaae-33.jpg
             * url : http://cntv.vod.cdn.myqcloud.com/flash/mp4video62/TMS/2017/12/20/f5f4a7638b2446ff9753006d658bcaae_h264200000nero_aac16.mp4
             * duration : 47
             */

            public String image;
            public String url;
            public String duration;
        }

        public static class Chapters3Bean {
            /**
             * image : http://p5.img.cctvpic.com/fmspic/2017/12/20/f5f4a7638b2446ff9753006d658bcaae-33.jpg
             * url : http://cntv.vod.cdn.myqcloud.com/flash/mp4video62/TMS/2017/12/20/f5f4a7638b2446ff9753006d658bcaae_h2641200000nero_aac16.mp4
             * duration : 47
             */

            public String image;
            public String url;
            public String duration;
        }

        public static class Chapters2Bean {
            /**
             * image : http://p5.img.cctvpic.com/fmspic/2017/12/20/f5f4a7638b2446ff9753006d658bcaae-33.jpg
             * url : http://cntv.vod.cdn.myqcloud.com/flash/mp4video62/TMS/2017/12/20/f5f4a7638b2446ff9753006d658bcaae_h264818000nero_aac32.mp4
             * duration : 47
             */

            public String image;
            public String url;
            public String duration;
        }
    }

    public static class LcBean {
        /**
         * isp_code : 3
         * city_code :
         * provice_code : GD
         * country_code : CN
         * ip : 221.176.33.249
         */

        public String isp_code;
        public String city_code;
        public String provice_code;
        public String country_code;
        public String ip;
    }

    @Override
    public String toString() {
        return "RecordVideo{" + "play_channel='" + play_channel + '\'' + ", f_pgmtime='" + f_pgmtime + '\'' + ", tag='" + tag + '\'' + ", cdn_info=" + cdn_info + ", editer_name='" + editer_name + '\'' + ", version='" + version + '\'' + ", is_fn_hot='" + is_fn_hot + '\'' + ", title='" + title + '\'' + ", is_protected='" + is_protected + '\'' + ", hls_url='" + hls_url + '\'' + ", hls_cdn_info=" + hls_cdn_info + ", client_sid='" + client_sid + '\'' + ", is_ipad_support='" + is_ipad_support + '\'' + ", video=" + video + ", is_invalid_copyright='" + is_invalid_copyright + '\'' + ", produce_id='" + produce_id + '\'' + ", default_stream='" + default_stream + '\'' + ", ack='" + ack + '\'' + ", is_fn_multi_stream=" + is_fn_multi_stream + ", embed='" + embed + '\'' + ", asp_error_code=" + asp_error_code + ", column='" + column + '\'' + ", lc=" + lc + ", publicX='" + publicX + '\'' + ", is_p2p_use=" + is_p2p_use + ", produce='" + produce + '\'' + '}';
    }
}
