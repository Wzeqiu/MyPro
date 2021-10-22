package com.wzq.common.net.ex

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.lxj.xpopup.XPopup
import com.wzq.common.net.BaseResponse
import com.wzq.common.net.Failure
import com.wzq.common.net.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
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
) {
    val dialog = if (isShowDialog) XPopup.Builder(this).asLoading() else null
    dialog?.show()
    requestResult(block) {
        dialog?.let { dialog ->
            if (dialog.isShow) {
                dialog.dismiss()
            }
        }
        when (it) {
            is Success<*> -> {
                success.invoke(it.data as DATA)
            }
            is Failure -> {
                error.invoke(it.code, it.message)
            }
        }
    }
}


fun <DATA : Any> FragmentActivity.request(
    block: suspend () -> BaseResponse<DATA>,
    error: (code: Int, message: String) -> Unit = { _: Int, _: String -> },
    success: (DATA) -> Unit = {}
) {
    lifecycleScope.launch {
        flow {
            val data = block.invoke()
            emit(data.data)
        }.onStart {

        }.flowOn(Dispatchers.IO)

    }

}