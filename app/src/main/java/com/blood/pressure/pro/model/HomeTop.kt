package com.blood.pressure.pro.model

import androidx.databinding.BaseObservable

data class HomeTop(
    var id: Int,
    var title: String,
    var content: String,
    var to: String,
    var question: String,
) : BaseObservable()
