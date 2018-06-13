package com.bigtiger.kmvp.di.module

import com.bigtiger.kmvp.integration.cache.IntelligentCache
import com.bigtiger.kmvp.integration.cache.CacheType

import android.app.Application
import com.bigtiger.kmvp.integration.IRepositoryManager
import com.bigtiger.kmvp.integration.cache.Cache
import com.bigtiger.kmvp.integration.cache.LruCache
import com.bigtiger.kmvp.utils.DataHelper
import java.io.File


class GlobalConfigModule(builder: Builder) {
    private val mCacheFile: File?
    private val mCacheFactory: Cache.Factory?
    private val mRepositoryManager: IRepositoryManager

    init {
        this.mCacheFile = builder.cacheFile
        this.mCacheFactory = builder.cacheFactory
        this.mRepositoryManager = builder.repositoryManager
    }

    fun provideCacheFile(application: Application): File {
        return mCacheFile?: DataHelper.getCacheFile(application)
    }

    fun provideRepositoryManager(): IRepositoryManager {
        return mRepositoryManager
    }

    fun provideCacheFactory(application: Application): Cache.Factory {
        return mCacheFactory ?: object : Cache.Factory{
            override fun <K,V>build(type: CacheType): Cache<K,V> {
                //若想自定义 LruCache 的 size, 或者不想使用 LruCache, 想使用自己自定义的策略
                //使用 GlobalConfigModule.Builder#cacheFactory() 即可扩展
                when (type.getCacheTypeId()) {
                //Activity、Fragment 以及 Extras 使用 IntelligentCache (具有 LruCache 和 可永久存储数据的 Map)
                    CacheType.EXTRAS_TYPE_ID, CacheType.ACTIVITY_CACHE_TYPE_ID, CacheType.FRAGMENT_CACHE_TYPE_ID -> {
                        return IntelligentCache<V>(type.calculateCacheSize(application)) as Cache<K,V>
                    }
                //其余使用 LruCache (当达到最大容量时可根据 LRU 算法抛弃不合规数据)
                    else -> return LruCache(type.calculateCacheSize(application))
                }
            }
        }
    }



    class Builder{
        var cacheFile: File? = null
        var cacheFactory: Cache.Factory? = null
        lateinit var repositoryManager: IRepositoryManager

        fun cacheFile(cacheFile: File): Builder {
            this.cacheFile = cacheFile
            return this
        }

        fun cacheFactory(cacheFactory: Cache.Factory): Builder {
            this.cacheFactory = cacheFactory
            return this
        }

        fun repositoryManager(repositoryManager: IRepositoryManager): Builder {
            this.repositoryManager = repositoryManager
            return this
        }

        fun build(): GlobalConfigModule {
            return GlobalConfigModule(this)
        }
    }

}