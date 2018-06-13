package com.bigtiger.kmvp.base

import android.support.v7.app.AppCompatActivity
import com.bigtiger.kmvp.base.delegate.IActivity
import com.bigtiger.kmvp.mvp.IPresenter
import android.os.Bundle
import com.bigtiger.kmvp.integration.cache.Cache
import com.bigtiger.kmvp.integration.cache.CacheType
import com.bigtiger.kmvp.utils.ArmsUtils


abstract class BaseActivity<P: IPresenter>: AppCompatActivity(), IActivity {
    protected val TAG = this.javaClass.simpleName
    private var mCache: Cache<String, Any>? = null
    protected var mPresenter: P? = null//如果当前页面逻辑简单, Presenter 可以为 null

    @Synchronized
    override fun provideCache(): Cache<String, Any> {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(this).cacheFactory().build(CacheType.ACTIVITY_CACHE)
        }
        return mCache!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val layoutResID = initView(savedInstanceState)
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResID != 0) {
                setContentView(layoutResID)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        initData(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroy()//释放资源
        this.mPresenter = null
    }

    /**
     * 这个Activity是否会使用Fragment,框架会根据这个属性判断是否注册[android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks]
     * 如果返回false,那意味着这个Activity不需要绑定Fragment,那你再在这个Activity中绑定继承于 [com.jess.arms.base.BaseFragment] 的Fragment将不起任何作用
     *
     * @return
     */
    override fun useFragment(): Boolean {
        return true
    }
}