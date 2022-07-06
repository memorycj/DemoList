package com.wn.myapplication.span

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Shader
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance
import android.widget.TextView
import com.wn.myapplication.util.dp

class TextSpan(var chatComment: TextView, var measureText: Float,var offsetX: Int = 0) : CharacterStyle(),UpdateAppearance {
    val martix = Matrix()
    var x = 0
    var grad:LinearGradient? = null
    var lastRefreshTime = 0L
    val refreshTime = 100L
    val refreshTask = {
        chatComment.invalidate()
    }
    override fun updateDrawState(tp: TextPaint) {
        if(measureText > chatComment.measuredWidth){
            measureText = chatComment.measuredWidth.toFloat()
        }
        val isRefreshX = System.currentTimeMillis() - lastRefreshTime >= refreshTime - 20L
        if(isRefreshX) {
            x = if(x >= measureText){
                0
            } else {
                (x + 6.dp).coerceAtMost(measureText.toInt())
            }
        }
        if(grad == null){
            grad = LinearGradient(0f,0f,20f,tp.descent() - tp.ascent(), Color.GREEN,Color.RED,Shader.TileMode.CLAMP)
        }
        martix.setTranslate(x.toFloat()+offsetX,0f)
        grad!!.setLocalMatrix(martix)
        tp.shader = grad
        if(isRefreshX) {
            chatComment.removeCallbacks(refreshTask)
            chatComment.postDelayed(refreshTask,refreshTime)
            lastRefreshTime = System.currentTimeMillis()
        }
    }
}