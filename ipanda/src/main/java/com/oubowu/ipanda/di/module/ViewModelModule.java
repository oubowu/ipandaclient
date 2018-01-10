package com.oubowu.ipanda.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.oubowu.ipanda.di.qualifier.ViewModelKey;
import com.oubowu.ipanda.viewmodel.HomeViewModel;
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

    // IpandaViewModelFactory作为注入，构造函数需要传入参数Map<Class<? extends ViewModel>, Provider<ViewModel>> creators；要实现这个可以写个provideCreators提供传入
    // 使用@Binds可以替代这个做法，不用再写provideCreators了
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

}
