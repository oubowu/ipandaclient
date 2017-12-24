package com.oubowu.ipanda.di.module;

import android.util.SparseArray;

import com.oubowu.ipanda.http.Hosts;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Oubowu on 2017/12/22 09:33.
 */
@Module
public class IpandaApiModule {

    @Provides
    public SparseArray<String> provideDynamicHosts() {
        SparseArray<String> hosts = new SparseArray<>(Hosts.COUNT);
        hosts.put(Hosts.IPANDA_KEHUDUAN, "http://www.ipanda.com");
        hosts.put(Hosts.CNTV_APPS,"http://vdn.apps.cntv.cn");
        hosts.put(Hosts.CNTV_LIVE,"http://vdn.live.cntv.cn");
        return hosts;
    }

}
