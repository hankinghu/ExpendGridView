package com.example.hannotest.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.hannotest.R
import com.example.hannotest.databinding.ItemViewBinding

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
 * create time 2021/12/16 2:38 下午
 * create by 胡汉君
 */
class ItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private var itemViewBinding: ItemViewBinding =
        ItemViewBinding.inflate(LayoutInflater.from(context), this)

    fun setPluginName(name: String) {
        itemViewBinding.pluginName.text = name
    }
}