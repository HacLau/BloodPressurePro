package com.blood.pressure.pro.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blood.pressure.pro.R
import com.blood.pressure.pro.databinding.LayoutChartItemBinding
import com.blood.pressure.pro.model.Level
import com.blood.pressure.pro.model.RecordEntity
import com.blood.pressure.pro.util.dp2px
import com.blood.pressure.pro.util.formatTimeChartItem
import com.blood.pressure.pro.util.getColorByLevel
import com.blood.pressure.pro.view.logE

class ChartAdapter(
    private val context: Context,
    private val max: Int = 0,
    private val min: Int = 0,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var viewHeight = 142
    var list: List<RecordEntity> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            "ChartAdapter value.size = ${value.size}".logE()
            notifyDataSetChanged()
        }
    lateinit var onItemClick: () -> Unit
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ChartViewHolder(LayoutChartItemBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        list[position]?.let { it ->
            (holder as ChartViewHolder).binding.apply {
                time.text = it.time.formatTimeChartItem()
                diaSys.text = "${it.diastolic}-${it.systolic}"

                val h = (it.systolic - it.diastolic) / (max - min).toFloat()
                val translationY = (((max + min) / 2.0f - (it.systolic + it.diastolic) / 2.0f) / (max - min).toFloat())
                level.layoutParams.height = if (it.systolic <= it.diastolic) {
                    1
                } else (viewHeight * h).toInt().dp2px()
                level.translationY = (translationY * viewHeight).dp2px()
                diaSys.translationY = (translationY * viewHeight).dp2px()

                time.translationY = (0.6f * viewHeight).dp2px()

                ColorStateList.valueOf(context.getColor(it.level.getColorByLevel())).let {
                    level.imageTintList = it
                    diaSys.setTextColor(it)
                }
                level.setOnClickListener {
                    onItemClick.invoke()
                }
            }
        }
    }

    inner class ChartViewHolder(val binding: LayoutChartItemBinding) : RecyclerView.ViewHolder(binding.root)
}
