package com.wzq.common.utils

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.wzq.common.R

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 16:20
 * Version: 1.0
 * Description: java类作用描述
 */

fun Activity.setTitleBarTitle(title: String): TextView? {
    return findViewById<TextView>(R.id.title_bar_tv_title)?.apply {
        text = title
    }
}

fun Activity.setTitleBarBackOnClickListener(onClick: View.OnClickListener): ImageView? {
    return findViewById<ImageView>(R.id.title_bar_iv_back)?.apply {
        setOnClickListener(onClick)
    }
}