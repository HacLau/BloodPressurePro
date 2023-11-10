package com.blood.pressure.pro.model

import androidx.databinding.BaseObservable

data class Level(
    var id: Int,
    var title: String,
    var content: String,
    var des: String
) : BaseObservable()
