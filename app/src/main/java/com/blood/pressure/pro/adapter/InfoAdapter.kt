package com.blood.pressure.pro.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blood.pressure.pro.databinding.LayoutInfoItemBinding
import com.blood.pressure.pro.model.Info

class InfoAdapter(
    private val context: Context,
    private val list: List<Info>,
    private val onItemClick: (Info) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        InfoViewHolder(LayoutInfoItemBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        list[position]?.let { entity ->
            (holder as InfoViewHolder).binding.apply {
                info = entity
                itemInfo.setOnClickListener {
                    onItemClick.invoke(entity)
                }
                itemImage.setImageResource(entity.image)
            }
        }
    }

    inner class InfoViewHolder(val binding: LayoutInfoItemBinding) : RecyclerView.ViewHolder(binding.root)
}