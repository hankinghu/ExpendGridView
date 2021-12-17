package com.example.hannotest.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.expendgridview.IExpendControl
import com.example.hannotest.R
import com.example.hannotest.databinding.LoadMoreViewBinding

/**
 *
 *  _                 _    _
 * | |               | |  (_)
 * | |__   __ _ _ __ | | ___ _ __   __ _
 * | '_ \ / _` | '_ \| |/ / | '_ \ / _` |
 * | | | | (_| | | | |   | | | | | (_| |
 * |_| |_|\__,_|_| |_|_|\_\_|_| |_|\__, |
 ********************************** __/ |
 ********************************* |___/
 * create time 2021/12/16 3:40 下午
 * create by 胡汉君
 */
class LoadMoreView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), IExpendControl {
    private var viewBinding: LoadMoreViewBinding =
        LoadMoreViewBinding.inflate(LayoutInflater.from(context), this)

    override fun expend() {
        //展开时操作
        viewBinding.loadMore.text = "收起"
        viewBinding.arrow.setImageResource(R.drawable.up_arrow)
    }

    override fun fold() {
        //折叠时操作
        viewBinding.loadMore.text = "展开全部"
        viewBinding.arrow.setImageResource(R.drawable.down_arrow)

    }
}