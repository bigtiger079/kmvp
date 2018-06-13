package com.bigtiger.kmvp.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.bigtiger.kmvp.integration.cache.CacheType
import com.bigtiger.kmvp.utils.ArmsUtils
import com.bigtiger.kmvp.base.delegate.IFragment
import com.bigtiger.kmvp.integration.cache.Cache
import com.bigtiger.kmvp.mvp.IPresenter


abstract class BaseFragment<P : IPresenter> : Fragment(), IFragment {
    protected val TAG = this.javaClass.simpleName
    private var mCache: Cache<String, Any>? = null
    protected var mPresenter: P? = null//如果当前页面逻辑简单, Presenter 可以为 null

    @Synchronized
    override fun provideCache(): Cache<String, Any> {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(activity!!).cacheFactory().build(CacheType.FRAGMENT_CACHE)
        }
        return mCache!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return initView(inflater, container, savedInstanceState)
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroy()//释放资源
        this.mPresenter = null
    }

}
