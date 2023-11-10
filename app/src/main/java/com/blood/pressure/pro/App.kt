package com.blood.pressure.pro

import android.app.Application

lateinit var application: Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
        registerActivityLifecycleCallbacks(AppLifecycle())
    }
}