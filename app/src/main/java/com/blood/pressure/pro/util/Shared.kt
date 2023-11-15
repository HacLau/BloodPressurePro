package com.blood.pressure.pro.util

import android.content.Context
import com.blood.pressure.pro.application
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


object SharedHelper {
    var launchedStep:Boolean by SharedUtil(application,"launchedStep",false)
    var launchedStart:Boolean by SharedUtil(application,"launchedStart",false)
    var activityInBackTime: Long by SharedUtil(application,"activityInBackTime", 0L)
    var androidId: String by SharedUtil(application,"androidId", "")
    var logId: String by SharedUtil(application,"logId", "")
    var cloakState: String by SharedUtil(application,"cloakState", "")
}

class SharedUtil<T>(
    private val context: Context,
    private val name: String,
    private val defValue: T,
) : ReadWriteProperty<Any?, T> {

    private val prefs by lazy {
        context.getSharedPreferences("BloodPressurePro", Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T =
        findPreference(findProperName(property))

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =
        putPreference(findProperName(property), value)

    private fun findProperName(property: KProperty<*>) = name.ifEmpty { property.name }

    private fun findPreference(key: String): T = when (defValue) {
        is Int -> prefs.getInt(key, defValue)
        is Long -> prefs.getLong(key, defValue)
        is Float -> prefs.getFloat(key, defValue)
        is Boolean -> prefs.getBoolean(key, defValue)
        is String -> prefs.getString(key, defValue)
        else -> throw IllegalArgumentException("Unsupported type.")
    } as T

    private fun putPreference(key: String, value: T) {
        val edit = prefs.edit().apply {
            when (value) {
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
                else -> throw IllegalArgumentException("Unsupported type.")
            }
        }
        edit.apply()
    }

}