package com.wzq.common.net.ex

import androidx.fragment.app.Fragment
import com.lxj.xpopup.XPopup
import com.wzq.common.net.BaseResponse

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 16:41
 * Version: 1.0
 * Description: java类作用描述
 */

fun <DATA : Any> Fragment.request(
    block: suspend () -> BaseResponse<DATA>,
    isShowDialog: Boolean = false,
    error: (code: Int, message: String) -> Unit = { _: Int, _: String -> },
    success: (DATA) -> Unit = {},
) {
    val dialog = if (isShowDialog) XPopup.Builder(requireContext()).asLoading() else null
    dialog?.show()
    requestResult(block) {

    }
}