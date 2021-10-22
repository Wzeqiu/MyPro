package com.wzq.common.net

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.lxj.xpopup.XPopup
import com.wzq.common.net.ex.request
import com.wzq.common.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 11:05
 * Version: 1.0
 * Description: java类作用描述
 */

fun <DATA : Any> FragmentActivity.request(
    block: suspend () -> BaseResponse<DATA>,
    isShowDialog: Boolean = false,
    error: (code: Int, message: String) -> Unit = { _: Int, _: String -> },
    success: (DATA) -> Unit = {}
) {
    val dialog = if (isShowDialog) XPopup.Builder(this).asLoading() else null
    dialog?.show()
    lifecycleScope.launch {
        runCatching {
            block.invoke()
        }.mapCatching {
            if (it.isSuccess) it.data else throw ApiException(it.code, it.message)
        }.onSuccess {
            withContext(Dispatchers.Main) {
                dialog?.let { dialog ->
                    if (dialog.isShow) {
                        dialog.dismiss()
                    }
                }
                success.invoke(it)
            }
        }.onFailure {
            requestError(it, error = { code: Int, message: String ->
                withContext(Dispatchers.Main) {
                    dialog?.let { dialog ->
                        if (dialog.isShow) {
                            dialog.dismiss()
                        }
                    }
                    showToast(message)
                    error.invoke(code, message)
                    Log.e("data", "code $code message $message")
                }
            })
        }
    }
}


fun <DATA : Any> Fragment.request(
    block: suspend () -> BaseResponse<DATA>,
    isShowDialog: Boolean = false,
    error: (code: Int, message: String) -> Unit = { _: Int, _: String -> },
    success: (DATA) -> Unit = {},
) {
    val dialog = if (isShowDialog) XPopup.Builder(requireContext()).asLoading() else null
    dialog?.show()
    lifecycleScope.launch {
        runCatching {
            block.invoke()
        }.mapCatching {
            if (it.isSuccess) it.data else throw ApiException(it.code, it.message)
        }.onSuccess {
            withContext(Dispatchers.Main) {
                dialog?.let { dialog ->
                    if (dialog.isShow) {
                        dialog.dismiss()
                    }
                }
                success.invoke(it)
            }
        }.onFailure {
            requestError(it, error = { code: Int, message: String ->
                withContext(Dispatchers.Main) {
                    dialog?.let { dialog ->
                        if (dialog.isShow) {
                            dialog.dismiss()
                        }
                    }
                    showToast(message)
                    error.invoke(code, message)
                    Log.e("data", "code $code message $message")
                }
            })
        }
    }
}


fun <DATA : Any> LifecycleOwner.request(
    block: suspend () -> BaseResponse<DATA>,
    result: (Result) -> Unit = {}
) {
    lifecycleScope.launch {
        runCatching {
            block.invoke()
        }.mapCatching {
            if (it.isSuccess) it.data else throw ApiException(it.code, it.message)
        }.onSuccess {
            withContext(Dispatchers.Main) {
                result.invoke(Success(it))
            }
        }.onFailure {
            requestError(it, error = { code: Int, message: String ->
                withContext(Dispatchers.Main) {
                    result.invoke(Failure(code, message))
                }
            })
        }
    }
}


