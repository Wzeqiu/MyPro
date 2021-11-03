package com.wzq.mypro

import android.os.Bundle
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.wzq.common.base.BaseVBActivity
import com.wzq.common.base.adapter.*
import com.wzq.common.utils.setTitleBarTitle
import com.wzq.common.net.ex.request.request
import com.wzq.common.utils.startActivity
import com.wzq.mypro.databinding.ActivityMainBinding
import com.wzq.mypro.databinding.ItemAdapterBinding
import com.wzq.mypro.databinding.ItemAdapterTwoBinding
import com.wzq.mypro.mvvm.MvvmActivity

class MainActivity : BaseVBActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    val datas = mutableListOf<MultiItemEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitleBarTitle("")
        viewBinding.tvContent.isVisible = true
        viewBinding.tvContent.setSingleOnClickListener {
            request({ netServer.test() }, isShowDialog = true) {
            }

            startActivity(MvvmActivity::class.java)
        }


        val adapter = initDefaultAdapter<String, ItemAdapterBinding>(ItemAdapterBinding::inflate) {
            setItemLayout {
                tvName.text = it
            }
            setOnItemClickListener { adapter, view, position ->
            }
        }

        val adapter1 = initDefaultMultiAdapter {
            addItemLayout<MultiItem<String>, ItemAdapterBinding>(0, ItemAdapterBinding::inflate) {
                tvName.text = it.data
            }

            addItemLayout<MultiItem<Int>, ItemAdapterTwoBinding>(1, ItemAdapterTwoBinding::inflate) {
                tvName.text = it.data.toString()
            }
        }

        for (i in 0..100) {
            datas.add(MultiItem(0, i.toString()))
            datas.add(MultiItem(1, i * 10))
        }
        adapter1.setList(datas)
        viewBinding.rv.adapter = adapter1
    }
}

class MyAdapter : DefaultAdapter<String, ItemAdapterBinding>(ItemAdapterBinding::inflate) {
    init {
        setItemLayout {
            tvName.text = it
        }
    }
}


class MyBaseAdapter : DefaultMultiAdapter() {
    init {
        addItemLayout<MultiItem<String>, ItemAdapterBinding>(0, ItemAdapterBinding::inflate) {
            tvName.text = it.data
        }

        addItemLayout<MultiItem<Int>, ItemAdapterTwoBinding>(1, ItemAdapterTwoBinding::inflate) {
            tvName.text = it.data.toString()
        }
    }
}
