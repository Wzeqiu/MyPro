package com.wzq.common.base

/**
 *
 * Author: WZQ
 * CreateDate: 2021/10/22 9:20
 * Version: 1.0
 * Description: java类作用描述
 */
interface InitListener<VB> {
    /**
     * 创建ViewBinding
     */
    fun createViewBinding(): VB

    /**
     * 初始化
     */
    fun initData()
}