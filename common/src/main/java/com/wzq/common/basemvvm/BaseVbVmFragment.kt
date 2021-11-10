package com.wzq.common.basemvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.wzq.common.base.BaseVBFragment

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/28 11:00
 * Version: 1.0
 * Description: java类作用描述
 */
class BaseVbVmFragment<VB : ViewBinding, VM : ViewModel>(
    inflate: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> VB,
    private val vmClass: Class<VM>
) : BaseVBFragment<VB>(inflate) {
    protected lateinit var viewModel: VM
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(vmClass)
        return super.onCreateView(inflater, container, savedInstanceState)

    }

}