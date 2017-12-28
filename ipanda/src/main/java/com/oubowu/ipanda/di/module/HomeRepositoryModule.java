package com.oubowu.ipanda.di.module;

import com.oubowu.ipanda.api.Hosts;
import com.oubowu.ipanda.api.IpandaApi;
import com.oubowu.ipanda.api.service.IpandaService;

/**
 * Created by Oubowu on 2017/12/25 0:58.
 */
@Deprecated
//@Module
public class HomeRepositoryModule {

    /*@Provides
    public SparseArray<String> provideDynamicHosts() {
        SparseArray<String> hosts = new SparseArray<>(Hosts.COUNT);
        hosts.put(Hosts.IPANDA_KEHUDUAN, "http://www.ipanda.com");
        hosts.put(Hosts.CNTV_APPS, "http://vdn.apps.cntv.cn");
        hosts.put(Hosts.CNTV_LIVE, "http://vdn.live.cntv.cn");
        return hosts;
    }
*/
//    @Provides
    public IpandaService provideIpandaService(IpandaApi ipandaApi) {
        return ipandaApi.getRetrofit(Hosts.IPANDA_KEHUDUAN).create(IpandaService.class);
    }

}
