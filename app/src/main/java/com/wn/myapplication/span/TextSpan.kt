package com.wn.myapplication.span

import android.graphics.*
import android.os.Build
import android.text.Spanned
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.LineBackgroundSpan
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.wn.myapplication.util.dp

class TextSpan(var chatComment: TextView, var measureText: Float, var nikeName: String, var offsetX: Int = 0
) :
    CharacterStyle() {
    var matrix = Matrix()
    var grad:LinearGradient? = null
    var lastRefreshTime = 0L
    var x = 0
    val refreshTime = 100L
    val refreshTask = {
        chatComment.invalidate()
    }
    override fun updateDrawState(paint: TextPaint) {
        val isRefreshX = System.currentTimeMillis() - lastRefreshTime >= refreshTime - 20L
        if(measureText > chatComment.measuredWidth){
            measureText = chatComment.measuredWidth.toFloat()
        }
        if(isRefreshX) {
            x = if(x >= measureText){
                0
            } else {
                (x + 6.dp).coerceAtMost(measureText.toInt())
            }
        }
        val gradientSize = (2 * measureText / nikeName.length).toInt()

        if(grad == null){
            grad = LinearGradient(gradientSize.toFloat(),0f,-gradientSize.toFloat(),paint.descent() - paint.ascent(), Color.GREEN,Color.RED,Shader.TileMode.CLAMP)
        }
        matrix.setTranslate((offsetX+x).toFloat(),0f)
        grad!!.setLocalMatrix(matrix)
        paint.shader = grad
        if(isRefreshX) {
            chatComment.removeCallbacks(refreshTask)
            chatComment.postDelayed(refreshTask,refreshTime)
            lastRefreshTime = System.currentTimeMillis()
        }
    }

}