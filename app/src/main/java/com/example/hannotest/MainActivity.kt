package com.example.hannotest

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.hannotest.databinding.ActivityMainBinding
import com.example.expendgridview.utils.Utils
import com.example.hannotest.view.ItemView
import com.example.hannotest.view.LoadMoreView

class MainActivity : Activity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private var data = mutableListOf<String>().apply {
        add("中国")
        add("日本")
        add("美国")
        add("韩国")
        add("英国")
        add("菲律宾")
        add("越南")
        add("老挝")
        add("缅甸")
        add("泰国")
        add("印度")
        add("古巴")
        add("缅甸")
        add("泰国")
        add("印度")
//        add("古巴")
    }

    //是否展开
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        val itemWidth = Utils.getScreenWidth(this) / 5
        //添加一个foot
        activityMainBinding.gridView.addExpendControlView(LoadMoreView(this))
        activityMainBinding.gridView.setFoldNum(2)
        activityMainBinding.gridView.adapter = object : BaseAdapter() {
            override fun getCount(): Int = data.size

            override fun getItem(position: Int): Any = data[position]

            override fun getItemId(position: Int): Long = position.toLong()

            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                //创建一个itemHoldView用于存放一行
                return ItemView(context = this@MainActivity).apply {
                    //设置一下大小
                    val param = ConstraintLayout.LayoutParams(itemWidth, itemWidth)
                    layoutParams = param
                    setPluginName(data[position])
                }
            }
        }
    }
}