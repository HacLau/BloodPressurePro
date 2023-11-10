package com.blood.pressure.pro.model

import androidx.databinding.BaseObservable

data class SetTime(
    var id: Int,
    var title: String,
    var year: String,
    var day: String,
    var hour: String,
    var minute: String,
    var cancel: String,
    var confirm: String
) : BaseObservable()
