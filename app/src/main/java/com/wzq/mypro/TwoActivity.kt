package com.wzq.mypro

import com.wzq.common.base.BaseVbVmActivity
import com.wzq.mypro.databinding.ActivityTwoBinding

class TwoActivity : BaseVbVmActivity<ActivityTwoBinding, MainViewModel>() {

    override fun createViewBinding(): ActivityTwoBinding {
        return ActivityTwoBinding.inflate(layoutInflater)
    }

    override fun initData() {
    }

    override fun createViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }
}