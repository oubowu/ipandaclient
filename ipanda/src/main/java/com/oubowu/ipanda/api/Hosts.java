package com.oubowu.ipanda.api;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Oubowu on 2017/12/22 13:22.
 */
public class Hosts {

    /**
     * 多少种Host类型
     */
    public static final int COUNT = 3;

    /**
     * 网易新闻视频的host
     */
    @HostsChecker
    public static final int IPANDA_KEHUDUAN = 1;

    /**
     * 新浪图片的host
     */
    @HostsChecker
    public static final int CNTV_APPS = 2;

    /**
     * 天气查询的host
     */
    @HostsChecker
    public static final int CNTV_LIVE = 3;

    /**
     * 替代枚举的方案，使用IntDef保证类型安全
     */
    @IntDef({IPANDA_KEHUDUAN, CNTV_APPS, CNTV_LIVE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HostsChecker {

    }

}
