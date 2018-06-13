package com.bigtiger.kmvp.base.delegate

import android.os.Bundle


interface ActivityDelegate {

    fun onCreate(savedInstanceState: Bundle?)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onSaveInstanceState(outState: Bundle)

    fun onDestroy()

    companion object {
        val LAYOUT_LINEARLAYOUT = "LinearLayout"
        val LAYOUT_FRAMELAYOUT = "FrameLayout"
        val LAYOUT_RELATIVELAYOUT = "RelativeLayout"
        val ACTIVITY_DELEGATE = "ACTIVITY_DELEGATE"
    }
}
