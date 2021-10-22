package com.wzq.common.utils

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



