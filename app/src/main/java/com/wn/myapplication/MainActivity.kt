package com.wn.myapplication

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.wn.myapplication.databinding.ActivityMainBinding
import com.wn.myapplication.span.TextSpan
import com.wn.myapplication.view.comment.BaseChatElement
import com.wn.myapplication.view.comment.IconChatElement
import com.wn.myapplication.view.comment.UserLevelChatElement

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
//        val test = "hhahahahahhahahahahhahahahhahahahahhahahahahhahahahhahahahahhahahahahhahahahhahahahahhahahahahhahahahhahahahahhahahahahhahaha"
//        val spanStr = SpannableStringBuilder()
//        spanStr.append("prefix---")
//        spanStr.append(test)
//        spanStr.append("--Wang")
//        val measureText = binding.chatComment.paint.measureText(test)
//        spanStr.setSpan(TextSpan(binding.chatComment,measureText),spanStr.indexOf(test),test.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        binding.chatComment.text = spanStr
//
//        binding.spanText.baseColor = Color.BLUE
//        binding.spanText.lightColor = Color.YELLOW
//        binding.spanText.text = "你好啊啊啊啊,你好啊啊啊啊你好啊啊啊啊你好啊啊啊啊你好啊啊啊啊你好啊啊啊啊你好啊啊啊啊你好啊啊啊啊"
//        binding.spanText.hasColorAnimation = true

        val list = mutableListOf<BaseChatElement>()
        list.add(IconChatElement("https://img0.baidu.com/it/u=512340543,3139277133&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281").apply {
           weight = -1
        })
        list.add(IconChatElement("https://img0.baidu.com/it/u=512340543,3139277133&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281").apply {
            weight = -1
        })
//        list.add(IconChatElement("https://img0.baidu.com/it/u=512340543,3139277133&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281").apply {
//            weight = -1
//        })
//        list.add(IconChatElement("https://img0.baidu.com/it/u=512340543,3139277133&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281").apply {
//            weight = -1
//        })


        val suffixList = mutableListOf<BaseChatElement>()
        suffixList.add(IconChatElement("https://img0.baidu.com/it/u=512340543,3139277133&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281").apply {
            weight = -1
        })
        suffixList.add(UserLevelChatElement("https://i.bmp.ovh/imgs/2022/06/21/e9202faee9e66918.png"))
        suffixList.add(UserLevelChatElement("https://i.bmp.ovh/imgs/2022/06/21/e9202faee9e66918.png").apply {
            weight = 2
        })
        binding.chatComment.nickNameColor = R.color.C_9AE0FF
        binding.chatComment.apply {
            nickNameColor = R.color.C_9AE0FF
            setPrefixElement(list)
            setSuffixElement(suffixList)
        }.render("nikeNamebbbbbb","shi is he,sslklkl klklkl klk lklkl shi is he,sslklkl klklkl klk lklkl lklkshi is he,sslklkl klklkl klk lklkl lklkshi is he,sslklkl klklkl klk lklkl lklkshi is he,sslklkl klklkl klk lklkl lklkshi is he,sslklkl klklkl klk lklkl lklkshi is he,sslklkl klklkl klk lklkl lklkshi is he,sslklkl klklkl klhi ")
//        val spanTextView = binding.chatComment
//        val tag = "tag_tag"
//        val linearGradinet = "l_grad"
//        val imgTag = "img_flag"
//        val icon_text = "icon_text"
//        val text = "normal textView $tag, linearGradinetText $linearGradinet, dynamic Image $imgTag, icon and Text $icon_text icon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Texticon and Text"
//        val span = SpannableStringBuilder(text)


//        val compile = Pattern.compile(tag)
//        val matcher = compile.matcher(text)
//        val layoutInflater = LayoutInflater.from(this)
//        while (matcher.find()) {
//            val tagView = layoutInflater.inflate(R.layout.tag_view, null, false)
//            val findViewById = tagView.findViewById<TextView>(R.id.tag)
//            findViewById.text = "s: ${matcher.start()},end: ${matcher.end()},s: ${matcher.start()},end: ${matcher.end()}s: ${matcher.start()},end: ${matcher.end()}s: ${matcher.start()},end: ${matcher.end()}s: ${matcher.start()},end: ${matcher.end()}s: ${matcher.start()},end: ${matcher.end()}s: ${matcher.start()},end: ${matcher.end()}s: ${matcher.start()},end: ${matcher.end()}s: ${matcher.start()},end: ${matcher.end()}s: ${matcher.start()},end: ${matcher.end()}s: ${matcher.start()},end: ${matcher.end()}s: ${matcher.start()},end: ${matcher.end()}s: ${matcher.start()},end: ${matcher.end()}s: ${matcher.start()},end: ${matcher.end()}"
//            span.setSpan(
//                ViewSpan(tagView),
//                matcher.start(),
//                matcher.end(),
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//        }



//        val lCompile = Pattern.compile(linearGradinet)
//        val lMatcher = lCompile.matcher(text)
//        while (lMatcher.find()) {
//            val tagView = layoutInflater.inflate(R.layout.linear_gradinet, null, false)
//            val findViewById = tagView.findViewById<LinearGradientTextView>(R.id.span_text)
//            findViewById.text = "linearColorText"
//            findViewById.baseColor = Color.RED
//            findViewById.lightColor = Color.GREEN
//            findViewById.hasColorAnimation = true
//            span.setSpan(
//                LinearGradientSpan(findViewById,spanTextView),
//                lMatcher.start(),
//                lMatcher.end(),
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//        }
//
//
//        val imgcompile = Pattern.compile(imgTag)
//        val matcherImg = imgcompile.matcher(text)
//        var flag = false
//        while (matcherImg.find()) {
//            var url = "https://img0.baidu.com/it/u=1149498394,1442276907&fm=253&fmt=auto&app=120&f=JPEG?w=1000&h=500"
//            if(flag){
//                url = "https://cdn.pixabay.com/photo/2016/12/23/12/40/night-1927265__480.jpg"
//            }
//            flag = !flag
//            span.setSpan(
//                GlideImageSpan(spanTextView,url).setDrawableSize(20.dp,20.dp),
//                matcherImg.start(),
//                matcherImg.end(),
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//        }
//
//
//        val iconTextCompile = Pattern.compile(icon_text)
//        val matcherIconText = iconTextCompile.matcher(text)
//        while (matcherIconText.find()) {
//            val view = layoutInflater.inflate(R.layout.img_view, null, false)
//
//            val iconView = view.findViewById<ImageView>(R.id.icon)
//            Glide.with(this).load("https://img0.baidu.com/it/u=512340543,3139277133&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281").into(object :
//                SimpleTarget<Drawable>() {
//                override fun onResourceReady(
//                    resource: Drawable,
//                    transition: Transition<Drawable>?
//                ) {
//                    iconView.setImageDrawable(resource)
//                    spanTextView.invalidate()
//                }
//
//            })
//            span.setSpan(
//                ViewSpan(view),
//                matcherIconText.start(),
//                matcherIconText.end(),
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//        }
//        spanTextView.text = span
    }
}