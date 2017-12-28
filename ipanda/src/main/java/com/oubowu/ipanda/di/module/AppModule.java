package com.oubowu.ipanda.di.module;

import com.oubowu.ipanda.api.IpandaApi;
import com.oubowu.ipanda.api.service.CntvAppsService;
import com.oubowu.ipanda.api.service.CntvLiveService;
import com.oubowu.ipanda.api.service.IpandaService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Oubowu on 2017/12/25 2:28.
 */
// 提供接口的单例；ViewModelModule提供ViewModelProvider.Factory的注入，用于生成ViewModel
@Module(includes = {ViewModelModule.class})
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

    //    @Singleton
    //    @Provides
    //    public IpandaApi provideIpandaApi() {
    //        return new IpandaApi();
    //    }

    @Singleton
    @Provides
    public IpandaService provideClientService(IpandaApi ipandaApi) {
        return ipandaApi.getRetrofit().create(IpandaService.class);
    }

    @Singleton
    @Provides
    public CntvAppsService provideRecordVideoService(IpandaApi ipandaApi) {
        return ipandaApi.getRetrofit().create(CntvAppsService.class);
    }

    @Singleton
    @Provides
    public CntvLiveService provideLiveVideoService(IpandaApi ipandaApi) {
        return ipandaApi.getRetrofit().create(CntvLiveService.class);
    }

}
