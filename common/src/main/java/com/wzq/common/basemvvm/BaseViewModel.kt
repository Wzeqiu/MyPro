package com.wzq.common.basemvvm

import android.content.Context
import androidx.lifecycle.ViewModel

/**
 *
 * Author: WZQ
 * CreateDate: 2021/11/4 11:15
 * Version: 1.0
 * Description: java类作用描述
 */
open class BaseViewModel : ViewModel() {
    lateinit var context: Context
}