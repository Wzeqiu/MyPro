package com.wzq.mypro

import android.os.Bundle
import androidx.core.view.isVisible
import com.wzq.common.base.BaseVBActivity
import com.wzq.common.base.BaseVBAdapter
import com.wzq.common.base.ex.setTitleBarTitle
import com.wzq.common.net.ex.request
import com.wzq.common.utils.setSingleOnClickListener
import com.wzq.mypro.databinding.ActivityMainBinding
import com.wzq.mypro.databinding.ItemAdapterBinding

class MainActivity : BaseVBActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    val datas = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitleBarTitle("")
        viewBinding.tvContent.isVisible = true
        viewBinding.tvContent.setSingleOnClickListener {
            request({ netServer.test() }) {

            }
        }

        for (i in 0..100) {
            datas.add(i.toString())
        }

        viewBinding.rv.adapter = object : BaseVBAdapter<String, ItemAdapterBinding>(datas, ItemAdapterBinding::inflate) {
            override fun onBindData(viewBinding: ItemAdapterBinding, data: String, position: Int) {
                viewBinding.tvName.text = data
            }

        }
    }


}