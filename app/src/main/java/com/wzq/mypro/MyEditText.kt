package com.wzq.mypro

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.annotation.Nullable
import com.wzq.common.net.ex.request.request

/**
 *
 * Author: WZQ
 * CreateDate: 2021/11/3 12:06
 * Version: 1.0
 * Description: java类作用描述
 */
class MyEditText @JvmOverloads constructor(context: Context, @Nullable attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    androidx.appcompat.widget.AppCompatEditText(context, attrs, defStyleAttr) {
    init {
        request({ netServer.test() }) {

        }
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        return super.dispatchTouchEvent(event)
    }

}