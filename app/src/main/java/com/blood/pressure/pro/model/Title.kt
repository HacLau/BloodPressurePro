package com.blood.pressure.pro.model

import androidx.databinding.BaseObservable

data class Title(
    var id: Int,
    var title_list: List<TitleList>,
    var more_record: String,
    var new_record: String,
    var edit_record: String
) : BaseObservable()

data class TitleList(
    var title: String,
    var bottom: String
)
