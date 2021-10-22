package com.wzq.common.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 9:53
 * Version: 1.0
 * Description: java类作用描述
 */

val Number.dp2px: Float
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )
    }

val Number.sp2px: Float
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )
    }