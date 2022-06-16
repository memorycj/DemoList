package com.wn.myapplication.span

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ReplacementSpan
import android.view.View


open class ViewSpan(protected var view: View) : ReplacementSpan() {
    /**
     * Returns the width of the span. Extending classes can set the height of the span by updating
     * attributes of [android.graphics.Paint.FontMetricsInt]. If the span covers the whole
     * text, and the height is not set,
     * [.draw] will not be
     * called for the span.
     *
     * @param paint Paint instance.
     * @param text Current text.
     * @param start Start character index for span.
     * @param end End character index for span.
     * @param fm Font metrics, can be null.
     * @return Width of the span.
     */
    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(widthSpec, heightSpec)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        fm?.apply {
            val vH = view.measuredHeight
            ascent = -vH / 2
            top = -vH / 2
            descent = vH / 2
            bottom = vH / 2
        }
        return view.right
    }

    /**
     * Draws the span into the canvas.
     *
     * @param canvas Canvas into which the span should be rendered.
     * @param text Current text.
     * @param start Start character index for span.
     * @param end End character index for span.
     * @param x Edge of the replacement closest to the leading margin.
     * @param top Top of the line.
     * @param y Baseline.
     * @param bottom Bottom of the line.
     * @param paint Paint instance.
     */
    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val fm = paint.fontMetricsInt
        val transY = (y + fm.descent + y + fm.ascent) / 2 - view.measuredHeight / 2
        canvas.save()
        canvas.translate(x, transY.toFloat())
        view.draw(canvas)
        canvas.restore()
    }
}