package com.wzq.common.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.wzq.common.utils.ActivityStackManager

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/21 17:47
 * Version: 1.0
 * Description: java类作用描述
 */
abstract class BaseVBActivity<VB : ViewBinding>(private val inflate: (inflate: LayoutInflater) -> VB) : AppCompatActivity() {
    protected lateinit var viewBinding: VB
    override fun onCreate(savedInstanceState: Bundle?) {
        ActivityStackManager.getInstance().onActivityCreated(this)
        super.onCreate(savedInstanceState)
        viewBinding = inflate(layoutInflater)
        setContentView(viewBinding.root)
    }

    override fun onDestroy() {
        ActivityStackManager.getInstance().onActivityDestroyed(this)
        super.onDestroy()
    }
}