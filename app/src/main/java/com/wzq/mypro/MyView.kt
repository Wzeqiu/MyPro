package com.wzq.mypro

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import com.wzq.common.net.ex.request.request

/**
 *
 * Author: WZQ
 * CreateDate: 2021/11/3 12:06
 * Version: 1.0
 * Description: java类作用描述
 */
class MyView @JvmOverloads constructor(context: Context, @Nullable attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {
    init {
        request({ netServer.test() }) {

        }
    }
}