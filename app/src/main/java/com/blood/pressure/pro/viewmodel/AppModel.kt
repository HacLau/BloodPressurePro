package com.blood.pressure.pro.viewmodel

import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModel
import com.blood.pressure.pro.R
import com.blood.pressure.pro.application
import com.blood.pressure.pro.databinding.LayoutHomeBinding
import com.blood.pressure.pro.databinding.LayoutInfoBinding
import com.blood.pressure.pro.databinding.LayoutRecordBinding
import com.blood.pressure.pro.databinding.LayoutSettingBinding
import com.blood.pressure.pro.db.RecordManager
import com.blood.pressure.pro.model.AppDataEntity
import com.blood.pressure.pro.model.RecordData
import com.blood.pressure.pro.model.RecordEntity
import com.blood.pressure.pro.view.MainActivity
import com.blood.pressure.pro.view.logE
import com.google.gson.Gson
import java.nio.charset.Charset

class AppModel : ViewModel() {
    fun getRecordLevel(entity: RecordEntity): Int {
        return if (entity.systolic > 180 || entity.diastolic > 120)
            5
        else if (entity.systolic >= 140 || entity.diastolic >= 90)
            4
        else if (entity.systolic >= 130 || entity.diastolic >= 80)
            3
        else if (entity.systolic >= 120 && entity.diastolic >= 60)
            2
        else if (entity.systolic >= 90 && entity.diastolic >= 60)
            1
        else if (entity.systolic < 90 || entity.diastolic < 60)
            0
        else 0
    }

    val appDataEntity: AppDataEntity by lazy {
        Gson().fromJson(Base64.decode(application.assets.open("data.txt").let {
            val buffer = ByteArray(it.available())
            it.read(buffer)
            it.close()
            String(buffer, Charset.defaultCharset())
        }, Base64.DEFAULT).decodeToString(), AppDataEntity::class.java)
    }

    val infoImageList: List<Int> by lazy {
        mutableListOf(
            R.mipmap.ic_info_item_image1,
            R.mipmap.ic_info_item_image2,
            R.mipmap.ic_info_item_image3,
            R.mipmap.ic_info_item_image4
        )
    }


    fun recordData(list: List<RecordEntity>): RecordData = list.run {
        if (isEmpty()) {
            RecordData("0", "0", "0", "0", "0", "0", "0")
        } else {
            var sys = 0
            var dia = 0
            forEach {
                sys += it.systolic
                dia += it.diastolic
            }
            sys /= size
            dia /= size
            var max: Int = this[0].systolic
            var min: Int = this[0].diastolic
            this.forEach {
                if (max - it.systolic < 0) {
                    max = it.systolic
                }
                if (it.diastolic - min < 0) {
                    min = it.diastolic
                }
            }
            max += 10
            min -= 7
            val dis = (max - min) / 4

            RecordData(
                sys.toString(),
                dia.toString(),
                max.toString(),
                (min + dis * 3).toString(),
                (min + dis * 2).toString(),
                (min + dis * 1).toString(),
                (min).toString()
            )
        }
    }


}