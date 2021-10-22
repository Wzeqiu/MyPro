package com.wzq.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 9:27
 * Version: 1.0
 * Description: java类作用描述
 */
abstract class BaseVBFragment<VB : ViewBinding> : Fragment(), InitListener<VB> {
    private lateinit var _viewBinding: VB
    protected val viewBinding get() = _viewBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewBinding = createViewBinding()
        initData()
        return viewBinding.root
    }
}