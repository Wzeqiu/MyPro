package com.wzq.common.basemvvm

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.wzq.common.base.BaseVBActivity

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/25 17:22
 * Version: 1.0
 * Description: java类作用描述
 */
abstract class BaseVbVmActivity<VB : ViewBinding, VM : BaseViewModel>(
    private val vmClass: Class<VM>? = null
) : BaseVBActivity<VB>() {

    protected var viewModel: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = vmClass?.let {
            val viewModel = ViewModelProvider(this).get(vmClass)
            viewModel.context = this
            viewModel
        }


        super.onCreate(savedInstanceState)

    }

}




