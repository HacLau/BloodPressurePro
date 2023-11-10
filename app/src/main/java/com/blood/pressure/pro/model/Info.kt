package com.blood.pressure.pro.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.databinding.BaseObservable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Info(
    var id: Int,
    var title: String,
    var from: String,
    var content: String,
    @DrawableRes
    var image: Int
) : BaseObservable(),Parcelable
