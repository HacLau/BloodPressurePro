package com.blood.pressure.pro

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.blood.pressure.pro.util.SharedHelper
import com.blood.pressure.pro.view.GuideActivity

class AppLifecycle: Application.ActivityLifecycleCallbacks {
    private var number = 0
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
        if (number == 0
            && System.currentTimeMillis() - SharedHelper.activityInBackTime > 5000
            && (activity is GuideActivity).not()
        ) {
            activity.startActivity(Intent(activity, GuideActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
        number++
    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {
        number--
        if (number == 0) {
            SharedHelper.activityInBackTime = System.currentTimeMillis()
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}