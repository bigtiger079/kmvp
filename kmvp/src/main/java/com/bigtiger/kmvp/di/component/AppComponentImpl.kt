package com.bigtiger.kmvp.di.component

import android.app.Application
import com.bigtiger.kmvp.base.delegate.AppDelegate
import com.bigtiger.kmvp.di.module.GlobalConfigModule
import com.bigtiger.kmvp.integration.AppManager
import com.bigtiger.kmvp.integration.IRepositoryManager
import com.bigtiger.kmvp.integration.cache.Cache
import com.bigtiger.kmvp.integration.cache.CacheType
import java.io.File

class AppComponentImpl constructor(private val application: Application,
                                           globalConfigModule: GlobalConfigModule): AppComponent {
    private val appManager = AppManager(application)
    private val factory: Cache.Factory = globalConfigModule.provideCacheFactory(application)
    private val cacheFile: File = globalConfigModule.provideCacheFile(application)
    private val extraCache: Cache<String, Any> = factory.build(CacheType.EXTRAS)
    private val repositoryManager: IRepositoryManager = globalConfigModule.provideRepositoryManager()

    override fun application(): Application {
        return application
    }

    override fun appManager(): AppManager {
        return appManager
    }

    override fun repositoryManager(): IRepositoryManager {
        return repositoryManager
    }

    override fun cacheFile(): File {
        return cacheFile
    }

    override fun extras(): Cache<String, Any> {
        return extraCache
    }

    override fun cacheFactory(): Cache.Factory {
        return factory
    }

    override fun inject(delegate: AppDelegate) {
    }
}