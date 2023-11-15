package com.blood.pressure.pro.view

import android.app.Activity
import android.content.res.ColorStateList
import android.os.Build
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import com.blood.pressure.pro.R
import com.blood.pressure.pro.databinding.ActivityRecordBinding
import com.blood.pressure.pro.db.RecordManager
import com.blood.pressure.pro.model.DialogQuestion
import com.blood.pressure.pro.model.RecordEntity
import com.blood.pressure.pro.ui.DateTimeDialog
import com.blood.pressure.pro.ui.NumberSelector
import com.blood.pressure.pro.ui.QuestionDialog
import com.blood.pressure.pro.util.dp2px
import com.blood.pressure.pro.util.formatTimeEndEdit
import com.blood.pressure.pro.util.formatTimeRecordItem
import com.blood.pressure.pro.util.formatTimeStartEdit
import com.blood.pressure.pro.util.getColorByLevel

class RecordActivity : BaseActivity<ActivityRecordBinding>(R.layout.activity_record) {
    private lateinit var pageType: String
    private lateinit var entity: RecordEntity
    private lateinit var sysPicker: NumberSelector
    private lateinit var diasPicker: NumberSelector
    override fun initView() {
        pageType = intent.getStringExtra(BaseName.type) ?: BaseName.new
        entity = intent.getParcelableExtra(BaseName.record) ?: RecordEntity()
        binding.back.setOnClickListener {
            finish()
        }
        binding.recordSave.setOnClickListener {
            if (entity.systolic > entity.diastolic) {
                RecordManager.queryByTime(entity.time).let {
                    if (it.isEmpty()) {
                        when(pageType){
                            BaseName.new -> RecordManager.insert(entity)
                            BaseName.edit -> RecordManager.update(entity)
                        }
                        setResult(Activity.RESULT_OK)
                        finish()
                    } else {
                        QuestionDialog(this, viewModel.appDataEntity.dialog, clickConfirm = {
                            RecordManager.update(entity.apply {
                                id = it[0].id
                            })
                            setResult(Activity.RESULT_OK)
                            finish()
                        }).show()
                    }
                }
            } else {
                viewModel.appDataEntity.record.record_toast.toast(this)
            }
        }
        initPicker()
    }

    override fun initData() {
        binding.entity = entity
        binding.record = viewModel.appDataEntity.record
        binding.titleText =
            when (pageType) {
                BaseName.edit -> viewModel.appDataEntity.title.editRecord
                else -> viewModel.appDataEntity.title.newRecord
            }

        binding.sysNumberPicker.addView(sysPicker)
        binding.diasNumberPicker.addView(diasPicker)
        binding.timeEdit.setOnClickListener {
            DateTimeDialog(this, entity.time, viewModel.appDataEntity.set_time, confirm = {
                entity.time = it
                binding.timeStart = entity.time.formatTimeStartEdit()
                binding.timeEnd = entity.time.formatTimeEndEdit()
            }).show()
        }
        setRecordLevel()
    }

    private fun setRecordLevel() {
        entity.level = viewModel.getRecordLevel(entity)
        entity.title = viewModel.appDataEntity.record_level[entity.level].title
        binding.timeStart = entity.time.formatTimeStartEdit()
        binding.timeEnd = entity.time.formatTimeEndEdit()
        binding.level = viewModel.appDataEntity.record_level[entity.level]
        binding.levelScale.translationX = (53.2f * entity.level).dp2px()
        ColorStateList.valueOf(getColor(entity.level.getColorByLevel())).let {
            binding.levelTitle.setTextColor(it)
            binding.levelContent.setTextColor(it)
        }
        sysPicker.value = entity.systolic
        diasPicker.value = entity.diastolic
    }


    private fun initPicker() {
        sysPicker = NumberSelector(this).apply {
            setOnValueChangedListener { _, _, value ->
                entity.systolic = value
                setRecordLevel()
            }
        }
        diasPicker = NumberSelector(this).apply {
            setOnValueChangedListener { _, _, value ->
                entity.diastolic = value
                setRecordLevel()
            }
        }
    }

}