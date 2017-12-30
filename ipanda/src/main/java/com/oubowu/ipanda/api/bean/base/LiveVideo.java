package com.oubowu.ipanda.api.bean.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Oubowu on 2017/12/21 11:25.
 */
public class LiveVideo {


    /**
     * ack : yes
     * lc : {"isp_code":"3","city_code":"","provice_code":"GD","country_code":"CN","ip":"221.176.33.249"}
     * client_sid : 238678f2-e5f8-11e7-aa4e-00505689d25d
     * flv_cdn_info : {"cdn_code":"LIVE-FLV-CDN-DN","cdn_name":"3rdFLV帝联"}
     * flv_url : {"flv1":"","flv2":"http://3811.liveplay.myqcloud.com/live/flv/3811_channel349.flv?AUTH=pkg3EwObu2lujzAjcXVIxkyHIw1g59wi1MHJVK7jOrv0acCGAYDgWtXqHswH7XAsSv6s3FtHOObRuIosrMH48w==","flv3":"qita?AUTH=pkg3EwObu2lujzAjcXVIxkyHIw1g59wi1MHJVK7jOrv0acCGAYDgWtXqHswH7XAsSv6s3FtHOObRuIosrMH48w==","flv4":"","flv5":"http://cctv.live.cntv.dnion.com/cache/14_/channel.pub?AUTH=pkg3EwObu2lujzAjcXVIxkyHIw1g59wi1MHJVK7jOrv0acCGAYDgWtXqHswH7XAsSv6s3FtHOObRuIosrMH48w==","flv6":""}
     * hls_cdn_info : {"cdn_code":"LIVE-HLS-CDN-ALI","cdn_name":"3rdHLS阿里云"}
     * hls_url : {"hls1":"http://3811.liveplay.myqcloud.com/live/m3u8/3811_channel349.m3u8?AUTH=pkg3EwObu2lujzAjcXVIxkyHIw1g59wi1MHJVK7jOrv0acCGAYDgWtXqHswH7XAsSv6s3FtHOObRuIosrMH48w==","hls2":"http://3811.liveplay.myqcloud.com/live/m3u8/3811_channel349.m3u8?AUTH=pkg3EwObu2lujzAjcXVIxkyHIw1g59wi1MHJVK7jOrv0acCGAYDgWtXqHswH7XAsSv6s3FtHOObRuIosrMH48w==","hls3":"qita?AUTH=pkg3EwObu2lujzAjcXVIxkyHIw1g59wi1MHJVK7jOrv0acCGAYDgWtXqHswH7XAsSv6s3FtHOObRuIosrMH48w==","hls4":"http://3811.liveplay.myqcloud.com/live/m3u8/3811_channel349.m3u8?AUTH=pkg3EwObu2lujzAjcXVIxkyHIw1g59wi1MHJVK7jOrv0acCGAYDgWtXqHswH7XAsSv6s3FtHOObRuIosrMH48w==","hls5":"","hls6":""}
     * hds_cdn_info : {"cdn_code":"LIVE-HDS-CDN-DN","cdn_name":"3rdHDS帝联"}
     * hds_url : {"hds1":"","hds2":"","hds3":"qita?AUTH=ip%3D221.176.33.249%7Est%3D1513823656%7Eexp%3D1513910056%7Eacl%3D%2F*%7Ehmac%3D87e658fbf04980bcb6c078fcf31fde55e6a69baedfe553d7e795673dda8c7315","hds4":"","hds5":"","hds6":""}
     * public : 1
     */

    public String ack;
    public LcBean lc;
    public String client_sid;
    public FlvCdnInfoBean flv_cdn_info;
    public FlvUrlBean flv_url;
    public HlsCdnInfoBean hls_cdn_info;
    public HlsUrlBean hls_url;
    public HdsCdnInfoBean hds_cdn_info;
    public HdsUrlBean hds_url;
    @SerializedName("public")
    public String publicX;

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

    public static class FlvCdnInfoBean {
        /**
         * cdn_code : LIVE-FLV-CDN-DN
         * cdn_name : 3rdFLV帝联
         */

        public String cdn_code;
        public String cdn_name;
    }

    public static class FlvUrlBean {
        /**
         * flv1 :
         * flv2 : http://3811.liveplay.myqcloud.com/live/flv/3811_channel349.flv?AUTH=pkg3EwObu2lujzAjcXVIxkyHIw1g59wi1MHJVK7jOrv0acCGAYDgWtXqHswH7XAsSv6s3FtHOObRuIosrMH48w==
         * flv3 : qita?AUTH=pkg3EwObu2lujzAjcXVIxkyHIw1g59wi1MHJVK7jOrv0acCGAYDgWtXqHswH7XAsSv6s3FtHOObRuIosrMH48w==
         * flv4 :
         * flv5 : http://cctv.live.cntv.dnion.com/cache/14_/channel.pub?AUTH=pkg3EwObu2lujzAjcXVIxkyHIw1g59wi1MHJVK7jOrv0acCGAYDgWtXqHswH7XAsSv6s3FtHOObRuIosrMH48w==
         * flv6 :
         */

        public String flv1;
        public String flv2;
        public String flv3;
        public String flv4;
        public String flv5;
        public String flv6;
    }

    public static class HlsCdnInfoBean {
        /**
         * cdn_code : LIVE-HLS-CDN-ALI
         * cdn_name : 3rdHLS阿里云
         */

        public String cdn_code;
        public String cdn_name;
    }

    public static class HlsUrlBean {
        /**
         * hls1 : http://3811.liveplay.myqcloud.com/live/m3u8/3811_channel349.m3u8?AUTH=pkg3EwObu2lujzAjcXVIxkyHIw1g59wi1MHJVK7jOrv0acCGAYDgWtXqHswH7XAsSv6s3FtHOObRuIosrMH48w==
         * hls2 : http://3811.liveplay.myqcloud.com/live/m3u8/3811_channel349.m3u8?AUTH=pkg3EwObu2lujzAjcXVIxkyHIw1g59wi1MHJVK7jOrv0acCGAYDgWtXqHswH7XAsSv6s3FtHOObRuIosrMH48w==
         * hls3 : qita?AUTH=pkg3EwObu2lujzAjcXVIxkyHIw1g59wi1MHJVK7jOrv0acCGAYDgWtXqHswH7XAsSv6s3FtHOObRuIosrMH48w==
         * hls4 : http://3811.liveplay.myqcloud.com/live/m3u8/3811_channel349.m3u8?AUTH=pkg3EwObu2lujzAjcXVIxkyHIw1g59wi1MHJVK7jOrv0acCGAYDgWtXqHswH7XAsSv6s3FtHOObRuIosrMH48w==
         * hls5 :
         * hls6 :
         */

        public String hls1;
        public String hls2;
        public String hls3;
        public String hls4;
        public String hls5;
        public String hls6;
    }

    public static class HdsCdnInfoBean {
        /**
         * cdn_code : LIVE-HDS-CDN-DN
         * cdn_name : 3rdHDS帝联
         */

        public String cdn_code;
        public String cdn_name;
    }

    public static class HdsUrlBean {
        /**
         * hds1 :
         * hds2 :
         * hds3 : qita?AUTH=ip%3D221.176.33.249%7Est%3D1513823656%7Eexp%3D1513910056%7Eacl%3D%2F*%7Ehmac%3D87e658fbf04980bcb6c078fcf31fde55e6a69baedfe553d7e795673dda8c7315
         * hds4 :
         * hds5 :
         * hds6 :
         */

        public String hds1;
        public String hds2;
        public String hds3;
        public String hds4;
        public String hds5;
        public String hds6;
    }
}
