package com.wzq.common.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 * index ViewBinding泛型下标位置
 */
fun <VB : ViewBinding> Any.reflectInflate(
    index: Int,
    inflater: LayoutInflater,
    parent: ViewGroup? = null,
    attachToParent: Boolean = false
): VB {
    return getSuperClassGenericType(index).reflectInflateBinding(inflater, parent, attachToParent) as VB
}

/**
 * 根据Class获取具体对象
 */
fun Class<*>.reflectInflateBinding(
    inflater: LayoutInflater,
    parent: ViewGroup? = null,
    attachToParent: Boolean = false
): ViewBinding {
    return getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    ).let {
        it.invoke(null, inflater, parent, attachToParent) as ViewBinding
    }
}




