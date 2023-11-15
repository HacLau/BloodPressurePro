package com.blood.pressure.pro.clock

import com.blood.pressure.pro.BuildConfig
import com.blood.pressure.pro.util.SharedHelper
import java.net.URLEncoder

object CloakHelper {

    var loadCount = 0
    fun requestCloakConfig(){
        HttpHelper.sendRequestGet(BuildConfig.clockUrl, PointHelper.getConfig(),
            resultSuccess = {
                loadCount = 0
                SharedHelper.cloakState = it
        }, resultFailed = {_,_->
                if (++loadCount < 20){
                    requestCloakConfig()
                }
        })
    }
}
fun String.encode():String{
    return URLEncoder.encode(this, "UTF-8")
}
