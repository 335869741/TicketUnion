package zzz.bing.ticketunion.utils

import android.content.Context
import android.util.TypedValue
import zzz.bing.ticketunion.BaseApplication

object SizeUtils {

    /**
     * dip转换px
     *
     * @param context 上下文
     * @param dpValue dip值
     * @return px值
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * sp转pd
     *
     * @param context 上下文
     * @param spValue sp值
     * @return px值
     */
    fun sp2px(context: Context, spValue: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, spValue,
            context.resources.displayMetrics
        ).toInt()
    }

    /**
     * px转换dip
     *
     * @param context 上下文
     * @param pxValue px值
     * @return dip值
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

}