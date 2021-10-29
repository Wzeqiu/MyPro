package com.wzq.common.net.ex.request

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.lxj.xpopup.XPopup
import com.wzq.common.net.ApiException
import com.wzq.common.net.BaseResponse
import com.wzq.common.net.ex.Cancel
import com.wzq.common.net.ex.Failure
import com.wzq.common.net.ex.Success
import com.wzq.common.net.ex.requestResult
import com.wzq.common.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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
        dialog?.let { dialog ->
            if (dialog.isShow) {
                dialog.dismiss()
            }
        }
        when (it) {
            is Success -> {
                success.invoke(it.data)
            }
            is Failure -> {
                showToast(it.message)
                error.invoke(it.code, it.message)
            }
        }
    }
}


internal fun <DATA : Any> FragmentActivity.requestFlow(
    block: suspend () -> BaseResponse<DATA>,
    success: (MutableStateFlow<DATA>)
) {
    lifecycleScope.launch {
        flow {
            val data = block.invoke()
            emit(data)
        }.map {
            if (it.isSuccess) it.data else throw ApiException(it.code, it.message)
        }.flowOn(Dispatchers.IO)
            .catch {
            }.collect {
                success.value = it
            }
    }

}
