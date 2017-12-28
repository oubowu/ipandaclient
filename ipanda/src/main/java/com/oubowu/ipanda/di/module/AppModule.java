package com.oubowu.ipanda.di.module;

import android.util.SparseArray;

import com.oubowu.ipanda.api.service.CntvAppsService;
import com.oubowu.ipanda.api.service.CntvLiveService;
import com.oubowu.ipanda.di.qualifier.Service;
import com.oubowu.ipanda.api.Hosts;
import com.oubowu.ipanda.api.IpandaApi;
import com.oubowu.ipanda.api.service.IpandaService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Oubowu on 2017/12/25 2:28.
 */
@Module
public class AppModule {

    //    private Context mContext;
    //
    //    public AppModule(Context context) {
    //        this.mContext = context;
    //    }
    //
    //    @Provides
    //    @Singleton
    //    public Context provideContext() {
    //        return mContext;
    //    }

    @Provides
    public SparseArray<String> provideDynamicHosts() {
        SparseArray<String> hosts = new SparseArray<>(Hosts.COUNT);
        hosts.put(Hosts.IPANDA_KEHUDUAN, "www.ipanda.com");
        hosts.put(Hosts.CNTV_APPS, "vdn.apps.cntv.cn");
        hosts.put(Hosts.CNTV_LIVE, "vdn.live.cntv.cn");
        return hosts;
    }

    @Singleton
    @Provides
    public IpandaApi provideIpandaApi(SparseArray<String> dynamicHosts) {
        return new IpandaApi(dynamicHosts);
    }

    @Singleton
    @Provides
    @Service("IPANDA_KEHUDUAN")
    public IpandaService provideClientService(IpandaApi ipandaApi) {
        return ipandaApi.getRetrofit(Hosts.IPANDA_KEHUDUAN).create(IpandaService.class);
    }

    @Singleton
    @Provides
    @Service("CNTV_APPS")
    public CntvAppsService provideRecordVideoService(IpandaApi ipandaApi) {
        return ipandaApi.getRetrofit(Hosts.CNTV_APPS).create(CntvAppsService.class);
    }

    @Singleton
    @Provides
    @Service("CNTV_LIVE")
    public CntvLiveService provideLiveVideoService(IpandaApi ipandaApi) {
        return ipandaApi.getRetrofit(Hosts.CNTV_LIVE).create(CntvLiveService.class);
    }

}
