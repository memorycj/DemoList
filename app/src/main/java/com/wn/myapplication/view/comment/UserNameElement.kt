package com.wn.myapplication.view.comment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

class UserNameElement: BaseChatElement() {
    override fun getView(inflater: LayoutInflater): View {
        return TextView(ctx).apply {
            textSize = 12f
            text = "wangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangnengwangneng"
            setTextColor(Color.RED)
        }
    }
}