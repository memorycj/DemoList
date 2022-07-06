package com.wn.myapplication.view.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.wn.myapplication.util.dp
import com.wn.myapplication.util.toast

class IconChatElement(private var url: String) : BaseChatElement() {
    init {
        elementClickListener = View.OnClickListener {
            toast("click icon")
        }
    }

    override fun getView(inflater: LayoutInflater): View {
        val img =  ImageView(ctx).apply {
            scaleType = ImageView.ScaleType.CENTER_INSIDE
            layoutParams = ViewGroup.LayoutParams(34.dp,14.dp)
        }
        loadImage(img,url)
        return img
    }
}