package com.wzq.mypro

import android.os.Bundle
import android.util.Log
import android.widget.BaseAdapter
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

class MainActivity : BaseVBActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    val datas = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitleBarTitle("")
        viewBinding.tvContent.isVisible = true
        viewBinding.tvContent.setSingleOnClickListener {
            Log.e("aa", "ddd")
            val job = request({ netServer.test() }, isShowDialog = true) {

            }

            it.postDelayed({ job.cancel() }, 5000)
        }


        val adapter1 = DefaultMultiAdapter<MultiItemEntity>()
//        adapter1.aa = 10

        for (i in 0..100) {
            datas.add(i.toString())
        }

//        adapter1.setList(datas)

        viewBinding.rv.adapter = adapter1
    }
}

class MyAdapter(var aa: Int = 0) : DefaultAdapter<String, ItemAdapterBinding>(ItemAdapterBinding::inflate, {
    if (aa == 0)
        tvName.text = it
}) {

}


class MyBaseAdapter : DefaultMultiAdapter<MultiItem>() {
    init {
        addItemType<ItemAdapterBinding>(0, ItemAdapterBinding::inflate) {

        }

        addItemType<ItemAdapterBinding>(1, ItemAdapterBinding::inflate) {

        }

    }
}

class MultiItem(override val itemType: Int) : MultiItemEntity {

}