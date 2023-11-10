package com.blood.pressure.pro.util

import java.util.Calendar

fun Long.formatTimeMain(): String {
    return Calendar.getInstance().let {
        it.timeInMillis = this
        "${it.get(Calendar.YEAR)}.${(it.get(Calendar.MONTH) + 1)}.${it.get(Calendar.DATE)}"
    }
}

fun Long.formatTimeRecordItem(): String {
    return Calendar.getInstance().let {
        it.timeInMillis = this
        "${it.get(Calendar.YEAR)}-${(it.get(Calendar.MONTH) + 1)}-${it.get(Calendar.DATE)} ${it.get(Calendar.HOUR_OF_DAY)}:${it.get(Calendar.MINUTE)}"
    }
}