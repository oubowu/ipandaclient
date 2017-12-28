package com.oubowu.ipanda.di.module;

import android.util.SparseArray;

import com.oubowu.ipanda.api.Hosts;

/**
 * Created by Oubowu on 2017/12/25 2:42.
 */
@Deprecated
//@Module
public class IpandaApiModule {

//    @Provides
    public SparseArray<String> provideDynamicHosts() {
        SparseArray<String> hosts = new SparseArray<>(Hosts.COUNT);
        hosts.put(Hosts.IPANDA_KEHUDUAN, "http://www.ipanda.com");
        hosts.put(Hosts.CNTV_APPS, "http://vdn.apps.cntv.cn");
        hosts.put(Hosts.CNTV_LIVE, "http://vdn.live.cntv.cn");
        return hosts;
    }

}
