package com.wzq.mypro

import com.google.auto.service.AutoService
import com.wzq.common.net.ex.ServerErrorNotifyInterface

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/29 11:48
 * Version: 1.0
 * Description: 网络请求服务器返回异常状态通知
 */
@AutoService(ServerErrorNotifyInterface::class)
class ServerErrorNotify : ServerErrorNotifyInterface {
    override fun errorNotify(code: Int) {


    }
}