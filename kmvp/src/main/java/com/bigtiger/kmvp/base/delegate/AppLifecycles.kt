package com.bigtiger.kmvp.base.delegate

import android.app.Application
import android.content.Context


interface AppLifecycles {
    fun attachBaseContext(base: Context)

    fun onCreate(application: Application)

    fun onTerminate(application: Application)
}
