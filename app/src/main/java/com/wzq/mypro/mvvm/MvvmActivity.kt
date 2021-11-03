package com.wzq.mypro.mvvm

import android.os.Bundle
import com.wzq.common.basemvvm.BaseVbVmActivity
import com.wzq.mypro.databinding.ActivityMvvmBinding

class MvvmActivity : BaseVbVmActivity<ActivityMvvmBinding, MvvmModel>(ActivityMvvmBinding::inflate, MvvmModel::class.java) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.model = viewModel
    }
}