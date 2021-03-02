package zzz.bing.ticketunion.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.ReplacementSpan
import android.util.TypedValue


class TextRoundBackground(
        val backgroundColor: Int, val textColor: Int, val context: Context
) : ReplacementSpan() {


    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        return paint.measureText(text, start, end).toInt() //+ px2dp(16)
    }

    override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val originalColor = paint.color
        paint.color = backgroundColor
        val textWidth = paint.measureText(text, start, end)
        canvas.drawRoundRect(
                RectF(
                        x,
//                        top.toFloat() + px2dp(3),
//                        x + textWidth + px2dp(16),
//                        bottom.toFloat() - px2dp(1)
                        top.toFloat(),
                        x + textWidth,
                        bottom.toFloat(),
                ),
//                px2dp(4).toFloat(),
//                px2dp(4).toFloat(),
                4f,
                4f,
                paint
        )

        paint.color = textColor
//        canvas.drawText(text!!, start, end, x + px2dp(8), y.toFloat(), paint)
        canvas.drawText(text!!, start, end, x + 8, y.toFloat(), paint)
        paint.color = originalColor
    }


    private fun px2dp(pxValue: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pxValue.toFloat(), context.resources.displayMetrics).toInt()
    }


}