package com.blood.pressure.pro.ui

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import com.blood.pressure.pro.databinding.LayoutSetTimeBinding
import com.blood.pressure.pro.model.SetTime
import com.blood.pressure.pro.util.format2
import com.blood.pressure.pro.util.getCurrentDateArrayByMill
import com.blood.pressure.pro.util.getCurrentDateMillByArray
import com.blood.pressure.pro.util.getLastYearToDay
import com.blood.pressure.pro.view.toast
import java.util.Calendar

class DateTimeDialog(
    private val mContext: Context,
    private val timeMills: Long,
    private val setTime: SetTime,
    private var cancel: () -> Unit = {},
    private var confirm: (Long) -> Unit = {}
) : AlertDialog(mContext) {
    private lateinit var binding: LayoutSetTimeBinding
    private lateinit var dateArray: IntArray
    private lateinit var pkDay: DateTimePicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutSetTimeBinding.inflate(LayoutInflater.from(mContext))
        binding.setTime = setTime
        initPK()
        setContentView(binding.root)
        this.window?.setGravity(Gravity.CENTER)
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.window?.setDimAmount(0.5f)
        initView()
        initData()
    }

    private fun initPK() {
        dateArray = timeMills.getCurrentDateArrayByMill()
        pkDay = DateTimePicker(context, onSelect = {
            dateArray[2] = it.toInt()
        })
        binding.groupYear.addView(DateTimePicker(context, visibleNumberCount = 5, onSelect = {
            dateArray[0] = it.toInt()
            setDay(getDayPickerDataList(dateArray[0], dateArray[1]))
        }).apply {
            setData(yearList, yearList.indexOf(dateArray[0].format2()))
        })
        binding.groupMonth.addView(DateTimePicker(context, onSelect = {
            dateArray[1] = it.toInt()
            setDay(getDayPickerDataList(dateArray[0], dateArray[1]))
        }).apply {
            setData(monthList, monthList.indexOf(dateArray[1].format2()))
        })
        binding.groupDay.addView(pkDay)
        binding.groupHour.addView(DateTimePicker(context, onSelect = {
            dateArray[3] = it.toInt()
        }).apply { setData(hourList, hourList.indexOf(dateArray[3].format2())) })
        binding.groupMinute.addView(DateTimePicker(context, visibleNumberCount = 5, onSelect = {
            dateArray[4] = it.toInt()
        }).apply { setData(minuteList, minuteList.indexOf(dateArray[4].format2())) })
    }

    private fun initData() {
        setDay(getDayPickerDataList(dateArray[0], dateArray[1]))
    }

    private fun initView() {
        binding.dialogCancel.setOnClickListener {
            dismiss()
            cancel.invoke()
        }
        binding.dialogConfirm.setOnClickListener {
            dateArray.getCurrentDateMillByArray().let {
                if (it > System.currentTimeMillis()) {
                    setTime.overTime.toast(context)
                    return@setOnClickListener
                } else if (it < System.currentTimeMillis().getLastYearToDay()) {
                    setTime.overYear.toast(context)
                    return@setOnClickListener
                } else {
                    dismiss()
                    confirm.invoke(it)
                }
            }
        }
    }

    private fun setDay(list: MutableList<String>) {
        pkDay.setData(
            list, if (list.size < dateArray[2]) {
                dateArray[2] = list[list.size - 1].toInt()
                list.size - 1
            } else list.indexOf(dateArray[2].format2())
        )
    }
}

private fun getDayPickerDataList(year: Int, month: Int): MutableList<String> {
    return mutableListOf<String>().apply {
        (1..getCurrentYearMonthHaveDay(year, month)).forEach {
            add(it.format2())
        }
    }
}

fun getCurrentYearMonthHaveDay(year: Int, month: Int): Int {
    return Calendar.getInstance().apply {
        set(year, month, 1)
        add(Calendar.DATE, -1)
    }.get(Calendar.DATE)
}

val yearList: MutableList<String> = mutableListOf<String>().apply {
    (1970..2099).forEach {
        add(it.format2())
    }
}
val monthList: MutableList<String> = mutableListOf<String>().apply {
    (1..12).forEach {
        add(it.format2())
    }
}
val hourList: MutableList<String> = mutableListOf<String>().apply {
    (0..23).forEach {
        add(it.format2())
    }
}
val minuteList: MutableList<String> = mutableListOf<String>().apply {
    (0..59).forEach {
        add(it.format2())
    }
}