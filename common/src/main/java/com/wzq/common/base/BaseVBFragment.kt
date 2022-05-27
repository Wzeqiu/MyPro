package com.wzq.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.wzq.common.utils.reflectInflate

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 9:27
 * Version: 1.0
 * Description: java类作用描述
 */
open class BaseVBFragment<VB : ViewBinding> :
    Fragment() {
    protected lateinit var viewBinding: VB
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = reflectInflate(0,inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}