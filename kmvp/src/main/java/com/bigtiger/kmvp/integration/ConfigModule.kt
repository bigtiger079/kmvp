package com.bigtiger.kmvp.integration

import android.app.Application
import android.content.Context
import android.support.v4.app.FragmentManager
import com.bigtiger.kmvp.base.delegate.AppLifecycles
import com.bigtiger.kmvp.di.module.GlobalConfigModule


interface ConfigModule {
    /**
     * 使用[GlobalConfigModule.Builder]给框架配置一些配置参数
     *
     * @param context
     * @param builder
     */
    fun applyOptions(context: Context, builder: GlobalConfigModule.Builder)

    /**
     * 使用[AppLifecycles]在Application的生命周期中注入一些操作
     *
     * @param context
     * @param lifecycles
     */
    fun injectAppLifecycle(context: Context, lifecycles: List<AppLifecycles>)

    /**
     * 使用[Application.ActivityLifecycleCallbacks]在Activity的生命周期中注入一些操作
     *
     * @param context
     * @param lifecycles
     */
    fun injectActivityLifecycle(context: Context, lifecycles: List<Application.ActivityLifecycleCallbacks>)


    /**
     * 使用[FragmentManager.FragmentLifecycleCallbacks]在Fragment的生命周期中注入一些操作
     *
     * @param context
     * @param lifecycles
     */
    fun injectFragmentLifecycle(context: Context, lifecycles: List<FragmentManager.FragmentLifecycleCallbacks>)
}
