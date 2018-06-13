package com.bigtiger.kmvp.mvp

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.LifecycleObserver
import com.bigtiger.kmvp.integration.IRepositoryManager


open class BaseModel(protected var mRepositoryManager: IRepositoryManager?//用于管理网络请求层, 以及数据缓存层
) : IModel, LifecycleObserver {

    /**
     * 在框架中 [BasePresenter.onDestroy] 时会默认调用 [IModel.onDestroy]
     */
    override fun onDestroy() {
        mRepositoryManager = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    internal fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }
}