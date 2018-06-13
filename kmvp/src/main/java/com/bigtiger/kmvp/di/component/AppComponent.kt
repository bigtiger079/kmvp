package com.bigtiger.kmvp.di.component

import com.bigtiger.kmvp.base.delegate.AppDelegate
import com.bigtiger.kmvp.integration.IRepositoryManager
import android.app.Application
import com.bigtiger.kmvp.integration.AppManager
import com.bigtiger.kmvp.integration.cache.Cache
import java.io.File


interface AppComponent {

    fun application(): Application

    //用于管理所有 activity
    fun appManager(): AppManager

    //用于管理网络请求层,以及数据缓存层
    fun repositoryManager(): IRepositoryManager

    //缓存文件根目录(RxCache 和 Glide 的缓存都已经作为子文件夹放在这个根目录下),应该将所有缓存都放到这个根目录下,便于管理和清理,可在 GlobalConfigModule 里配置
    fun cacheFile(): File

    //用来存取一些整个App公用的数据,切勿大量存放大容量数据
    fun extras(): Cache<String, Any>

    //用于创建框架所需缓存对象的工厂
    fun cacheFactory(): Cache.Factory

    fun inject(delegate: AppDelegate)
}