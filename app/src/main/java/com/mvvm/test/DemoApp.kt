package com.mvvm.test

import android.app.Application
import com.mvvm.test.utils.PreferenceUtils

class DemoApp: Application() {

    override fun onCreate() {
        super.onCreate()
        PreferenceUtils.init(this)
    }
}