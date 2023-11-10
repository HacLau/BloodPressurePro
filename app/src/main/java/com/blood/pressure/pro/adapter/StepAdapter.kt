package com.blood.pressure.pro.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.blood.pressure.pro.databinding.LayoutGuideStepBinding
import com.blood.pressure.pro.model.Step

class StepAdapter(
    private val context: Context,
    private val list: List<Step>):PagerAdapter() {
    override fun getCount(): Int = list.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = LayoutGuideStepBinding.inflate(LayoutInflater.from(context))
        binding.step = list[position]
        binding.image.setImageResource(list[position].image)
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}