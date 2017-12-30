package com.oubowu.ipanda.api.service;

import android.arch.lifecycle.LiveData;

import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.api.bean.TabIndex;
import com.oubowu.ipanda.api.bean.base.VideoList;
import com.oubowu.ipanda.api.bean.home.HomeIndex;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
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
    LiveData<ApiResponse<List<TabIndex>>> getTabIndex();

    /**
     * 首页-索引：(来自1的tab标签)
     * http://www.ipanda.com/kehuduan
     *
     * @param url 请求网址
     * @return 首页数据
     */
    @Headers("realHost:www.ipanda.com")
    @GET
    LiveData<ApiResponse<HomeIndex>> getHomeIndex(@Url String url);

    /**
     * 首页-精彩一刻(来自2的cctv的listurl字段)
     * http://www.ipanda.com/kehuduan/shipinliebieye/jingcaiyike/index.json
     *
     * @param url 请求网址
     * @return 精彩一刻视频列表
     */
    @Headers("realHost:www.ipanda.com")
    @GET
    LiveData<ApiResponse<List<VideoList>>> getWonderfulMomentIndex(@Url String url);

    /**
     * 首页-滚滚视频(来自2的list的listUrl字段)
     * http://www.ipanda.com/kehuduan/shipinliebieye/video/index.json
     *
     * @param url 请求网址
     * @return 精彩一刻视频列表
     */
    @Headers("realHost:www.ipanda.com")
    @GET
    LiveData<ApiResponse<List<VideoList>>> getGungunVideoIndex(@Url String url);

}
