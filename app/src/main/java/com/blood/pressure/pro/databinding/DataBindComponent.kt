package com.blood.pressure.pro.databinding

import android.widget.TextView
import androidx.databinding.BindingAdapter

object DataBindComponent {
    @BindingAdapter("customParam")
    @JvmStatic
    fun TextView.setText(value:String){
        text = value
    }
}