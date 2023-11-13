package com.blood.pressure.pro.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blood.pressure.pro.databinding.LayoutRecordItemBinding
import com.blood.pressure.pro.db.RecordManager
import com.blood.pressure.pro.model.RecordEntity
import com.blood.pressure.pro.util.formatTimeRecordItem
import com.blood.pressure.pro.util.getColorByLevel
import com.blood.pressure.pro.view.logE

class RecordAdapter(
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list: List<RecordEntity> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            "RecordAdapter value.size = ${value.size}".logE()
            notifyDataSetChanged()
        }
    var onItemClick: (RecordEntity) -> Unit = {}
    var onItemLongClick:(RecordEntity) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        RecordViewHolder(LayoutRecordItemBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        list[position]?.let { entity ->
            (holder as RecordViewHolder).binding.apply {
                record = entity
                itemEdit.setOnClickListener {
                    onItemClick.invoke(entity)
                }
                ColorStateList.valueOf(context.getColor(entity.level.getColorByLevel())).let {
                    itemTitle.setTextColor(it)
                }
                time = entity.time.formatTimeRecordItem()
                des = "Diastolic:${entity.diastolic} Systolic:${entity.systolic}"
                root.setOnLongClickListener {
                    onItemLongClick.invoke(entity)
                    false
                }
            }
        }
    }

    inner class RecordViewHolder(val binding: LayoutRecordItemBinding) : RecyclerView.ViewHolder(binding.root)
}