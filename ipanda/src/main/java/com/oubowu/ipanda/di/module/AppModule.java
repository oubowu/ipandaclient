package com.oubowu.ipanda.di.module;

import android.app.Application;

import com.oubowu.ipanda.api.IpandaApi;
import com.oubowu.ipanda.api.service.CntvAppsService;
import com.oubowu.ipanda.api.service.CntvLiveService;
import com.oubowu.ipanda.api.service.IpandaService;
import com.oubowu.ipanda.db.IpandaDb;
import com.oubowu.ipanda.db.dao.TabIndexDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Oubowu on 2017/12/25 2:28.
 */
// 提供接口的单例；ViewModelModule提供ViewModelProvider.Factory的注入，用于生成ViewModel
@Module(includes = {ViewModelModule.class})
public class AppModule {

    @Singleton
    @Provides
    public IpandaDb provideIpandaDb(Application application) {
        return IpandaDb.buildDatabase(application);
    }

    @Singleton
    @Provides
    public TabIndexDao provideTabIndexDao(IpandaDb ipandaDb) {
        return ipandaDb.tabIndexDao();
    }

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
