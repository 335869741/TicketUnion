package zzz.bing.ticketunion.utils

import android.util.TypedValue
import zzz.bing.ticketunion.BaseApplication

@Suppress("unused")
object SizeUtils {

    /**
     * dip转换px
     *
     * @param dpValue dip值
     * @return px值
     */
    fun dip2px(dpValue: Float): Int {
        val scale: Float = BaseApplication.getContext().resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * sp转pd
     *
     * @param spValue sp值
     * @return px值
     */
    fun sp2px(spValue: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, spValue,
            BaseApplication.getContext().resources.displayMetrics
        ).toInt()
    }

    /**
     * px转换dip
     *
     * @param pxValue px值
     * @return dip值
     */
    fun px2dip(pxValue: Float): Int {
        val scale = BaseApplication.getContext().resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

}