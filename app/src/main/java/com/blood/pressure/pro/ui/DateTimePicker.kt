package com.blood.pressure.pro.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.text.TextPaint
import android.view.MotionEvent
import android.view.View
import com.blood.pressure.pro.R
import kotlin.math.abs

class DateTimePicker(
val mContext: Context,
val visibleNumberCount:Int = 3,
var onSelect: (String) -> Unit = { _ -> },
var onMove: (String) -> Unit = { _ -> }
) : View(mContext) {
    private var dataList: List<String> = arrayListOf()
    private var anInt = 0
    private var firstVisible = true
    private var width = 0
    private var height = 0
    private var index = 0
    private var downX = 0f
    private var anOffset = 0f
    private var rect: Rect? = Rect()
    private var textWidth = 0
    private var textHeight = 0
    private var centerTextHeight = 0

    private lateinit var textPaint: TextPaint
    private lateinit var selectedPaint: TextPaint
    init {
        initData()
    }

    private fun initData() {
        setWillNotDraw(false)
        this.isClickable = true
        initPaint()
    }


    private fun initPaint() {
        textPaint = TextPaint(1).apply {
            textSize = 50f
            color = mContext.resources.getColor(R.color.record_new_normal_number)
        }
        selectedPaint = TextPaint(1).apply {
            color = mContext.resources.getColor(R.color.record_new_select_number)
            textSize = 60f
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
            }

            MotionEvent.ACTION_UP -> {
                anOffset = 0.0f
                this.invalidate()
                onSelect.invoke(getSelectedString())
            }

            MotionEvent.ACTION_MOVE -> {
                val scrollX = event.x

                if (index != 0 && index != dataList.size - 1) {
                    anOffset = scrollX - downX
                }
                if (index == 0 || index == dataList.size - 1 || abs(anOffset) > abs(50 - dataList.size.div(6.0f))) {
                    if (scrollX > downX) {
                        if (index > 0) {
                            anOffset = 0.0f
                            --index
                            downX = scrollX
                        }
                    } else if (index < dataList.size - 1) {
                        anOffset = 0.0f
                        ++index
                        downX = scrollX
                    }
                }
                onMove.invoke(getSelectedString())
                this.invalidate()
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (firstVisible) {
            width = getWidth()
            height = getHeight()
            anInt = width / visibleNumberCount
            firstVisible = false
        }
        if (index >= 0 && index <= dataList.size - 1) {
            selectedPaint.getTextBounds(dataList[index], 0, dataList[index].length, rect)
            val centerTextWidth = rect!!.width()
            centerTextHeight = rect!!.height()
            canvas.drawText(
                dataList[index], (getWidth() / 2 - centerTextWidth / 2).toFloat() + anOffset, (getHeight() / 2 + centerTextHeight / 2).toFloat(),
                selectedPaint
            )
            for (i in dataList.indices) {
                if (index > 0 && index < dataList.size - 1) {
                    textPaint.getTextBounds(dataList[index - 1], 0, dataList[index - 1].length, rect)
                    val width1 = rect!!.width()
                    textPaint.getTextBounds(dataList[index + 1], 0, dataList[index + 1].length, rect)
                    val width2 = rect!!.width()
                    textWidth = (width1 + width2) / 2
                }
                if (i == 0) {
                    textPaint.getTextBounds(dataList[0], 0, dataList[0].length, rect)
                    textHeight = rect!!.height()
                }
                if (i != index) {
                    canvas.drawText(
                        dataList[i],
                        ((i - index) * anInt + getWidth() / 2 - textWidth / 2).toFloat() + anOffset,
                        (getHeight() / 2 + textHeight / 2).toFloat(),
                        textPaint
                    )
                }
            }
        }
    }

    fun setData(dataList: List<String>, index: Int = dataList.size / 2) {
        this.dataList = dataList
        this.index = index
        this.invalidate()
    }

    private fun getSelectedString(): String {
        return if (dataList.isNotEmpty()) dataList[index] else ""
    }

}