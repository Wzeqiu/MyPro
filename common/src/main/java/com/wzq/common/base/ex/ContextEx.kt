package com.wzq.common.base.ex

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 10:39
 * Version: 1.0
 * Description: java类作用描述
 */

fun <T : Activity> Activity.startActivity(clazz: Class<T>, vararg par: Pair<String, Any>) {
    startActivity(Intent(this, clazz).putExtras(bundleOf(*par)))
}

fun <T : Activity> Fragment.startActivity(clazz: Class<T>, vararg par: Pair<String, Any>) {
    requireContext().startActivity(Intent(requireContext(), clazz).putExtras(bundleOf(*par)))
}

fun <T : Activity> View.startActivity(clazz: Class<T>, vararg par: Pair<String, Any>) {
    context.startActivity(Intent(context, clazz).putExtras(bundleOf(*par)))
}