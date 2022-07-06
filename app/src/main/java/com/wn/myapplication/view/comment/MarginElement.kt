package com.wn.myapplication.view.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MarginElement(var endMargin: Int): BaseChatElement() {
    override fun getView(inflater: LayoutInflater): View {
        return View(ctx).apply {
            layoutParams = ViewGroup.LayoutParams(endMargin,ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    override fun getRenderMode(): ChatElementRenderMode {
        return ChatElementRenderMode.MARGIN_MODE
    }
}