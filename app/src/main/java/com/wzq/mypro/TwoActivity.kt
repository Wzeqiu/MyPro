package com.wzq.mypro

import android.os.Bundle
import com.wzq.common.base.BaseVBActivity
import com.wzq.common.utils.eventBusPost
import com.wzq.mypro.databinding.ActivityTwoBinding

class TwoActivity : BaseVBActivity<ActivityTwoBinding>(ActivityTwoBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        eventBusPost("张三")
        eventBusPost(10)
        eventBusPost(10)
        eventBusPost(10)
        eventBusPost(10)
    }

}