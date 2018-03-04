package com.oubowu.ipanda.api.service;

import android.arch.lifecycle.LiveData;

import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.bean.base.VideoList;
import com.oubowu.ipanda.bean.home.HomeIndex;
import com.oubowu.ipanda.bean.pandabroadcast.PandaBroadcastIndex;
import com.oubowu.ipanda.bean.pandabroadcast.PandaBroadcastList;
import com.oubowu.ipanda.bean.pandalive.LiveTab;
import com.oubowu.ipanda.bean.pandalive.MultipleLive;
import com.oubowu.ipanda.bean.pandalive.RecordTab;
import com.oubowu.ipanda.bean.pandalive.TabList;
import com.oubowu.ipanda.bean.pandalive.WatchTalk;
import com.oubowu.ipanda.bean.TabIndex;
import com.oubowu.ipanda.bean.pandavideo.PandaVideoIndex;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Headers;
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
    @Headers("realHost:www.ipanda.com")
    @GET("kehuduan/dibubiaoqian/index.json")
    LiveData<ApiResponse<Map<String, List<TabIndex>>>> getTabIndex();

    /**
     * 首页-索引：(来自1的tab标签)
     * http://www.ipanda.com/kehuduan
     *
     * @param url 请求网址
     * @return 首页数据
     */
    @Headers("realHost:www.ipanda.com")
    @GET
    LiveData<ApiResponse<Map<String, HomeIndex>>> getHomeIndex(@Url String url);

    //    /**
    //     * 首页-精彩一刻(来自2的cctv的listurl字段)
    //     * http://www.ipanda.com/kehuduan/shipinliebieye/jingcaiyike/index.json
    //     *
    //     * @param url 请求网址
    //     * @return 精彩一刻视频列表
    //     */
    //    @Headers("realHost:www.ipanda.com")
    //    @GET
    //    LiveData<ApiResponse<Map<String,List<VideoList>>>> getWonderfulMomentIndex(@Url String url);
    //
    //    /**
    //     * 首页-滚滚视频(来自2的list的listUrl字段)
    //     * http://www.ipanda.com/kehuduan/shipinliebieye/video/index.json
    //     *
    //     * @param url 请求网址
    //     * @return 精彩一刻视频列表
    //     */
    //    @Headers("realHost:www.ipanda.com")
    //    @GET
    //    LiveData<ApiResponse<Map<String,List<VideoList>>>> getGungunVideoIndex(@Url String url);

    // 首页-精彩一刻(来自2的cctv的listurl字段) & 首页-滚滚视频(来自2的list的listUrl字段)
    @Headers("realHost:www.ipanda.com")
    @GET
    LiveData<ApiResponse<Map<String, List<VideoList>>>> getVideoListIndex(@Url String url);

    /**************************************************************************************************************/

    // 熊猫直播-顶部tab（url来自1的tab标签）
    @Headers("realHost:www.ipanda.com")
    @GET
    LiveData<ApiResponse<Map<String, List<TabList>>>> getTabList(@Url String url);

    // 熊猫直播-直播tab(来自tablist的url)
    @Headers("realHost:www.ipanda.com")
    @GET
    LiveData<ApiResponse<LiveTab>> getLiveTab(@Url String url);

    //  熊猫直播-直播tab-多视角直播（来自live的bookmark的multiple的url）
    @Headers("realHost:www.ipanda.com")
    @GET
    LiveData<ApiResponse<Map<String, List<MultipleLive>>>> getMultipleLive(@Url String url);

    // 熊猫直播-直播tab-边看边聊（itemid来自live的bookmark的multiple的watchTalk的url）
    @Headers("realHost:newcomment.cntv.cn")
    @GET("/comment/list")
    LiveData<ApiResponse<WatchTalk>> getLiveWatchTalk(@Query("prepage") int prepage, @Query("nature") int nature, @Query("app") String app, @Query("page") int page, @Query("itemid") String itemid);

    // 熊猫直播-除直播之外的其余tab（vsid来自tablist的id
    @Headers("realHost:api.cntv.cn")
    @GET("/video/videolistById")
    LiveData<ApiResponse<RecordTab>> getRecordTab(@Query("vsid") String vsid, @Query("n") int n, @Query("serviceId") String serviceId, @Query("o") String o, @Query("of") String of, @Query("p") int p);

    /**************************************************************************************************************/

    // 滚滚视频
    @Headers("realHost:www.ipanda.com")
    @GET
    LiveData<ApiResponse<PandaVideoIndex>> getPandaVideoIndex(@Url String url);

    /**************************************************************************************************************/

    // 熊猫播报-索引
    @Headers("realHost:www.ipanda.com")
    @GET
    LiveData<ApiResponse<Map<String,PandaBroadcastIndex>>> getPandaBroadcastIndex(@Url String url);

    // 熊猫播报-列表
    @Headers("realHost:api.cntv.cn")
    @GET
    LiveData<ApiResponse<PandaBroadcastList>> getPandaBroadcastList(@Url String url);

    /**************************************************************************************************************/

}
