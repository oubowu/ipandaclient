package com.oubowu.daggerpractice.learn.component;

import com.oubowu.daggerpractice.learn.ClothHandler;
import com.oubowu.daggerpractice.learn.module.BaseModule;
import com.oubowu.daggerpractice.learn.module.Main2Module;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Oubowu on 2017/11/28 11:00.
 */
@Singleton
@Component(modules = BaseModule.class)
public interface BaseComponent {

    // 这个Component怎么有点不一样,怎么没有inject方法呢?上面讲过,我们通过inject方法依赖需求方实例送到Component中,从而帮助依赖需求方实现依赖,
    // 但是我们这个BaseComponent是给其他Component提供依赖的,所以我们就可以不用inject方法,但是BaseComponent中多了一个getClothHandler方法,它的返回值是ClothHandler对象,
    // 这个方法有什么用呢?它的作用就是告诉依赖于BaseComponent的Component,BaseComponent能为你们提供ClothHandler对象,如果没有这个方法,
    // BaseComponent就不能提供ClothHandler对象(这个提供规则和上面的依赖规则相同,可以实现单例).既然有了BaseComponent,那我们就可在其它Component中依赖它了.
    ClothHandler getClothHandler();


    //@Subcomponent使用的声明方式,声明一个返回值为子组件的方法,子组件需要什么Module,就在方法参数中添加什么
    SubMain2Component getSubMainComponent(Main2Module module);

}
