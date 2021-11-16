package com.wzq.common.net.ex.request

import android.app.Dialog
import android.content.Context
import android.util.Log
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
fun <DATA : Any> FragmentActivity.request(
    block: suspend () -> BaseResponse<DATA>,
    isShowDialog: Boolean = false,
    error: (code: Int, message: String) -> Unit = { _: Int, _: String -> },
    success: (DATA) -> Unit = {}
): Job {
    return lifecycleRequest(this, isShowDialog, block, error, success)
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
    return lifecycleRequest(requireContext(), isShowDialog, block, error, success)
}

/**
 * 生命周期控制的网络请求
 */
internal fun <DATA : Any> LifecycleOwner.lifecycleRequest(
    context: Context,
    isShowDialog: Boolean = false,
    block: suspend () -> BaseResponse<DATA>,
    error: (code: Int, message: String) -> Unit = { _: Int, _: String -> },
    success: (DATA) -> Unit = {}
): Job {
    val dialog = if (isShowDialog) XPopup.Builder(context).asLoading() else null
    dialog?.show()
    return request(lifecycleScope, block) {
        withContext(Dispatchers.Main) {
            requestResult(it, error, success)
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
    return hasCancelRequest(context, isShowDialog, block, error, success)
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
    return hasCancelRequest(context, isShowDialog, block, error, success)
}

/**
 * 可取消 网络请求
 */
internal fun <DATA : Any> hasCancelRequest(
    context: Context,
    isShowDialog: Boolean = false,
    block: suspend () -> BaseResponse<DATA>,
    error: (code: Int, message: String) -> Unit = { _: Int, _: String -> },
    success: (DATA) -> Unit = {}
): Job {
    val dialog = if (isShowDialog) XPopup.Builder(context).asLoading() else null
    dialog?.show()
    return request(GlobalScope, block) {
        withContext(Dispatchers.Main) {
            requestResult(it, error, success)
            dialog?.let { dialog ->
                if (dialog.isShow) {
                    dialog.dismiss()
                }
            }
        }
    }
}

/**
 * 请求结果
 */
internal fun <DATA : Any> requestResult(
    result: RequestResult<DATA>,
    error: (code: Int, message: String) -> Unit = { _: Int, _: String -> },
    success: (DATA) -> Unit = {}
) {
    when (result) {
        is RequestResult.Success -> {
            Log.e("UIRequestEx", "Success  ${Thread.currentThread().name}")
            success.invoke(result.data)
        }
        is RequestResult.Failure -> {
            Log.e("UIRequestEx", "Failure result${result}   ${Thread.currentThread().name}")
            error.invoke(result.code, result.message)
        }
        is RequestResult.Cancel -> {
            Log.e("UIRequestEx", "Cancel  ${Thread.currentThread().name}")
        }
    }
}

