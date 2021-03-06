package com.wzq.common.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.wzq.common.base.BaseVBActivity
import java.lang.reflect.ParameterizedType

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 10:39
 * Version: 1.0
 * Description: java类作用描述
 */

inline fun <reified T : Activity> Activity.startActivity(clazz: Class<T>, vararg par: Pair<String, Any>) {
    startActivity(Intent(this, clazz).putExtras(bundleOf(*par)))
}

inline fun <reified T : Activity> Fragment.startActivity(clazz: Class<T>, vararg par: Pair<String, Any>) {
    requireContext().startActivity(Intent(requireContext(), clazz).putExtras(bundleOf(*par)))
}

inline fun <reified T : Activity> View.startActivity(clazz: Class<T>, vararg par: Pair<String, Any>) {
    context.startActivity(Intent(context, clazz).putExtras(bundleOf(*par)))
}


/**
 * context 转 Activity
 */
fun Context.context2Activity(): FragmentActivity? {
    var context = this
    while (context is ContextWrapper) {
        context = if (context is FragmentActivity) {
            return context
        } else {
            context.baseContext
        }
    }
    return null
}


