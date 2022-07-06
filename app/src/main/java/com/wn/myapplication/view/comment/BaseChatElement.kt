package com.wn.myapplication.view.comment

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.text.style.CharacterStyle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.wn.myapplication.util.dp

abstract class BaseChatElement {
    //渲染element的textview
    private var renderTextView: TextView? = null
    protected var ctx: Context? = null
    private var renderTag: String? = null

    //权重，数值越大，越靠前
    var weight : Int = 0
    // 消息前/后缀type
    var type: Int = 0

    var elementClickListener: View.OnClickListener? = null
        protected set

    fun init(renderTextView: TextView) {
        this.renderTextView = renderTextView
        this.ctx = renderTextView.context
    }

    fun getPlaceholderTag(): String {
        if(TextUtils.isEmpty(renderTag)) {
            renderTag = hashCode().toString()
        }
        return renderTag!!
    }

    open fun getMarinEnd(): Int = 4.dp

    open fun getRenderMode(): ChatElementRenderMode = ChatElementRenderMode.VIEW_MODE

    abstract fun getView(inflater: LayoutInflater): View

    /**
     * 图片下载完成，ui更新，需要调用此方法
     */
    protected fun refresh() {
        renderTextView?.postInvalidate()
    }

    protected fun loadImage(view: View,url: String){
        Glide.with(view.context).load(url).into(object :
            SimpleTarget<Drawable>() {
            override fun onResourceReady(
                resource: Drawable,
                transition: Transition<in Drawable>?
            ) {
                view.background = resource
                refresh()
            }
        })
    }
}