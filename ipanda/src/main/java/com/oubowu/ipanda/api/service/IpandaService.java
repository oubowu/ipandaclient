package com.oubowu.ipanda.api.service;

import android.arch.lifecycle.LiveData;

import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.bean.TabIndex;
import com.oubowu.ipanda.bean.base.LiveVideo;
import com.oubowu.ipanda.bean.base.VideoList;
import com.oubowu.ipanda.bean.home.HomeIndex;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Oubowu on 2017/12/21 16:14.
 */
public interface IpandaService {


    /**
     * 获取底部标签
     * http://www.ipanda.com/kehuduan/dibubiaoqian/index.json
     *
     * @return 底部标签列表
     */
    @GET("kehuduan/dibubiaoqian/index.json")
    LiveData<ApiResponse<List<TabIndex>>> getTabIndex();

    /**
     * 首页-索引：(来自1的tab标签)
     * http://www.ipanda.com/kehuduan
     *
     * @param url 请求网址
     * @return 首页数据
     */
    @GET
    LiveData<ApiResponse<HomeIndex>> getHomeIndex(@Url String url);

    /**
     * 首页-精彩一刻(来自2的cctv的listurl字段)
     * http://www.ipanda.com/kehuduan/shipinliebieye/jingcaiyike/index.json
     *
     * @param url 请求网址
     * @return 精彩一刻视频列表
     */
    @GET
    LiveData<ApiResponse<List<VideoList>>> getWonderfulMomentIndex(@Url String url);

    /**
     * 首页-滚滚视频(来自2的list的listUrl字段)
     * http://www.ipanda.com/kehuduan/shipinliebieye/video/index.json
     *
     * @param url 请求网址
     * @return 精彩一刻视频列表
     */
    @GET
    LiveData<ApiResponse<List<VideoList>>> getGungunVideoIndex(@Url String url);










//    /**
//     * 获取底部标签
//     * http://www.ipanda.com/kehuduan/dibubiaoqian/index.json
//     *
//     * @return 底部标签列表
//     */
//    @GET("kehuduan/dibubiaoqian/index.json")
//    Observable<Map<String, List<TabIndex>>> getTabIndex();
//
//    /**
//     * 首页-索引：(来自1的tab标签)
//     * http://www.ipanda.com/kehuduan
//     *
//     * @param url 请求网址
//     * @return 首页数据
//     */
//    @GET
//    Observable<HomeIndex> getHomeIndex(@Url String url);
//
//    /**
//     * 首页-精彩一刻(来自2的cctv的listurl字段)
//     * http://www.ipanda.com/kehuduan/shipinliebieye/jingcaiyike/index.json
//     *
//     * @param url 请求网址
//     * @return 精彩一刻视频列表
//     */
//    @GET
//    Observable<Map<String, List<VideoList>>> getWonderfulMomentIndex(@Url String url);
//
//    /**
//     * 首页-滚滚视频(来自2的list的listUrl字段)
//     * http://www.ipanda.com/kehuduan/shipinliebieye/video/index.json
//     *
//     * @param url 请求网址
//     * @return 精彩一刻视频列表
//     */
//    @GET
//    Observable<Map<String, List<VideoList>>> getGungunVideoIndex(@Url String url);
//
//
//    /**
//     * 首页-大图-点击进去(pid来自2的bigImg的pid)
//     * http://vdn.apps.cntv.cn/api/getVideoInfoForCBox.do?pid=f5f4a7638b2446ff9753006d658bcaae
//     *
//     * @param pid 视频id
//     * @return 视频信息
//     */
//    @GET("api/getVideoInfoForCBox.do")
//    Observable<RecordVideo> getBannerDetail(@Query("pid") String pid);
//
//    /**
//     * 首页-熊猫播报-点击进去(pid来自2的pandaeye的items的pid)
//     * http://vdn.apps.cntv.cn/api/getVideoInfoForCBox.do?pid=29abf1c6e9624cb19568808256662b3b
//     *
//     * @param pid 视频id
//     * @return 视频信息
//     */
//    @GET("api/getVideoInfoForCBox.do")
//    Observable<RecordVideo> getPandaBroadcastDetail(@Query("pid") String pid);
//
//    /**
//     * 首页-直播秀场-点击进去(channel来自2的pandalive的list的id)(channel=pa://cctv_p2p_hd{id})
//     * http://vdn.live.cntv.cn/api2/live.do?channel=pa://cctv_p2p_hdxiongmao03&client=androidapp
//     *
//     * @param channel 频道
//     * @param client  客户端
//     * @return 直播信息
//     */
//    @GET("api2/live.do")
//    Observable<LiveVideo> getLiveShowDetail(@Query("channel") String channel, @Query("client") String client);
//
//    /**
//     * 首页-精彩一刻-点击进去(来自3的list的pid)
//     * http://vdn.apps.cntv.cn/api/getVideoInfoForCBox.do?pid=74c30918c1f34b59b3500affae080136
//     *
//     * @param pid 视频id
//     * @return 视频信息
//     */
//    @GET("api/getVideoInfoForCBox.do")
//    Observable<RecordVideo> getWonderfulMomentDetail(@Query("pid") String pid);
//
//    /**
//     * 首页-滚滚视频-点击进去(来自4的list的pid)
//     * http://vdn.apps.cntv.cn/api/getVideoInfoForCBox.do?pid=6116fbf58ac4484dbbef9d9925c864f1
//     *
//     * @param pid 视频id
//     * @return 视频信息
//     */
//    @GET("api/getVideoInfoForCBox.do")
//    Observable<RecordVideo> getGungunVideoDetail(@Query("pid") String pid);
//
//    /**
//     * 首页-直播中国-点击进去(来自2的chinalive的list的id)
//     * http://vdn.live.cntv.cn/api2/live.do?channel=pa://cctv_p2p_hdzjjmht&client=androidapp
//     *
//     * @param channel 频道
//     * @param client  客户端
//     * @return 直播信息
//     */
//    @GET("api2/live.do")
//    Observable<LiveVideo> getChinaLiveDetail(@Query("channel") String channel, @Query("client") String client);

}
