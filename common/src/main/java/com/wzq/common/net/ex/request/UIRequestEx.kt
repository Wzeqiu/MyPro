package com.wzq.common.net.ex.request

import android.app.Dialog
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.lxj.xpopup.XPopup
import com.wzq.common.net.BaseResponse
import com.wzq.common.net.ex.RequestResult
import kotlinx.coroutines.*

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/29 17:32
 * Version: 1.0
 * Description: 网络请求扩展函数
 */

/**
 * Activity 网络请求
 */
fun <DATA> FragmentActivity.request(
    block: suspend () -> BaseResponse<DATA>,
    isShowDialog: Boolean = false,
    error: (code: Int, message: String) -> Unit = { _: Int, _: String -> },
    success: (DATA) -> Unit = {}
): Job {
    return lifecycleResult(this, isShowDialog, block, error, success)
}

/**
 * Fragment 网络请求
 */
fun <DATA : Any> Fragment.request(
    block: suspend () -> BaseResponse<DATA>,
    isShowDialog: Boolean = false,
    error: (code: Int, message: String) -> Unit = { _: Int, _: String -> },
    success: (DATA) -> Unit = {}
): Job {
    return lifecycleResult(requireContext(), isShowDialog, block, error, success)
}

/**
 * 生命周期控制的网络请求
 */
fun <DATA> LifecycleOwner.lifecycleResult(
    context: Context,
    isShowDialog: Boolean = false,
    block: suspend () -> BaseResponse<DATA>,
    error: (code: Int, message: String) -> Unit = { _: Int, _: String -> },
    success: (DATA) -> Unit = {}
): Job {
    val dialog = if (isShowDialog) XPopup.Builder(context).asLoading() else null
    dialog?.show()
    return lifecycleScope.launch {
        when (val result = request(block)) {
            is RequestResult.Success<*> -> {
                withContext(Dispatchers.Main) {
                    success.invoke(result.data as DATA)
                }
            }
            is RequestResult.Failure -> {
                withContext(Dispatchers.Main) {
                    error.invoke(result.code, result.message)
                }
            }
            is RequestResult.Cancel -> {

            }
        }
        withContext(Dispatchers.Main) {
            dialog?.let { dialog ->
                if (dialog.isShow) {
                    dialog.dismiss()
                }
            }
        }

    }
}

/**
 * Dialog 网络请求
 */
fun <DATA : Any> Dialog.request(
    isShowDialog: Boolean = false,
    block: suspend () -> BaseResponse<DATA>,
    error: (code: Int, message: String) -> Unit = { _: Int, _: String -> },
    success: (DATA) -> Unit = {}
): Job {
    return cancelRequest(context, isShowDialog, block, error, success)
}

/**
 * View 网络请求
 */
fun <DATA : Any> View.request(
    block: suspend () -> BaseResponse<DATA>,
    isShowDialog: Boolean = false,
    error: (code: Int, message: String) -> Unit = { _: Int, _: String -> },
    success: (DATA) -> Unit = {}
): Job {
    return cancelRequest(context, isShowDialog, block, error, success)
}

/**
 * 可取消 网络请求
 */
fun <DATA : Any> cancelRequest(
    context: Context,
    isShowDialog: Boolean = false,
    block: suspend () -> BaseResponse<DATA>,
    error: (code: Int, message: String) -> Unit = { _: Int, _: String -> },
    success: (DATA) -> Unit = {}
): Job {
    val dialog = if (isShowDialog) XPopup.Builder(context).asLoading() else null
    dialog?.show()
    return GlobalScope.launch(Dispatchers.IO) {
        when (val result = request(block)) {
            is RequestResult.Success -> {
                withContext(Dispatchers.Main) {
                    success.invoke(result.data)
                }
            }
            is RequestResult.Failure -> {
                withContext(Dispatchers.Main) {
                    error.invoke(result.code, result.message)
                }
            }
            is RequestResult.Cancel -> {

            }
        }
        withContext(Dispatchers.Main) {
            dialog?.let { dialog ->
                if (dialog.isShow) {
                    dialog.dismiss()
                }
            }
        }
    }
}

