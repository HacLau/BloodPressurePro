package com.blood.pressure.pro.model

import androidx.databinding.BaseObservable

data class Title(
    var id: Int,
    var title_list: List<TitleList>,
    var moreRecord: String,
    var newRecord: String,
    var editRecord: String
) : BaseObservable()

data class TitleList(
    var title: String,
    var bottom: String
)
