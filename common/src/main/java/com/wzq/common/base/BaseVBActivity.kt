package com.wzq.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.wzq.common.utils.reflectInflate

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/21 17:47
 * Version: 1.0
 * Description: java类作用描述
 */
open class BaseVBActivity<VB : ViewBinding> : AppCompatActivity() {
    protected lateinit var viewBinding: VB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = reflectInflate(0,layoutInflater)
        setContentView(viewBinding.root)
    }

}