package com.wzq.common.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView


class TestView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            (measuredWidth + elevation).toInt(),
            (measuredHeight + elevation).toInt()
        )
    }

    override fun dispatchDraw(canvas: Canvas?) {


        super.dispatchDraw(canvas)

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        val mPaint1 = Paint()
        mPaint1.color = Color.RED
        // 画一个矩形
        // 画一个矩形
        canvas?.drawRect(
            elevation / 2,
            elevation / 2,
            measuredWidth - elevation / 2,
            measuredHeight - elevation / 2,
            mPaint1
        )

        if (elevation > 0) {
            val mPaint = Paint()
            mPaint.color = Color.RED
            // 设置画笔遮罩滤镜  ,传入度数和样式
            // 设置画笔遮罩滤镜  ,传入度数和样式
            mPaint.maskFilter = BlurMaskFilter(elevation / 3, BlurMaskFilter.Blur.OUTER)
            canvas?.drawRoundRect(
                elevation / 2, elevation / 2, measuredWidth - elevation / 2,
                measuredHeight - elevation / 2, 10f, 10f, mPaint
            )
        }

        super.onDraw(canvas)

    }


}