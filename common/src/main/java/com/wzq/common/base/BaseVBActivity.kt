package com.wzq.common.base

import android.os.Bundle
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
abstract class BaseVBActivity<VB : ViewBinding> : AppCompatActivity(), InitListener<VB> {
    private lateinit var _viewBinding: VB
    protected val viewBinding get() = _viewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        ActivityStackManager.getInstance().onActivityCreated(this)
        super.onCreate(savedInstanceState)
        _viewBinding = createViewBinding()
        initData()
        setContentView(viewBinding.root)
    }

    override fun onDestroy() {
        ActivityStackManager.getInstance().onActivityDestroyed(this)
        super.onDestroy()
    }
}