package com.blood.pressure.pro.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blood.pressure.pro.databinding.LayoutRecordItemBinding
import com.blood.pressure.pro.model.RecordEntity
import com.blood.pressure.pro.util.formatTimeRecordItem

class RecordAdapter(
    private val context: Context,
    private val list: List<RecordEntity>,
    private val onItemClick: (RecordEntity) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
                time = entity.time.formatTimeRecordItem()
                des = "Diastolic:${entity.diastolic} Systolic:${entity.systolic}"

            }
        }
    }

    inner class RecordViewHolder(val binding: LayoutRecordItemBinding) : RecyclerView.ViewHolder(binding.root)
}