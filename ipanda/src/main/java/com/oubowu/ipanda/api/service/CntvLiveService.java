package com.oubowu.ipanda.api.service;

import android.arch.lifecycle.LiveData;

import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.bean.base.LiveVideo;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Oubowu on 2017/12/28 03:13.
 */
public interface CntvLiveService {

    @Headers("realHost:vdn.live.cntv.cn")
    @GET("api2/live.do")
    LiveData<ApiResponse<LiveVideo>> getLiveVideoDetail(@Query("channel") String channel, @Query("client") String client);

//    /**
//     * 首页-直播秀场-点击进去(channel来自2的pandalive的list的id)(channel=pa://cctv_p2p_hd{id})
//     * http://vdn.live.cntv.cn/api2/live.do?channel=pa://cctv_p2p_hdxiongmao03&client=androidapp
//     *
//     * @param channel 频道
//     * @param client  客户端
//     * @return 直播信息
//     */
//    @Headers("realHost:vdn.live.cntv.cn")
//    @GET("api2/live.do")
//    LiveData<ApiResponse<LiveVideo>> getLiveShowDetail(@Query("channel") String channel, @Query("client") String client);
//
//    /**
//     * 首页-直播中国-点击进去(来自2的chinalive的list的id)
//     * http://vdn.live.cntv.cn/api2/live.do?channel=pa://cctv_p2p_hdzjjmht&client=androidapp
//     *
//     * @param channel 频道
//     * @param client  客户端
//     * @return 直播信息
//     */
//    @HEAD("realHost:vdn.live.cntv.cn")
//    @GET("api2/live.do")
//    LiveData<ApiResponse<LiveVideo>> getChinaLiveDetail(@Query("channel") String channel, @Query("client") String client);

}
