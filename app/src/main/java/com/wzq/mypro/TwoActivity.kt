package com.wzq.mypro

import android.os.Bundle
import com.wzq.common.base.BaseVbVmActivity
import com.wzq.mypro.databinding.ActivityTwoBinding

class TwoActivity : BaseVbVmActivity<ActivityTwoBinding, MainViewModel>(ActivityTwoBinding::inflate, MainViewModel::class.java) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}