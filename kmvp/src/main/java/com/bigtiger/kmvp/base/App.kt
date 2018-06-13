package com.bigtiger.kmvp.base

import com.bigtiger.kmvp.di.component.AppComponent


interface App {
    fun getAppComponent(): AppComponent
}