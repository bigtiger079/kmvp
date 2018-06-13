package com.bigtiger.kmvp.base.delegate

import android.os.Bundle
import android.app.Activity


class ActivityDelegateImpl(private var mActivity: Activity?) : ActivityDelegate {


    private var iActivity: IActivity? = null

    init {
        this.iActivity = mActivity as IActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //这里提供 AppComponent 对象给 BaseActivity 的子类, 用于 Dagger2 的依赖注入
//        iActivity!!.setupActivityComponent(ArmsUtils.obtainAppComponentFromContext(mActivity))
    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onSaveInstanceState(outState: Bundle) {

    }

    override fun onDestroy() {
        this.iActivity = null
        this.mActivity = null
    }
}
