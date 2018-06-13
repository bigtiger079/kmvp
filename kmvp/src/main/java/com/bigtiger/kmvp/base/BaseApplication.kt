package com.bigtiger.kmvp.base

import com.bigtiger.kmvp.base.delegate.AppDelegate
import com.bigtiger.kmvp.di.component.AppComponent

import com.bigtiger.kmvp.utils.ArmsUtils
import com.bigtiger.kmvp.base.delegate.AppLifecycles
import android.app.Application
import android.content.Context
import com.bigtiger.kmvp.utils.Preconditions


class BaseApplication : Application(), App {
    private var mAppDelegate: AppLifecycles? = null

    /**
     * 这里会在 [BaseApplication.onCreate] 之前被调用,可以做一些较早的初始化
     * 常用于 MultiDex 以及插件化框架的初始化
     *
     * @param base
     */
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        if (mAppDelegate == null)
            this.mAppDelegate = AppDelegate(base)
        this.mAppDelegate?.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        this.mAppDelegate?.onCreate(this)

    }

    /**
     * 在模拟环境中程序终止时会被调用
     */
    override fun onTerminate() {
        super.onTerminate()
        this.mAppDelegate?.onTerminate(this)

    }

    /**
     * 将 [AppComponent] 返回出去, 供其它地方使用, [AppComponent] 接口中声明的方法所返回的实例, 在 [.getAppComponent] 拿到对象后都可以直接使用
     *
     * @see ArmsUtils.obtainAppComponentFromContext
     * @return AppComponent
     */
    override fun getAppComponent(): AppComponent {
        Preconditions.checkNotNull(mAppDelegate, "%s cannot be null", AppDelegate::class.java.name)
        Preconditions.checkState(mAppDelegate is App, "%s must be implements %s", AppDelegate::class.java.name, App::class.java.name)
        return (mAppDelegate as App).getAppComponent()
    }

}
