package com.blood.pressure.pro.model

import androidx.databinding.BaseObservable

data class AppDataEntity(
    var guide: Guide,
    var title: Title,
    var setting: Settings,
    var record: Record,
    var home_top: HomeTop,
    var set_time: SetTime,
    var dialog:DialogQuestion,
    var record_level: List<Level>,
    var info: List<Info>
):BaseObservable()
