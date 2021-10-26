package com.wzq.mypro

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.wzq.common.base.BaseVBActivity
import com.wzq.common.base.ex.setTitleBarTitle
import com.wzq.common.base.ex.startActivity
import com.wzq.common.net.ex.request
import com.wzq.common.net.ex.requestFlow
import com.wzq.common.utils.dp2px
import com.wzq.common.utils.setSingleOnClickListener
import com.wzq.mypro.databinding.ActivityMainBinding

class MainActivity : BaseVBActivity<ActivityMainBinding>() {
    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val aa = 10.dp2px
    }

    override fun createViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initData() {
        setTitleBarTitle("")
        viewBinding.tvContent.isVisible = true
        viewBinding.tvContent.setSingleOnClickListener {
            request({ netServer.test() }, isShowDialog = false) {


            }
            request({ netServer.test() }) {

            }

            startActivity(TwoActivity::class.java)
        }
    }


}