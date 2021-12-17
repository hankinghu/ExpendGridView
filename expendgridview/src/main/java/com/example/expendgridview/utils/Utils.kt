package com.example.expendgridview.utils

import android.content.Context

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
 * create time 2021/12/16 2:46 下午
 * create by 胡汉君
 */
object Utils {
    /**
     * 获取屏幕的宽度
     */
    fun getScreenWidth(context: Context): Int {
        val dm = context.resources.displayMetrics
        return dm.widthPixels
    }
}