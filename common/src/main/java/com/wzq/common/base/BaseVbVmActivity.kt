package com.wzq.common.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/25 17:22
 * Version: 1.0
 * Description: java类作用描述
 */
internal abstract class BaseVbVmActivity<VB : ViewBinding, VM : ViewModel> : BaseVBActivity<VB>() {

    private lateinit var _viewModel: VM
    protected val viewModel get() = _viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        _viewModel = ViewModelProvider(this).get(createViewModel())
        super.onCreate(savedInstanceState)
    }

    abstract fun createViewModel(): Class<VM>


}




