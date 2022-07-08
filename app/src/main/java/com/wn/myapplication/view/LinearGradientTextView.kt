package com.wn.myapplication.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.text.Spanned
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import com.wn.myapplication.R


/**
 * @author menghua.wang
 * @创建时间：2018/8/14 下午2:14
 * @项目名称：starmaker-android-client
 */
class LinearGradientTextView : AppCompatTextView {
    var parentTextView: TextView? = null
    private var gradientPaint: TextPaint? = null
    private var linearGradient: LinearGradient? = null
    private var gradientMatrix: Matrix? = null

    private var translateX: Float = 0.toFloat()
    private var deltaX = 10f

    private var showTime: Int = 0 // 显示的时间
    private var lineNumber: Int = 0 // 行数
    private var showStyle: Int = 0
    private var gradientColor: Int = 1
    private var tileMode: Int = 0

    @ColorInt
    var baseColor = 0
    @ColorInt
    var lightColor = 0
    var hasColorAnimation = false
        set(value) {
            if (field != value) {
                field = value
                initColorAnimationData()
            }
        }

    // 5.1 及以下系统emoji上面加渐变动画支持有问题，因此屏蔽
    private val supportColorAnimation = Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrs(context, attrs)
    }

    @SuppressLint("CustomViewStyleable", "Recycle")
    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView, 0, 0)
        showTime = typedArray.getInteger(R.styleable.GradientTextView_showTime, 80)
        lineNumber = typedArray.getInteger(R.styleable.GradientTextView_lineNumber, 1)
        showStyle = typedArray.getInt(R.styleable.GradientTextView_showStyle, UNIDIRECTION)
        gradientColor = typedArray.getColor(R.styleable.GradientTextView_textColor, Color.WHITE)
        tileMode = typedArray.getInt(R.styleable.GradientTextView_tileMode, 0)
    }

    private fun initColorAnimationData() {
        if (hasColorAnimation && supportColorAnimation) {
            gradientPaint = paint
            val text = text.toString()
            val textWidth = gradientPaint?.measureText(text)
            var gradientSize = 0
            textWidth?.let {
                gradientSize = (2 * it / text.length).toInt()
            }
            // 边缘融合
            var mode = Shader.TileMode.CLAMP
            when (tileMode) {
                CLAMP -> {
                    mode = Shader.TileMode.CLAMP
                }
                REPEAT -> {
                    mode = Shader.TileMode.REPEAT
                }
                MIRROR -> {
                    mode = Shader.TileMode.MIRROR
                }
            }
            linearGradient = LinearGradient(-gradientSize.toFloat() * 2, 0f, gradientSize.toFloat(), gradientSize.toFloat(),
                    intArrayOf(baseColor, lightColor, baseColor),
                    floatArrayOf(0f, 0.5f, 1f), mode)

            gradientPaint?.shader = linearGradient
            gradientMatrix = Matrix()
            translateX = 0f
            postInvalidate()
//            LogUtil.d("LinearGradientTextView", "text:$text hasColorAnimation:$hasColorAnimation")
        } else {
            val text = text.toString()
            val textWidth = gradientPaint?.measureText(text)
            var gradientSize = 0
            textWidth?.let {
                gradientSize = (2 * it / text.length).toInt()
            }
            val color = textColors.getColorForState(drawableState, 0)
            linearGradient = LinearGradient(gradientSize.toFloat(), gradientSize.toFloat(), -gradientSize.toFloat(), 0f,
                    intArrayOf(color, color, color),
                    floatArrayOf(0f, 0.5f, 1f), Shader.TileMode.CLAMP)
            gradientPaint?.shader = null
            gradientMatrix = Matrix()
            linearGradient?.setLocalMatrix(gradientMatrix)
            postInvalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (hasColorAnimation && supportColorAnimation) {
            val textWidth = gradientPaint?.measureText(text.toString())?.div(lineNumber)
            translateX += deltaX
            when (showStyle) {
                UNIDIRECTION ->
                    // 单向闪动
                    if (textWidth != null) {
                        if (translateX > textWidth + 1 || translateX < 1) {
                            translateX = 0f
                            translateX += deltaX
                        }
                    }
                TWOWAY ->
                    // 来回闪动
                    if (textWidth != null) {
                        if (translateX > textWidth + 1 || translateX < 1) {
                            deltaX = -deltaX
                        }
                    }
            }
            gradientMatrix?.setTranslate(translateX, 0f)
            linearGradient?.setLocalMatrix(gradientMatrix)
            postInvalidateDelayed((showTime * lineNumber).toLong())
        }
    }


    companion object {
        const val UNIDIRECTION = 0
        const val TWOWAY = 1
        const val CLAMP = 0
        const val REPEAT = 1
        const val MIRROR = 2
    }
}
