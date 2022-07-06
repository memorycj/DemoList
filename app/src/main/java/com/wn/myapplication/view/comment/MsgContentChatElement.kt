package com.wn.myapplication.view.comment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.wn.myapplication.util.dp
import com.wn.myapplication.util.sp
import com.wn.myapplication.util.toast

class MsgContentChatElement(var msgContent: String) : BaseChatElement() {


    override fun getView(inflater: LayoutInflater): View {
        val tv =  TextView(ctx).apply {
            textSize = 12f
            setTextColor(Color.WHITE)
            text = msgContent
        }
        return tv
    }
}