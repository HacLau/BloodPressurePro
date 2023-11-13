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
        "${it.get(Calendar.YEAR)}-${(it.get(Calendar.MONTH) + 1).format2()}-${it.get(Calendar.DATE).format2()} ${it.get(Calendar.HOUR_OF_DAY).format2()}:${it.get(Calendar.MINUTE).format2()}"
    }
}

fun Long.formatTimeChartItem(): String {
    return Calendar.getInstance().let {
        it.timeInMillis = this
        "${(it.get(Calendar.MONTH) + 1)}-${it.get(Calendar.DATE)}"
    }
}

fun Long.formatTimeStartEdit(): String {
    return Calendar.getInstance().let {
        it.timeInMillis = this
        "${it.get(Calendar.YEAR)} ${(it.get(Calendar.MONTH) + 1).format2()} ${it.get(Calendar.DATE).format2()}"
    }
}

fun Long.formatTimeEndEdit(): String {
    return Calendar.getInstance().let {
        it.timeInMillis = this
        "${it.get(Calendar.HOUR_OF_DAY).format2()} : ${it.get(Calendar.MINUTE).format2()}"
    }
}

fun Long.getCurrentDateArrayByMill(): IntArray {
    return Calendar.getInstance().let {
        it.timeInMillis = this
        intArrayOf(
            it.get(Calendar.YEAR),
            it.get(Calendar.MONTH) + 1,
            it.get(Calendar.DATE),
            it.get(Calendar.HOUR_OF_DAY),
            it.get(Calendar.MINUTE)
        )
    }
}
fun IntArray.getCurrentDateMillByArray(): Long {
    return Calendar.getInstance().let {
        it.set(Calendar.YEAR, this[0])
        it.set(Calendar.MONTH, this[1] - 1)
        it.set(Calendar.DATE, this[2])
        it.set(Calendar.HOUR_OF_DAY, this[3])
        it.set(Calendar.MINUTE, this[4])
        it.timeInMillis
    }
}

fun Long.getLastYearToDay():Long{
    return Calendar.getInstance().let {
        it.timeInMillis = this
        it.add(Calendar.YEAR,-1)
        it.timeInMillis
    }
}
