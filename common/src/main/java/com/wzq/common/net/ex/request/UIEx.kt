package com.wzq.common.net.ex.request

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.lxj.xpopup.XPopup
import com.wzq.common.net.ApiException
import com.wzq.common.net.BaseResponse
import com.wzq.common.net.ex.Result
import com.wzq.common.net.requestError
import com.wzq.common.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 16:41
 * Version: 1.0
 * Description: java类作用描述
 */

fun <DATA : Any> FragmentActivity.request(
    block: suspend () -> BaseResponse<DATA>,
    isShowDialog: Boolean = false,
    error: (code: Int, message: String) -> Unit = { _: Int, _: String -> },
    success: (DATA) -> Unit = {}
): Job {
    val dialog = if (isShowDialog) XPopup.Builder(this).asLoading() else null
    dialog?.show()
    return requestResult(block) {
        when (it) {
            is Result.Success -> {
                success.invoke(it.data as DATA)
            }
            is Result.Failure -> {
                showToast(it.message)
                error.invoke(it.code, it.message)
            }
            is Result.Cancel -> {
            }
        }

        dialog?.let { dialog ->
            if (dialog.isShow) {
                dialog.dismiss()
            }
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
    requestResult(block) {
        when (it) {
            is Result.Success -> {
                success.invoke(it.data as DATA)
            }
            is Result.Failure -> {
                showToast(it.message)
                error.invoke(it.code, it.message)
            }
            is Result.Cancel -> {
            }
        }

        dialog?.let { dialog ->
            if (dialog.isShow) {
                dialog.dismiss()
            }
        }
    }
}


internal fun <DATA : Any> LifecycleOwner.requestResult(
    block: suspend () -> BaseResponse<DATA>,
    result: (Result) -> Unit = {}
): Job {
    return lifecycleScope.launch {
        try {
            runCatching {
                block.invoke()
            }.mapCatching {
                if (it.isSuccess) it.data else throw ApiException(it.code, it.message)
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    result.invoke(Result.Success(it))
                }
            }.onFailure {
                val httpError = requestError(it)
                withContext(Dispatchers.Main) {
                    result.invoke(Result.Failure(httpError.first, httpError.second ?: ""))
                }
            }
        } finally {
            result.invoke(Result.Cancel)
        }
    }

}