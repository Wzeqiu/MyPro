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
abstract class BaseVBFragment<VB : ViewBinding>(private val inflate: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> VB) :
    Fragment() {
    protected lateinit var viewBinding: VB
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = inflate(inflater, container, false)
        return viewBinding.root
    }
}