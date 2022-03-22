package com.wzq.common.utils

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.wzq.common.R


/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 9:35
 * Version: 1.0
 * Description: java类作用描述
 */

/**
 * 点击间隔
 */
fun View.setSingleOnClickListener(intervalTime: Long = 500, onClick: (view: View) -> Unit): View {
    this.setOnClickListener {
        val time = getTag(R.id.interval_time_tag_id)?.toString()?.toLong() ?: 0
        val systemTime = System.currentTimeMillis()
        if ((time + intervalTime) <= systemTime) {
            setTag(R.id.interval_time_tag_id, systemTime)
            onClick(it)
        }
    }
    return this
}

/**
 * View 点击效果
 */
@SuppressLint("ClickableViewAccessibility")
fun View.onClickUp(): View {
    this.setOnTouchListener(View.OnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                this.animate().scaleX(1.1f).scaleY(1.1f).setDuration(100).start()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                this.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100).start()
            }
        }
        return@OnTouchListener true
    })
    return this
}




