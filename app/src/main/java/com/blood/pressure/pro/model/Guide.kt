package com.blood.pressure.pro.model

import androidx.annotation.DrawableRes
import androidx.databinding.BaseObservable

data class Guide(
    var id: Int,
    var app_name: String,
    var guide_step: List<Step>,
    var next: List<Next>,
    var skip: String,
    var start: String,
    var start_record: String,
    var privacy: String,
    var agreement: String,
    var check: String
) : BaseObservable()

data class Next(var title: String)
data class Step(
    var title: String,
    var content: String,
    @DrawableRes
    var image: Int
)
