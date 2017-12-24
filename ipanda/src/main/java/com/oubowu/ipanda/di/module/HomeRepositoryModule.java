package com.oubowu.ipanda.di.module;

import com.oubowu.ipanda.http.Hosts;
import com.oubowu.ipanda.http.IpandaApi;
import com.oubowu.ipanda.http.service.IpandaService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Oubowu on 2017/12/25 0:58.
 */
@Module
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
    @Provides
    public IpandaService provideIpandaService(IpandaApi ipandaApi) {
        return ipandaApi.getRetrofit(Hosts.IPANDA_KEHUDUAN).create(IpandaService.class);
    }

}
