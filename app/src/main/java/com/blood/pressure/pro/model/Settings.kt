package com.blood.pressure.pro.model

import androidx.databinding.BaseObservable

data class Settings(
    var id: Int,
    var alarm: String,
    var language: String,
    var share: String,
    var privacy: String,
    var policy: String,
    var contact: String,
    var mail: String
) : BaseObservable()
