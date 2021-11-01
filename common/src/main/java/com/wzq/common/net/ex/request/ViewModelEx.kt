package com.wzq.common.net.ex.request

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import com.wzq.common.net.ApiException
import com.wzq.common.net.BaseResponse
import com.wzq.common.net.requestError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *
 * Author: WZQ
 * CreateDate: 2021/11/1 10:29
 * Version: 1.0
 * Description: java类作用描述
 */
fun <DATA : Any> ViewModel.request(
    context: Context,
    block: suspend () -> BaseResponse<DATA>,
    isShowDialog: Boolean = false,
    error: (code: Int, message: String) -> Unit = { _: Int, _: String -> },
    success: (DATA) -> Unit = {}
): Job {
    val dialog = if (isShowDialog) XPopup.Builder(context).asLoading() else null
    dialog?.show()
    return viewModelScope.launch {
        try {
            runCatching {
                block.invoke()
            }.mapCatching {
                if (it.isSuccess) it.data else throw ApiException(it.code, it.message)
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    success.invoke(it)
                    dismiss(dialog)
                }
            }.onFailure {
                val httpError = requestError(it)
                withContext(Dispatchers.Main) {
                    error.invoke(httpError.first, httpError.second ?: "")
                    dismiss(dialog)
                }
            }
        } finally {
            dismiss(dialog)
            Log.e("request", "request cancel")

        }
    }

}

private fun dismiss(loading: LoadingPopupView?) {
    loading?.let {
        if (it.isShow) {
            it.dismiss()
        }
    }
}
