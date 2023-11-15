package com.blood.pressure.pro

import android.app.Application
import com.blood.pressure.pro.clock.CloakHelper
import com.blood.pressure.pro.clock.PointHelper

lateinit var application: Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
        registerActivityLifecycleCallbacks(AppLifecycle())
//        PointHelper.updateCustom()
        CloakHelper.requestCloakConfig()
    }
}