package com.wzq.common.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

fun <VB : ViewBinding> Any.reflectInflate(
    index: Int,
    inflater: LayoutInflater,
    parent: ViewGroup? = null,
    attachToParent: Boolean = false
): VB {
    return getSuperClassGenericType(index).getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    ).let {
        it.invoke(null, inflater, parent, attachToParent) as VB
    }
}

