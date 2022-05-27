package com.wzq.common.base

import android.os.Bundle
import android.view.LayoutInflater
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
internal abstract class BaseVbVmActivity<VB : ViewBinding, VM : ViewModel>(
    private val vmClass: Class<VM>
) : BaseVBActivity<VB>() {

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(vmClass)
        super.onCreate(savedInstanceState)

    }

}




