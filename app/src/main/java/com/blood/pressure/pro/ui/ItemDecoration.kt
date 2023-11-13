package com.blood.pressure.pro.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blood.pressure.pro.util.dp2px

class ItemBottomDecoration(
    private val itemGap:Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        itemGap.dp2px().let {
            outRect.bottom = it
        }
    }
}
class ItemLRBottomDecoration(
    private val itemGap:Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        itemGap.dp2px().let {
            outRect.left = it
            outRect.right = it
            outRect.bottom = it
        }
    }
}