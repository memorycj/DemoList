package com.wn.myapplication.view.comment

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.wn.myapplication.R
import com.wn.myapplication.span.LongClickableSpan
import com.wn.myapplication.span.TextSpan
import com.wn.myapplication.span.ViewSpan
import com.wn.myapplication.util.KtvNameLongClickLinkMovementMethod
import com.wn.myapplication.util.StringUtils
import com.wn.myapplication.util.dp
import com.wn.myapplication.util.toast
import java.util.*
import java.util.regex.Pattern

/**
 * 公屏聊天view
 * 格式： 消息前缀view+昵称view+公屏消息view
 */
class ChatCommentTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {
    var nickNameColor: Int =  R.color.C_9AE0FF
    var nickNameSize: Int = 12
    private var prefixElement: List<BaseChatElement>? = null
    private var suffixElement: List<BaseChatElement>? = null
    private val comparator: Comparator<BaseChatElement> =
        Comparator<BaseChatElement> { o1: BaseChatElement, o2: BaseChatElement -> o2.weight - o1.weight }
    private val inflater by lazy {
        LayoutInflater.from(context)
    }
    init {
        movementMethod = KtvNameLongClickLinkMovementMethod.getInstance()
    }
    fun setPrefixElement(prefixElement: List<BaseChatElement>){
        this.prefixElement = prefixElement
        this.prefixElement?.let {
            Collections.sort(it,comparator)
        }
    }

    fun setSuffixElement(prefixElement: List<BaseChatElement>){
        this.suffixElement = prefixElement
        this.suffixElement?.let {
            Collections.sort(it,comparator)
        }
    }

    fun render(nickName: String, msgContent: CharSequence) {
        val spanStr = SpannableStringBuilder()
        val tagSets: Queue<BaseChatElement> = LinkedList()
        prefixElement?.forEach {
            tagSets.offer(it)
            spanStr.append(it.getPlaceholderTag())
            //添加margin end
            if(it.getMarinEnd() > 0) {
                val marginElement = MarginElement(it.getMarinEnd())
                tagSets.add(marginElement)
                spanStr.append(marginElement.getPlaceholderTag())
            }
        }
        spanStr.append(nickName)
        //昵称后添加margin
        MarginElement(4.dp).let {
            tagSets.add(it)
            spanStr.append(it.getPlaceholderTag())
        }
        //消息后缀
        suffixElement?.forEach {
            tagSets.offer(it)
            spanStr.append(it.getPlaceholderTag())
            //添加margin end
            if(it.getMarinEnd() > 0) {
                val suffixMarinEnd = MarginElement(it.getMarinEnd())
                tagSets.add(suffixMarinEnd)
                spanStr.append(suffixMarinEnd.getPlaceholderTag())
            }
        }
        spanStr.append(msgContent)
        assembleElement(tagSets,spanStr)
        StringUtils.setClickSpan(spanStr, nickName ,object : LongClickableSpan() {
            override fun onClick(widget: View) {
                toast("nick click")
            }

            override fun onLongClick(view: View?) {
                toast("Long click")
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }

        })
        StringUtils.setTextSize(spanStr,nickName,nickNameSize)
        spanStr.setSpan(TextSpan(this,paint.measureText(nickName)),spanStr.indexOf(nickName),spanStr.indexOf(nickName) + nickName.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        text = spanStr
    }

    private fun assembleElement(elements: Queue<BaseChatElement>,spanStr: SpannableStringBuilder) {
        while (elements.size > 0) {
            val element = elements.poll()
            element?.let {
                element.init(this)
                val elementTag = element.getPlaceholderTag()
                val pattern = Pattern.compile(elementTag)
                val matcher = pattern.matcher(spanStr)
                while (matcher.find()) {
                    spanStr.setSpan(createSpanByRenderMode(element.getRenderMode(),element.getView(inflater)), matcher.start(), matcher.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    if(element.elementClickListener != null){
                        spanStr.setSpan(
                            object : LongClickableSpan() {
                                override fun onClick(widget: View) {
                                    element.elementClickListener?.onClick(widget)
                                }

                                override fun onLongClick(view: View?) {
                                    toast("onLongClick")
                                }

                            }, matcher.start(), matcher.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                }
            }
        }
    }

    private fun createSpanByRenderMode(renderMode: ChatElementRenderMode, view: View): Any {
        return when(renderMode){
            ChatElementRenderMode.VIEW_MODE ->{
                ViewSpan(view)
            }
            ChatElementRenderMode.MARGIN_MODE ->{
                ViewSpan(view)
            }
            else -> {
                ViewSpan(view)
            }
        }
    }
}