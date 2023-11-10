package com.blood.pressure.pro.util

import com.blood.pressure.pro.application

fun Int.dp2px():Int = (application.resources.displayMetrics.density * this).toInt()
