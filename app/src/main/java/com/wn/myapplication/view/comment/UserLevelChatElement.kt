package com.wn.myapplication.view.comment

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.wn.myapplication.R
import com.wn.myapplication.util.ResourceUtils
import com.wn.myapplication.util.dp
import com.wn.myapplication.util.toast

class UserLevelChatElement(val bgUrl: String? = null) : BaseChatElement() {
    init {
        elementClickListener = View.OnClickListener { toast("UserLevelChatElement click") }
    }

    override fun getView(inflater: LayoutInflater): View {
        val inflate = inflater.inflate(R.layout.tag_view, null, false)
        val findViewById = inflate.findViewById<TextView>(R.id.tag)
        val drawable = ResourceUtils.getDrawable(R.mipmap.ic_launcher)
        drawable.setBounds(0,0,20.dp,20.dp)
        findViewById.setCompoundDrawablesRelative(drawable,null, null, null)
        val root = inflate.findViewById<View>(R.id.root)
        val icon = inflate.findViewById<View>(R.id.icon)
        findViewById.text = "3611"
//        loadImage(icon,"https://img0.baidu.com/it/u=512340543,3139277133&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281")
//        if (bgUrl != null) {
//            loadImage(root, bgUrl)
//        }
        return inflate
    }
}