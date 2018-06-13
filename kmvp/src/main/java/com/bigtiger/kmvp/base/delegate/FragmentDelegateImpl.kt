package com.bigtiger.kmvp.base.delegate

import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.View
import com.bigtiger.kmvp.utils.ArmsUtils


class FragmentDelegateImpl(private var mFragmentManager: android.support.v4.app.FragmentManager?,
                           private var mFragment: android.support.v4.app.Fragment?) : FragmentDelegate {
    private var iFragment: IFragment = mFragment as IFragment

    /**
     * Return true if the fragment is currently added to its activity.
     */
    override val isAdded: Boolean
        get() = mFragment != null && mFragment!!.isAdded

    override fun onAttach(context: Context) {

    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        //iFragment.setupFragmentComponent(ArmsUtils.obtainAppComponentFromContext(mFragment!!.activity))
    }

    override fun onCreateView(@Nullable view: View?, @Nullable savedInstanceState: Bundle?) {
    }

    override fun onActivityCreate(@Nullable savedInstanceState: Bundle?) {
        iFragment.initData(savedInstanceState)
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

    override fun onDestroyView() {
    }

    override fun onDestroy() {
        this.mFragmentManager = null
        this.mFragment = null
    }

    override fun onDetach() {

    }
}
