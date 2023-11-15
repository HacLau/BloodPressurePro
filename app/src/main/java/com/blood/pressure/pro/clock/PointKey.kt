package com.blood.pressure.pro.clock

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import com.blood.pressure.pro.BuildConfig
import com.blood.pressure.pro.application
import com.blood.pressure.pro.util.IpHelper
import com.blood.pressure.pro.util.SharedHelper
import org.json.JSONObject
import java.util.Locale
import java.util.UUID

object PointKey {
    val distinct_id = "reflect"
    val client_ts = "daemon"
    val device_model = "exercise"
    val bundle_id = "glissade"
    val os_version = "reap"
    //    val idfv = "seaman"
//    val gaid = "autonomy"
    val os = "gawk"
    val android_id = "leather"
    //    val idfa = "gluten"
    val app_version = "feet"
    //    val key = "dugout"
    val network_type = "theresa"
    val ip = "triplett"

    val minutiae = "minutiae"
    val hotelman = "hotelman"

    val operator = "firm"
    val battery_left = "tel"
    val ab_test = "breakup"
    val cpu_arch = "policy"
    val brand = "pancho"
    val log_id = "biplane"
    val channel = "smythe"
    val zone_offset = "vacuo"
    val system_language = "camellia"
    val manufacturer = "shoemake"
    val sdk_ver = "anent"
    val os_country = "retina"

    val gregory = "gregory"
    val customName = "spent$"
}
object PointHelper{
    fun updateCustom(){
        Thread {
            HttpHelper.sendRequestPost(
                BuildConfig.updateUrl,
                jsonObject = getRequestJson {
                    mutableMapOf<String, Any>().apply {
                        put(PointKey.gregory, getCustomEvent())
                        put("spent\$CustomIp",(IpHelper.getIpAddress()?.encode() ?: ""))
                    }
                }, resultSuccess = {
                }, resultFailed = { code, message ->
                }
            )
        }.start()
    }

    private fun getRequestJson(function: () -> MutableMap<String, Any>): JSONObject {
        return JSONObject().apply {
            put(PointKey.operator,(application.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).simOperator.encode())
            put(PointKey.client_ts,System.currentTimeMillis().toString().encode())
            put(PointKey.os,"kiwanis".encode())
            put(PointKey.bundle_id,BuildConfig.APPLICATION_ID.encode())
            put(PointKey.app_version,BuildConfig.VERSION_NAME.encode())
            put(PointKey.distinct_id,getAndroidId().encode())
            put(PointKey.android_id,getAndroidId().encode())
            put(PointKey.device_model,Build.MODEL.encode())
            put(PointKey.log_id, getLogId().encode())
            put(PointKey.ip,(IpHelper.getIpAddress()?.encode() ?: ""))
            put(PointKey.system_language, Locale.getDefault().language.encode())
            put(PointKey.manufacturer,Build.MANUFACTURER.encode())
            function.invoke().forEach { (key, value) ->
                put(key, value)
            }
        }
    }

    fun getConfig():String{
        return StringBuilder().apply {
            mutableMapOf(
                PointKey.distinct_id to getAndroidId().encode(),
                PointKey.client_ts to System.currentTimeMillis().toString().encode(),
                PointKey.device_model to Build.MODEL.encode(),
                PointKey.bundle_id to BuildConfig.APPLICATION_ID.encode(),
                PointKey.os_version to Build.VERSION.RELEASE.encode(),
                PointKey.os to "kiwanis".encode(),
                PointKey.android_id to getAndroidId().encode(),
                PointKey.app_version to BuildConfig.VERSION_NAME.encode()
            ).forEach{
                this.append("&${it.key}=${it.value}")
            }
        }.toString().substring(1)
    }

    private fun getCustomEvent(): String = "CustomIp"


    @SuppressLint("HardwareIds")
    private fun getAndroidId(): String {
        if (SharedHelper.androidId.isBlank()) {
            SharedHelper.androidId = Settings.Secure.getString(application.contentResolver, Settings.Secure.ANDROID_ID)
            if(SharedHelper.androidId.isBlank()){
                SharedHelper.androidId = UUID.randomUUID().toString()
            }
        }
        return SharedHelper.androidId
    }

    private fun getLogId():String{
        if (SharedHelper.logId.isBlank()){
            SharedHelper.logId = UUID.randomUUID().toString()
        }
        return SharedHelper.logId
    }
}