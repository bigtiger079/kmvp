package com.bigtiger.kmvp.mvp

import android.app.Activity
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v4.app.SupportActivity
import android.arch.lifecycle.LifecycleObserver
import com.bigtiger.kmvp.utils.Preconditions


open class BasePresenter<M : IModel, V : IView> : IPresenter, LifecycleObserver {
    protected var mModel: M? = null
    protected var mRootView: V? = null

    /**
     * 如果当前页面同时需要 Model 层和 View 层,则使用此构造函数(默认)
     *
     * @param model
     * @param rootView
     */
    constructor(model: M, rootView: V) {

        Preconditions.checkNotNull(model, "%s cannot be null", model::class.simpleName!!)
        Preconditions.checkNotNull(rootView, "%s cannot be null", rootView::class.simpleName!!)
        this.mModel = model
        this.mRootView = rootView
        onStart()
    }

    /**
     * 如果当前页面不需要操作数据,只需要 View 层,则使用此构造函数
     *
     * @param rootView
     */
    constructor(rootView: V) {
        Preconditions.checkNotNull(rootView, "%s cannot be null", rootView::class.simpleName!!)
        this.mRootView = rootView
        onStart()
    }

    constructor() {
        onStart()
    }


    override fun onStart() {
        //将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
        if (mRootView != null && mRootView is LifecycleOwner) {
            (mRootView as LifecycleOwner).lifecycle.addObserver(this)
            if (mModel != null && mModel is LifecycleObserver) {
                (mRootView as LifecycleOwner).lifecycle.addObserver((mModel as LifecycleObserver?)!!)
            }
        }
    }

    /**
     * 在框架中 [Activity.onDestroy] 时会默认调用 [IPresenter.onDestroy]
     */
    override fun onDestroy() {
        if (mModel != null)
            mModel!!.onDestroy()
        this.mModel = null
        this.mRootView = null
    }

    /**
     * 只有当 `mRootView` 不为 null, 并且 `mRootView` 实现了 [LifecycleOwner] 时, 此方法才会被调用
     * 所以当您想在 [Service] 以及一些自定义 [View] 或自定义类中使用 `Presenter` 时
     * 您也将不能继续使用 [OnLifecycleEvent] 绑定生命周期
     *
     * @param owner link [SupportActivity] and [Fragment]
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    internal fun onDestroy(owner: LifecycleOwner) {
        /**
         * 注意, 如果在这里调用了 [.onDestroy] 方法, 会出现某些地方引用 `mModel` 或 `mRootView` 为 null 的情况
         * 比如在 [RxLifecycle] 终止 [Observable] 时, 在 [io.reactivex.Observable.doFinally] 中却引用了 `mRootView` 做一些释放资源的操作, 此时会空指针
         * 或者如果你声明了多个 @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY) 时在其他 @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
         * 中引用了 `mModel` 或 `mRootView` 也可能会出现此情况
         */
        owner.lifecycle.removeObserver(this)
    }
}
