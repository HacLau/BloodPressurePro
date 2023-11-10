package com.blood.pressure.pro.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import kotlinx.parcelize.Parcelize

data class Record(
    var id: Int,
    var diastolic: String,
    var systolic: String,
    var add_now: String,
    var new_time: String,
    var no: String,
    var now: String,
    var unit2: String,
    var sys: String,
    var dia: String,
    var unit: String,
    var add_new: String,
    var more: String,
    var filter: List<Filter>,
) : BaseObservable()

@Parcelize
data class RecordEntity(
    var id: Int,
    var level: Int = 0,
    var title: String = "",
    var systolic: Int = 90,
    var diastolic: Int = 60,
    var time: Long = System.currentTimeMillis()
):Parcelable