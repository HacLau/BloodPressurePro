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
import com.blood.pressure.pro.model.RecordEntity
import com.blood.pressure.pro.view.MainActivity
import com.google.gson.Gson
import java.nio.charset.Charset

class AppModel : ViewModel() {
    val appDataEntity: AppDataEntity by lazy {
        Gson().fromJson(Base64.decode(application.assets.open("data.txt").let {
            val buffer = ByteArray(it.available())
            it.read(buffer)
            it.close()
            String(buffer, Charset.defaultCharset())
        }, Base64.DEFAULT).decodeToString(), AppDataEntity::class.java)
    }

    val infoImageList:List<Int> by lazy {
        mutableListOf(
            R.mipmap.ic_info_item_image1,
            R.mipmap.ic_info_item_image2,
            R.mipmap.ic_info_item_image3,
            R.mipmap.ic_info_item_image4
        )
    }

    val recordList:List<RecordEntity> by lazy {
        RecordManager.query()
    }

}