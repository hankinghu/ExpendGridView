package com.example.hannotest

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.hannotest.databinding.ActivityMainBinding
import com.example.hannotest.utils.Utils
import com.example.hannotest.view.ItemView
import com.example.hannotest.view.LoadMoreView

class MainActivity : Activity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private var data = mutableListOf<String>().apply {
        add("1")
        add("2")
        add("3")
        add("4")
        add("1")
        add("2")
        add("3")
        add("4")
        add("1")
        add("2")
        add("3")
        add("4")
    }

    //是否展开
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        val itemWidth = Utils.getScreenWidth(this) / 5
        //添加一个foot
        activityMainBinding.gridView.addFooterView(LoadMoreView(this))
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
                }
            }
        }
        activityMainBinding.gridView.setFooterClickListener {
            //里面有notify 不需要另外再加

        }


    }
}