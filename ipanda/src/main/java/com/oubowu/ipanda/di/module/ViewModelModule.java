package com.oubowu.ipanda.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.oubowu.ipanda.di.qualifier.ViewModelKey;
import com.oubowu.ipanda.viewmodel.ChinaLiveSubViewModel;
import com.oubowu.ipanda.viewmodel.ChinaLiveViewModel;
import com.oubowu.ipanda.viewmodel.HomeViewModel;
import com.oubowu.ipanda.viewmodel.HostViewModel;
import com.oubowu.ipanda.viewmodel.PandaBroadcastViewModel;
import com.oubowu.ipanda.viewmodel.PandaLiveOtherViewModel;
import com.oubowu.ipanda.viewmodel.PandaLiveSubViewModel;
import com.oubowu.ipanda.viewmodel.PandaLiveViewModel;
import com.oubowu.ipanda.viewmodel.PandaVideoListViewModel;
import com.oubowu.ipanda.viewmodel.PandaVideoViewModel;
import com.oubowu.ipanda.viewmodel.VideoViewModel;
import com.oubowu.ipanda.viewmodel.factory.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Oubowu on 2017/12/28 23:40.
 */
@Module
public abstract class ViewModelModule {

    // Binds：HomeViewModel构造函数需要传入参数HomeRepository
    // 提供Map<Class<? extends ViewModel>, Provider<ViewModel>> creators
    @Binds
    @IntoMap // 生成Map<Class<? extends ViewModel>,Provider<ViewModel>>
    @ViewModelKey(HomeViewModel.class) // key是HomeViewModel.class；value是HomeViewModel实例
    abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HostViewModel.class)
    abstract ViewModel bindHostViewModel(HostViewModel hostViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(VideoViewModel.class)
    abstract ViewModel bindVideoViewModel(VideoViewModel videoViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PandaLiveViewModel.class)
    abstract ViewModel bindPandaLiveViewModel(PandaLiveViewModel pandaLiveViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PandaLiveSubViewModel.class)
    abstract ViewModel bindPandaLiveSubViewModel(PandaLiveSubViewModel pandaLiveSubViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PandaLiveOtherViewModel.class)
    abstract ViewModel bindPandaLiveOtherViewModel(PandaLiveOtherViewModel pandaLiveOtherViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PandaVideoViewModel.class)
    abstract ViewModel bindPandaVideoViewModel(PandaVideoViewModel pandaVideoViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PandaBroadcastViewModel.class)
    abstract ViewModel bindPandaBroadcastViewModel(PandaBroadcastViewModel pandaBroadcastViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChinaLiveViewModel.class)
    abstract ViewModel bindChinaLiveViewModel(ChinaLiveViewModel chinaLiveViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChinaLiveSubViewModel.class)
    abstract ViewModel bindChinaLiveSubViewModel(ChinaLiveSubViewModel chinaLiveSubViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PandaVideoListViewModel.class)
    abstract ViewModel bindPandaVideoListViewModel(PandaVideoListViewModel pandaVideoListViewModel);

    // IpandaViewModelFactory作为注入，构造函数需要传入参数Map<Class<? extends ViewModel>, Provider<ViewModel>> creators；要实现这个可以写个provideCreators提供传入
    // 使用@Binds可以替代这个做法，不用再写provideCreators了
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

}
