package com.wzq.mypro

import android.os.Bundle
import androidx.core.view.isVisible
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.wzq.common.base.BaseVBActivity
import com.wzq.common.base.adapter.DefaultAdapter
import com.wzq.common.base.adapter.DefaultMultiAdapter
import com.wzq.common.base.ex.setTitleBarTitle
import com.wzq.common.net.ex.request.request
import com.wzq.common.utils.setSingleOnClickListener
import com.wzq.mypro.databinding.ActivityMainBinding
import com.wzq.mypro.databinding.ItemAdapterBinding
import com.wzq.mypro.databinding.ItemAdapterTwoBinding

class MainActivity : BaseVBActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    val datas = mutableListOf<MultiItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitleBarTitle("")
        viewBinding.tvContent.isVisible = true
        viewBinding.tvContent.setSingleOnClickListener {
            request({ netServer.test() }, isShowDialog = true) {
            }
        }

        val adapter = MyAdapter()
        adapter.aa
        val adapter1 = MyBaseAdapter()
        for (i in 0..100) {
            datas.add(MultiItem(i % 2))
        }
        adapter1.setList(datas)
        viewBinding.rv.adapter = adapter1
    }
}

class MyAdapter : DefaultAdapter<String, ItemAdapterBinding>(ItemAdapterBinding::inflate) {
    val aa = 10

    init {
        setItemLayout {
            tvName.text = it
        }
    }
}


class MyBaseAdapter : DefaultMultiAdapter<MultiItem>() {
    init {
        addItemLayout(0, ItemAdapterBinding::inflate) {
            tvName.text = it.itemType.toString()
        }

        addItemLayout(1, ItemAdapterTwoBinding::inflate) {
            tvName.text = it.itemType.toString()
        }
    }
}

class MultiItem(override val itemType: Int) : MultiItemEntity {

}