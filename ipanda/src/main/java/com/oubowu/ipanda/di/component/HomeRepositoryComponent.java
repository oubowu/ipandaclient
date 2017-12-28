package com.oubowu.ipanda.di.component;

/**
 * Created by Oubowu on 2017/12/21 16:50.
 */

import com.oubowu.ipanda.viewmodel.HomeViewModel;
import com.oubowu.ipanda1.ui.HomeActivity;

@Deprecated
//@PerActivity
//@Component(modules = {HomeRepositoryModule.class},
//        dependencies = {AppComponent.class})
public interface HomeRepositoryComponent {

    void inject(HomeViewModel homeViewModel);

    void inject(HomeActivity homeActivity);

}
