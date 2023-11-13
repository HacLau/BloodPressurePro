package com.blood.pressure.pro.util

import com.blood.pressure.pro.R
import com.blood.pressure.pro.application

fun Int.dp2px(): Int = (application.resources.displayMetrics.density * this).toInt()
fun Float.dp2px(): Float = application.resources.displayMetrics.density * this
fun Int.format2(): String = if (this < 10) "0${this}" else "$this"

fun Int.getColorByLevel(): Int {
    return when (this) {
        0 -> R.color.record_level_0
        1 -> R.color.record_level_1
        2 -> R.color.record_level_2
        3 -> R.color.record_level_3
        4 -> R.color.record_level_4
        5 -> R.color.record_level_5
        else -> R.color.record_level_0
    }
}