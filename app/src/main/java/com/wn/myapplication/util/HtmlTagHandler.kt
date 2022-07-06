package com.wn.myapplication.util

import android.graphics.drawable.Drawable
import android.os.Build
import android.text.*
import android.text.style.ClickableSpan
import android.view.View
import com.bumptech.glide.Glide
import com.wn.myapplication.App
import com.wn.myapplication.R
import com.wn.myapplication.span.GlideImageSpan
import com.wn.myapplication.view.comment.ChatCommentTextView
import org.xml.sax.XMLReader

object HtmlTagHandler {

    const val LOCAL_RES = "res/"
    const val CLICKABLE_TAG = "clickable"
    const val IMG = "smImg"

    internal fun parseHtml(
        text: String?,
        fromUid: Long?,
        fromNickName: String?,
        chatComment: ChatCommentTextView
    ): Spanned{
        val span = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Html.fromHtml(text
                ?: "", Html.FROM_HTML_MODE_LEGACY, null, TagHandler(fromUid, fromNickName,chatComment))

        } else {
            Html.fromHtml(text,null, TagHandler(fromUid, fromNickName,chatComment))
        }
        return span
    }

    private class IconImageGetter(var chatComment: ChatCommentTextView) : Html.ImageGetter {
        override fun getDrawable(source: String?): Drawable? {
            val iconSize = DensityUtils.dip2px(12f)

            var drawable: Drawable? = null

            try {
                when {
                    TextUtils.isEmpty(source) -> drawable = getDefaultIcon()

                    source!!.startsWith(LOCAL_RES) -> source.let {
                        val drawableResId = it.substring(it.indexOf("/") + 1, it.length).toInt()
                        drawable = ResourceUtils.getDrawable(drawableResId)
                    }

                    else -> {
                        drawable = Glide.with(App.INSTANCE)
                            .asDrawable()
                            .load(source)
                            .submit(iconSize, iconSize).get()
                    }
                }

            } catch (exception: Exception) {
                drawable = getDefaultIcon()
            } catch (error: OutOfMemoryError) {
                drawable = getDefaultIcon()
            }

            drawable?.setBounds(0, 0, iconSize, iconSize)
            return drawable
        }

        private fun getDefaultIcon(): Drawable {
            return ResourceUtils.getDrawable(R.mipmap.ic_launcher)
        }
    }

    private class TagHandler(
        val fromUid: Long?,
        val fromNickName: String?,
        val chatComment: ChatCommentTextView
    ) : Html.TagHandler {

        var startTag: Int = 0
        var endTag: Int = 0

        override fun handleTag(opening: Boolean, tag: String?, output: Editable?, xmlReader: XMLReader?) {
            if (TextUtils.equals(tag, CLICKABLE_TAG)) {
                if (opening) {
                    startTag = output!!.length
                } else {
                    endTag = output!!.length
                    output.setSpan(UserNameClickSpan(fromUid, fromNickName), startTag, endTag, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            } else if(TextUtils.equals(tag, IMG)){
                if (opening) {
                    startTag = output!!.length
                } else {
                    endTag = output!!.length
                    output.setSpan(GlideImageSpan(chatComment,"https://img0.baidu.com/it/u=512340543,3139277133&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281"), startTag, endTag, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
        }
    }

    private class UserNameClickSpan(val fromUid: Long?, val fromNickName: String?) : ClickableSpan(), View.OnClickListener {
        override fun onClick(view: View) {

        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
        }
    }
}